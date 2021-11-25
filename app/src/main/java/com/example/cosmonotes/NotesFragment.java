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
import com.example.cosmonotes.Notes.Notes;
import com.example.cosmonotes.Notes.NotesAdapter;
import com.example.cosmonotes.Notes.RecyclerViewTouchHelperNotes;
import com.example.cosmonotes.Utils.DataBaseHelper;
import com.example.cosmonotes.todoModels.RecyclerViewTouchHelperToDo;

import java.util.Collections;
import java.util.List;


public class NotesFragment extends Fragment implements OnDialogCloseListner {
    private RecyclerView mRecyclerView;
    private DataBaseHelper db;

    private List<Notes> mList;
    private NotesAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_notes, container, false);

        mRecyclerView = view.findViewById(R.id.RViewNotas);
        db = new DataBaseHelper(getActivity());
        adapter = new NotesAdapter(db, getContext(), getActivity());

        setNoteAdapter();
        return view;
    }

    private void setNoteAdapter(){
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(adapter);

        mList = db.leerNotas();
        adapter.setNotes(mList);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerViewTouchHelperNotes(adapter, db, mList, getContext()));
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

    }

    @Override
    public void onDialogClose(DialogInterface dialogInterface) {
        mList = db.leerNotas();
        Collections.reverse(mList);
        adapter.setNotes(mList);
        adapter.notifyDataSetChanged();
    }
}