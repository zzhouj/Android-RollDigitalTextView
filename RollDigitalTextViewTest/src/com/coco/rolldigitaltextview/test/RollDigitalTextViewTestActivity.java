package com.coco.rolldigitaltextview.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.coco.rolldigitaltextview.RollDigitalTextView;

public class RollDigitalTextViewTestActivity extends Activity implements OnClickListener {

	private RollDigitalTextView mRollDigitalTextView;
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
		mDigitalEdit = (EditText) findViewById(R.id.digital_edit);

		mTypeIntegerRadio = (RadioButton) findViewById(R.id.type_integer_radio);
		mTypeCurrencyRadio = (RadioButton) findViewById(R.id.type_currency_radio);
		if (mRollDigitalTextView.getDigitalType() == RollDigitalTextView.DIGITAL_TYPE_INTEGER) {
			mTypeIntegerRadio.setChecked(true);
		} else if (mRollDigitalTextView.getDigitalType() == RollDigitalTextView.DIGITAL_TYPE_CURRENCY) {
			mTypeCurrencyRadio.setChecked(true);
		}

		mIncreaseButton = (Button) findViewById(R.id.increase_button);
		mDecreaseButton = (Button) findViewById(R.id.decrease_button);
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

}
