package com.example.phonepicker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class IphoneAdapter extends ArrayAdapter<iPhone> {

    public IphoneAdapter(Context context, List<iPhone> items) {
        super(context, R.layout.activity_iphone, items);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.layout_info, parent, false);


        iPhone iPhone = getItem(position);

        ((TextView) convertView.findViewById(R.id.model)).setText(iPhone.model);
        ((TextView) convertView.findViewById(R.id.os)).setText(iPhone.ios);
        ((TextView) convertView.findViewById(R.id.releaseyear)).setText(String.valueOf(iPhone.releaseyear));


        return convertView;
    }

}
