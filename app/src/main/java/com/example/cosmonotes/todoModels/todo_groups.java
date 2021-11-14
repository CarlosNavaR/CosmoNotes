package com.example.cosmonotes.todoModels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class todo_groups {
    private String titulo_grupo;
    private int grupo_ID;
    private boolean abierto;
    private ArrayList<elemento> mListaEl_unchecked;
    private ArrayList<elemento> mListaEl_checked;

    public todo_groups(String titulo_grupo, int grupo_ID, boolean pinned,ArrayList<elemento> LEU, ArrayList<elemento> LEC) {
        this.titulo_grupo = titulo_grupo;
        this.grupo_ID = grupo_ID;
        this.abierto = pinned;
        mListaEl_unchecked = LEU;
        mListaEl_checked = LEC;
    }

    public todo_groups(String titulo_grupo, int grupo_ID) {
        this.titulo_grupo = titulo_grupo;
        this.grupo_ID = grupo_ID;
        abierto = false;
        mListaEl_checked = new ArrayList<elemento>();
        mListaEl_unchecked = new ArrayList<elemento>();
    }
    // GETTER SETTER
    public String getTitulo_grupo() {
        return titulo_grupo;
    }
    public void setTitulo_grupo(String titulo_grupo) {
        this.titulo_grupo = titulo_grupo;
    }
    public int getGrupo_ID() {
        return grupo_ID;
    }
    public void setGrupo_ID(int grupo_ID) {
        this.grupo_ID = grupo_ID;
    }
    public boolean isAbierto() {
        return abierto;
    }
    public void setAbierto(boolean abierto) {
        this.abierto = abierto;
    }
    public ArrayList<elemento> getListaEl_unchecked() {
        return mListaEl_unchecked;
    }
    public void setListaEl_unchecked(ArrayList<elemento> listaEl_unchecked) {
        mListaEl_unchecked = listaEl_unchecked;
        Collections.sort( mListaEl_unchecked, new Comparator<elemento>() {
            @Override
            public int compare(elemento e1, elemento e2) {
                if (e1.getOrden() == e2.getOrden() && e1.getGroup() == e2.getGroup())
                {e2.setOrden(e2.getOrden()+1);}
                return Integer.valueOf( e1.getOrden()).compareTo( e2.getOrden() );
            }
        } );
    }
    public ArrayList<elemento> getListaEl_checked() {
        return mListaEl_checked;
    }
    public void setListaEl_checked(ArrayList<elemento> listaEl_checked) {
        mListaEl_checked = listaEl_checked;
        Collections.sort( mListaEl_checked, new Comparator<elemento>() {
            @Override
            public int compare(elemento e1, elemento e2) {
                if (e1.getOrden() == e2.getOrden() && e1.getGroup() == e2.getGroup())
                {e2.setOrden(e2.getOrden()+1);}
                return Integer.valueOf( e1.getOrden()).compareTo( e2.getOrden() );
            }
        } );
    }
    // Movimientos en lista
    public void additemLista_unchecked(elemento ele){
        mListaEl_unchecked.add( ele );
        actualizarOrden();

    }

    public void additemLista_unchecked(elemento ele, int toindex){
        mListaEl_unchecked.add( toindex,ele );
        actualizarOrden();
    }
    public void removeitemLista_unchecked(int index){
        mListaEl_unchecked.remove( index );
        actualizarOrden();
    }
    public void moveitemLista_unchecked(int fromindex, int toindex){
        elemento ele = mListaEl_unchecked.get( fromindex );
        ele.setOrden( toindex );
        mListaEl_unchecked.add( toindex,ele  );
        mListaEl_unchecked.remove( fromindex );
        actualizarOrden();
    }

    public void additemLista_checked(elemento ele){
        mListaEl_checked.add( ele );
        actualizarOrden();
    }
    public void additemLista_checked(elemento ele, int toindex){
        mListaEl_checked.add( toindex,ele );
        actualizarOrden();
    }
    public void removeitemLista_checked(int index){
        mListaEl_checked.remove( index );
        actualizarOrden();
    }
    public void moveitemLista_checked(int fromindex, int toindex){
        elemento ele = mListaEl_checked.get( fromindex );
        mListaEl_checked.add( toindex,ele  );
        mListaEl_checked.remove( fromindex );
        actualizarOrden();
    }

    //Movimientos de elemento entre listas
    //Actualizacion de lista

    public void actualizarOrden(){
        for (int i = 0; i < mListaEl_unchecked.size(); i++) {
            mListaEl_unchecked.get( i ).setOrden( i );
        }
        for (int i = 0; i < mListaEl_checked.size(); i++) {
            mListaEl_checked.get( i ).setOrden( i );
        }
    }

}