package com.example.cosmonotes;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cosmonotes.CalendarModels.Event;
import com.example.cosmonotes.CalendarModels.OnDialogCloseListner;
import com.example.cosmonotes.Utils.DataBaseHelper;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.time.LocalTime;

import es.dmoral.toasty.Toasty;


public class NewEventFragment extends BottomSheetDialogFragment {
    public static final String TAG = "AddNewEvent";

    private EditText mTituloEventoET;
    private TextView mFechaEventoTV, mHoraEventoTV;
    private boolean isUpdate = false;
    private LocalTime hora;
    private String selectedColor;

    private DataBaseHelper db;

    public static NewEventFragment newInstance(){
        return new NewEventFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_event, container, false);

        db = new DataBaseHelper(getActivity());

        mTituloEventoET = view.findViewById(R.id.eventTitleET);
        mFechaEventoTV = view.findViewById(R.id.eventDate);
        mHoraEventoTV = view.findViewById(R.id.eventTime);
        selectedColor = "#F1524F";

        final info.androidhive.fontawesome.FontTextView imageColor1 = view.findViewById(R.id.iconColor1);
        final info.androidhive.fontawesome.FontTextView imageColor2 = view.findViewById(R.id.iconColor2);
        final info.androidhive.fontawesome.FontTextView imageColor3 = view.findViewById(R.id.iconColor3);
        final info.androidhive.fontawesome.FontTextView imageColor4 = view.findViewById(R.id.iconColor4);
        final info.androidhive.fontawesome.FontTextView imageColor5 = view.findViewById(R.id.iconColor5);
        final info.androidhive.fontawesome.FontTextView imageColor6 = view.findViewById(R.id.iconColor6);
        hora = LocalTime.now();

        final Bundle bundle = getArguments();

        if(bundle != null){
            isUpdate = true;
            String title = bundle.getString("event");
            String time = bundle.getString("time");
            mTituloEventoET.setText(title);
            mFechaEventoTV.setText(CalendarUtils.formatoFecha(CalendarUtils.selectedDate));
            selectedColor = bundle.getString("color");
            mHoraEventoTV.setText(time);

            if(Color.parseColor(selectedColor) == Color.parseColor("#F1524F"))
                imageColor1.setText(R.string.fa_check_circle_solid);
            if(Color.parseColor(selectedColor) == Color.parseColor("#FEC627")){
                imageColor1.setText(R.string.fa_circle);
                imageColor2.setText(R.string.fa_check_circle_solid);
            }
            if(Color.parseColor(selectedColor) == Color.parseColor("#FEC627")){
                imageColor1.setText(R.string.fa_circle);
                imageColor2.setText(R.string.fa_check_circle_solid);
            }
            if(Color.parseColor(selectedColor) == Color.parseColor("#038ED1")){
                imageColor1.setText(R.string.fa_circle);
                imageColor3.setText(R.string.fa_check_circle_solid);
            }
            if(Color.parseColor(selectedColor) == Color.parseColor("#8362A7")){
                imageColor1.setText(R.string.fa_circle);
                imageColor4.setText(R.string.fa_check_circle_solid);
            }
            if(Color.parseColor(selectedColor) == Color.parseColor("#4BB168")){
                imageColor1.setText(R.string.fa_circle);
                imageColor5.setText(R.string.fa_check_circle_solid);
            }
            if(Color.parseColor(selectedColor) == Color.parseColor("#E86DA4")){
                imageColor1.setText(R.string.fa_circle);
                imageColor6.setText(R.string.fa_check_circle_solid);
            }
        }else{
            mHoraEventoTV.setText(" " + CalendarUtils.formatoTIempo(hora));
            mFechaEventoTV.setText(" " + CalendarUtils.formatoFecha(CalendarUtils.selectedDate));
        }

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

        final boolean updateEvent = isUpdate;

        view.findViewById(R.id.SaveEvent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(!mTituloEventoET.getText().toString().isEmpty()){
                   String TituloEvento = mTituloEventoET.getText().toString();
                   Event newEvent = new Event(TituloEvento, CalendarUtils.selectedDate, hora, selectedColor);

                   if(updateEvent)
                       db.updateEvent(bundle.getInt("id"), newEvent);
                   else
                       db.saveEvent(newEvent);
                   //Event.eventsList.add(newEvent);

                   ocultarTeclado();
                   FragmentTransaction trans = getFragmentManager().beginTransaction();
                   trans.replace(R.id.fragment_Container, new CalendarFragment());
                   trans.commit();
                   if(updateEvent)
                       Toasty.success(getContext(), "Evento actualizado correctamente", Toast.LENGTH_SHORT, true).show();
                   else
                       Toasty.success(getContext(), "Evento creado correctamente", Toast.LENGTH_SHORT, true).show();
               }else{
                   Toasty.warning(getContext(), "Ning??n campo debe estar vac??o", Toast.LENGTH_SHORT, true).show();
               }
            }
        });
        return view;
    }

    // Se usa para ocultar el teclado antes de crar el evento y no modifique los elementos
    public void ocultarTeclado(){
        View vieww = getActivity().getCurrentFocus();
        if(vieww != null){
            //Aqu?? esta la magia
            InputMethodManager input = (InputMethodManager) (getActivity().getSystemService(Context.INPUT_METHOD_SERVICE));
            input.hideSoftInputFromWindow(vieww.getWindowToken(), 0);
        }
    }
}