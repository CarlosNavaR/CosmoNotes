package com.example.cosmonotes.todoModels;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cosmonotes.R;

import java.util.ArrayList;

public class Adapter_item_check extends RecyclerView.Adapter<Adapter_item_check.ItemViewHolder> {
    private ArrayList<elemento> mElementos;

    private OnItemClickListenerCH mCHListener;
    public interface OnItemClickListenerCH{
        void OnItemClickCH(int position);
        void OnCheckboxClickCH(int position);
    }
    public void setOnItemClickListenerCH(OnItemClickListenerCH listenerCH){mCHListener = listenerCH;}

    public static class ItemViewHolder extends RecyclerView.ViewHolder{
        public CheckBox mCheckBox_estadoTodoItem;


        public ItemViewHolder(View itemView, OnItemClickListenerCH listenerCH) {
            super( itemView );
            mCheckBox_estadoTodoItem = itemView.findViewById(R.id.cb_item);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listenerCH != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listenerCH.OnItemClickCH(position);
                        }
                    }
                }
            });
            mCheckBox_estadoTodoItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(listenerCH != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listenerCH.OnCheckboxClickCH(position);
                        }
                    }
                }
            });

        }
    }

    public Adapter_item_check(ArrayList<elemento> ListaElementos){
        mElementos = ListaElementos;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        elemento elementoActual = mElementos.get( position );
        holder.mCheckBox_estadoTodoItem.setText( elementoActual.getTitulo() );
        if (elementoActual.isEstado()){
            holder.mCheckBox_estadoTodoItem.setChecked( true );
        }
        else{holder.mCheckBox_estadoTodoItem.setChecked( false );}

        holder.mCheckBox_estadoTodoItem.setOnClickListener( new View.OnClickListener() { // TODO BORRAR SI INNECESARIO
            @Override
            public void onClick(View view) { // TODO EVENTOS POR ITEM
            }
        } );
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate( R.layout.to_do_item, parent,false );
        ItemViewHolder evh = new ItemViewHolder(v, mCHListener);
        return evh;
    }

    @Override
    public int getItemCount() {
        return mElementos.size();
    }


}