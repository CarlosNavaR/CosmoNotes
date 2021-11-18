package com.example.cosmonotes.todoModels;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cosmonotes.CalendarModels.EventAdapter;
import com.example.cosmonotes.R;
import com.example.cosmonotes.Utils.DataBaseHelper;

import java.util.List;

public class ToDoModelAdapter extends RecyclerView.Adapter<ToDoModelAdapter.MyViewHolder> {
    private List<toDoModel> mList;
    private Context context;
    private static DataBaseHelper db;
    private FragmentActivity activity;
    private static Callback callback;

    public ToDoModelAdapter(DataBaseHelper db, Context context, FragmentActivity activity){
        this.context = context;
        this.db = db;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.to_do_item , parent , false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final toDoModel ToDoItem = mList.get(position);

        holder.ItemToDoCB.setText(ToDoItem.getTask());
        holder.ItemToDoCB.setChecked(db.ConvertIntToBoolean(ToDoItem.getStatus()));

        holder.bind(ToDoItem);
    }


    public void setItems(List<toDoModel> mList){
        this.mList = mList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    // Sets the callback
    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    // Callback interface, used to notify when an item's checked status changed
    public interface Callback {
        void onCheckedChanged(String item, boolean isChecked);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private CheckBox ItemToDoCB;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ItemToDoCB = itemView.findViewById(R.id.cb_item);
        }

        void bind(toDoModel s) {
            // Listen to changes (i.e. when the user checks or unchecks the box)
            ItemToDoCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // Invoke the callback
                    if(isChecked){
                        db.updateStatusItem(s.getIdItem(), 1);
                    }

                    if(callback != null) callback.onCheckedChanged(s.getTask(), isChecked);
                }
            });
        }
    }
}
