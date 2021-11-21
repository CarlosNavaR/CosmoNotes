package com.example.cosmonotes.Utils;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.cosmonotes.CalendarModels.Event;
import com.example.cosmonotes.todoModels.groupModel;
import com.example.cosmonotes.todoModels.toDoModel;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "CosmoNotesDB";

    private SQLiteDatabase db;

    // Datos tabla usuarios
    private static final String TABLE_USERS = "Users";
    private static final String COL_U1 = "Id";
    private static final String COL_U2 = "Name";
    private static final String COL_U3 = "UUID";

    // Datos tabla eventos
    private static final String TABLE_EVENTS = "Events";
    private static final String COL_E1 = "ID";
    private static final String COL_E2 = "Title";
    private static final String COL_E3 = "Date";
    private static final String COL_E4 = "Time";
    private static final String COL_E5 = "Category";
    private static final String COL_E6 = "IdUser";

    // Datos tabla pendientes
    private static final String TABLE_GROUPS_TODO = "ToDo_Groups";
    private static final String COL_G1 = "Id";
    private static final String COL_G2 = "Title";
    private static final String COL_G3 = "Status";
    private static final String COL_G4 = "Category";

    private static final String TABLE_ITEMS_TODO = "ToDo_Items";
    private static final String COL_I1 = "Id";
    private static final String COL_I2 = "Title";
    private static final String COL_I3 = "Status";
    private static final String COL_I4 = "IdGroup";


    // Variables de creacion de tablas
    private static final String USER_TABLE_CREATE = "create table " + TABLE_USERS + " ("
            + COL_U1 + " integer primary key autoincrement, "
            + COL_U2 + " text not null, "
            + COL_U3 + " text not null);";

    private static final String EVENTS_TABLE_CREATE = "create table " + TABLE_EVENTS + " ("
            + COL_E1 + " integer primary key autoincrement, "
            + COL_E2 + " text not null, "
            + COL_E3 + " text not null,"
            + COL_E4 + " text not null,"
            + COL_E5 + " text not null );";

    private static final String GROUOPTODO_TABLE_CREATE = "create table " + TABLE_GROUPS_TODO + " ("
            + COL_G1 + " integer primary key autoincrement, "
            + COL_G2 + " text not null, "
            + COL_G3 + " integer not null,"
            + COL_G4 + " text not null);";

    private static final String ITEMSTODO_TABLE_CREATE = "create table " + TABLE_ITEMS_TODO + " ("
            + COL_I1 + " integer primary key autoincrement, "
            + COL_I2 + " text not null, "
            + COL_I3 + " integer not null,"
            + COL_I4 + " integer not null, foreign key ("+COL_I4+") references "+TABLE_GROUPS_TODO+" ("+COL_G1+"));";

    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(USER_TABLE_CREATE);
        db.execSQL(EVENTS_TABLE_CREATE);
        db.execSQL(GROUOPTODO_TABLE_CREATE);
        db.execSQL(ITEMSTODO_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_EVENTS);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_GROUPS_TODO);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_ITEMS_TODO);
        onCreate(db);
    }

    // Operaciones para registros de la tabla de eventos

    public void saveEvent(Event model){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_E2, model.getTitulo());
        values.put(COL_E3, model.getDate().toString());
        values.put(COL_E4, model.getTime().toString());
        values.put(COL_E5, model.getColorEvent());
        db.insert(TABLE_EVENTS, null, values);
    }

    public void updateEvent(int id, Event event){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_E2, event.getTitulo());
        values.put(COL_E5, event.getColorEvent());
        db.update(TABLE_EVENTS, values, "ID=?", new String[]{String.valueOf(id)});
    }

    public void deleteEvent(int id){
        db = this.getWritableDatabase();
        db.delete(TABLE_EVENTS, "ID=?", new String[]{String.valueOf(id)});
    }

    @SuppressLint("Range")
    public List<Event> getAllEvents(){
        db = this.getWritableDatabase();
        Cursor cursor = null;
        List<Event> modelEventList = new ArrayList<>();
        db.beginTransaction();
        try{
            cursor = db.query(TABLE_EVENTS, null, null, null, null, null, null);
            if(cursor != null){
                if (cursor.moveToFirst()){
                    do{
                        Event event = new Event();
                        event.setId(cursor.getInt(cursor.getColumnIndex(COL_E1)));
                        event.setTitulo(cursor.getString(cursor.getColumnIndex(COL_E2)));
                        event.setDate(cursor.getString(cursor.getColumnIndex(COL_E3)));
                        event.setTime(cursor.getString(cursor.getColumnIndex(COL_E4)));
                        event.setColorEvent(cursor.getString(cursor.getColumnIndex(COL_E5)));
                        modelEventList.add(event);
                    }while (cursor.moveToNext());
                }
            }
        } finally {
            db.endTransaction();
            cursor.close();
        }
        return modelEventList;
    }

    // Operaciones para registros de la tabla de grupos (pendientes)

    public void saveGroupToDo(groupModel groupModel){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_G2, groupModel.getTitleGroup());
        values.put(COL_G3, 0);
        values.put(COL_G4, groupModel.getColorGroup());
        db.insert(TABLE_GROUPS_TODO, null, values);
    }

    public void updateGroup(int id, groupModel group){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_G2, group.getTitleGroup());
        values.put(COL_G4, group.getColorGroup());
        db.update(TABLE_GROUPS_TODO, values, "ID=?", new String[]{String.valueOf(id)});
    }

    public void updateStatus(int id, int status){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_G3, status);
        db.update(TABLE_GROUPS_TODO, values, "ID?", new String[]{String.valueOf(id)});
    }

    @SuppressLint("Range")
    public List<groupModel> getAllGroupsTodo(){
        db = this.getWritableDatabase();
        Cursor cursor = null;
        List<groupModel> modelGroupList = new ArrayList<>();
        db.beginTransaction();
        try{
            cursor = db.query(TABLE_GROUPS_TODO, null, null, null, null, null, null);
            if(cursor != null){
                if (cursor.moveToFirst()){
                    do{
                        groupModel group = new groupModel();
                        group.setIdGroup(cursor.getInt(cursor.getColumnIndex(COL_G1)));
                        group.setTitleGroup(cursor.getString(cursor.getColumnIndex(COL_G2)));
                        group.setStatus(ConvertIntToBoolean(cursor.getInt(cursor.getColumnIndex(COL_G3))));
                        group.setColorGroup(cursor.getString(cursor.getColumnIndex(COL_G4)));
                        modelGroupList.add(group);
                    }while (cursor.moveToNext());
                }
            }
        } finally {
            db.endTransaction();
            cursor.close();
        }
        return modelGroupList;
    }

    public void RemoveGroup(int id){
        db = this.getWritableDatabase();
        db.delete(TABLE_GROUPS_TODO, "ID=?", new String[]{String.valueOf(id)});
    }

    // Operaciones para registros de la tabla de items de grupos (Pendientes)

    public void saveItemToDo(String title, int IdGroup){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_I2, title);
        values.put(COL_I3, 0);
        values.put(COL_I4, IdGroup);
        db.insert(TABLE_ITEMS_TODO, null, values);
    }

    public void updateStatusItem(int id, int status){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_I3, status);
        db.update(TABLE_ITEMS_TODO, values, "Id=?", new String[]{String.valueOf(id)});
    }

    public List<toDoModel> getAllItemsForGroup(int id){
        db = this.getWritableDatabase();
        Cursor cursor = null;
        List<toDoModel> modelItemList = new ArrayList<>();
        db.beginTransaction();
        try{
            cursor = db.rawQuery("SELECT * FROM ToDo_Items WHERE idGroup=? AND Status=?", new String[]{String.valueOf(id), String.valueOf(0)});
            if(cursor != null){
                if (cursor.moveToFirst()){
                    do{
                        toDoModel item = new toDoModel();
                        item.setIdItem(cursor.getInt(0));
                        item.setTask(cursor.getString(1));
                        item.setStatus(cursor.getInt(2));
                        item.setGroup(cursor.getInt(3));
                        modelItemList.add(item);
                    }while (cursor.moveToNext());
                }
            }
        } finally {
            db.endTransaction();
            cursor.close();
        }
        return modelItemList;
    }

    public List<toDoModel> getAllItemsCheckedForGroup(int id){
        db = this.getWritableDatabase();
        Cursor cursor = null;
        List<toDoModel> modelCheckItemList = new ArrayList<>();
        db.beginTransaction();
        try{
            cursor = db.rawQuery("SELECT * FROM ToDo_Items WHERE idGroup=? AND Status=?", new String[]{String.valueOf(id+1), String.valueOf(1)});
            if(cursor != null){
                if (cursor.moveToFirst()){
                    do{
                        toDoModel item = new toDoModel();
                        item.setIdItem(cursor.getInt(0));
                        item.setTask(cursor.getString(1));
                        item.setStatus(cursor.getInt(2));
                        item.setGroup(cursor.getInt(3));
                        modelCheckItemList.add(item);
                    }while (cursor.moveToNext());
                }
            }
        } finally {
            db.endTransaction();
            cursor.close();
        }
        return modelCheckItemList;
    }

    public void UpdateItemToDo(int id, toDoModel toDoModel){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_I2, toDoModel.getTask());
        db.update(TABLE_ITEMS_TODO, values, "ID=?", new String[]{String.valueOf(id)});
    }

    public void RemoveItemList(int id){
        db = this.getWritableDatabase();
        db.delete(TABLE_ITEMS_TODO, "ID=?", new String[]{String.valueOf(id)});

    }

    // Clase para convertir de entero a booleano para actualizar status de elementos

    public boolean ConvertIntToBoolean(int num){
        return num != 0;
    }
}
