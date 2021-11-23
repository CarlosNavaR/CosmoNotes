package com.example.cosmonotes.todoModels;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cosmonotes.CalendarModels.EventAdapter;
import com.example.cosmonotes.NewGroupFragment;
import com.example.cosmonotes.NewItemFragment;
import com.example.cosmonotes.R;
import com.example.cosmonotes.Utils.DataBaseHelper;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class GroupModelAdapter extends RecyclerView.Adapter<GroupModelAdapter.MyViewHolder> {
    public static final String TAG = "AddNewItem";
    private List<groupModel> mList;
    private List<toDoModel> mListItems;
    private List<toDoModel> mListItemsCheck;
    private List<toDoModel> items = new ArrayList<>();
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
        GradientDrawable gradientDrawable = (GradientDrawable) holder.ColorCategoryLL.getBackground();
        gradientDrawable.setColor(Color.parseColor(model.getColorGroup()));

        mListItems = db.getAllItemsForGroup(model.getIdGroup());
        mListItemsCheck = db.getAllItemsCheckedForGroup(model.getIdGroup());

        if(model.getStatus() == false){
            holder.IconGroupArrowFTV.setText(R.string.fa_caret_down_solid);
            holder.ContainerButtonAddItem.setVisibility(View.GONE);
            holder.nestedItemsRV.setVisibility(View.GONE);
            holder.nestedItemsCheckedRV.setVisibility(View.GONE);
            holder.GroupSeparatorLL.setVisibility(View.GONE);
        }else{
            holder.IconGroupArrowFTV.setText(R.string.fa_caret_up_solid);
            holder.ContainerButtonAddItem.setVisibility(View.VISIBLE);
            holder.nestedItemsRV.setVisibility(View.VISIBLE);
            holder.nestedItemsCheckedRV.setVisibility(View.VISIBLE);
            holder.GroupSeparatorLL.setVisibility(View.VISIBLE);
            setRecyclerViewItems(holder,position, db);
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

        holder.newItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewItemFragment newItemFragment = NewItemFragment.newInstance();
                newItemFragment.setIdGroup(mList.get(position).getIdGroup());
                newItemFragment.show(activity.getSupportFragmentManager(), NewItemFragment.TAG);
                notifyDataSetChanged();
            }

        });
    }

    public void setGroups(List<groupModel> mList){
        this.mList = mList;
        notifyDataSetChanged();
    }

    public void deleteGroup(int position){
        groupModel group = mList.get(position);

        items = db.getAllItemsForGroup(group.getIdGroup());

        if(items.size() == 0){
            db.RemoveGroup(group.getIdGroup());
            mList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void modifyGroup(int position){
        groupModel group = mList.get(position);

        Bundle bundle = new Bundle();
        bundle.putInt("id", group.getIdGroup());
        bundle.putString("group", group.getTitleGroup());
        bundle.putString("color", group.getColorGroup());

        NewGroupFragment groupFragment = new NewGroupFragment();
        groupFragment.setArguments(bundle);
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_Container, groupFragment).commit();
        notifyItemChanged(position);
    }

    public void setRecyclerViewItems(MyViewHolder holder, int groupIndex,DataBaseHelper db){
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
        LinearLayout ContainerButtonAddItem;
        TextView newItem;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            GroupTitleTV = itemView.findViewById(R.id.tv_GroupTitle);
            IconGroupArrowFTV = itemView.findViewById(R.id.group_arrow);
            GroupSeparatorLL = itemView.findViewById(R.id.LL_group_lineSeparator);
            nestedItemsRV = itemView.findViewById(R.id.nested_rv);
            nestedItemsCheckedRV = itemView.findViewById(R.id.nested_rv_done);
            ColorCategoryLL = itemView.findViewById(R.id.ColorCategory);
            ContainerButtonAddItem = itemView.findViewById(R.id.btnAddNewToDo);
            newItem = itemView.findViewById(R.id.newItem);
        }
    }
}