package com.software.kinson.cisinvoicegenerator.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.software.kinson.cisinvoicegenerator.Model.Contractors;
import com.software.kinson.cisinvoicegenerator.R;

import java.util.ArrayList;

public class ContractorsAdapter extends BaseAdapter {

    Context context;
    ArrayList<Contractors> arrayList;

    public ContractorsAdapter(Context context, ArrayList<Contractors> arrayList) {
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
        convertView = layoutInflater.inflate(R.layout.custom_row,null);
        TextView t1_id = convertView.findViewById(R.id.tvCustomRowID);
        TextView t2_name = convertView.findViewById(R.id.tvCustomRowName);
        TextView t3_town = convertView.findViewById(R.id.tvCustomRowTown);

        Contractors contractors = arrayList.get(position);

        t1_id.setText(String.valueOf(contractors.getId()));
        t2_name.setText(contractors.getName());
        t3_town.setText(contractors.getTown());

        return convertView;
    }
}
