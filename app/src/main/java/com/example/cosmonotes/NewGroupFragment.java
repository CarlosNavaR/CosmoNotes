package com.example.cosmonotes;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.cosmonotes.CalendarModels.Event;
import com.example.cosmonotes.CalendarModels.OnDialogCloseListner;
import com.example.cosmonotes.Utils.DataBaseHelper;
import com.example.cosmonotes.todoModels.groupModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.security.acl.Group;
import java.time.LocalTime;

public class NewGroupFragment extends BottomSheetDialogFragment {
    public static final String TAG = "AddNewGroup";

    private EditText mTituloGroupET;
    private boolean isUpdate = false;
    private String selectedColor;

    private DataBaseHelper db;

    public static NewGroupFragment newInstance() {
        return new NewGroupFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_group, container, false);;

        db = new DataBaseHelper(getActivity());

        mTituloGroupET = view.findViewById(R.id.GroupTitleET);
        selectedColor = "#F1524F";

        final info.androidhive.fontawesome.FontTextView imageColor1 = view.findViewById(R.id.iconColor1);
        final info.androidhive.fontawesome.FontTextView imageColor2 = view.findViewById(R.id.iconColor2);
        final info.androidhive.fontawesome.FontTextView imageColor3 = view.findViewById(R.id.iconColor3);
        final info.androidhive.fontawesome.FontTextView imageColor4 = view.findViewById(R.id.iconColor4);
        final info.androidhive.fontawesome.FontTextView imageColor5 = view.findViewById(R.id.iconColor5);
        final info.androidhive.fontawesome.FontTextView imageColor6 = view.findViewById(R.id.iconColor6);

        final Bundle bundle = getArguments();

        if(bundle != null){
            isUpdate = true;
            String title = bundle.getString("group");
            mTituloGroupET.setText(title);
            selectedColor = bundle.getString("color");

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

        final boolean updateGroup = isUpdate;

        view.findViewById(R.id.SaveGroup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String TituloGroup = mTituloGroupET.getText().toString();
                groupModel newGroup = new groupModel(TituloGroup, selectedColor);

                if(updateGroup)
                    db.updateGroup(bundle.getInt("id"), newGroup);
                else
                    db.saveGroupToDo(newGroup);

                ocultarTeclado();
                FragmentTransaction trans = getFragmentManager().beginTransaction();
                trans.replace(R.id.fragment_Container, new ToDoFragment());
                trans.commit();
            }
        });
        return view;
    }


    // Se usa para ocultar el teclado antes de crar el evento y no modifique los elementos
    public void ocultarTeclado(){
        View view = getActivity().getCurrentFocus();
        if(view != null){
            //Aquí esta la magia
            InputMethodManager input = (InputMethodManager) (getActivity().getSystemService(Context.INPUT_METHOD_SERVICE));
            input.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}