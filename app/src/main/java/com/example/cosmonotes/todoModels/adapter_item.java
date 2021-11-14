package com.example.cosmonotes.todoModels;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cosmonotes.R;

import java.util.ArrayList;

public class adapter_item extends RecyclerView.Adapter<adapter_item.ItemViewHolder> {
    private ArrayList<elemento> mElementos;
    private OnItemClickListenerUC mUCListener;

    public interface OnItemClickListenerUC{
        void OnItemClickUC(int position);
        void OnCheckboxClickUC(int position);
    }
    public void setOnItemClickListenerUC(OnItemClickListenerUC listenerUC){mUCListener = listenerUC;}

    public static class ItemViewHolder extends RecyclerView.ViewHolder{
        public CheckBox mCheckBox_estadoTodoItem;


        public ItemViewHolder(View itemView, OnItemClickListenerUC listenerUC) {
            super( itemView );
            mCheckBox_estadoTodoItem = itemView.findViewById(R.id.cb_item);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listenerUC != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listenerUC.OnItemClickUC(position);
                        }
                    }
                }
            });
            mCheckBox_estadoTodoItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(listenerUC != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listenerUC.OnCheckboxClickUC(position);
                        }
                    }
                }
            });

        }
    }

    public adapter_item(ArrayList<elemento> ListaElementos){
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
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate( R.layout.to_do_item, parent,false );
        ItemViewHolder evh = new ItemViewHolder(v, mUCListener);
        return evh;
    }

    @Override
    public int getItemCount() {
        return mElementos.size();
    }


}