package com.coco.rolldigitaltextview;

/*
 The MIT License (MIT)

 Copyright (c) 2014 justin

 Permission is hereby granted, free of charge, to any person obtaining a copy of
 this software and associated documentation files (the "Software"), to deal in
 the Software without restriction, including without limitation the rights to
 use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 the Software, and to permit persons to whom the Software is furnished to do so,
 subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;
import android.widget.TextView;

/**
 * Android roll digital text view, digital will rolling from 0 to actual value (increase), or vice versa (decrease).
 */
public class RollDigitalTextView extends TextView {
	private static final String TAG = "RollDigitalTextView";
	private static final boolean DEBUG = false;

	private static void DEBUG_LOG(String msg) {
		if (DEBUG) {
			Log.v(TAG, msg);
		}
	}

	public static final int DIGITAL_TYPE_INTEGER = 0; // 1200
	public static final int DIGITAL_TYPE_CURRENCY = 1; // 1,200.00

	private static final int MIN_ROLLING_DURATION = 600; // ms
	private static final int DEFAULT_ROLLING_DURATION = 2000; // ms

	private static final int MAX_VALUE = 97;

	// options
	private int mDigitalType = DIGITAL_TYPE_INTEGER;
	private long mRollingDuration = DEFAULT_ROLLING_DURATION;

	// original state
	private CharSequence mOriginalText;
	private BufferType mOriginalTextType;
	private double mOriginalDigital;

	// rolling
	private double mCurrentDigital;
	private Scroller mScroller;

	private boolean mLastCompleteShown;

	public RollDigitalTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initRollDigitalTextView();
	}

	public RollDigitalTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initRollDigitalTextView();
	}

	public RollDigitalTextView(Context context) {
		super(context);
		initRollDigitalTextView();
	}

	private void initRollDigitalTextView() {
		final Context context = getContext();
		mScroller = new Scroller(context, new LinearInterpolator());
	}

	public int getDigitalType() {
		return mDigitalType;
	}

	public void setDigitalType(int digitalType) {
		if (digitalType == DIGITAL_TYPE_INTEGER
				|| digitalType == DIGITAL_TYPE_CURRENCY) {
			mDigitalType = digitalType;
		}
	}

	public long getRollingDuration() {
		return mRollingDuration;
	}

	public void setRollingDuration(long rollingDuration) {
		if (rollingDuration < MIN_ROLLING_DURATION) {
			rollingDuration = MIN_ROLLING_DURATION;
		}
		mRollingDuration = rollingDuration;
	}

	@Override
	public void setText(CharSequence text, BufferType type) {
		super.setText(text, type);
		saveOriginalText(text, type);
	}

	private void saveOriginalText(CharSequence text, BufferType type) {
		mOriginalText = text;
		mOriginalTextType = type;
		try {
			mOriginalDigital = Double.parseDouble(mOriginalText.toString());
		} catch (NumberFormatException e) {
			e.printStackTrace();
			mOriginalDigital = 0f;
		}
	}

	public void startRolling(boolean increase) {
		if (mOriginalDigital > 0) {
			int sx;
			int dx;
			if (increase) {
				sx = 0;
				dx = MAX_VALUE;
			} else {
				sx = MAX_VALUE;
				dx = -MAX_VALUE;
			}
			mScroller.abortAnimation();
			mScroller.startScroll(sx, 0, dx, 0, (int) (mRollingDuration));
			ViewCompat.postInvalidateOnAnimation(this);
		}
	}

	@Override
	public void computeScroll() {
		if (mOriginalDigital > 0) {
			if (!mScroller.isFinished() && mScroller.computeScrollOffset()) {
				final int scrollX = Math.max(0, Math.min(MAX_VALUE, Math.abs(mScroller.getCurrX())));
				mCurrentDigital = scrollX * (mOriginalDigital / MAX_VALUE);
				DEBUG_LOG("computeScroll mCurrentDigital=" + mCurrentDigital);
				super.setText(formatDigital(mCurrentDigital), mOriginalTextType);

				// Keep on drawing until the animation has finished.
				ViewCompat.postInvalidateOnAnimation(this);
				return;
			}
			// Done with scroll, clean up state.
			// do nothing
		}
	}

	private CharSequence formatDigital(double digital) {
		if (getDigitalType() == DIGITAL_TYPE_INTEGER) {
			return String.valueOf((int) digital);
		} else if (getDigitalType() == DIGITAL_TYPE_CURRENCY) {
			return formatCurrency(digital);
		}
		return null;
	}

	private CharSequence formatCurrency(double digital) {
		StringBuffer sb = new StringBuffer();
		// sign
		sb.append(digital < 0 ? "-" : "");
		digital = Math.abs(digital);
		// integer part
		String integerPart = String.valueOf((int) digital);
		sb.append(integerPart);
		int commaCount = (integerPart.length() + 2) / 3;
		if (commaCount > 0) {
			commaCount -= 1;
		}
		for (int i = 0; i < commaCount; i++) {
			sb.insert(sb.length() - (i * 4 + 3), ',');
		}
		// point part
		sb.append(String.format(".%02d", (long) (digital * 100) % 100));
		return sb.toString();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		final boolean isCompleteShown = isCompleteShown();
		if (mLastCompleteShown != isCompleteShown) {
			mLastCompleteShown = isCompleteShown;
			post(new Runnable() {
				@Override
				public void run() {
					startRolling(mLastCompleteShown);
				}
			});
		}
	}

	private boolean isCompleteShown() {
		if (isShown()) {
			final Rect visibleRect = new Rect();
			if (getLocalVisibleRect(visibleRect)
					&& visibleRect.width() == getWidth()
					&& visibleRect.height() == getHeight()) {
				return true;
			}
		}
		return false;
	}

}
