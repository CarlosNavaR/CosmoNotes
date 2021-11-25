package com.example.cosmonotes.Notes;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cosmonotes.CalendarModels.Event;
import com.example.cosmonotes.NewEventFragment;
import com.example.cosmonotes.NewNoteFragment;
import com.example.cosmonotes.R;
import com.example.cosmonotes.Utils.DataBaseHelper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class NotesAdapter  extends RecyclerView.Adapter<NotesAdapter.MyViewHolder> {
    private List<Notes> mlist;
    private Context context;
    private DataBaseHelper db;
    FragmentActivity activity;

    public NotesAdapter(DataBaseHelper db, Context context, FragmentActivity activity){
        this.context = context;
        this.db = db;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_cell, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Notes nota = mlist.get(position);
        GradientDrawable gradientDrawable = (GradientDrawable) holder.LLTagColor.getBackground();
        gradientDrawable.setColor(Color.parseColor(nota.getColorCategoria()));

        holder.noteTitleCellTV.setText(nota.getTitulo());
        holder.noteContentCell.setText(nota.getContenido());
        LocalDate fecha = nota.getFechaNota();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        holder.noteTimeCell.setText(fecha.format(formatter));
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setNotes(List<Notes> mList) {
        this.mlist = mList;
        notifyDataSetChanged();
    }

    public void deleteNote(int position){
        Notes note = mlist.get(position);
        db.eliminarNota(note.getId_notas());
        mlist.remove(position);
        notifyItemRemoved(position);
    }

    public void updateNote(int position){
        Notes note = mlist.get(position);

        Bundle bundle = new Bundle();
        bundle.putInt("id", note.getId_notas());
        bundle.putString("Titulo", note.getTitulo());
        bundle.putString("Contenido", note.getContenido());
        bundle.putString("color", note.getColorCategoria());
        bundle.putString("fecha", String.valueOf(note.getFechaNota()));

        NewNoteFragment noteFragment = new NewNoteFragment();
        noteFragment.setArguments(bundle);
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_Container, noteFragment).commit();
        notifyItemChanged(position);
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView noteTitleCellTV;
        TextView noteTimeCell;
        TextView noteContentCell;
        LinearLayout LLTagColor;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            noteTitleCellTV = itemView.findViewById(R.id.noteCellTV);
            noteTimeCell = itemView.findViewById(R.id.TimeCell);
            noteContentCell = itemView.findViewById(R.id.noteContent);
            LLTagColor = itemView.findViewById(R.id.TagColorLY);
        }
    }
}
