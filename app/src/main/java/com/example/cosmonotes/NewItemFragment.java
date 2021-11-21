package com.example.cosmonotes;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.cosmonotes.CalendarModels.OnDialogCloseListner;
import com.example.cosmonotes.Utils.DataBaseHelper;
import com.example.cosmonotes.todoModels.toDoModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class NewItemFragment extends BottomSheetDialogFragment {
    public static final String TAG = "AddNewItem";

    private EditText mTituloItemET;
    private boolean isUpdate = false;
    private DataBaseHelper db;
    private int IdGroup;

    public static NewItemFragment newInstance(){
        return new NewItemFragment();
    }

    public int getIdGroup() {
        return IdGroup;
    }

    public void setIdGroup(int idGroup) {
        IdGroup = idGroup;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_item, container, false);

        db = new DataBaseHelper(getActivity());
        mTituloItemET = view.findViewById(R.id.ToDoTextET);

        final Bundle bundle = getArguments();
        if(bundle != null){
            isUpdate = true;
            String task = bundle.getString("task");
            mTituloItemET.setText(task);
        }

        final boolean updateItem = isUpdate;

        view.findViewById(R.id.SaveItemBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tituloItem = mTituloItemET.getText().toString();

                if(updateItem){
                    toDoModel item = new toDoModel(tituloItem, 0,bundle.getInt("idGroup"));
                    db.UpdateItemToDo(bundle.getInt("id"), item);
                }
                else
                    db.saveItemToDo(tituloItem, getIdGroup());
                dismiss();
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

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if(getActivity() instanceof OnDialogCloseListner)
            ((OnDialogCloseListner)getActivity()).onDialogClose(dialog);
    }


}