package com.paysys.indMojaloopCustomer.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.fragment.app.DialogFragment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RegistrationDateSelectionDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    public final Calendar calendar = Calendar.getInstance();
    public String mSelectedDate="";

    private DateSelectedCallback callBack;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePicker = new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog,
                this, yy, mm, dd);
        datePicker.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePicker.setTitle("");
        datePicker.getDatePicker().setSpinnersShown(true);
        return datePicker;
    }

    public void onDateSet(DatePicker view, int yy, int mm, int dd) {
        if(view.isShown())
            populateSetDate(yy, mm + 1, dd);
    }

    public void populateSetDate(int year, int month, int day) {

        mSelectedDate = "";

        /*mSelectedDate += year + "/";

        if (month < 10) mSelectedDate += "0" + month + "/";
        else mSelectedDate += "" + month + "/";

        if (day < 10) mSelectedDate += "0" + day;
        else mSelectedDate += "" + day+"/" ;


        mSelectedDate = "";

        if (month < 10) mSelectedDate += "0" + month + "/";
        else mSelectedDate += "" + month + "/";


        if (day < 10) mSelectedDate += "0" + day;
        else mSelectedDate += "" + day + "/" ;

        mSelectedDate += year;


        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);

        Log.d("mSelectedDate = " + mSelectedDate);

        callBack.dateSelected(mSelectedDate);*/

        String input_date = day+"/"+month+"/"+year;
        SimpleDateFormat format1=new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date dt1=format1.parse(input_date);
            DateFormat format2=new SimpleDateFormat("MM/dd/yyyy");
            mSelectedDate = format2.format(dt1);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        callBack.dateSelected(mSelectedDate);
    }

    public void setCallback(DateSelectedCallback callback){

        this.callBack = callback;
    }

    public interface DateSelectedCallback {

        public void dateSelected(String date);
    }
}
