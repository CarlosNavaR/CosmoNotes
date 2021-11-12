package com.example.cosmonotes;

import android.content.Context;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cosmonotes.Models.Event;

import java.time.LocalTime;

import info.androidhive.fontawesome.FontDrawable;


public class NewEventFragment extends Fragment {
    private EditText mTituloEventoET;
    private TextView mFechaEventoTV, mHoraEventoTV;

    private LocalTime hora;
    private String selectedColor;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_event, container, false);

        mTituloEventoET = view.findViewById(R.id.eventTitleET);
        mFechaEventoTV = view.findViewById(R.id.eventDate);
        mHoraEventoTV = view.findViewById(R.id.eventTime);

        hora = LocalTime.now();

        mHoraEventoTV.setText(" " + CalendarUtils.formatoTIempo(hora));
        mFechaEventoTV.setText(" " + CalendarUtils.formatoFecha(CalendarUtils.selectedDate));

        selectedColor = "#ff0000";

        final info.androidhive.fontawesome.FontTextView imageColor1 = view.findViewById(R.id.iconColor1);
        final info.androidhive.fontawesome.FontTextView imageColor2 = view.findViewById(R.id.iconColor2);
        final info.androidhive.fontawesome.FontTextView imageColor3 = view.findViewById(R.id.iconColor3);
        final info.androidhive.fontawesome.FontTextView imageColor4 = view.findViewById(R.id.iconColor4);
        final info.androidhive.fontawesome.FontTextView imageColor5 = view.findViewById(R.id.iconColor5);
        final info.androidhive.fontawesome.FontTextView imageColor6 = view.findViewById(R.id.iconColor6);


        view.findViewById(R.id.ViewColor1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedColor = "#F1524F";
                imageColor1.setText(R.string.fa_check_circle_solid);
                imageColor2.setText(R.string.fa_circle);
                imageColor3.setText(R.string.fa_circle);
                imageColor4.setText(R.string.fa_circle);
                imageColor5.setText(R.string.fa_circle);
                imageColor6.setText(R.string.fa_circle);
            }
        });

        view.findViewById(R.id.ViewColor2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedColor = "#FEC627";
                imageColor2.setText(R.string.fa_check_circle_solid);
                imageColor1.setText(R.string.fa_circle);
                imageColor3.setText(R.string.fa_circle);
                imageColor4.setText(R.string.fa_circle);
                imageColor5.setText(R.string.fa_circle);
                imageColor6.setText(R.string.fa_circle);
            }
        });
        view.findViewById(R.id.ViewColor3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedColor = "#038ED1";
                imageColor3.setText(R.string.fa_check_circle_solid);
                imageColor2.setText(R.string.fa_circle);
                imageColor1.setText(R.string.fa_circle);
                imageColor4.setText(R.string.fa_circle);
                imageColor5.setText(R.string.fa_circle);
                imageColor6.setText(R.string.fa_circle);
            }
        });
        view.findViewById(R.id.ViewColor4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedColor = "#8362A7";
                imageColor4.setText(R.string.fa_check_circle_solid);
                imageColor2.setText(R.string.fa_circle);
                imageColor3.setText(R.string.fa_circle);
                imageColor1.setText(R.string.fa_circle);
                imageColor5.setText(R.string.fa_circle);
                imageColor6.setText(R.string.fa_circle);
            }
        });
        view.findViewById(R.id.ViewColor5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedColor = "#4BB168";
                imageColor5.setText(R.string.fa_check_circle_solid);
                imageColor2.setText(R.string.fa_circle);
                imageColor3.setText(R.string.fa_circle);
                imageColor4.setText(R.string.fa_circle);
                imageColor1.setText(R.string.fa_circle);
                imageColor6.setText(R.string.fa_circle);
            }
        });
        view.findViewById(R.id.ViewColor6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedColor = "#E86DA4";
                imageColor6.setText(R.string.fa_check_circle_solid);
                imageColor2.setText(R.string.fa_circle);
                imageColor3.setText(R.string.fa_circle);
                imageColor4.setText(R.string.fa_circle);
                imageColor5.setText(R.string.fa_circle);
                imageColor1.setText(R.string.fa_circle);
            }
        });

        view.findViewById(R.id.SaveEvent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String TituloEvento = mTituloEventoET.getText().toString();
                Event newEvent = new Event(TituloEvento, CalendarUtils.selectedDate, hora, selectedColor);
                Event.eventsList.add(newEvent);

                ocultarTeclado();
                FragmentTransaction trans = getFragmentManager().beginTransaction();
                trans.replace(R.id.fragment_Container, new CalendarFragment());
                trans.commit();
            }
        });

        return view;
    }

    // Se usa para ocultar el teclado antes de crar el evento y no modifique los elementos
    public void ocultarTeclado(){
        View vieww = getActivity().getCurrentFocus();
        if(vieww != null){
            //Aquí esta la magia
            InputMethodManager input = (InputMethodManager) (getActivity().getSystemService(Context.INPUT_METHOD_SERVICE));
            input.hideSoftInputFromWindow(vieww.getWindowToken(), 0);
        }
    }

}