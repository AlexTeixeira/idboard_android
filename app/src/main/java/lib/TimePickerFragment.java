package lib;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TimePicker;



@SuppressLint("ValidFragment")
public class TimePickerFragment extends DialogFragment
implements TimePickerDialog.OnTimeSetListener {
	
	Button buttonTimePicker;

	public TimePickerFragment(Button buttonTimePicker) {
		this.buttonTimePicker = buttonTimePicker;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		// Create a new instance of TimePickerDialog and return it
		return new TimePickerDialog(getActivity(), this, 0, 0,
				true);
	}

	//Set the button's text with the selected time
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		String hh = "" + hourOfDay, mm = "" + minute;
		if(minute < 10) {
			mm = "0" + minute;
		}
		if(hourOfDay < 10) {
			hh = "0" + hourOfDay;
		}
		buttonTimePicker.setText(hh + ":" + mm);
	}
}