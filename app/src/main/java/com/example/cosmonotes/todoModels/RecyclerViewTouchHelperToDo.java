package com.example.cosmonotes.todoModels;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cosmonotes.CalendarModels.EventAdapter;
import com.example.cosmonotes.R;
import com.example.cosmonotes.Utils.DataBaseHelper;

import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class RecyclerViewTouchHelperToDo extends ItemTouchHelper.SimpleCallback {
    private static final String TAG = "GoogleActivity";
    private GroupModelAdapter adapter;
    private DataBaseHelper db;
    private List<groupModel> GroupList;
    private List<toDoModel> mListItems;
    private List<toDoModel> mListItemsCheck;

    public RecyclerViewTouchHelperToDo(GroupModelAdapter groupModelAdapter, DataBaseHelper db, List<groupModel> GroupList) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.adapter = groupModelAdapter;
        this.db = db;
        this.GroupList = GroupList;
    }


    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        final int position = viewHolder.getBindingAdapterPosition();
        int groupId = GroupList.get(position).getIdGroup();

        mListItems = db.getAllItemsForGroup(groupId);
        mListItemsCheck = db.getAllItemsCheckedForGroup(groupId);
        Log.d(TAG, "tamanio RVTH: " +  mListItems.size() + " sd " + mListItemsCheck.size());

        if (direction == ItemTouchHelper.RIGHT){
            AlertDialog.Builder builder = new AlertDialog.Builder(adapter.getContext());
            builder.setTitle("Eliminar categoria");
            builder.setMessage("Desea eliminar esta categoria?");
            builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(mListItems.size() != 0 || mListItemsCheck.size() != 0){
                        AlertDialog.Builder builder = new AlertDialog.Builder(adapter.getContext());
                        builder.setTitle("ERROR");
                        builder.setMessage("No puedes eliminar una categoria con elementos");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                adapter.notifyItemChanged(position);
                            }
                        });
                        AlertDialog dialogError = builder.create();
                        dialogError.show();
                    }
                    else
                    {
                        adapter.deleteGroup(position);
                        adapter.notifyItemRemoved(position);
                    }
                }
            });
            builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    adapter.notifyItemChanged(position);
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }else{
            adapter.modifyGroup(position);
        }
    }


    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

        new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                .addSwipeLeftBackgroundColor(ContextCompat.getColor(adapter.getContext() , R.color.eventoVerde))
                .addSwipeLeftActionIcon(R.drawable.ic_baseline_edit_24)
                .addSwipeRightBackgroundColor(ContextCompat.getColor(adapter.getContext(), R.color.eventoRojo))
                .addSwipeRightActionIcon(R.drawable.ic_baseline_delete_24)
                .create()
                .decorate();
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }
}
