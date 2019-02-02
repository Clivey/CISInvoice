package com.software.kinson.cisinvoicegenerator;

public class DbUtils {
    public static final String COMPANY_TABLE = "companyTbl";
    public static final String CONTRACTORS_TABLE = "contractorsTbl";
    public static final String INVOICE_TABLE = "invoiceTbl";
    public static final String DAYS_WORKED_TABLE = "days_workedTbl";

    public DbUtils(DatabaseHelper databaseHelper) {
    }

    public static String createCompanyTable() {
        String tableQry = null;

        tableQry = "create table if not exists " + COMPANY_TABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "company_name TEXT NOT NULL, address TEXT NOT NULL, area TEXT, town TEXT NOT NULL, county TEXT NOT NULL," +
                "post_code TEXT NOT NULL, tel_no TEXT NOT NULL, bank TEXT NOT NULL,account_title TEXT NOT NULL," +
                "account_number INTEGER NOT NULL,sort_code TEXT NOT NULL, utr_number TEXT NOT NULL," +
                "email TEXT NOT NULL);";
        //Globals.companyQry = tableQry;
        return tableQry;
    }

    public static String createInvoiceTable() {
        String str = null;

        str = "CREATE TABLE if not exists " + INVOICE_TABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " contractor_ID INTEGER NOT NULL," +
                " invoice_no INTEGER NOT NULL, date text NOT NULL, work_addr TEXT, work_area text," +
                " work_town TEXT, work_county TEXT, work_postCode TEXT, work_description TEXT NOT NULL," +
                " work_done text NOT NULL, hourly_rate real NOT NULL, gross_total real NOT NULL, tax real, net_total real);";
        return str;
    }

    public static String createContractorTable() {
        String str = null;

        str = "CREATE TABLE if not exists " + CONTRACTORS_TABLE + " (ID integer PRIMARY KEY AUTOINCREMENT," +
                " name TEXT NOT NULL, address TEXT NOT NULL, area TEXT, town TEXT NOT NULL, " +
                " county TEXT NOT NULL, post_code TEXT NOT NULL, tel_no INTEGER NOT NULL, email TEXT NOT NULL," +
                " cis INTEGER);";
        return str;
    }

    public static String createDaysWorkedTable() {
        String str = null;

        str = "CREATE TABLE IF NOT EXISTS " + DAYS_WORKED_TABLE + " (ID integer PRIMARY KEY AUTOINCREMENT," +
                " contractors_id integer not null, invoice_no integer not null, date text not null," +
                " start_time real not null, end_time real not null, hours_worked real not null);";
        //Globals.companyQry = str;
        return str;
    }

    public static String createInvoiceView() {
        String str = null;
        str = "CREATE VIEW IF NOT EXISTS invoiceView AS SELECT" +
                " companyTbl.ID as companyTbl_ID," +
                " companyTbl.company_name as companyTbl_name," +
                " companyTbl.address as companyTbl_address," +
                " companyTbl.area as companyTbl_area," +
                " companyTbl.town as companyTbl_town," +
                " companyTbl.county as companyTbl_county," +
                " companyTbl.post_code as companyTbl_post_code," +
                " companyTbl.tel_no as companyTbl_tel_no," +
                " companyTbl.bank as companyTbl_bank," +
                " companyTbl.account_title as companyTbl_account_title," +
                " companyTbl.account_number as companyTbl_account_number," +
                " companyTbl.sort_code as companyTbl_sort_code," +
                " companyTbl.utr_number as companyTbl_utr_number," +
                " companyTbl.email as companyTbl_email," +
                " contractorsTbl.ID as contractorsTbl_ID," +
                " contractorsTbl.name as contractorsTbl_name," +
                " contractorsTbl.address as contractorsTbl_address," +
                " contractorsTbl.area as contractorsTbl_area," +
                " contractorsTbl.town as contractorsTbl_town," +
                " contractorsTbl.county as contractorsTbl_county," +
                " contractorsTbl.post_code as contractorsTbl_post_code," +
                " contractorsTbl.tel_no as contractorsTbl_tel_no," +
                " contractorsTbl.email as contractorsTbl_email," +
                " invoiceTbl.ID as invoiceTbl_ID," +
                " invoiceTbl.invoice_no as invoiceTbl_invoice_no," +
                " invoiceTbl.date as invoiceTbl_date," +
                " invoiceTbl.work_addr as invoiceTbl_work_addr," +
                " invoiceTbl.work_area as invoiceTbl_work_area," +
                " invoiceTbl.work_town as invoiceTbl_work_town," +
                " invoiceTbl.work_county as invoiceTbl_work_county," +
                " invoiceTbl.work_postCode as invoiceTbl_work_postCode," +
                " invoiceTbl.work_desciption as invoiceTbl_work_description," +
                " invoiceTbl.work_done as invoiceTbl_work_done," +
                " invoiceTbl.hourly_rate as invoiceTbl_hourly_rate," +
                " invoiceTbl.gross_total as invoiceTbl_gross_total," +
                " invoiceTbl.tax as invoiceTbl_tax," +
                " invoiceTbl.net_total as invoiceTbl_net," +
                " invoiceTbl.cis as invoiceTbl_cis," +
                " days_workedTbl.ID as days_workedTbl_ID," +
                " days_workedTbl.contractors_id as days_workedTbl_contractors_id," +
                " days_workedTbl.invoice_no as days_workedTbl_invoice_no," +
                " days_workedTbl.date as days_workedTbl_date," +
                " days_workedTbl.hours_worked as days_workedTbl_hours_worked" +
                " FROM" +
                " companyTbl," +
                " contractorsTbl," +
                " invoiceTbl," +
                " days_workedTbl;";
        //Globals.companyQry = str;
        return str;
    }


}
