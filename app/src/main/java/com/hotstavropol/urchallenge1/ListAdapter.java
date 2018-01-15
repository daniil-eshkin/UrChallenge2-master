package com.hotstavropol.urchallenge1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.zip.Inflater;

/**
 * Created by bkb on 13.01.2018.
 */

public class ListAdapter extends BaseAdapter{

    @Override
    public int getCount() {
        return DataBase.Disc.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chalenge_layout, viewGroup, false);
        ((TextView)view.findViewById(R.id.name)).setText(DataBase.Title[i]);
        ((TextView)view.findViewById(R.id.description)).setText(DataBase.Disc[i]);
        return view;
    }
}
