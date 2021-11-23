package com.example.cosmonotes.todoModels;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cosmonotes.R;
import com.example.cosmonotes.Utils.DataBaseHelper;

import java.util.List;

import javax.security.auth.callback.Callback;

public class ToDoCheckModelAdapter extends RecyclerView.Adapter<ToDoCheckModelAdapter.MyViewHolder>{
    private List<toDoModel> mListItemsChecked;
    private Context context;
    private static DataBaseHelper db;
    private FragmentActivity activity;
    private static ToDoCheckModelAdapter.Callback callback;

    public ToDoCheckModelAdapter(DataBaseHelper db, Context context, FragmentActivity activity){
        this.context = context;
        ToDoCheckModelAdapter.db = db;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.to_do_item , parent , false);
        return new ToDoCheckModelAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final toDoModel ToDoItem = mListItemsChecked.get(position);

        holder.ItemToDoCB.setText(ToDoItem.getTask());
        holder.ItemToDoCB.setTextColor(Integer.parseInt(String.valueOf(Color.parseColor("#808080"))));
        holder.ItemToDoCB.setChecked(db.ConvertIntToBoolean(ToDoItem.getStatus()));
        holder.ItemToDoCB.setPaintFlags(holder.ItemToDoCB.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.OperacionesItem.setText(R.string.fa_trash_solid);
        holder.OperacionesItem.setTextColor(activity.getResources().getColor(R.color.eventoRojo));

        holder.OperacionesItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Advertencia");
                builder.setMessage("Esta seguro que desea eliminar esta tarea?");
                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DeleteItemChecked(position);
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        notifyItemChanged(position);
                    }
                });
                AlertDialog dialogError = builder.create();
                dialogError.show();
            }
        });

        holder.bind(ToDoItem);
    }

    public void setListItemsChecked(List<toDoModel> mListItemsChecked){
        this.mListItemsChecked = mListItemsChecked;
        notifyDataSetChanged();
    }

    public void DeleteItemChecked(int position){
        toDoModel item = mListItemsChecked.get(position);
        db.RemoveItemList(item.getIdItem());
        mListItemsChecked.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return mListItemsChecked.size();
    }

    // Sets the callback
    public void setCallback(ToDoCheckModelAdapter.Callback callback) {
        ToDoCheckModelAdapter.callback = callback;
    }

    // Callback interface, used to notify when an item's checked status changed
    public interface Callback {
        void onCheckedChanged(String item, boolean isChecked);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private CheckBox ItemToDoCB;
        private info.androidhive.fontawesome.FontTextView OperacionesItem;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ItemToDoCB = itemView.findViewById(R.id.cb_item);
            OperacionesItem = itemView.findViewById(R.id.OperacionesItemsFTV);
        }

        void bind(toDoModel s) {
            // Listen to changes (i.e. when the user checks or unchecks the box)
            ItemToDoCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // Invoke the callback
                    if(!isChecked){
                        db.updateStatusItem(s.getIdItem(), 0);
                        //db.updateStatusItem(s.getIdItem(), 1);
                    }
                    if(callback != null) callback.onCheckedChanged(s.getTask(), isChecked);
                }
            });
        }
    }
}
