package com.coco.rolldigitaltextview.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.coco.rolldigitaltextview.RollDigitalTextView;

public class RollDigitalTextViewTestActivity extends Activity implements OnClickListener, OnSeekBarChangeListener {

	private RollDigitalTextView mRollDigitalTextView;

	private TextView mRollingDurationText;
	private SeekBar mRollingDurationSeek;

	private EditText mDigitalEdit;

	private RadioButton mTypeIntegerRadio;
	private RadioButton mTypeCurrencyRadio;

	private Button mIncreaseButton;
	private Button mDecreaseButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.roll_digital_text_view_test);

		mRollDigitalTextView = (RollDigitalTextView) findViewById(R.id.roll_digital_text);
		mRollingDurationText = (TextView) findViewById(R.id.rolling_duration_text);
		mRollingDurationSeek = (SeekBar) findViewById(R.id.rolling_duration_seek);
		mDigitalEdit = (EditText) findViewById(R.id.digital_edit);
		mTypeIntegerRadio = (RadioButton) findViewById(R.id.type_integer_radio);
		mTypeCurrencyRadio = (RadioButton) findViewById(R.id.type_currency_radio);
		mIncreaseButton = (Button) findViewById(R.id.increase_button);
		mDecreaseButton = (Button) findViewById(R.id.decrease_button);

		mRollingDurationText.setText(String.valueOf(mRollDigitalTextView.getRollingDuration()));
		mRollingDurationSeek.setProgress((int) mRollDigitalTextView.getRollingDuration());
		mRollingDurationSeek.setOnSeekBarChangeListener(this);
		if (mRollDigitalTextView.getDigitalType() == RollDigitalTextView.DIGITAL_TYPE_INTEGER) {
			mTypeIntegerRadio.setChecked(true);
		} else if (mRollDigitalTextView.getDigitalType() == RollDigitalTextView.DIGITAL_TYPE_CURRENCY) {
			mTypeCurrencyRadio.setChecked(true);
		}
		mIncreaseButton.setOnClickListener(this);
		mDecreaseButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		final String digital = mDigitalEdit.getText().toString();
		final int digitalType = mTypeIntegerRadio.isChecked() ? RollDigitalTextView.DIGITAL_TYPE_INTEGER
				: RollDigitalTextView.DIGITAL_TYPE_CURRENCY;
		if (v == mIncreaseButton) {
			mRollDigitalTextView.setText(digital);
			mRollDigitalTextView.setDigitalType(digitalType);
			mRollDigitalTextView.startRolling(true);
		} else if (v == mDecreaseButton) {
			mRollDigitalTextView.setText(digital);
			mRollDigitalTextView.setDigitalType(digitalType);
			mRollDigitalTextView.startRolling(false);
		}
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		if (fromUser) {
			if (seekBar == mRollingDurationSeek) {
				mRollDigitalTextView.setRollingDuration(progress);
				mRollingDurationText.setText(String.valueOf(mRollDigitalTextView.getRollingDuration()));
			}
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// do nothing
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// do nothing
	}

}
