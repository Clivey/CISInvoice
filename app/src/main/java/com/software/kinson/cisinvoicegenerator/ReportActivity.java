package com.software.kinson.cisinvoicegenerator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ReportActivity extends BaseActivity {
    Button btnShowInvoice, btnEmailInvoice, btnCancelInvoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        myDb = new DatabaseHelper(ReportActivity.this);

        btnShowInvoice = findViewById(R.id.btnShowInvoiceID);
        btnCancelInvoice = findViewById(R.id.btnCancelInvoiceID);
        btnEmailInvoice = findViewById(R.id.btnEmailInvoiceID);

        showReportInvoice();
        emailReportInvoice();
        cancelReportInvoice();
    }

    public void showReportInvoice(){
        btnShowInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void emailReportInvoice(){
        btnEmailInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void cancelReportInvoice(){
        btnCancelInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReportActivity.this,BaseActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }
}
