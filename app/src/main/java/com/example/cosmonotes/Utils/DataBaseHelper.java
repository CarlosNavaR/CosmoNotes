package com.example.cosmonotes.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.cosmonotes.CalendarModels.Event;

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
    private static final String COL_G4 = "IdUser";

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
            + COL_G3 + " integer not null);";

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

    public void newUser(){

    }

    public void saveEvent(Event model){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_E2, model.getTitulo());
        values.put(COL_E3, model.getDate().toString());
        values.put(COL_E4, model.getTime().toString());
        values.put(COL_E5, model.getColorNote());
        db.insert(TABLE_EVENTS, null, values);
    }

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
                        event.setTitulo(cursor.getString(cursor.getColumnIndex(COL_E2)));
                        event.setDate(cursor.getString(cursor.getColumnIndex(COL_E3)));
                        event.setTime(cursor.getString(cursor.getColumnIndex(COL_E4)));
                        event.setColorNote(cursor.getString(cursor.getColumnIndex(COL_E5)));
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
}
