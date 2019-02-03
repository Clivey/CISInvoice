package com.software.kinson.cisinvoicegenerator.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.software.kinson.cisinvoicegenerator.Model.Invoices;
import com.software.kinson.cisinvoicegenerator.R;

import java.util.ArrayList;

public class InvoiceAdapter extends BaseAdapter {
    Context context;
    ArrayList<Invoices> arrayList;

    public InvoiceAdapter(Context context, ArrayList<Invoices> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return this.arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.custom_invoice,null);
        TextView t1_id = convertView.findViewById(R.id.tvInvoiceNoID);
        TextView t2_name = convertView.findViewById(R.id.tvInvoiceDateID);

        Invoices invoices = arrayList.get(position);

        t1_id.setText(String.valueOf(invoices.getId()));
        t2_name.setText(invoices.getDate());

        return convertView;
    }
}
