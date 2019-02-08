package com.software.kinson.cisinvoicegenerator;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.software.kinson.cisinvoicegenerator.Adapters.DaysAdapter;
import com.software.kinson.cisinvoicegenerator.Model.Days;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class InvoiceActivity2 extends BaseActivity implements DatePickerDialog.OnDateSetListener {
    DatabaseHelper myDb;
    EditText workDoneID, hourlyRateID;
    TextView hoursWorkedID, dateWorkedID, startTimeID, endTimeID, dayID;
    Button btnAddDay, btnSaveInvoiceID, btnExitInvoice, btnDeleteDay;
    ListView lvDays;
    TimePickerDialog timePickerDialog;
    DatePickerDialog.OnDateSetListener mdatePickerDialog;
    Calendar calendar;
    int startHour, startMinute, endHour, endMinute, totalHours, totalMinutes;
    String amPm;
    ArrayList<Days> arrayList;
    DaysAdapter  daysAdapter;
    private Context _context;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice2);
        myDb = new DatabaseHelper(InvoiceActivity2.this);
        arrayList = new ArrayList<>();
        Intent intent = getIntent();
        String date = intent.getStringExtra("date");
        //showMessage("Activity 2",InvoiceGlobals.workDescription);
        workDoneID = findViewById(R.id.etWorkDoneID);
        hourlyRateID = findViewById(R.id.etHourlyRateID);
        dateWorkedID = findViewById(R.id.tvDateWorkedID);
        startTimeID = findViewById(R.id.tvStartTimeID);
        endTimeID = findViewById(R.id.tvEndTimeID);
        hoursWorkedID = findViewById(R.id.etHoursWorkedID);
        btnAddDay = findViewById(R.id.btnAddDayID);
        btnDeleteDay = findViewById(R.id.btnDeleteDayID);
        btnSaveInvoiceID = findViewById(R.id.btnSaveInvoiceID);
        btnExitInvoice = findViewById(R.id.btnExitInvoiceID);
        lvDays = findViewById(R.id.lvDaysID);
        dateWorkedID.setText(date);
        if(InvoiceGlobals.editInvoice){
            btnSaveInvoiceID.setText("Update Invoice");
            workDoneID.setText(InvoiceGlobals.workCompleted);
            hourlyRateID.setText(String.valueOf(InvoiceGlobals.hourlyRate));
            dateWorkedID.requestFocus();
            //btnAddDay.setVisibility(View.INVISIBLE);
            loadDaysListView();
            selectDay();
            deleteDay();
        }else{
            btnSaveInvoiceID.setText("Save Invoice");
            btnAddDay.setVisibility(View.VISIBLE);
        }
        btnSaveInvoiceID.setEnabled(false);

        btnExitInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitInvoice();
            }
        });

        dateWorkedID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickDate();
            }
        });

        startTimeID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dateWorkedID.getText().toString().isEmpty())
                    pickDate();
                else
                    setStartTime();
            }
        });

        endTimeID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(dateWorkedID.getText().toString().isEmpty())
                    pickDate();
                else
                    setEndTime();
            }
        });

        dateWorkedID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if(InvoiceGlobals.editInvoice)
//                    return;
                if(!dateWorkedID.getText().toString().isEmpty())
                    setStartTime();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mdatePickerDialog = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = dayOfMonth + "/" + month + "/" + year;
                dateWorkedID.setText(date);
                InvoiceGlobals.dayOfMonth = dayOfMonth;
            }

        };


        startTimeID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!(dateWorkedID.getText().toString().isEmpty()))
                    setEndTime();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        endTimeID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                totalHours();
                //dateWorkedID.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        dateWorkedID.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                    btnSaveInvoiceID.setEnabled(false);
            }
        });

        startTimeID.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    //setEndTime();
                }
            }
        });

        endTimeID.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    btnAddDay.setEnabled(true);
                }

            }
        });

        hourlyRateID.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                    pickDate();
            }
        });

        if(Globals.DateIntent == "update"){
            startTimeID.requestFocus();
            Globals.DateIntent = "";
        }
        //pickDate();
        addDay();
        saveInvoice();
        btnAddDay.setEnabled(false);
        //totalHours();
    }

    private void exitInvoice() {
        clearInvoiceGlobals();
        Intent intent = new Intent(InvoiceActivity2.this,BaseActivity.class);
        finish();
        startActivity(intent);
    }

    public void selectDay(){
        lvDays.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tvid = view.findViewById(R.id.tvDayID);
                TextView start = view.findViewById(R.id.tvDayStartID);
                TextView end = view.findViewById(R.id.tvDayEndID);
                TextView hours = view.findViewById(R.id.tvDayTotalID);
                TextView date = view.findViewById(R.id.tvDayDateID);
                if(InvoiceGlobals.editInvoice)
                    btnAddDay.setText("Update Day");
                InvoiceGlobals.dayID = Integer.parseInt(tvid.getText().toString());
                startTimeID.setText(start.getText().toString());
                endTimeID.setText(end.getText().toString());
                hoursWorkedID.setText(hours.getText().toString());
                dateWorkedID.setText(date.getText().toString());
                dateWorkedID.requestFocus();
                arrayList = myDb.populateDays();
                daysAdapter = new DaysAdapter(InvoiceActivity2.this,arrayList);
                lvDays.setAdapter(daysAdapter);
                daysAdapter.notifyDataSetChanged();
                btnDeleteDay.setEnabled(true);
                Toast.makeText(InvoiceActivity2.this, "ID = " + InvoiceGlobals.dayID, Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void loadDaysListView(){
        //arrayList = new ArrayList<>();
        arrayList = myDb.populateDays();
        daysAdapter = new DaysAdapter(InvoiceActivity2.this,arrayList);
        lvDays.setAdapter(daysAdapter);
        daysAdapter.notifyDataSetChanged();

    }

    public void setEndTime(){
        calendar = Calendar.getInstance();
        if(InvoiceGlobals.editInvoice){
            SimpleDateFormat sdf = new SimpleDateFormat("hh.mm");
            Date date = null;
            try {
                date = sdf.parse(endTimeID.getText().toString());
            } catch (ParseException e) {
            }
            calendar = Calendar.getInstance();
            if(!endTimeID.getText().toString().isEmpty()) {
                calendar.setTime(date);
            }
            endHour = calendar.get(Calendar.HOUR_OF_DAY);

        }else if(InvoiceGlobals.endHour == 0)
            endHour = calendar.get(Calendar.HOUR_OF_DAY);
        else
            endHour = InvoiceGlobals.endHour;
//        endMinute = calendar.get(Calendar.MINUTE);

        timePickerDialog = new TimePickerDialog(InvoiceActivity2.this, 3, new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
                endHour = hourOfDay;
                endMinute = minute;
                if(endMinute <= 14){
                    endMinute = 00;
                }
                else if(endMinute > 14 && endMinute < 45){
                    endMinute = 30;
                }
                else{
                    endHour = endHour + 1;
                    endMinute = 00;
                }
                endTimeID.setText(String.format("%02d:%02d", endHour, endMinute));
                InvoiceGlobals.endHour = endHour;
                //endTimeID.setText(endHour);
            }

        }, endHour, endMinute, true);
        timePickerDialog.setTitle("Select Finish Time");
        timePickerDialog.show();
    }

    public void totalHours(){
        totalHours = 0;
        totalHours = endHour - startHour;
        totalMinutes = endMinute - startMinute;
        if(endMinute <= 14){
            endMinute = 00;
        }
        else if(endMinute > 14 && endMinute < 45){
            endMinute = 30;
        }
        else{
            endHour = endHour + 1;
            endMinute = 00;
        }
        if(totalMinutes <= 14){
            totalMinutes = 00;
        }
        else if(totalMinutes > 14 && totalMinutes < 45){
            totalMinutes = 30;
        }
        else{
            totalHours = totalHours + 1;
            totalMinutes = 00;
        }


        hoursWorkedID.setText(String.format("%02d:%02d", totalHours, totalMinutes));
        btnAddDay.setEnabled(true);
        //hoursWorkedID.setText(totalHours);
    }

    public void deleteDay(){
        btnDeleteDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deleteRows = myDb.deleteDay(String.valueOf(InvoiceGlobals.invoiceNo),dateWorkedID.getText().toString());
                if(deleteRows > 0){
                    Toast.makeText(InvoiceActivity2.this, "Data sucessfully deleted", Toast.LENGTH_SHORT).show();

                    btnDeleteDay.setEnabled(false);
                    loadDaysListView();
                    saveInvoice();
                    clearEdits();
                }
                else{
                    Toast.makeText(InvoiceActivity2.this, "Data deletetion failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void setStartTime(){
        calendar = Calendar.getInstance();
        if(InvoiceGlobals.editInvoice){
            SimpleDateFormat sdf = new SimpleDateFormat("hh.mm");
            Date date = null;
            try {
                date = sdf.parse(startTimeID.getText().toString());
            } catch (ParseException e) {
            }
            //calendar = Calendar.getInstance();
            if(!startTimeID.getText().toString().isEmpty()) {
                calendar.setTime(date);
            }
            startHour = calendar.get(Calendar.HOUR_OF_DAY);
        }
        else if(InvoiceGlobals.startHour == 0)
            startHour = calendar.get(Calendar.HOUR_OF_DAY);
        else
            startHour = InvoiceGlobals.startHour;
//        startMinute = calendar.get(Calendar.MINUTE);

        timePickerDialog = new TimePickerDialog(InvoiceActivity2.this, 3, new TimePickerDialog.OnTimeSetListener() {
            @Override
           public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
                startHour = hourOfDay;
                startMinute = minute;

                if(startMinute <= 14){
                    startMinute = 00;
                }
                else if(startMinute > 14 && startMinute < 45){
                    startMinute = 30;
                }
                else{
                    startHour = startHour + 1;
                    startMinute = 00;
                }
//                        if (hourOfDay >= 12) {
//                            amPm = "PM";
//                        } else {
//                            amPm = "AM";
//                        }
                startTimeID.setText(String.format("%02d:%02d", startHour, startMinute));
                InvoiceGlobals.startHour = startHour;
                //startTimeID.setText(startHour);
            }

        }, startHour, startMinute, true);
        timePickerDialog.setTitle("Select Start Time");
        timePickerDialog.show();
    }

    public void saveInvoice(){
        btnSaveInvoiceID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //float hourlyRate = InvoiceGlobals.hourlyRate;
                float hourlyRate = Float.parseFloat(hourlyRateID.getText().toString());
                float time = InvoiceGlobals.totalHours;
                float totalEarned = (time * hourlyRate);
                float tax = (float) (.2 * totalEarned);
                float net = (totalEarned - tax);
                String workDone = workDoneID.getText().toString();
                InvoiceGlobals.workCompleted = workDone;
                //showMessage("Time","Time = " + time);
                if(InvoiceGlobals.editInvoice){
                    addDay();
                    boolean isinserted = myDb.updateInvoice(String.valueOf(InvoiceGlobals.contractorID),
                            String.valueOf(InvoiceGlobals.invoiceNo),
                            InvoiceGlobals.invoiceDate, InvoiceGlobals.workAddress,InvoiceGlobals.workArea,
                            InvoiceGlobals.workTown, InvoiceGlobals.workCounty, InvoiceGlobals.workPostCode,
                            InvoiceGlobals.workDescription, workDoneID.getText().toString(),
                            hourlyRateID.getText().toString(),String.valueOf(totalEarned), String.valueOf(tax),
                            String.valueOf(net));


                    if (isinserted) {
                        Toast.makeText(InvoiceActivity2.this, "Invoice Data Updated", Toast.LENGTH_SHORT).show();
                        clearInvoiceGlobals();
                    }
                    else
                        Toast.makeText(InvoiceActivity2.this, "Invoice Update Failed", Toast.LENGTH_SHORT).show();
                    addDay();
                }else {
                    boolean isinserted = myDb.addInvoice(String.valueOf(InvoiceGlobals.contractorID),
                            String.valueOf(InvoiceGlobals.invoiceNo),
                            InvoiceGlobals.invoiceDate, InvoiceGlobals.workAddress, InvoiceGlobals.workArea,
                            InvoiceGlobals.workTown, InvoiceGlobals.workCounty, InvoiceGlobals.workPostCode,
                            InvoiceGlobals.workDescription, workDoneID.getText().toString(),
                            hourlyRateID.getText().toString(), String.valueOf(totalEarned), String.valueOf(tax),
                            String.valueOf(net));

                    if (isinserted) {
                        Toast.makeText(InvoiceActivity2.this, "Invoice Data Added", Toast.LENGTH_SHORT).show();
                        clearInvoiceGlobals();
                    }
                    else
                        Toast.makeText(InvoiceActivity2.this, "Invoice Data Entry Failed", Toast.LENGTH_SHORT).show();
                }
                btnSaveInvoiceID.setEnabled(false);
                Globals.DateIntent = "";
                //Update to new invoice number
                int hasNumber = myDb.isTableEmpty(DatabaseHelper.INVOICE_TABLE);
                if (hasNumber > 0)
                    Globals.invNo = myDb.invoiceNumber() - 1;
                Intent intent = new Intent(InvoiceActivity2.this,BaseActivity.class);
                finish();
                startActivity(intent);


            }
        });
    }

    public void clearInvoiceGlobals(){
        InvoiceGlobals.contractorID = 0;
        InvoiceGlobals.invoiceNo = 0;
        InvoiceGlobals.workDescription = "";
        InvoiceGlobals.workCompleted = "";
        InvoiceGlobals.workAddress = "";
        InvoiceGlobals.workArea = "";
        InvoiceGlobals.workTown = "";
        InvoiceGlobals.workCounty = "";
        InvoiceGlobals.workPostCode = "";
        InvoiceGlobals.invoiceDate = "";
        InvoiceGlobals.hourlyRate = 0;
        InvoiceGlobals.totalHours = 0;
        InvoiceGlobals.dayOfMonth = 0;
        InvoiceGlobals.startHour = 0;
        InvoiceGlobals.endHour = 0;
        InvoiceGlobals.dayID = 0;
        btnSaveInvoiceID.setText("Save Invoice");
        InvoiceGlobals.editInvoice = false;
        InvoiceGlobals.maxDay = false;
    }



    public void pickDate(){
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        //Toast.makeText(this, "Day = " + InvoiceGlobals.dayOfMonth, Toast.LENGTH_LONG).show();
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int day;
        if(InvoiceGlobals.month == 0) {
            InvoiceGlobals.month = calendar.get(Calendar.MONTH);
        }
        int year = calendar.get(Calendar.YEAR);
        int month = InvoiceGlobals.month;
        if(InvoiceGlobals.dayOfMonth == daysInMonth){
            InvoiceGlobals.month = InvoiceGlobals.month + 1;
            month = InvoiceGlobals.month;
            InvoiceGlobals.maxDay = true;
            InvoiceGlobals.dayOfMonth = 0;
            //Toast.makeText(this, "Reset dayOfMonth invdom = " + InvoiceGlobals.dayOfMonth, Toast.LENGTH_SHORT).show();
        }
        if(InvoiceGlobals.dayOfMonth < 1 && !InvoiceGlobals.maxDay) {
            day = calendar.get(Calendar.DAY_OF_MONTH);
            InvoiceGlobals.dayOfMonth = day;
            InvoiceGlobals.maxDay = false;
        }
        else {
            calendar.set(Calendar.DAY_OF_MONTH,InvoiceGlobals.dayOfMonth);
            calendar.roll(Calendar.DAY_OF_MONTH,1);
            day = InvoiceGlobals.dayOfMonth + 1;
        }


        DatePickerDialog datePickerDialog = new DatePickerDialog(InvoiceActivity2.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                mdatePickerDialog, year, month, day);

        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.setTitle("Select Day Worked");
        datePickerDialog.show();

    }
    //todo change total fields in custom_day_view to display correctly
    public void addDay(){
        btnAddDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hoursWorked = hoursWorkedID.getText().toString();
                String [] timeSplit = hoursWorked.split(":");
                float hours = Float.parseFloat(timeSplit[0]);
                float mins = Float.parseFloat(timeSplit[1]);
                float time = (mins / 60) + hours;
                InvoiceGlobals.totalHours = InvoiceGlobals.totalHours + time;
                boolean isDayUpdated;

                if(InvoiceGlobals.editInvoice){
                    Cursor cursor = myDb.dayExists(String.valueOf(InvoiceGlobals.invoiceNo),dateWorkedID.getText().toString());
                    int count = cursor.getCount();
                    showMessage("Cursor","cursor count = " + count);
                    if(count < 1){
                        isDayUpdated = myDb.addDay(String.valueOf(InvoiceGlobals.contractorID),
                                String.valueOf(InvoiceGlobals.invoiceNo),
                                dateWorkedID.getText().toString(),
                                startTimeID.getText().toString(),
                                endTimeID.getText().toString(),
                                String.valueOf(time));
                        saveInvoice();
                    }else {
                        //showMessage("Add Day","Into update day");
                        btnAddDay.setText("Add Day");
                         isDayUpdated = myDb.updateDay(String.valueOf(InvoiceGlobals.contractorID),
                                String.valueOf(InvoiceGlobals.invoiceNo),
                                dateWorkedID.getText().toString(),
                                startTimeID.getText().toString(),
                                endTimeID.getText().toString(),
                                String.valueOf(time));
                    }
                    if (isDayUpdated == true) {
                        Toast.makeText(InvoiceActivity2.this, "Day Data sucessfully updated", Toast.LENGTH_SHORT).show();
                        loadDaysListView();
                        InvoiceGlobals.dayOfMonth = InvoiceGlobals.dayOfMonth + 1;
                        saveInvoice();
                        clearEdits();
                    }else
                        Toast.makeText(InvoiceActivity2.this, "Day Data failed to update", Toast.LENGTH_SHORT).show();

                    btnAddDay.setEnabled(false);
                }else {
                    //showMessage("Add Day","Into else clause");
                    isDayUpdated = myDb.addDay(String.valueOf(Globals.contractorID),
                            String.valueOf(Globals.invNo),
                            dateWorkedID.getText().toString(),
                            startTimeID.getText().toString(),
                            endTimeID.getText().toString(),
                            String.valueOf(time));


                    btnAddDay.setEnabled(false);
                    if (isDayUpdated == true) {
                        Toast.makeText(InvoiceActivity2.this, "Day Data sucessfully inserted", Toast.LENGTH_SHORT).show();
                        clearEdits();
                        Globals.DateIntent = "Invoice2";
                        loadDaysListView();
                        pickDate();
                    } else
                        Toast.makeText(InvoiceActivity2.this, "Day Data failed to insert", Toast.LENGTH_SHORT).show();
                }
                btnAddDay.setText("Add Day");
                btnSaveInvoiceID.setEnabled(true);
            }
        });
    }

    public void clearEdits(){
        dateWorkedID.setText("");
        startTimeID.setText("");
        endTimeID.setText("");
        hoursWorkedID.setText("");
        dateWorkedID.requestFocus();
    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
    }
}
