package com.delombaertdamien.mareu.controller.Fragment;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TimePicker;

import androidx.fragment.app.DialogFragment;

import com.delombaertdamien.mareu.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TimePickerFragment extends DialogFragment {

    @BindView(R.id.configure_activity_button_set_start_clock)
    Button mButtonStartClock;

    SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.getDefault());

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        ButterKnife.bind(getActivity());

        Calendar mCalendar = (Calendar) format.getCalendar().clone();
        int hour = mCalendar.get(Calendar.HOUR_OF_DAY);
        int min = mCalendar.get(Calendar.MINUTE);

        Log.d("TimePickerDialog", "heure du timePicker : " + hour + " : " + min);

        TimePickerDialog timePicker = new TimePickerDialog(getActivity(), (TimePickerDialog.OnTimeSetListener) getActivity(), hour , min, true );

        return timePicker;
    }


}
