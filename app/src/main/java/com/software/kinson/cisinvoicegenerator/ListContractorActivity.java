package com.software.kinson.cisinvoicegenerator;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.software.kinson.cisinvoicegenerator.Adapters.ContractorsAdapter;
import com.software.kinson.cisinvoicegenerator.Model.Contractors;

import java.util.ArrayList;

public class ListContractorActivity extends BaseActivity {
    DatabaseHelper myDb;
    ArrayList<Contractors> arrayList;
    ContractorsAdapter contractorsAdapter;
    private ListView listView;
    private TextView tv;

    //ListAdapter listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_contractor);

        listView = findViewById(R.id.lvContractor);
        //tv = findViewById(R.id.tvCustomRowID);
        myDb = new DatabaseHelper(this);
        arrayList = new ArrayList<>();
        loadDataInListView();
        //populateList();

        populateListView();
    }

    private void loadDataInListView() {
        arrayList = myDb.populateList();
        contractorsAdapter = new ContractorsAdapter(this, arrayList);
        listView.setAdapter(contractorsAdapter);
        contractorsAdapter.notifyDataSetChanged();
    }

    private void populateListView() {
        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // String name = parent.getItemAtPosition(position).toString();
                        TextView temp = view.findViewById(R.id.tvCustomRowID);
                        TextView temp2 = view.findViewById(R.id.tvCustomRowName);
                        Globals.contractorID = Integer.parseInt(temp.getText().toString());
                        Globals.contractorsName = temp2.getText().toString();

                        arrayList = myDb.populateList();
                        contractorsAdapter = new ContractorsAdapter(ListContractorActivity.this, arrayList);
                        listView.setAdapter(contractorsAdapter);
                        contractorsAdapter.notifyDataSetChanged();


                        if (Globals.conEdit) {
                            Intent intent = new Intent(ListContractorActivity.this, ContractorsActivity.class);
                            intent.putExtra("conName", Globals.contractorsName);
                            finish();
                            Toast.makeText(ListContractorActivity.this, "Selected edit " + Globals.contractorsName, Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                        } else if (InvoiceGlobals.editInvoice) {
                            Intent intent = new Intent(ListContractorActivity.this, EditInvoiceActivity.class);
                            //InvoiceGlobals.invoiceNo = Globals.invNo;
                            finish();
                            Toast.makeText(ListContractorActivity.this, "Selected Edit Invoice", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                        } else {

                            Intent intent = new Intent(ListContractorActivity.this, DatePickerActivity.class);
                            InvoiceGlobals.invoiceNo = Globals.invNo;
                            finish();
                            Toast.makeText(ListContractorActivity.this, "Selected " + Globals.contractorID, Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                        }
                    }
                }
        );
    }

    private ArrayList<Contractors> populateList() {
        ArrayList<Contractors> arrayList = new ArrayList<>();
        Cursor cursor = myDb.getAllContractorsData();
        if (cursor.getCount() == 0) {
            //showMessage("Error","No data to show");
            //Toast.makeText(ListContractorActivity.this, "No Data to show", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ListContractorActivity.this, BaseActivity.class);
            finish();
            startActivity(intent);
        }
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String town = cursor.getString(4);

            Contractors contractors = new Contractors(id, name, town);
            arrayList.add(contractors);
        }
        return arrayList;

    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
