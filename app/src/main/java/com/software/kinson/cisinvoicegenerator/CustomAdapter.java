package com.software.kinson.cisinvoicegenerator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

class CustomAdapter extends ArrayAdapter<ListContractor> {

    private Context mContext;
    int mResource;

    public CustomAdapter(Context context, int resource, List<ListContractor> objects, Context mContext, int mResource) {
        super(context, resource, objects);
        this.mContext = mContext;
        this.mResource = mResource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String ID = getItem(position).getName();
        String name = getItem(position).getName();
        String town = getItem(position).getTown();

        ListContractor listContractor = new ListContractor(ID, name, town);

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        //View view = layoutInflater.inflate(R.layout.custom_row, parent, false);
        convertView = layoutInflater.inflate(mResource,parent,false);

        TextView tvID = convertView.findViewById(R.id.tvCustomRowID);
        TextView tvName = convertView.findViewById(R.id.tvCustomRowName);
        TextView tvTown = convertView.findViewById(R.id.tvCustomRowTown);

        tvID.setText(ID);
        tvName.setText(name);
        tvTown.setText(town);

        return convertView;
    }


}
