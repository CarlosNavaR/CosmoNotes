package com.example.cosmonotes;

import static com.example.cosmonotes.CalendarUtils.diasSemanaArray;
import static com.example.cosmonotes.CalendarUtils.mesAnioFecha;

import android.icu.util.LocaleData;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cosmonotes.Models.Event;
import com.example.cosmonotes.Models.EventAdapter;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Locale;

public class CalendarFragment extends Fragment implements CalendarAdapter.OnItemListener{
    private RecyclerView mCalendarRecycleView;
    private TextView mMesAnioTV;
    private ListView meventListVIew;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        mCalendarRecycleView = view.findViewById(R.id.calendarRecyclerView);
        mMesAnioTV = view.findViewById(R.id.monthYearTV);
        meventListVIew = view.findViewById(R.id.eventList);
        setWeekView();

        view.findViewById(R.id.NextWeekAction).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1);
                setWeekView();
            }
        });
        view.findViewById(R.id.PreviousWeekAction).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1);
                setWeekView();
            }
        });

        return view;
    }

    public void setWeekView(){
        mMesAnioTV.setText(mesAnioFecha(CalendarUtils.selectedDate));
        ArrayList<LocalDate> dias =  diasSemanaArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(dias, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 7);
        mCalendarRecycleView.setLayoutManager(layoutManager);
        mCalendarRecycleView.setAdapter(calendarAdapter);
        setEventAdpater();
    }


    private void setEventAdpater()
    {
        ArrayList<Event> dailyEvents = Event.eventsForDate(CalendarUtils.selectedDate);
        EventAdapter eventAdapter = new EventAdapter(getContext(), dailyEvents);
        meventListVIew.setAdapter(eventAdapter);
    }


    @Override
    public void onItemClick(int position, LocalDate date) {
        CalendarUtils.selectedDate = date;
        setWeekView();
    }
}