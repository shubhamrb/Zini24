package com.mamits.zini24user.ui.utils.calender;

import androidx.fragment.app.FragmentManager;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.CompositeDateValidator;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.mamits.zini24user.ui.customviews.CustomInputEditText;
import com.mamits.zini24user.ui.utils.DateConvertor;

import java.util.ArrayList;
import java.util.Calendar;

public class SimpleCalender {

    private FragmentManager fragmentManager;

    public SimpleCalender(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void showSimpleCalender(CustomInputEditText etFromDate) {
        MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
        CalendarConstraints.Builder constraintsBuilderRange = new CalendarConstraints.Builder();
        builder.setTitleText("SELECT A DATE");

        Calendar fromCal = Calendar.getInstance();
        builder.setSelection(fromCal.getTimeInMillis());

        CalendarConstraints.DateValidator dateValidatorMin = DateValidatorPointForward.from(fromCal.getTimeInMillis());

        ArrayList<CalendarConstraints.DateValidator> listValidators =
                new ArrayList<>();
        listValidators.add(dateValidatorMin);

        CalendarConstraints.DateValidator validators = CompositeDateValidator.allOf(listValidators);
        constraintsBuilderRange.setValidator(validators);

        builder.setCalendarConstraints(constraintsBuilderRange.build());

        final MaterialDatePicker<Long> materialDatePicker = builder.build();

        materialDatePicker.show(fragmentManager, materialDatePicker.toString());

        materialDatePicker.addOnPositiveButtonClickListener(selection -> {
            etFromDate.setText(new DateConvertor().getDate(selection, DateConvertor.FORMAT_yyyy_MM_dd));

        });
    }

}
