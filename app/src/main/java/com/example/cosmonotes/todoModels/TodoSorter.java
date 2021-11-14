package com.example.cosmonotes.todoModels;

import java.util.ArrayList;


public class TodoSorter {
    private ArrayList<elemento> ListaElemento;
    private ArrayList<todo_groups> ListaGrupo;
    private ArrayList<elemento> tempElList_Checked;
    private ArrayList<elemento> tempElList_UnChecked;
    private ArrayList<elemento> tempElList;

    public TodoSorter(ArrayList<elemento> ListaElemento, ArrayList<todo_groups> ListaGrupo) {
        this.ListaElemento = ListaElemento;
        this.ListaGrupo = ListaGrupo;
        tempElList_Checked = new ArrayList<elemento>();
        tempElList_UnChecked = new ArrayList<elemento>();
        SortGroup();
        SortChecked();
    }

    public void SortGroup(){
        for (elemento e:ListaElemento) {
            if (e.getGroup() > ListaGrupo.size()-1) {e.setGroup( 0 );} // 0 is todo default list
            if (e.getGroup() < 0) {e.setGroup( 0 );}
            ListaGrupo.get(e.getGroup()).additemLista_unchecked(e);
        }
    }
    public void SortChecked(){
        for(todo_groups tg:ListaGrupo){
            tempElList = tg.getListaEl_unchecked();
            for (elemento e: tempElList) {
                if (e.isEstado()){tempElList_Checked.add( e );}
                else{tempElList_UnChecked.add( e );}
            }
            tg.setListaEl_unchecked( tempElList_UnChecked );
            tg.setListaEl_checked( tempElList_Checked );
            tempElList_Checked = new ArrayList<elemento>();
            tempElList_UnChecked = new ArrayList<elemento>();
        }

    }
    public ArrayList<todo_groups> getListaGrupo(){
        return ListaGrupo;
    }
}