package com.example.phonepicker;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class AndroidAdapter extends ArrayAdapter<Android> {

    public AndroidAdapter(Context context, List<Android> items) {
        super(context, R.layout.activity_android, items);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.layout_info, parent, false);


        Android android = getItem(position);

        ((TextView) convertView.findViewById(R.id.model)).setText(android.model);
        ((TextView) convertView.findViewById(R.id.os)).setText(android.os);
        ((TextView) convertView.findViewById(R.id.releaseyear)).setText(String.valueOf(android.released));


        return convertView;
    }
}
