package com.software.kinson.cisinvoicegenerator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class BaseActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    DbUtils myUtils;
    Button btnNewCon, btnNewInv, btnExit, btnDeleteInvoice, btnEditInvoice;
    public Menu toggleMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        btnNewCon = findViewById(R.id.btnNewContractor);
        btnNewInv = findViewById(R.id.btnNewInvoice);
        btnEditInvoice = findViewById(R.id.btnEditInvoice);
        btnExit = findViewById(R.id.btnExitID);
        btnDeleteInvoice = findViewById(R.id.mnuDeleteInvoice);
        if(Globals.invNo < 1)
            btnEditInvoice.setEnabled(false);
        else
            btnEditInvoice.setEnabled(true);

        showContractor();
        showInvoice();
        exitApp();
        editInvoice();
    }

    private void editInvoice() {
        btnEditInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InvoiceGlobals.editInvoice = true;
                Intent intent = new Intent(BaseActivity.this,ListContractorActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }

    public void showContractor(){
        btnNewCon.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(BaseActivity.this,ContractorsActivity.class);
                        finish();
                        startActivity(intent);
                    }
                }
        );
    }
    public void exitApp(){
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAndRemoveTask();
            }
        });
    }

    public void showInvoice(){
        btnNewInv.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        InvoiceGlobals.editInvoice = false;
                        Intent intent = new Intent(BaseActivity.this,ListContractorActivity.class);
                        finish();
                        startActivity(intent);
                    }
                }
        );
    }

    public void showNewInvoice(){
        InvoiceGlobals.editInvoice = false;
        Intent intent = new Intent(this,ListContractorActivity.class);
        finish();
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.commonmenus,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        toggleMenu = menu;
        toggleItem();

        return super.onPrepareOptionsMenu(menu);
    }

    public void toggleItem(){
        MenuItem item1= toggleMenu.findItem(R.id.mnuNewInvoice);
        MenuItem item2= toggleMenu.findItem(R.id.mnuContractor);
        if(Globals.userData)
        {
            item1.setVisible(false);
            item2.setVisible(true);
        }
        else
        {
            item1.setVisible(false);
            item2.setVisible(true);
        }
        if(Globals.userData && Globals.contractorsData){
            item1.setVisible(true);
        }
        else{
            item1.setVisible(false);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.mnuNewInvoice) {
            Globals.userMenuClicked = true;
            //Toast.makeText(this, "New Invoice", Toast.LENGTH_SHORT).show();
            showNewInvoice();
        }
        if (id == R.id.mnuUserDetails) {
            Globals.userMenuClicked = false;
            Globals.triggerUserDetails = true;
            //Toast.makeText(this, "User Details", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(BaseActivity.this,MainActivity.class);
            finish();
            startActivity(intent);
        }
        if (id == R.id.mnuContractor) {
            Globals.userMenuClicked = false;
            //Toast.makeText(this, "Add Contractor", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,ContractorsActivity.class);
            finish();
            startActivity(intent);
        }
        if(id == R.id.mnuDeleteInvoice){

        }
//        if (id == R.id.mnuSettings) {
//            Globals.userMenuClicked = true;
//            Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
//        }

        return super.onOptionsItemSelected(item);
    }
}
