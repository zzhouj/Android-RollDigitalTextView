package com.coco.rolldigitaltextview.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class RollDigitalTextViewTestActivity extends Activity {

	// private RollDigitalTextView mRollDigitalTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.roll_digital_text_view_test);

		// mRollDigitalTextView = (RollDigitalTextView) findViewById(R.id.roll_digital_text);
	}

}
