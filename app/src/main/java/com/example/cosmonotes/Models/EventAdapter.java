package com.example.cosmonotes.Models;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.cosmonotes.CalendarUtils;
import com.example.cosmonotes.R;

import java.util.List;

public class EventAdapter extends ArrayAdapter<Event> {


    public EventAdapter(@NonNull Context context, List<Event> events){
        super(context, 0, events);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        Event event = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.event_cell, parent, false);

        TextView eventCellTV = convertView.findViewById(R.id.eventCellTV);
        TextView eventTimeCell = convertView.findViewById(R.id.TimeCell);
        LinearLayout LLTagColor = convertView.findViewById(R.id.TagColorLY);

        GradientDrawable gradientDrawable = (GradientDrawable) LLTagColor.getBackground();
        gradientDrawable.setColor(Color.parseColor(event.getColorNote()));
        eventCellTV.setText(event.getTitulo());
        eventTimeCell.setText(CalendarUtils.formatoTIempo(event.getTime()));
        return convertView;
    }


}
