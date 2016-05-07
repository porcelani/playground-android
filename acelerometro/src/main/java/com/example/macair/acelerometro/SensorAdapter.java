package com.example.macair.acelerometro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by Edlaine on 07/05/16.
 */
public class SensorAdapter extends BaseAdapter {
    private Context			context;
    private SensorInfo[]	list;

    SensorAdapter (Context context, SensorInfo[] list) {
        this.context	= context;
        this.list		= list;
    }

    @Override
    public int getCount() {
        return list.length;
    }

    @Override
    public Object getItem (int pos) {
        return list[pos];
    }

    @Override
    public long getItemId (int pos) {
        return pos;
    }

    @Override
    public View getView (int pos, View view, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService (Context.LAYOUT_INFLATER_SERVICE);

        if (view == null) {
            view = inflater.inflate (R.layout.sensordata, parent, false);
        }

        SensorInfo si = list[pos];

        ((TextView)view.findViewById (R.id.sensorName)  ).setText (si.getName());
        ((TextView)view.findViewById (R.id.sensorValue1)).setText (String.valueOf(si.getValue(0)));
        ((TextView)view.findViewById (R.id.sensorValue2)).setText (String.valueOf(si.getValue(1)));
        ((TextView)view.findViewById (R.id.sensorValue3)).setText (String.valueOf(si.getValue(2)));

        return view;
    }
}
