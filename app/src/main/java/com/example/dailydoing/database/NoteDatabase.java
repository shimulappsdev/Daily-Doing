package com.example.dailydoing.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    public abstract NoteDao getNoteDao();
    static NoteDatabase noteDatabase = null;
    public static NoteDatabase getInstance(Context context){
        if (noteDatabase == null){
            noteDatabase = Room.databaseBuilder(
                    context,
                    NoteDatabase.class,
                    "Note_Database"
            ).allowMainThreadQueries().build();
        }
        return noteDatabase;
    }
}