package lib;

import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.Button;
import android.widget.DatePicker;

@SuppressLint("ValidFragment")
public class DatePickerFragment extends DialogFragment implements
		DatePickerDialog.OnDateSetListener {
	
	Button buttonDatePicker;

	public DatePickerFragment(Button buttonDatePicker) {
		this.buttonDatePicker = buttonDatePicker;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current date as the default date in the picker
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);

		// Create a new instance of DatePickerDialog and return it
		return new DatePickerDialog(getActivity(), this, year, month, day);
	}

	public void onDateSet(DatePicker view, int year, int month, int day) {
		String dd = "" + day, mm = "" + month;
		if(day < 10) {
			dd = "0" + day;
		}
		if(month < 10) {
			mm = "0" + month;
		}
		buttonDatePicker.setText(dd + "/" + mm + "/" + year);
	}
}