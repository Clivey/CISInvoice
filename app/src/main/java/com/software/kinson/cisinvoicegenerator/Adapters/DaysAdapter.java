package com.software.kinson.cisinvoicegenerator.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.software.kinson.cisinvoicegenerator.Model.Days;
import com.software.kinson.cisinvoicegenerator.R;

import java.util.ArrayList;

public class DaysAdapter extends BaseAdapter {

    Context context;
    ArrayList<Days> arrayList;

    public DaysAdapter(Context context, ArrayList<Days> arrayList) {
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
        convertView = layoutInflater.inflate(R.layout.custom_day_view,null);
        TextView t_id = convertView.findViewById(R.id.tvDayID);
        TextView t1_date = convertView.findViewById(R.id.tvDayDateID);
        TextView t2_start = convertView.findViewById(R.id.tvDayStartID);
        TextView t3_end = convertView.findViewById(R.id.tvDayEndID);
        TextView t4_total = convertView.findViewById(R.id.tvDayTotalID);

        Days days = arrayList.get(position);
        int dayID = days.getDayID();
        float start = days.getStartTime();
        float end = days.getEndTime();
        float totalTime = days.getTotalTime();

        t_id.setText(String.valueOf(dayID));
        t1_date.setText(days.getDate());
        t2_start.setText(String.format("%.2f",start));
        t3_end.setText(String.format("%.2f",end));
        t4_total.setText(String.format("%.2f",totalTime));

        return convertView;
    }
}
