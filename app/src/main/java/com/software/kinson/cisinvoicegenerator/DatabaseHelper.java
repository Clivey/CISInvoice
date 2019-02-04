package com.software.kinson.cisinvoicegenerator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.software.kinson.cisinvoicegenerator.Model.Contractors;
import com.software.kinson.cisinvoicegenerator.Model.Days;
import com.software.kinson.cisinvoicegenerator.Model.Invoices;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    DbUtils myUtils;

    //Declare all constants for Database and Tables
    public static final String DATABASE_NAME = "cis_invoice.db";
    public static final String COMPANY_TABLE = "companyTbl";
    public static final String CONTRACTORS_TABLE = "contractorsTbl";
    public static final String INVOICE_TABLE = "invoiceTbl";
    public static final String DAYS_WORKED_TABLE = "days_workedTbl";
    public static final String COMPANY_COL0 = "_ID";
    public static final String COMPANY_COL1 = "company_name";
    public static final String COMPANY_COL2 = "address";
    public static final String COMPANY_COL3 = "area";
    public static final String COMPANY_COL4 = "town";
    public static final String COMPANY_COL5 = "county";
    public static final String COMPANY_COL6 = "post_code";
    public static final String COMPANY_COL7 = "tel_no";
    public static final String COMPANY_COL8 = "bank";
    public static final String COMPANY_COL9 = "account_title";
    public static final String COMPANY_COL10 = "account_number";
    public static final String COMPANY_COL11 = "sort_code";
    public static final String COMPANY_COL12 = "utr_number";
    public static final String COMPANY_COL13 = "email";
    public static final String CONTRACTORS_COL0 = "ID";
    public static final String CONTRACTORS_COL1 = "name";
    public static final String CONTRACTORS_COL2 = "address";
    public static final String CONTRACTORS_COL3 = "area";
    public static final String CONTRACTORS_COL4 = "town";
    public static final String CONTRACTORS_COL5 = "county";
    public static final String CONTRACTORS_COL6 = "post_code";
    public static final String CONTRACTORS_COL7 = "tel_no";
    public static final String CONTRACTORS_COL8 = "email";
    public static final String CONTRACTORS_COL9 = "cis";
    public static final String DAYS_COL0 = "ID";
    public static final String DAYS_COL1 = "contractors_id";
    public static final String DAYS_COL2 = "invoice_no";
    public static final String DAYS_COL3 = "date";
    public static final String DAYS_COL4 = "start_time";
    public static final String DAYS_COL5 = "end_time";
    public static final String DAYS_COL6 = "hours_worked";
    public static final String INVOICE_COL1 = "contractor_ID";
    public static final String INVOICE_COL2 = "invoice_no";
    public static final String INVOICE_COL3 = "date";
    public static final String INVOICE_COL4 = "work_addr";
    public static final String INVOICE_COL5 = "work_area";
    public static final String INVOICE_COL6 = "work_town";
    public static final String INVOICE_COL7 = "work_county";
    public static final String INVOICE_COL8 = "work_postCode";
    public static final String INVOICE_COL9 = "work_description";
    public static final String INVOICE_COL10 = "work_done";
    public static final String INVOICE_COL11 = "hourly_rate";
    public static final String INVOICE_COL12 = "gross_total";
    public static final String INVOICE_COL13 = "tax";
    public static final String INVOICE_COL14 = "net_total";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        myUtils = new DbUtils(DatabaseHelper.this);
        createTables();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + COMPANY_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CONTRACTORS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + INVOICE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DAYS_WORKED_TABLE);
        db.execSQL("DROP VIEW IF EXISTS invoiceView");
        onCreate(db);
    }

    //Create Database Tables if they don't exist
    public void createTables(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(DbUtils.createCompanyTable());
        db.execSQL(DbUtils.createContractorTable());
        db.execSQL(DbUtils.createInvoiceTable());
        db.execSQL(DbUtils.createDaysWorkedTable());
        db.execSQL(DbUtils.createInvoiceView());
        db.close();
    }

    //Add Contractors to Table
    public boolean addContractorsData(String name, String address, String area, String town, String county, String postcode,
                                  String telno, String email, String cis){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(CONTRACTORS_COL1,name);
        contentValues.put(CONTRACTORS_COL2,address);
        contentValues.put(CONTRACTORS_COL3,area);
        contentValues.put(CONTRACTORS_COL4,town);
        contentValues.put(CONTRACTORS_COL5,county);
        contentValues.put(CONTRACTORS_COL6,postcode);
        contentValues.put(CONTRACTORS_COL7,telno);
        contentValues.put(CONTRACTORS_COL8,email);
        contentValues.put(CONTRACTORS_COL9,cis);

        long result = db.insert(CONTRACTORS_TABLE, null, contentValues);
        db.close();
        return result != -1;
    }

    //Update days Table
    public boolean updateDay(String contractorID, String invoiceNo, String date, String startTime, String endTime, String hoursWorked){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(DAYS_COL1,contractorID);
        contentValues.put(DAYS_COL2,invoiceNo);
        contentValues.put(DAYS_COL3,date);
        contentValues.put(DAYS_COL4,startTime);
        contentValues.put(DAYS_COL5,endTime);
        contentValues.put(DAYS_COL6,hoursWorked);

        long result = db.update(DAYS_WORKED_TABLE, contentValues, "invoice_no = ? AND date = ?", new String[] { invoiceNo,date });
        db.close();
        return result != -1;
    }

    //Add new day to day Table
    public boolean addDay(String contractorID, String invoiceNo, String date, String startTime, String endTime, String hoursWorked){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(DAYS_COL1,contractorID);
        contentValues.put(DAYS_COL2,invoiceNo);
        contentValues.put(DAYS_COL3,date);
        contentValues.put(DAYS_COL4,startTime);
        contentValues.put(DAYS_COL5,endTime);
        contentValues.put(DAYS_COL6,hoursWorked);

        long result = db.insert(DAYS_WORKED_TABLE, null, contentValues);
        db.close();
        return result != -1;
    }

    //Add new Invoice
    public boolean addInvoice(String contractorID, String invoiceNo, String date, String workAddress,
                              String workArea, String workTown, String workCounty, String workPostCode,
                              String workDescription, String workDone, String hourlyRate,
                              String grossTotal, String tax, String netTotal){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(INVOICE_COL1,contractorID);
        contentValues.put(INVOICE_COL2,invoiceNo);
        contentValues.put(INVOICE_COL3,date);
        contentValues.put(INVOICE_COL4,workAddress);
        contentValues.put(INVOICE_COL5,workArea);
        contentValues.put(INVOICE_COL6,workTown);
        contentValues.put(INVOICE_COL7,workCounty);
        contentValues.put(INVOICE_COL8,workPostCode);
        contentValues.put(INVOICE_COL9,workDescription);
        contentValues.put(INVOICE_COL10,workDone);
        contentValues.put(INVOICE_COL11,hourlyRate);
        contentValues.put(INVOICE_COL12,grossTotal);
        contentValues.put(INVOICE_COL13,tax);
        contentValues.put(INVOICE_COL14,netTotal);

        long result = db.insert(INVOICE_TABLE, null, contentValues);
        db.close();
        if(result == -1)
            return false;
        else {
            Globals.userData = true;
            return true;
        }


    }

    //Update Invoice
    public boolean updateInvoice(String contractorID, String invoiceNo, String date, String workAddress,
                              String workArea, String workTown, String workCounty, String workPostCode,
                              String workDescription, String workDone, String hourlyRate,
                              String grossTotal, String tax, String netTotal){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(INVOICE_COL1,contractorID);
        contentValues.put(INVOICE_COL2,invoiceNo);
        contentValues.put(INVOICE_COL3,date);
        contentValues.put(INVOICE_COL4,workAddress);
        contentValues.put(INVOICE_COL5,workArea);
        contentValues.put(INVOICE_COL6,workTown);
        contentValues.put(INVOICE_COL7,workCounty);
        contentValues.put(INVOICE_COL8,workPostCode);
        contentValues.put(INVOICE_COL9,workDescription);
        contentValues.put(INVOICE_COL10,workDone);
        contentValues.put(INVOICE_COL11,hourlyRate);
        contentValues.put(INVOICE_COL12,grossTotal);
        contentValues.put(INVOICE_COL13,tax);
        contentValues.put(INVOICE_COL14,netTotal);

        long result = db.update(INVOICE_TABLE, contentValues,"invoice_no = ?", new String[] { invoiceNo });
        db.close();
        if(result == -1)
            return false;
        else {
            Globals.userData = true;
            return true;
        }


    }

    //Add Company details
    public boolean addCompanyData(String company, String address, String area, String town, String county, String postcode,
                           String telno, String bank, String accounttitle, String accountnumber, String sortcode, String utr,
                                  String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COMPANY_COL1,company);
        contentValues.put(COMPANY_COL2,address);
        contentValues.put(COMPANY_COL3,area);
        contentValues.put(COMPANY_COL4,town);
        contentValues.put(COMPANY_COL5,county);
        contentValues.put(COMPANY_COL6,postcode);
        contentValues.put(COMPANY_COL7,telno);
        contentValues.put(COMPANY_COL8,bank);
        contentValues.put(COMPANY_COL9,accounttitle);
        contentValues.put(COMPANY_COL10,accountnumber);
        contentValues.put(COMPANY_COL11,sortcode);
        contentValues.put(COMPANY_COL12,utr);
        contentValues.put(COMPANY_COL13,email);

        long result = db.insert(COMPANY_TABLE, null, contentValues);
        db.close();
        if(result == -1)
            return false;
        else {
            Globals.userData = true;
            return true;
        }
    }

    //Retrieve all Company details
    public Cursor getAllCompanyData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + COMPANY_TABLE, null);
        return res;
    }

    //Get all Contractors details
    public Cursor getAllContractorsData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + CONTRACTORS_TABLE + " order by name asc";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    //Get specific data from Days Table
    public Cursor getDaysData(int invoiceNo){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select * from " + DAYS_WORKED_TABLE +
                " WHERE " + DAYS_COL2 + " = '" + invoiceNo + "'" +
                " order by ID asc";
        Cursor data = db.rawQuery(query, null);
        //db.close();
        return data;

    }

    //Check to see if specific day exists in Days Table
    public Cursor dayExists(String invoiceNo, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select * from " + INVOICE_TABLE +
                " where " + DAYS_COL3 + " = '" +  invoiceNo + "'";
        Cursor res = db.rawQuery(query,null);
        return res;
    }

    //Edit specific invoice data
    public Cursor editInvoiceData(int invoiceNo){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select * from " + INVOICE_TABLE +
                " where " + INVOICE_COL2 + " = '" + invoiceNo + "'";
        Cursor res = db.rawQuery(query,null);
        return res;
    }

    //Retrieve specific invoice data
    public Cursor getInvoiceData(int conID){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select * from " + INVOICE_TABLE +
                " where " + INVOICE_COL1 + " = '" + conID + "'";
        Cursor res = db.rawQuery(query,null);
        return res;
    }

    //Get specific Contractor data
    public Cursor getContractorID(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select * from " + CONTRACTORS_TABLE +
                " WHERE " + CONTRACTORS_COL1 + " = '" + name + "'";
        Cursor res = db.rawQuery(query,null);
        //Globals.companyQry = query;
        //db.close();
        return res;
    }

    //Get Contractor ID
    public Cursor getContractorNumber(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select * from " + CONTRACTORS_TABLE +
                " WHERE " + CONTRACTORS_COL0 + " = '" + id + "'";
        Cursor res = db.rawQuery(query,null);
        //Globals.companyQry = query;
        //db.close();
        return res;
    }

    //Update Contractor data
    public boolean UpdateContractorsData(String id, String name, String address, String area, String town, String county, String postcode,
                                      String telno, String email, String cis){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(CONTRACTORS_COL0,id);
        contentValues.put(CONTRACTORS_COL1,name);
        contentValues.put(CONTRACTORS_COL2,address);
        contentValues.put(CONTRACTORS_COL3,area);
        contentValues.put(CONTRACTORS_COL4,town);
        contentValues.put(CONTRACTORS_COL5,county);
        contentValues.put(CONTRACTORS_COL6,postcode);
        contentValues.put(CONTRACTORS_COL7,telno);
        contentValues.put(CONTRACTORS_COL8,email);
        contentValues.put(CONTRACTORS_COL9,cis);

        db.update(CONTRACTORS_TABLE,contentValues, "ID = ?", new String[] { id });
        db.close();
        return true;
    }

    //Update Company data
    public boolean updateCompanyData(String id, String company, String address, String area, String town, String county, String postcode,
                           String telno, String bank, String accounttitle, String accountnumber, String sortcode, String utr, String email){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        //contentValues.put(COMPANY_COL0,id);
        contentValues.put(COMPANY_COL1,company);
        contentValues.put(COMPANY_COL2,address);
        contentValues.put(COMPANY_COL3,area);
        contentValues.put(COMPANY_COL4,town);
        contentValues.put(COMPANY_COL5,county);
        contentValues.put(COMPANY_COL6,postcode);
        contentValues.put(COMPANY_COL7,telno);
        contentValues.put(COMPANY_COL8,bank);
        contentValues.put(COMPANY_COL9,accounttitle);
        contentValues.put(COMPANY_COL10,accountnumber);
        contentValues.put(COMPANY_COL11,sortcode);
        contentValues.put(COMPANY_COL12,utr);
        contentValues.put(COMPANY_COL13,email);

        db.update(COMPANY_TABLE,contentValues, "ID = ?", new String[] { id });
        db.close();
        return true;

    }

    //Delete Company data
    public Integer deleteCompanyData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(COMPANY_TABLE, "ID = ?", new String[] { id });
    }

    //Delete specific day
    public Integer deleteDay(String invoiceNo, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(DAYS_WORKED_TABLE,  "invoice_no = ? AND date = ?", new String[] { invoiceNo,date });
    }

    //Delete specific Invoice
    public int deleteInvoice(String invoiceNo){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(DAYS_WORKED_TABLE, "invoice_no = ?", new String[] { invoiceNo });
    }

    //Delete specific Contractor data
    public Integer deleteContractorsData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(CONTRACTORS_TABLE, "ID = ?", new String[] { id });
    }

    //Check if specified Table has any data
    public Integer isTableEmpty(String tableName){
        SQLiteDatabase db = this.getWritableDatabase();
        String count = "SELECT count(*) FROM " + tableName;
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        db.close();
        mcursor.close();
        return icount;
    }

    //Increment the next Invoice number by one
    public Integer invoiceNumber(){
        SQLiteDatabase db = this.getWritableDatabase();
        String count = "SELECT count(*) FROM " + INVOICE_TABLE;
        Cursor cursor = db.rawQuery(count,null);
        cursor.moveToLast();
        int num = cursor.getInt(0);
        db.close();
        cursor.close();
        return num + 1;
    }

    //Add all invoices for specified Contractor to display list
    public ArrayList<Invoices> populateInvoiceList(){
        ArrayList<Invoices> arrayList = new ArrayList<>();
        Cursor cursor = getInvoiceData(Globals.contractorID);

        while (cursor.moveToNext()){
            int id = cursor.getInt(2);
            String date = cursor.getString(3);

            Invoices invoices = new Invoices(id,date);
            arrayList.add(invoices);
        }
        return arrayList;
    }

    //Add all contractors data to display list
    public ArrayList<Contractors> populateList(){
        ArrayList<Contractors> arrayList = new ArrayList<>();
        Cursor cursor = getAllContractorsData();

        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String town = cursor.getString(4);

            Contractors contractors = new Contractors(id,name,town);
            arrayList.add(contractors);
        }
        return arrayList;

    }

    //Add all Days to display list
    public ArrayList<Days> populateDays(){
        ArrayList<Days> arrayList = new ArrayList<>();
        Cursor cursor;
        InvoiceGlobals.totalHours = 0;
        if(InvoiceGlobals.editInvoice){
            cursor = getDaysData(InvoiceGlobals.invoiceNo);
        }else {
            cursor = getDaysData(Globals.invNo);
        }

        while(cursor.moveToNext()){
            String date = cursor.getString(3);
            int start = cursor.getInt(4);
            int end = cursor.getInt(5);
            int total = cursor.getInt(6);

            Days days = new Days(date,start,end,total);
            arrayList.add(days);
            //Increase variable totalHours in InvoiceGlobals
            //to reflect the hours added
            InvoiceGlobals.totalHours = InvoiceGlobals.totalHours + total;
        }
        return arrayList;
    }

    public void addTempCompanyData() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COMPANY_COL1,"Clive Tompkinson");
        contentValues.put(COMPANY_COL2,"24 Rodney Close");
        contentValues.put(COMPANY_COL3,"");
        contentValues.put(COMPANY_COL4,"Exmouth");
        contentValues.put(COMPANY_COL5,"Devon");
        contentValues.put(COMPANY_COL6,"EX8 2RP");
        contentValues.put(COMPANY_COL7,"07851804152");
        contentValues.put(COMPANY_COL8,"HSBC");
        contentValues.put(COMPANY_COL9,"Mr C R Tompkinson");
        contentValues.put(COMPANY_COL10,"12341234");
        contentValues.put(COMPANY_COL11,"402032");
        contentValues.put(COMPANY_COL12,"1777265342");
        contentValues.put(COMPANY_COL13,"cliveray@live.co.uk");

        db.insert(COMPANY_TABLE, null, contentValues);
        db.close();

    }

    public void addTempContractorsData(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(CONTRACTORS_COL1,"Red Southwest Ltd");
        contentValues.put(CONTRACTORS_COL2,"41 Yarlington Mill");
        contentValues.put(CONTRACTORS_COL3,"");
        contentValues.put(CONTRACTORS_COL4,"Cranbrook");
        contentValues.put(CONTRACTORS_COL5,"Devon");
        contentValues.put(CONTRACTORS_COL6,"EX5 4RF");
        contentValues.put(CONTRACTORS_COL7,"07654362786");
        contentValues.put(CONTRACTORS_COL8,"robwads@hotmail.com");
        contentValues.put(CONTRACTORS_COL9,"1");

        db.insert(CONTRACTORS_TABLE, null, contentValues);
        db.close();

    }

    public void addTempContractorsData2(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(CONTRACTORS_COL1,"Malcolm Hughes");
        contentValues.put(CONTRACTORS_COL2,"Foxholes");
        contentValues.put(CONTRACTORS_COL3,"Bluehayes Lane");
        contentValues.put(CONTRACTORS_COL4,"Cranbrook");
        contentValues.put(CONTRACTORS_COL5,"Devon");
        contentValues.put(CONTRACTORS_COL6,"EX5 4BD");
        contentValues.put(CONTRACTORS_COL7,"0784673524");
        contentValues.put(CONTRACTORS_COL8,"malch@gmail.com");
        contentValues.put(CONTRACTORS_COL9,"1");

        db.insert(CONTRACTORS_TABLE, null, contentValues);
        db.close();

    }

    public void addTempContractorsData3(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(CONTRACTORS_COL1,"AAA Construction Ltd");
        contentValues.put(CONTRACTORS_COL2,"Unit 14 Falcon Way");
        contentValues.put(CONTRACTORS_COL3,"Sowton");
        contentValues.put(CONTRACTORS_COL4,"Exeter");
        contentValues.put(CONTRACTORS_COL5,"Devon");
        contentValues.put(CONTRACTORS_COL6,"EX2 3BG");
        contentValues.put(CONTRACTORS_COL7,"01392 442673");
        contentValues.put(CONTRACTORS_COL8,"aaa_construction@aaa.com");
        contentValues.put(CONTRACTORS_COL9,"1");

        db.insert(CONTRACTORS_TABLE, null, contentValues);
        db.close();

    }



}
