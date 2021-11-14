package com.example.cosmonotes;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.cosmonotes.todoModels.ExampleAdapter;
import com.example.cosmonotes.todoModels.TodoSorter;
import com.example.cosmonotes.todoModels.elemento;
import com.example.cosmonotes.todoModels.todo_groups;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class ToDoFragment extends Fragment {

    private info.androidhive.fontawesome.FontTextView mAddButton;
    private RecyclerView mRv;
    private ExampleAdapter mAdapter_rv;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<todo_groups> mListaGrupos;
    private ArrayList<elemento> mListaElementos;
    private TodoSorter ts;

    public static WeakReference<ToDoFragment> mWeakReference;
    public static ToDoFragment getInstance(){
        return mWeakReference.get();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_to_do, container, false);
        mRv = view.findViewById(R.id.recyclerView_todoGroups );
        mWeakReference = new WeakReference<>(ToDoFragment.this);

        mAddButton = view.findViewById(R.id.newToDo);

        createList();
        buildRecyclerView();
        return view;
    }

    public void createList(){

        mListaGrupos = new ArrayList<>();
        mListaElementos = new ArrayList<>();
        mListaElementos.add(new elemento("Tarea nueva!1",0));
        mListaElementos.add(new elemento("Tarea nueva!2",1));
        mListaElementos.add(new elemento("Tarea nueva!3",1));
        mListaElementos.add(new elemento("Tarea nueva!4",2));
        mListaElementos.add(new elemento("Tarea nueva!5",2));
        mListaElementos.add(new elemento("Tarea nueva!6",2));
        mListaElementos.add(new elemento("Tarea nueva!7",3));
        mListaElementos.add(new elemento("Tarea nueva!8",3));
        mListaElementos.add(new elemento("Tarea nueva!9",3));
        mListaElementos.add(new elemento("Tarea nueva!10",3));
        mListaElementos.add(new elemento("Tarea nueva!11",4));
        mListaElementos.add(new elemento("Tarea nueva!12",4));
        mListaElementos.add(new elemento("Tarea nueva!13",4));


        mListaElementos.add(new elemento("Tarea nueva!1",true,0));
        mListaElementos.add(new elemento("Tarea nueva!2",true,0));
        mListaElementos.add(new elemento("Tarea nueva!3",true,1));
        mListaElementos.add(new elemento("Tarea nueva!4",true,1));
        mListaElementos.add(new elemento("Tarea nueva!5",true,3));
        mListaElementos.add(new elemento("Tarea nueva!6",true,4));
        mListaElementos.add(new elemento("Tarea nueva!7",true,4));

        mListaGrupos.add( new todo_groups( "Pendientes",0 ) );
        mListaGrupos.add( new todo_groups( "Lista 2",1) );
        mListaGrupos.add( new todo_groups( "Lista 3",2) );
        mListaGrupos.add( new todo_groups( "Lista 4",3) );
        mListaGrupos.add( new todo_groups( "Lista 5",4) );
        ts = new TodoSorter(mListaElementos,mListaGrupos);
        mListaGrupos = ts.getListaGrupo();
    }

    public void buildRecyclerView(){

        mRv.setHasFixedSize( false );
        mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter_rv = new ExampleAdapter( mListaGrupos );
        mRv.setLayoutManager( mLayoutManager );
        mRv.setAdapter( mAdapter_rv );
        mAdapter_rv.setOnItemClickListener(new ExampleAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                // EVENTO CLICK A GRUPO
                changeItem(position,"this is new :)");
            }

            @Override
            public void OnArrowClick(int position) {
                mListaGrupos.get(position).setAbierto(!mListaGrupos.get(position).isAbierto());
                mAdapter_rv.notifyItemChanged(position);
            }

            @Override
            public void OnCheckClick(int position) {
                //mAdapter_rv.notifyItemChanged(position);
                mAdapter_rv.notifyDataSetChanged();

            }

            @Override
            public void OnUncheckClick(int position) {
                //mAdapter_rv.notifyItemChanged(position);
                mAdapter_rv.notifyDataSetChanged();
            }
        });
    }
    public void changeItem(int position, String txt){
        mListaGrupos.get(position).setTitulo_grupo(txt);
        Logger("Changed TO : " + mListaGrupos.get(position) +" " );
        mAdapter_rv.notifyItemChanged(position);
        Logger("END CHANGE!!!");
    }
    public void Logger(String s){
        Log.d("GAY",s);
    } // TODO solo es para ver desde mas adentro de la matrix

}