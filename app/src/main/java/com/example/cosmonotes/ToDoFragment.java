package com.example.cosmonotes;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cosmonotes.CalendarModels.OnDialogCloseListner;
import com.example.cosmonotes.CalendarModels.RecyclerViewTouchHelper;
import com.example.cosmonotes.Utils.DataBaseHelper;
import com.example.cosmonotes.todoModels.GroupModelAdapter;
import com.example.cosmonotes.todoModels.RecyclerViewTouchHelperToDo;
import com.example.cosmonotes.todoModels.groupModel;
import com.example.cosmonotes.todoModels.toDoModel;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ToDoFragment extends Fragment  implements OnDialogCloseListner {
    private RecyclerView mRecyclerView;
    private RecyclerView mRecyclerViewItems;
    private DataBaseHelper db;

    private List<groupModel> mList;
    private GroupModelAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_to_do, container, false);

        mRecyclerView = view.findViewById(R.id.recyclerView_todoGroups);
        db = new DataBaseHelper(getActivity());
        adapter = new GroupModelAdapter(db, getContext(),getActivity());

        setGroupAdapter();
        return view;
    }

    private void setGroupAdapter(){
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(adapter);

        mList = db.getAllGroupsTodo();
        adapter.setGroups(mList);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerViewTouchHelperToDo(adapter, db, mList));
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    public void onDialogClose(DialogInterface dialogInterface) {
        mList = db.getAllGroupsTodo();
        Collections.reverse(mList);
        adapter.setGroups(mList);
        adapter.notifyDataSetChanged();
    }
}