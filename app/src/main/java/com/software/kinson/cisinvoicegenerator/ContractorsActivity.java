package com.software.kinson.cisinvoicegenerator;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ContractorsActivity extends BaseActivity {

    DatabaseHelper myDb;
    EditText editName, editAddress, editArea, editTown, editCounty, editPostCode, editTelno, editEmail;
    Button btnAdd, btnView, btnDelete, btnCancel;
    String conName;
    int conID;
    TextView tvID;
    CheckBox cisCheckBox;
    boolean checked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contractors);
        myDb = new DatabaseHelper(this);


        tvID = findViewById(R.id.tvContractorsID);
        editName = findViewById(R.id.etContractorsName);
        editAddress = findViewById(R.id.etContractorsAddress);
        editArea = findViewById(R.id.etContractorsArea);
        editTown = findViewById(R.id.etContractorsTown);
        editCounty = findViewById(R.id.etContractorsCounty);
        editPostCode = findViewById(R.id.etContractorsPostCode);
        editTelno = findViewById(R.id.etContractorsTelno);
        editEmail = findViewById(R.id.etContractorsEmail);
        btnAdd = findViewById(R.id.btnContractorsAdd);
        btnView = findViewById(R.id.btnContractorsView);
        btnDelete = findViewById(R.id.btnContractorsDelete);
        btnDelete.setEnabled(false);
        btnCancel = findViewById(R.id.btnContractorsCancelID);
        cisCheckBox = findViewById(R.id.cbCisID);
        Intent intent = getIntent();
        conName = intent.getStringExtra("conName");
        conID = intent.getIntExtra("conID", 0);
        //showMessage("Query",conName);
        //showMessage("Query ",Globals.companyQry);
        addContractorData();
        viewContractorData();
        showContractorData();
        deleteContractor();
        cancelContractorData();
    }

    public void showContractorData() {
        Cursor res = myDb.getContractorID(conName);
        //showMessage("Query ",Globals.companyQry);
        if (res.getCount() == 0) {
            //showMessage("Error","No data to show");
            Toast.makeText(ContractorsActivity.this, "No Contractor Data to show", Toast.LENGTH_SHORT).show();
            return;
        }

        //StringBuffer buffer = new StringBuffer();
        //while (res.moveToFirst()){
        if (res.getCount() > 0) {
            //showMessage("Query","rescount > 0");
            res.moveToFirst();
            //buffer.append("ID : "+res.getString(0)+"\n");
            tvID.setText(res.getString(0));
            //buffer.append("Name : "+res.getString(1)+"\n");
            editName.setText(res.getString(1));
            //buffer.append("Address : "+res.getString(2)+"\n");
            editAddress.setText(res.getString(2));
            //buffer.append("Area : "+res.getString(3)+"\n");
            editArea.setText(res.getString(3));
            //buffer.append("Town : "+res.getString(4)+"\n");
            editTown.setText(res.getString(4));
            //buffer.append("County : "+res.getString(5)+"\n");
            editCounty.setText(res.getString(5));
            //buffer.append("Post Code : "+res.getString(6)+"\n");
            editPostCode.setText(res.getString(6));
            //buffer.append("Telephone No : "+res.getString(7)+"\n");
            editTelno.setText(res.getString(7));
            //buffer.append("Bank : "+res.getString(8)+"\n");
            editEmail.setText(res.getString(8));
            if (res.getInt(9) == 1) {
                cisCheckBox.setChecked(true);
            }
            btnDelete.setEnabled(true);
            btnView.setEnabled(false);
            btnAdd.setText("Update");
        }
    }

    public void deleteContractor() {
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deleteRows = myDb.deleteContractorsData(tvID.getText().toString());
                if (deleteRows > 0) {
                    Toast.makeText(ContractorsActivity.this, "Data sucessfully deleted", Toast.LENGTH_SHORT).show();
                    clearEdits();

                } else {
                    Toast.makeText(ContractorsActivity.this, "Data deletetion failed", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void cancelContractorData() {
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContractorsActivity.this, BaseActivity.class);
                Globals.conEdit = false;
                finish();
                startActivity(intent);
            }
        });
    }

    public void viewContractorData() {
        btnView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Globals.conEdit = true;
                        Intent intent = new Intent(ContractorsActivity.this, ListContractorActivity.class);
                        //Globals.conEdit = false;
                        finish();
                        startActivity(intent);
                    }
                }
        );
    }

    public void addContractorData() {
        btnAdd.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (editName.getText().toString().isEmpty()) {
                            Toast.makeText(ContractorsActivity.this, "Name is required", Toast.LENGTH_SHORT).show();
                            editName.requestFocus();
                            return;
                        }
                        if (editAddress.getText().toString().isEmpty()) {
                            Toast.makeText(ContractorsActivity.this, "Address is required", Toast.LENGTH_SHORT).show();
                            editAddress.requestFocus();
                            return;
                        }
                        if (editTown.getText().toString().isEmpty()) {
                            Toast.makeText(ContractorsActivity.this, "Town is required", Toast.LENGTH_SHORT).show();
                            editTown.requestFocus();
                            return;
                        }
                        if (editPostCode.getText().toString().isEmpty()) {
                            Toast.makeText(ContractorsActivity.this, "Post code is required", Toast.LENGTH_SHORT).show();
                            editPostCode.requestFocus();
                            return;
                        }
                        if (editTelno.getText().toString().isEmpty()) {
                            Toast.makeText(ContractorsActivity.this, "Telephone number is required", Toast.LENGTH_SHORT).show();
                            editTelno.requestFocus();
                            return;
                        }
                        if (editEmail.getText().toString().isEmpty()) {
                            Toast.makeText(ContractorsActivity.this, "Email address is required", Toast.LENGTH_SHORT).show();
                            editEmail.requestFocus();
                            return;
                        }
                        if (Globals.conEdit) {
                            int checked = 0;
                            if (cisCheckBox.isChecked()) {
                                checked = 1;
                                Toast.makeText(ContractorsActivity.this, "checked = " + checked, Toast.LENGTH_SHORT).show();
                            } else {
                                checked = 0;
                                Toast.makeText(ContractorsActivity.this, "checked = " + checked, Toast.LENGTH_SHORT).show();
                            }

                            //showMessage("Contractor","conEdit = true");
                            boolean isConUpdated = myDb.UpdateContractorsData(tvID.getText().toString(),
                                    editName.getText().toString(),
                                    editAddress.getText().toString(), editArea.getText().toString(),
                                    editTown.getText().toString(), editCounty.getText().toString(),
                                    editPostCode.getText().toString(), editTelno.getText().toString(),
                                    editEmail.getText().toString(), String.valueOf(checked));


                            if (isConUpdated == true) {
                                Toast.makeText(ContractorsActivity.this, "Contractors Data sucessfully updated", Toast.LENGTH_SHORT).show();
                                Globals.contractorsData = true;
                                Globals.conEdit = false;
                                btnView.setEnabled(true);
                                Intent intent = new Intent(ContractorsActivity.this, BaseActivity.class);
                                finish();
                                startActivity(intent);
                            } else {
                                Toast.makeText(ContractorsActivity.this, "Contractors update failed", Toast.LENGTH_SHORT).show();
                                Globals.conEdit = false;
                                btnView.setEnabled(true);
                            }
                        } else {
                            //showMessage("Contractor","conEdit = false");
                            boolean isConInserted = myDb.addContractorsData(editName.getText().toString(),
                                    editAddress.getText().toString(), editArea.getText().toString(),
                                    editTown.getText().toString(), editCounty.getText().toString(),
                                    editPostCode.getText().toString(), editTelno.getText().toString(),
                                    editEmail.getText().toString(), String.valueOf(checked));


                            if (isConInserted == true) {
                                Toast.makeText(ContractorsActivity.this, "Contractors Data sucessfully inserted", Toast.LENGTH_SHORT).show();
                                Globals.contractorsData = true;
                                Intent intent = new Intent(ContractorsActivity.this, BaseActivity.class);
                                finish();
                                startActivity(intent);
                            } else {
                                Toast.makeText(ContractorsActivity.this, "Contractors Data entry failed", Toast.LENGTH_SHORT).show();
                            }
                        }

                        clearEdits();
                    }
                }
        );
    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void clearEdits() {
        tvID.setText("");
        editName.setText("");
        editAddress.setText("");
        editArea.setText("");
        editTown.setText("");
        editCounty.setText("");
        editPostCode.setText("");
        editTelno.setText("");
        editEmail.setText("");
        btnDelete.setEnabled(false);
        btnView.setEnabled(true);
        btnAdd.setText("Add Data");
    }

}
