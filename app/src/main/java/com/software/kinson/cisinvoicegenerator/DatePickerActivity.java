package com.software.kinson.cisinvoicegenerator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CalendarView;

public class DatePickerActivity extends AppCompatActivity {
    private static String TAG = "Calander Activity";
    private CalendarView calendarView;
    //DateFormat.getDateInstance(DateFormat.LONG).format(date);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker);

        calendarView = findViewById(R.id.cvCalander);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                //DateFormat.getDateInstance(DateFormat.LONG).format(date);
                Log.d(TAG, "onSelectedDayChanged : date: " + date);
                if (Globals.DateIntent == "Invoice2") {
                    Intent intent = new Intent(DatePickerActivity.this, InvoiceActivity2.class);
                    intent.putExtra("date", date);
                    finish();
                    Globals.DateIntent = "update";
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(DatePickerActivity.this, InvoiceActivity.class);
                    intent.putExtra("date", date);
                    finish();
                    startActivity(intent);
                }

            }
        });
    }

}
