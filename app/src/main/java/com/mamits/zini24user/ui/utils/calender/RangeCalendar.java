package com.mamits.zini24user.ui.utils.calender;

import androidx.core.util.Pair;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.CompositeDateValidator;
import com.google.android.material.datepicker.DateValidatorPointBackward;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.util.ArrayList;
import java.util.Calendar;

public class RangeCalendar {
    private FragmentManager fragmentManager;
    private onRangeDateSelectedListener listener;

    public RangeCalendar(FragmentManager fragmentManager, onRangeDateSelectedListener listener) {
        this.fragmentManager = fragmentManager;
        this.listener = listener;
    }

    public void showRangeCalender() {
        MaterialDatePicker.Builder<Pair<Long, Long>> builderRange = MaterialDatePicker.Builder.dateRangePicker();
        CalendarConstraints.Builder constraintsBuilderRange = new CalendarConstraints.Builder();

        Calendar fromCal = Calendar.getInstance();
        Calendar toCal = Calendar.getInstance();
        toCal.add(Calendar.MONTH, 1);

        CalendarConstraints.DateValidator dateValidatorMin = DateValidatorPointForward.from(fromCal.getTimeInMillis());
        CalendarConstraints.DateValidator dateValidatorMax = DateValidatorPointBackward.before(toCal.getTimeInMillis());

        ArrayList<CalendarConstraints.DateValidator> listValidators =
                new ArrayList<>();
        listValidators.add(dateValidatorMin);
        listValidators.add(dateValidatorMax);
        CalendarConstraints.DateValidator validators = CompositeDateValidator.allOf(listValidators);
        constraintsBuilderRange.setValidator(validators);

        builderRange.setCalendarConstraints(constraintsBuilderRange.build());
        MaterialDatePicker<Pair<Long, Long>> pickerRange = builderRange.build();

        pickerRange.show(fragmentManager, pickerRange.toString());
        pickerRange.addOnPositiveButtonClickListener(selection -> {
            listener.onDateSelected(selection);
        });
    }

    public interface onRangeDateSelectedListener {
        void onDateSelected(Pair<Long, Long> selection);
    }
}
