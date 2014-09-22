package com.alexz.donotdisturb;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by AlexZ on 01.07.14.
 */
public class DialogUtil{


    public static void choiseWiFiSpotsDialog(Activity activity, final String[] arraySpots) {

        final AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
        alertDialog.setTitle(activity.getString(R.string.dialog_get_wifi));

        View viewWiFiDialog = activity.getLayoutInflater().inflate(R.layout.wifi_spots, null);
        final ListView spots = (ListView) viewWiFiDialog.findViewById(R.id.wifi_spots_list);
        ArrayAdapter adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_multiple_choice, arraySpots);
        spots.setAdapter(adapter);
        spots.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        Set<String> trigers = QuietApp.getInstans().getTrigersWiFi();
        final int count = spots.getCount();
        for (int i = 0; i < count; i++){
            if (trigers.contains(arraySpots[i])) {
                spots.setItemChecked(i, true);
            }
        }


        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                HashSet<String> selected = new HashSet<String>();
                SparseBooleanArray checkedItems = spots.getCheckedItemPositions();
                for (int i = 0; i < count; i++){
                    if (checkedItems.get(i)) {
                        selected.add(arraySpots[i]);
                    }
                }
                QuietApp.getInstans().addTrigersWiFi(selected);
                alertDialog.dismiss();
            }
        });

        alertDialog.setView(viewWiFiDialog);
        alertDialog.setIcon(R.drawable.ic_launcher);
        alertDialog.show();
    }




    public static void choiseTimeDialog(final Activity activity) {

        final AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
        alertDialog.setTitle(activity.getString(R.string.dialog_get_wifi));

        View viewTimeDialog = activity.getLayoutInflater().inflate(R.layout.time_dialog, null);
        final Switch activated = (Switch) viewTimeDialog.findViewById(R.id.switch1);
        final TextView from = (TextView) viewTimeDialog.findViewById(R.id.time_from);
        final TextView to = (TextView) viewTimeDialog.findViewById(R.id.time_to);
        activated.setChecked(QuietApp.getInstans().getTimeEnabled());
        from.setText(QuietApp.getInstans().getTimeFrom());
        to.setText(QuietApp.getInstans().getTimeTo());

        final TimePickerDialog.OnTimeSetListener fromCallBack = new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String timeFrom = intValueToStringWithPrefix(hourOfDay) + ":"
                        + intValueToStringWithPrefix(minute);
                QuietApp.getInstans().setTimeFrom(timeFrom);
                from.setText(timeFrom);
            }
        };

        final TimePickerDialog.OnTimeSetListener toCallBack = new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String timeTo = intValueToStringWithPrefix(hourOfDay) + ":"
                        + intValueToStringWithPrefix(minute);
                QuietApp.getInstans().setTimeTo(timeTo);
                to.setText(timeTo);
            }
        };



        from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int hour = 0;
                int min = 0;
                String timeFrom = QuietApp.getInstans().getTimeFrom();
                hour = Integer.parseInt(timeFrom.substring(0, 2));
                min = Integer.parseInt(timeFrom.substring(3, 4));

                TimePickerDialog tpd = new TimePickerDialog(activity, fromCallBack, hour, min, true);
                tpd.show();
            }
        });


        to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int hour = 0;
                int min = 0;
                String timeTo = QuietApp.getInstans().getTimeTo();
                hour = Integer.parseInt(timeTo.substring(0, 2));
                min = Integer.parseInt(timeTo.substring(3, 4));

                TimePickerDialog tpd = new TimePickerDialog(activity, toCallBack, hour, min, true);
                tpd.show();
            }
        });


        alertDialog.setCancelable(false);


        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                QuietApp.getInstans().setTimeEnabled(activated.isChecked());
                if (activated.isChecked()){
                    TimeUtil.setTime(activity, ExtraUtils.TIME_FROM);
                    TimeUtil.setTime(activity, ExtraUtils.TIME_TO);
                } else {
                    TimeUtil.disableTime(activity, ExtraUtils.TIME_FROM);
                    TimeUtil.disableTime(activity, ExtraUtils.TIME_TO);
                }
                alertDialog.dismiss();
            }
        });

        alertDialog.setView(viewTimeDialog);
        alertDialog.setIcon(R.drawable.ic_launcher);
        alertDialog.show();
    }



    private static String intValueToStringWithPrefix(int value){
        String string = String.valueOf(value);
        if (string.length() == 1) return "0" + string;
        return string;
    }



}
