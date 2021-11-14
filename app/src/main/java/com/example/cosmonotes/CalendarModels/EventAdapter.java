package com.example.cosmonotes.CalendarModels;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cosmonotes.CalendarUtils;
import com.example.cosmonotes.MainActivity;
import com.example.cosmonotes.NewEventFragment;
import com.example.cosmonotes.R;
import com.example.cosmonotes.Utils.DataBaseHelper;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder> {
    private List<Event> mList;
    private Context context;
    private DataBaseHelper db;
    FragmentActivity activity;


    public EventAdapter(DataBaseHelper db, Context context, FragmentActivity activity){
        this.context = context;
        this.db = db;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_cell , parent , false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Event event = mList.get(position);
        GradientDrawable gradientDrawable = (GradientDrawable) holder.LLTagColor.getBackground();
        gradientDrawable.setColor(Color.parseColor(event.getColorNote()));
        holder.eventCellTV.setText(event.getTitulo());
        holder.eventTimeCell.setText(CalendarUtils.formatoTIempo(event.getTime()));
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setEvents(List<Event> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    public void deleteEvent(int position){
        Event event = mList.get(position);
        db.deleteEvent(event.getId());
        mList.remove(position);
        notifyItemRemoved(position);
    }

    public void updateEvent(int position){
        Event event = mList.get(position);

        Bundle bundle = new Bundle();
        bundle.putInt("id", event.getId());
        bundle.putString("event", event.getTitulo());
        bundle.putString("date", event.getDate().toString());
        bundle.putString("time", event.getTime().toString());
        bundle.putString("color", event.getColorNote());

        NewEventFragment eventFragment = new NewEventFragment();
        eventFragment.setArguments(bundle);
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_Container, eventFragment).commit();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView eventCellTV;
        TextView eventTimeCell;
        LinearLayout LLTagColor;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
             eventCellTV = itemView.findViewById(R.id.eventCellTV);
             eventTimeCell = itemView.findViewById(R.id.TimeCell);
             LLTagColor = itemView.findViewById(R.id.TagColorLY);
        }
    }
}
