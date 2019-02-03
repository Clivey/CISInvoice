package com.software.kinson.cisinvoicegenerator;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class InvoiceActivity extends BaseActivity {
    DatabaseHelper myDb;
    Button btnNext;
    TextView displayDate, invoiceNumber, workDescription,workArea,workTown,workPostCode, workAddress, workCounty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);
        myDb = new DatabaseHelper(this);
        int invNo = myDb.isTableEmpty(DatabaseHelper.INVOICE_TABLE);
        invNo = invNo + 1;
        displayDate = findViewById(R.id.tvDate);
        btnNext = findViewById(R.id.btnNextID);
        invoiceNumber = findViewById(R.id.tvInvoiceNumber);
        workAddress = findViewById(R.id.etWorkAddress);
        workArea = findViewById(R.id.etWorkArea);
        workTown = findViewById(R.id.etWorkTown);
        workCounty = findViewById(R.id.etWorkCounty);
        workPostCode = findViewById(R.id.etPostCodeID);
        workDescription = findViewById(R.id.workDescriptionID);
        Intent intent = getIntent();
        String date = intent.getStringExtra("date");
        displayDate.setText(date);

        if(InvoiceGlobals.editInvoice){
            invoiceNumber.setText(String.valueOf(InvoiceGlobals.invoiceNo));
            showInvoiceData();
            //Toast.makeText(this, "onCreate editInvoice = true", Toast.LENGTH_SHORT).show();
        }else
            invoiceNumber.setText(String.valueOf(invNo));
        showActivityTwo();
    }

    public void showActivityTwo(){
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(InvoiceGlobals.editInvoice){
//                    displayDate.setText(InvoiceGlobals.invoiceDate);
//                    workDescription.setText(InvoiceGlobals.workDescription);
//                    workArea.setText(InvoiceGlobals.workArea);
//                    workTown.setText(InvoiceGlobals.workTown);
//                    workCounty.setText(InvoiceGlobals.workCounty);
//                    workPostCode.setText(InvoiceGlobals.workPostCode);
//                    InvoiceGlobals.contractorID = Globals.contractorID;
//                    Toast.makeText(InvoiceActivity.this, "editInvoice = true", Toast.LENGTH_SHORT).show();
//                }else {
                    InvoiceGlobals.invoiceDate = displayDate.getText().toString();
                    InvoiceGlobals.workDescription = workDescription.getText().toString();
                    InvoiceGlobals.workAddress = workAddress.getText().toString();
                    InvoiceGlobals.workArea = workArea.getText().toString();
                    InvoiceGlobals.workTown = workTown.getText().toString();
                    InvoiceGlobals.workCounty = workCounty.getText().toString();
                    InvoiceGlobals.workPostCode = workPostCode.getText().toString();
                    InvoiceGlobals.contractorID = Globals.contractorID;
                    //Toast.makeText(InvoiceActivity.this, "editInvoice = false", Toast.LENGTH_SHORT).show();
                //}

                Intent intent = new Intent(InvoiceActivity.this,InvoiceActivity2.class);
                finish();
                Globals.DateIntent = "Invoice2";
                startActivity(intent);
            }
        });
    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    private void showInvoiceData() {
        Cursor res = myDb.editInvoiceData(InvoiceGlobals.invoiceNo);
        int c = res.getCount();
        //Toast.makeText(this, "res count = " + c, Toast.LENGTH_SHORT).show();
        if(res.getCount() == 0){
            //showMessage("Error","No data to show");
            Toast.makeText(InvoiceActivity.this, "No Invoice Data to show", Toast.LENGTH_SHORT).show();
            return;
        }

        if(res.getCount() > 0) {
            //Toast.makeText(this, "There is data", Toast.LENGTH_SHORT).show();
            //showMessage("Query","rescount > 0");
            res.moveToFirst();
            InvoiceGlobals.contractorID = Integer.parseInt(res.getString(1));
            //InvoiceGlobals.invoiceNo = Integer.parseInt(res.getString(2));
            InvoiceGlobals.invoiceDate = res.getString(3);
            InvoiceGlobals.workAddress = res.getString(4);
            InvoiceGlobals.workArea = res.getString(5);
            InvoiceGlobals.workTown = res.getString(6);
            InvoiceGlobals.workCounty = res.getString(7);
            InvoiceGlobals.workPostCode = res.getString(8);
            InvoiceGlobals.workDescription = res.getString(9);
            InvoiceGlobals.workCompleted = res.getString(10);
            InvoiceGlobals.hourlyRate = Float.parseFloat(res.getString(11));
            displayDate.setText(InvoiceGlobals.invoiceDate);
            workDescription.setText(InvoiceGlobals.workDescription);
            workAddress.setText(InvoiceGlobals.workAddress);
            workArea.setText(InvoiceGlobals.workArea);
            workTown.setText(InvoiceGlobals.workTown);
            workCounty.setText(InvoiceGlobals.workCounty);
            workPostCode.setText(InvoiceGlobals.workPostCode);
        }

    }


}
