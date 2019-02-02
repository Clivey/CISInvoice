package com.software.kinson.cisinvoicegenerator;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends BaseActivity {

    DatabaseHelper myDb;
    EditText editCompany, editAddress, editArea, editTown, editCounty, editPostCode, editTelno,
            editBank, editAccountTitle, editAccountNumber, editSortCode, editUtr, editID, editEmail;
    Button btnAddData, btnViewUpdate, btnDelete;
    private int commonmenus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(MainActivity.this);

        editID = findViewById(R.id.editTextID);
        editCompany = findViewById(R.id.editTextCompany);
        editAddress = findViewById(R.id.etContractorsAddress);
        editArea = findViewById(R.id.etContractorsArea);
        editTown = findViewById(R.id.etContractorsTown);
        editCounty = findViewById(R.id.etContractorsCounty);
        editPostCode = findViewById(R.id.etContractorsPostCode);
        editTelno = findViewById(R.id.etContractorsTelno);
        editBank = findViewById(R.id.editTextBankName);
        editAccountTitle = findViewById(R.id.editTextAccountTitle);
        editAccountNumber = findViewById(R.id.editTextAccountNumber);
        editSortCode = findViewById(R.id.editTextSortCode);
        editUtr = findViewById(R.id.editTextUtr);
        editEmail = findViewById(R.id.editTextEmail);
        btnAddData = findViewById(R.id.buttonAddData);
        btnViewUpdate = findViewById(R.id.buttonUpdate);
        btnDelete = findViewById(R.id.buttonDelete);
        addData();
        updateData();
        deleteData();
        if (Globals.triggerUserDetails)
            viewUserDetails();
        Globals.invNo = myDb.invoiceNumber();
        //showMessage("Invoice Number = ", String.valueOf(Globals.invNo));

        int conConCount = myDb.isTableEmpty(DatabaseHelper.CONTRACTORS_TABLE);
        if (conConCount > 0) {
            Globals.conCount = conConCount;
        }

        int isEmpty = myDb.isTableEmpty(DatabaseHelper.COMPANY_TABLE);
        if (isEmpty < 1) {
            myDb.addTempCompanyData();
            myDb.addTempContractorsData();
            myDb.addTempContractorsData2();
            myDb.addTempContractorsData3();
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            finish();
            startActivity(intent);
            //Toast.makeText(this, "isEmpty = " + isEmpty, Toast.LENGTH_SHORT).show();
        }
        if (isEmpty > 0) {
            Globals.showUserDetails = true;
            btnAddData.setEnabled(false);
            Globals.userData = true;
        }

        if (isEmpty > 0 && Globals.showUserDetails && Globals.userMenuClicked) {
            Intent intent = new Intent(this, BaseActivity.class);
            finish();
            startActivity(intent);
            //Toast.makeText(this, "Company Table has Data "+isEmpty, Toast.LENGTH_LONG).show();
        }
        if (isEmpty < 1) {
            Toast.makeText(this, "Company table is empty", Toast.LENGTH_LONG).show();
            btnAddData.setEnabled(true);
            btnViewUpdate.setEnabled(false);
            btnDelete.setEnabled(false);
        }
    }

    public void deleteData() {
        btnDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deleteRows = myDb.deleteCompanyData(editID.getText().toString());
                        if (deleteRows > 0) {
                            Toast.makeText(MainActivity.this, "Data sucessfully deleted", Toast.LENGTH_SHORT).show();
                            clearEdits();
                            Globals.triggerUserDetails = false;
                            Globals.userMenuClicked = true;
                        } else {
                            Toast.makeText(MainActivity.this, "Data deletetion failed", Toast.LENGTH_SHORT).show();
                        }
                        int isEmpty = myDb.isTableEmpty(DatabaseHelper.COMPANY_TABLE);
                        if (isEmpty > 0) {
                            btnAddData.setEnabled(false);
                            Globals.userData = true;
                        } else {
                            btnAddData.setEnabled(true);
                            btnViewUpdate.setEnabled(false);
                            btnDelete.setEnabled(false);
                        }
                    }

                }

        );
    }

    public void updateData() {
        btnViewUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdated = myDb.updateCompanyData(editID.getText().toString(),
                                editCompany.getText().toString(),
                                editAddress.getText().toString(),
                                editArea.getText().toString(),
                                editTown.getText().toString(),
                                editCounty.getText().toString(),
                                editPostCode.getText().toString(),
                                editTelno.getText().toString(),
                                editBank.getText().toString(),
                                editAccountTitle.getText().toString(),
                                editAccountNumber.getText().toString(),
                                editSortCode.getText().toString(),
                                editUtr.getText().toString(),
                                editEmail.getText().toString());

                        if (isUpdated == true) {
                            Toast.makeText(MainActivity.this, "Data sucessfully updated", Toast.LENGTH_SHORT).show();
                            btnAddData.setEnabled(false);
                            btnViewUpdate.setEnabled(true);
                            btnDelete.setEnabled(true);
                            Globals.showUserDetails = true;
                            Globals.userMenuClicked = true;
                            Globals.triggerUserDetails = false;
                        } else {
                            Toast.makeText(MainActivity.this, "Data update failed", Toast.LENGTH_SHORT).show();
                            btnAddData.setEnabled(true);
                            btnViewUpdate.setEnabled(false);
                            btnDelete.setEnabled(false);
                        }
                        Intent intent = new Intent(MainActivity.this, BaseActivity.class);
                        finish();
                        startActivity(intent);
                    }
                }
        );
    }


    public void addData() {

        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (editCompany.getText().toString().isEmpty()) {
                            Toast.makeText(MainActivity.this, "Name is required", Toast.LENGTH_SHORT).show();
                            editCompany.requestFocus();
                            return;
                        }
                        if (editAddress.getText().toString().isEmpty()) {
                            Toast.makeText(MainActivity.this, "Address is required", Toast.LENGTH_SHORT).show();
                            editAddress.requestFocus();
                            return;
                        }
                        if (editTown.getText().toString().isEmpty()) {
                            Toast.makeText(MainActivity.this, "Town is required", Toast.LENGTH_SHORT).show();
                            editTown.requestFocus();
                            return;
                        }
                        if (editCounty.getText().toString().isEmpty()) {
                            Toast.makeText(MainActivity.this, "County is required", Toast.LENGTH_SHORT).show();
                            editCounty.requestFocus();
                            return;
                        }
                        if (editPostCode.getText().toString().isEmpty()) {
                            Toast.makeText(MainActivity.this, "Post code is required", Toast.LENGTH_SHORT).show();
                            editPostCode.requestFocus();
                            return;
                        }
                        if (editTelno.getText().toString().isEmpty()) {
                            Toast.makeText(MainActivity.this, "Telephone number is required", Toast.LENGTH_SHORT).show();
                            editTelno.requestFocus();
                            return;
                        }
                        if (editBank.getText().toString().isEmpty()) {
                            Toast.makeText(MainActivity.this, "Bank is required", Toast.LENGTH_SHORT).show();
                            editBank.requestFocus();
                            return;
                        }
                        if (editAccountTitle.getText().toString().isEmpty()) {
                            Toast.makeText(MainActivity.this, "Account Title is required", Toast.LENGTH_SHORT).show();
                            editAccountTitle.requestFocus();
                            return;
                        }
                        if (editAccountNumber.getText().toString().isEmpty()) {
                            Toast.makeText(MainActivity.this, "Account number is required", Toast.LENGTH_SHORT).show();
                            editAccountNumber.requestFocus();
                            return;
                        }
                        if (editSortCode.getText().toString().isEmpty()) {
                            Toast.makeText(MainActivity.this, "Bank sort code is required", Toast.LENGTH_SHORT).show();
                            editSortCode.requestFocus();
                            return;
                        }
                        if (editUtr.getText().toString().isEmpty()) {
                            Toast.makeText(MainActivity.this, "UTR Number is required", Toast.LENGTH_SHORT).show();
                            editUtr.requestFocus();
                            return;
                        }
                        if (editEmail.getText().toString().isEmpty()) {
                            Toast.makeText(MainActivity.this, "Email Address is required", Toast.LENGTH_SHORT).show();
                            editEmail.requestFocus();
                            return;
                        }
                        boolean isInserted = myDb.addCompanyData(editCompany.getText().toString(),
                                editAddress.getText().toString(),
                                editArea.getText().toString(),
                                editTown.getText().toString(),
                                editCounty.getText().toString(),
                                editPostCode.getText().toString(),
                                editTelno.getText().toString(),
                                editBank.getText().toString(),
                                editAccountTitle.getText().toString(),
                                editAccountNumber.getText().toString(),
                                editSortCode.getText().toString(),
                                editUtr.getText().toString(),
                                editEmail.getText().toString());

                        if (isInserted == true) {
                            Toast.makeText(MainActivity.this, "Data sucessfully inserted", Toast.LENGTH_SHORT).show();
                            clearEdits();
                            Globals.userData = true;
                            Intent intent = new Intent(MainActivity.this, BaseActivity.class);
                            finish();
                            btnAddData.setEnabled(false);
                            btnViewUpdate.setEnabled(true);
                            btnDelete.setEnabled(true);
                            Globals.triggerUserDetails = false;
                            Globals.userMenuClicked = true;
                            startActivity(intent);
                        } else {
                            Toast.makeText(MainActivity.this, "Data entry failed", Toast.LENGTH_SHORT).show();
                            //clearEdits();
                        }

                        int isEmpty = myDb.isTableEmpty(DatabaseHelper.COMPANY_TABLE);
                        if (isEmpty > 0) {
                            btnAddData.setEnabled(false);
                        }


                    }
                }
        );

    }

    public void viewUserDetails() {
        Cursor res = myDb.getAllCompanyData();
        if (res.getCount() == 0) {
            //showMessage("Error","No data to show");
            Toast.makeText(MainActivity.this, "No Data to show", Toast.LENGTH_SHORT).show();
            return;
        }

        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            buffer.append("ID : " + res.getString(0) + "\n");
            editID.setText(res.getString(0));
            buffer.append("Company Name : " + res.getString(1) + "\n");
            editCompany.setText(res.getString(1));
            buffer.append("Address : " + res.getString(2) + "\n");
            editAddress.setText(res.getString(2));
            buffer.append("Area : " + res.getString(3) + "\n");
            editArea.setText(res.getString(3));
            buffer.append("Town : " + res.getString(4) + "\n");
            editTown.setText(res.getString(4));
            buffer.append("County : " + res.getString(5) + "\n");
            editCounty.setText(res.getString(5));
            buffer.append("Post Code : " + res.getString(6) + "\n");
            editPostCode.setText(res.getString(6));
            buffer.append("Telephone No : " + res.getString(7) + "\n");
            editTelno.setText(res.getString(7));
            buffer.append("Bank : " + res.getString(8) + "\n");
            editBank.setText(res.getString(8));
            buffer.append("Account Title : " + res.getString(9) + "\n");
            editAccountTitle.setText(res.getString(9));
            buffer.append("Account Number : " + res.getString(10) + "\n");
            editAccountNumber.setText(res.getString(10));
            buffer.append("Sort Code : " + res.getString(11) + "\n");
            editSortCode.setText(res.getString(11));
            buffer.append("UTR : " + res.getString(12) + "\n");
            editUtr.setText(res.getString(12));
            buffer.append("email : " + res.getString(13) + "\n");
            editEmail.setText(res.getString(13));
        }
    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void clearEdits() {
        editID.setText("");
        editCompany.setText("");
        editAddress.setText("");
        editArea.setText("");
        editTown.setText("");
        editCounty.setText("");
        editPostCode.setText("");
        editTelno.setText("");
        editBank.setText("");
        editAccountTitle.setText("");
        editAccountNumber.setText("");
        editSortCode.setText("");
        editUtr.setText("");
        editEmail.setText("");
    }
}
