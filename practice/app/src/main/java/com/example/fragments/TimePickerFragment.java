package com.example.fragments;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.TimePicker;

import androidx.fragment.app.DialogFragment;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.function.Consumer;

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    private Consumer<LocalDateTime> timeConsumer;

    public TimePickerFragment(Consumer<LocalDateTime> timeConsumer) {
        this.timeConsumer = timeConsumer;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker.
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        Log.i("TimePickerFragment", "Time set: " + timePicker.getHour() + ":" + timePicker.getMinute());

        LocalDateTime time = LocalDateTime.now()
                    .withHour(timePicker.getHour())
                    .withMinute(timePicker.getMinute());

        timeConsumer.accept(time);
    }
}
