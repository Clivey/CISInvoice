package com.software.kinson.cisinvoicegenerator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.software.kinson.cisinvoicegenerator.Adapters.InvoiceAdapter;
import com.software.kinson.cisinvoicegenerator.Model.Invoices;

import java.util.ArrayList;

public class EditInvoiceActivity extends BaseActivity {
    DatabaseHelper myDb;
    private ListView listView;
    private Button btnCancel;
    ArrayList<Invoices> arrayList;
    InvoiceAdapter invoiceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_invoice);

        listView = findViewById(R.id.lvInvoiceID);
        btnCancel = findViewById(R.id.btnCancelInvoiceID);
        myDb = new DatabaseHelper(this);
        arrayList = new ArrayList<>();

        loadInvoiceData();
        populateListView();
        cancelInvoice();
    }

    private void cancelInvoice() {
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditInvoiceActivity.this,BaseActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }

    private void populateListView() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tvTemp1 = view.findViewById(R.id.tvInvoiceNoID);
                InvoiceGlobals.invoiceNo = Integer.parseInt(tvTemp1.getText().toString());
                arrayList = myDb.populateInvoiceList();
                invoiceAdapter = new InvoiceAdapter(EditInvoiceActivity.this,arrayList);
                listView.setAdapter(invoiceAdapter);
                invoiceAdapter.notifyDataSetChanged();


                if(InvoiceGlobals.editInvoice){
                    //Toast.makeText(EditInvoiceActivity.this, "On Click Listener Invoice Number = " + InvoiceGlobals.invoiceNo, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditInvoiceActivity.this,InvoiceActivity.class);
                    finish();
                    startActivity(intent);
                }
            }
        });
    }

    private void loadInvoiceData() {
        arrayList = myDb.populateInvoiceList();
        invoiceAdapter = new InvoiceAdapter(this,arrayList);
        listView.setAdapter(invoiceAdapter);
        invoiceAdapter.notifyDataSetChanged();
        //Toast.makeText(EditInvoiceActivity.this, "Contractor ID is " + Globals.contractorID, Toast.LENGTH_SHORT).show();
    }
}
