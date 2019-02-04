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
        editBank, editAccountTitle, editAccountNumber, editSortCode1, editUtr, editID, editEmail,
        editSortCode2, editSortCode3;
    Button btnAddData, btnViewUpdate, btnDelete;
    private int commonmenus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Create new DatabaseHelper and create database if it does not exist
        myDb = new DatabaseHelper(MainActivity.this);

        //Associate textfield objects
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
        editSortCode1 = findViewById(R.id.editTextSortCode1);
        editSortCode2 = findViewById(R.id.editTextSortCode2);
        editSortCode3 = findViewById(R.id.editTextSortCode3);
        editUtr = findViewById(R.id.editTextUtr);
        editEmail = findViewById(R.id.editTextEmail);
        btnAddData = findViewById(R.id.buttonAddData);
        btnViewUpdate = findViewById(R.id.buttonUpdate);
        btnDelete = findViewById(R.id.buttonDelete);
        addData();
        updateData();
        deleteData();

        //Check if show user details has been selected
        if(Globals.triggerUserDetails)
            viewUserDetails();

        //Check if there are any contractors
        int conConCount = myDb.isTableEmpty(DatabaseHelper.CONTRACTORS_TABLE);
        if(conConCount > 0){
            Globals.conCount = conConCount;
        }

        //Update to new invoice number
        int hasNumber = myDb.isTableEmpty(DatabaseHelper.INVOICE_TABLE);
        if (hasNumber > 0)
            Globals.invNo = myDb.invoiceNumber();


        int isEmpty = myDb.isTableEmpty(DatabaseHelper.COMPANY_TABLE);
        //Just for development to insert dummy data
        if(isEmpty < 1) {
            myDb.addTempCompanyData();
            myDb.addTempContractorsData();
            myDb.addTempContractorsData2();
            myDb.addTempContractorsData3();
        }

        //Set various global and button values
        if(isEmpty > 0) {
            Globals.showUserDetails = true;
            btnAddData.setEnabled(false);
            Globals.userData = true;
        }

        //If there is data in the Company Table start base activity
        if (isEmpty > 0 && Globals.showUserDetails && Globals.userMenuClicked) {
        Intent intent = new Intent(this,BaseActivity.class);
        finish();
        startActivity(intent);
            //Toast.makeText(this, "Company Table has Data "+isEmpty, Toast.LENGTH_LONG).show();
        }

        //If company table has no data set button values to allow and disallow actions
        if(isEmpty < 1){
            Toast.makeText(this, "Company table is empty", Toast.LENGTH_LONG).show();
            btnAddData.setEnabled(true);
            btnViewUpdate.setEnabled(false);
            btnDelete.setEnabled(false);
        }
    }

    //Set the bank sort code
    public String sortCode(){
        String sorted1 = editSortCode1.getText().toString();
        String sorted2 = editSortCode2.getText().toString();
        String sorted3 = editSortCode3.getText().toString();
        String sorted4 = sorted1 + "-" + sorted2 + "-" + sorted3;
        return sorted4;
    }

    //Delete Company data
    public void deleteData(){
        btnDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deleteRows = myDb.deleteCompanyData(editID.getText().toString());
                        if(deleteRows > 0){
                            Toast.makeText(MainActivity.this, "Data sucessfully deleted", Toast.LENGTH_SHORT).show();
                            clearEdits();
                            Globals.triggerUserDetails = false;
                            Globals.userMenuClicked = true;
                        }
                        else{
                            Toast.makeText(MainActivity.this, "Data deletetion failed", Toast.LENGTH_SHORT).show();
                        }
                        int isEmpty = myDb.isTableEmpty(DatabaseHelper.COMPANY_TABLE);
                        if(isEmpty > 0) {
                            btnAddData.setEnabled(false);
                            Globals.userData = true;
                        }else {
                            btnAddData.setEnabled(true);
                            btnViewUpdate.setEnabled(false);
                            btnDelete.setEnabled(false);
                        }
                    }

                }

        );
    }

    //Update Company data
    public void updateData(){
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
                                sortCode(),
                                editUtr.getText().toString(),
                                editEmail.getText().toString());

                        //If data updated show user message of success
                        if(isUpdated == true) {
                            Toast.makeText(MainActivity.this, "Data sucessfully updated", Toast.LENGTH_SHORT).show();
                            btnAddData.setEnabled(false);
                            btnViewUpdate.setEnabled(true);
                            btnDelete.setEnabled(true);
                            Globals.showUserDetails = true;
                            Globals.userMenuClicked = true;
                            Globals.triggerUserDetails = false;
                            //Start Base activity
                            Intent intent = new Intent(MainActivity.this,BaseActivity.class);
                            finish();
                            startActivity(intent);
                        }
                        //If data updated show user message of failure to update
                        else {
                            Toast.makeText(MainActivity.this, "Data update failed", Toast.LENGTH_SHORT).show();
                            btnAddData.setEnabled(true);
                            btnViewUpdate.setEnabled(false);
                            btnDelete.setEnabled(false);
                        }

                    }
                }
        );
    }

    //Add Company data
    public void addData(){

        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Ensure all the required fields contain input
                        if(editCompany.getText().toString().isEmpty()) {
                            Toast.makeText(MainActivity.this, "Name is required", Toast.LENGTH_SHORT).show();
                            editCompany.requestFocus();
                            return;
                        }
                        if(editAddress.getText().toString().isEmpty()) {
                            Toast.makeText(MainActivity.this, "Address is required", Toast.LENGTH_SHORT).show();
                            editAddress.requestFocus();
                            return;
                        }
                        if(editTown.getText().toString().isEmpty()) {
                            Toast.makeText(MainActivity.this, "Town is required", Toast.LENGTH_SHORT).show();
                            editTown.requestFocus();
                            return;
                        }
                        if(editCounty.getText().toString().isEmpty()) {
                            Toast.makeText(MainActivity.this, "County is required", Toast.LENGTH_SHORT).show();
                            editCounty.requestFocus();
                            return;
                        }
                        if(editPostCode.getText().toString().isEmpty()) {
                            Toast.makeText(MainActivity.this, "Post code is required", Toast.LENGTH_SHORT).show();
                            editPostCode.requestFocus();
                            return;
                        }
                        if(editTelno.getText().toString().isEmpty()) {
                            Toast.makeText(MainActivity.this, "Telephone number is required", Toast.LENGTH_SHORT).show();
                            editTelno.requestFocus();
                            return;
                        }
                        if(editBank.getText().toString().isEmpty()) {
                            Toast.makeText(MainActivity.this, "Bank is required", Toast.LENGTH_SHORT).show();
                            editBank.requestFocus();
                            return;
                        }
                        if(editAccountTitle.getText().toString().isEmpty()) {
                            Toast.makeText(MainActivity.this, "Account Title is required", Toast.LENGTH_SHORT).show();
                            editAccountTitle.requestFocus();
                            return;
                        }
                        if(editAccountNumber.getText().toString().isEmpty()) {
                            Toast.makeText(MainActivity.this, "Account number is required", Toast.LENGTH_SHORT).show();
                            editAccountNumber.requestFocus();
                            return;
                        }
                        if(editSortCode1.getText().toString().isEmpty()) {
                            Toast.makeText(MainActivity.this, "Bank sort code is required", Toast.LENGTH_SHORT).show();
                            editSortCode1.requestFocus();
                            return;
                        }
                        if(editSortCode2.getText().toString().isEmpty()) {
                            Toast.makeText(MainActivity.this, "Bank sort code is required", Toast.LENGTH_SHORT).show();
                            editSortCode2.requestFocus();
                            return;
                        }
                        if(editSortCode3.getText().toString().isEmpty()) {
                            Toast.makeText(MainActivity.this, "Bank sort code is required", Toast.LENGTH_SHORT).show();
                            editSortCode3.requestFocus();
                            return;
                        }
                        if(editUtr.getText().toString().isEmpty()) {
                            Toast.makeText(MainActivity.this, "UTR Number is required", Toast.LENGTH_SHORT).show();
                            editUtr.requestFocus();
                            return;
                        }
                        if(editEmail.getText().toString().isEmpty()) {
                            Toast.makeText(MainActivity.this, "Email Address is required", Toast.LENGTH_SHORT).show();
                            editEmail.requestFocus();
                            return;
                        }
                        //Check for successfull data added to database
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
                                sortCode(),
                                editUtr.getText().toString(),
                                editEmail.getText().toString());

                       if(isInserted == true){
                           //Show user data succsessfully added
                           Toast.makeText(MainActivity.this, "Data sucessfully inserted", Toast.LENGTH_SHORT).show();
                           clearEdits();
                           Globals.userData = true;
                           Intent intent = new Intent(MainActivity.this,BaseActivity.class);
                           finish();
                           //Set variables and button states
                           btnAddData.setEnabled(false);
                           btnViewUpdate.setEnabled(true);
                           btnDelete.setEnabled(true);
                           Globals.triggerUserDetails = false;
                           Globals.userMenuClicked = true;
                           startActivity(intent);
                       }

                       else{
                           Toast.makeText(MainActivity.this, "Data entry failed", Toast.LENGTH_SHORT).show();
                           //clearEdits();
                       }
                        //If company table has data disable add data button
                        int isEmpty = myDb.isTableEmpty(DatabaseHelper.COMPANY_TABLE);
                        if(isEmpty > 0) {
                            btnAddData.setEnabled(false);
                        }


                    }
                }
        );

    }

    //Remove the hyphen from the sort code string
    public void splitSortCode(String sortCode){
        String code = sortCode;
        String [] sortSplit = code.split("-");
        editSortCode1.setText(sortSplit[0]);
        editSortCode2.setText(sortSplit[1]);
        editSortCode3.setText(sortSplit[2]);
    }

    //Retrieve Company data and display it to the user
    public void viewUserDetails(){
        Cursor res = myDb.getAllCompanyData();
        if(res.getCount() == 0){
            //showMessage("Error","No data to show");
            Toast.makeText(MainActivity.this, "No Data to show", Toast.LENGTH_SHORT).show();
            return;
        }

        while (res.moveToNext()){
            editID.setText(res.getString(0));
            editCompany.setText(res.getString(1));
            editAddress.setText(res.getString(2));
            editArea.setText(res.getString(3));
            editTown.setText(res.getString(4));
            editCounty.setText(res.getString(5));
            editPostCode.setText(res.getString(6));
            editTelno.setText(res.getString(7));
            editBank.setText(res.getString(8));
            editAccountTitle.setText(res.getString(9));
            editAccountNumber.setText(res.getString(10));
            splitSortCode(res.getString(11));
            editUtr.setText(res.getString(12));
            editEmail.setText(res.getString(13));
        }

    }

    //Method for development purposes only
    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    //Clear all edit fields
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
        editSortCode1.setText("");
        editSortCode2.setText("");
        editSortCode3.setText("");
        editUtr.setText("");
        editEmail.setText("");
    }
}
