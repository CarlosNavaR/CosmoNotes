package com.example.cosmonotes.todoModels;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cosmonotes.CalendarModels.EventAdapter;
import com.example.cosmonotes.R;
import com.example.cosmonotes.Utils.DataBaseHelper;

import java.util.List;

public class GroupModelAdapter extends RecyclerView.Adapter<GroupModelAdapter.MyViewHolder> {
    private List<groupModel> mList;
    private List<toDoModel> mListItems;
    private List<toDoModel> mListItemsCheck;
    private Context context;
    private DataBaseHelper db;
    FragmentActivity activity;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public GroupModelAdapter(DataBaseHelper db, Context context, FragmentActivity activity){
        this.context = context;
        this.db = db;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.to_do_group , parent , false);
        return new GroupModelAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final groupModel model = mList.get(position);

        holder.GroupTitleTV.setText(model.getTitleGroup());
        mListItems = db.getAllItemsForGroup(position);
        mListItemsCheck = db.getAllItemsCheckedForGroup(position);

        if(model.getStatus() == false){
            holder.IconGroupArrowFTV.setText(R.string.fa_caret_down_solid);
            holder.nestedItemsRV.setVisibility(View.GONE);
            holder.nestedItemsCheckedRV.setVisibility(View.GONE);
            holder.GroupSeparatorLL.setVisibility(View.GONE);
        }else{
            holder.IconGroupArrowFTV.setText(R.string.fa_caret_up_solid);
            holder.nestedItemsRV.setVisibility(View.VISIBLE);
            holder.nestedItemsCheckedRV.setVisibility(View.VISIBLE);
            holder.GroupSeparatorLL.setVisibility(View.VISIBLE);
            setRecyclerViewItems(holder,position);
        }

        if(mListItems.size() == 0)
            holder.GroupSeparatorLL.setVisibility(View.GONE);
        if(mListItemsCheck.size() == 0)
            holder.GroupSeparatorLL.setVisibility(View.GONE);

        holder.IconGroupArrowFTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mList.get(position).setStatus(!mList.get(position).getStatus());
                notifyItemChanged(position);
            }
        });
    }

    public void setGroups(List<groupModel> mList){
        this.mList = mList;
        notifyDataSetChanged();
    }

    public void setRecyclerViewItems(MyViewHolder holder, int groupIndex){
        ToDoModelAdapter adapter = new ToDoModelAdapter(db, context,activity);
        holder.nestedItemsRV.setLayoutManager(new LinearLayoutManager(context));
        holder.nestedItemsRV.setAdapter(adapter);

        adapter.setCallback(new ToDoModelAdapter.Callback() {
            @Override
            public void onCheckedChanged(String item, boolean isChecked) {
                if(isChecked)
                    notifyDataSetChanged();
            }
        });

        ToDoCheckModelAdapter adapterCheck = new ToDoCheckModelAdapter(db, context, activity);
        holder.nestedItemsCheckedRV.setLayoutManager(new LinearLayoutManager(context));
        holder.nestedItemsCheckedRV.setAdapter(adapterCheck);

        adapterCheck.setCallback(new ToDoCheckModelAdapter.Callback() {
            @Override
            public void onCheckedChanged(String item, boolean isChecked) {
                if(!isChecked)
                    notifyDataSetChanged();
            }
        });

        adapter.setItems(mListItems);
        adapter.notifyDataSetChanged();

        adapterCheck.setListItemsChecked(mListItemsCheck);
        adapterCheck.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView GroupTitleTV;
        info.androidhive.fontawesome.FontTextView IconGroupArrowFTV;
        LinearLayout GroupSeparatorLL;
        RecyclerView nestedItemsRV;
        RecyclerView nestedItemsCheckedRV;
        LinearLayout ColorCategoryLL;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            GroupTitleTV = itemView.findViewById(R.id.tv_GroupTitle);
            IconGroupArrowFTV = itemView.findViewById(R.id.group_arrow);
            GroupSeparatorLL = itemView.findViewById(R.id.LL_group_lineSeparator);
            nestedItemsRV = itemView.findViewById(R.id.nested_rv);
            nestedItemsCheckedRV = itemView.findViewById(R.id.nested_rv_done);
            ColorCategoryLL = itemView.findViewById(R.id.ColorCategory);
        }
    }
}