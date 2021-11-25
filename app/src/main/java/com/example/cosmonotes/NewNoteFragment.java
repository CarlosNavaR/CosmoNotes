package com.example.cosmonotes;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

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
import com.example.cosmonotes.Notes.Notes;
import com.example.cosmonotes.Notes.TtsManager;
import com.example.cosmonotes.Utils.DataBaseHelper;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import es.dmoral.toasty.Toasty;

public class NewNoteFragment extends Fragment {
    public static final String TAG = "AddNewNote";
    private EditText mContenidoTV, mTituloTV;
    private String selectedColor;
    private TextView txtVFechaNota;
    private info.androidhive.fontawesome.FontTextView VozIconFAW;

    private boolean isUpdate = false;
    private boolean PlayNote = false;

    private DataBaseHelper db;

    private TtsManager ttsManager = null;  //Objeto que convierte el texto a voz
    private int stopTtsManager = 0;  // Variable para parar la lectura del texto

    public static NewNoteFragment newInstance(){return new NewNoteFragment();}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_note, container, false);
        db = new DataBaseHelper(getActivity());
        mTituloTV = view.findViewById(R.id.EtxtTitulo);
        mContenidoTV = view.findViewById(R.id.EtxtContenido);
        txtVFechaNota = view.findViewById(R.id.txtVFechaNota);
        VozIconFAW = view.findViewById(R.id.readNote);

        LocalDate localDate = LocalDate.now();//For reference
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE dd MMMM yyyy");
        txtVFechaNota.setText(String.valueOf(localDate.format(formatter)));

        selectedColor = "#F1524F";

        final info.androidhive.fontawesome.FontTextView imageColor1 = view.findViewById(R.id.iconColor1);
        final info.androidhive.fontawesome.FontTextView imageColor2 = view.findViewById(R.id.iconColor2);
        final info.androidhive.fontawesome.FontTextView imageColor3 = view.findViewById(R.id.iconColor3);
        final info.androidhive.fontawesome.FontTextView imageColor4 = view.findViewById(R.id.iconColor4);
        final info.androidhive.fontawesome.FontTextView imageColor5 = view.findViewById(R.id.iconColor5);
        final info.androidhive.fontawesome.FontTextView imageColor6 = view.findViewById(R.id.iconColor6);

        final Bundle bundle = getArguments();
        if(bundle != null) {
            comprobarContenido();
            isUpdate = true;
            String title = bundle.getString("Titulo");
            String content = bundle.getString("Contenido");
            selectedColor = bundle.getString("color");

            mTituloTV.setText(title);
            mContenidoTV.setText(content);

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

        final boolean updateNote = isUpdate;
        final boolean play = PlayNote;
        VozIconFAW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(play){
                    PlayNote = false;
                    VozIconFAW.setText(R.string.fa_volume_mute_solid);
                }else{
                    PlayNote = true;
                    VozIconFAW.setText(R.string.fa_volume_up_solid);
                    readingDescription(0);
                }
            }
        });

        view.findViewById(R.id.NewNote).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mTituloTV.getText().toString().isEmpty() && !mContenidoTV.getText().toString().isEmpty()){
                    String TituloEvento = mTituloTV.getText().toString();
                    String contenido = mContenidoTV.getText().toString();
                    Notes newNote = new Notes(TituloEvento, contenido, selectedColor, localDate,0);

                    if(updateNote)
                        db.actualizarNota(bundle.getInt("id"), newNote);
                    else
                        db.insertarNota(newNote);

                    ocultarTeclado();
                    FragmentTransaction trans = getFragmentManager().beginTransaction();
                    trans.replace(R.id.fragment_Container, new NotesFragment());
                    trans.commit();

                    if(updateNote)
                        Toasty.success(getContext(), "Nota actualizada correctamente", Toast.LENGTH_SHORT, true).show();
                    else
                        Toasty.success(getContext(), "Nota creada correctamente", Toast.LENGTH_SHORT, true).show();
                }else{
                    Toasty.warning(getContext(), "Ningún campo debe estar vacío", Toast.LENGTH_SHORT, true).show();
                }
            }
        });
        return view;
    }

    public void ocultarTeclado(){
        View vieww = getActivity().getCurrentFocus();
        if(vieww != null){
            //Aquí esta la magia
            InputMethodManager input = (InputMethodManager) (getActivity().getSystemService(Context.INPUT_METHOD_SERVICE));
            input.hideSoftInputFromWindow(vieww.getWindowToken(), 0);
        }
    }

    private void comprobarContenido() {
        if (mContenidoTV.getText().toString().isEmpty()) {
            //Se inicializa el obejeto ttsManager y se llama al metodo init() para inicializar los atributos de la clase
            ttsManager = new TtsManager();
            ttsManager.init(this);
            VozIconFAW.setText(R.string.fa_volume_mute_solid);
        } else {
            VozIconFAW.setText(R.string.fa_volume_off_solid);
        }
    }

    // Se inicia o detiene la lectura de voz
    public void readingDescription(int stopTtsManager) {
        switch (stopTtsManager) {
            case 0:
                stopTtsManager = 1;
                //le pasamos el string que queremos que convierta a voz el Objeto ttsManager
                ttsManager.initQueue(mContenidoTV.getText().toString());
                break;
            case 1:
                stopTtsManager = 0;
                ttsManager.stop();
                break;
        }
    }

    // Se destruye el lector de voz para que no consuma recursos del sistema
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (ttsManager != null) {
            ttsManager.shutDown();
        }
    }

}