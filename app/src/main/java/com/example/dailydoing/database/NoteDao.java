package com.example.dailydoing.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface NoteDao {

    @Insert
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

    @Query("SELECT * FROM Note")
    List<Note> getAllNotes();

    @Query("SELECT * FROM Note WHERE noteDoingDate == :sameday")
    List<Note> getAllNotesBySameDay(String sameday);

//    @Query("SELECT * FROM Note WHERE noteDoingTime == :today")
//    List<Note> getAllNotesOfToDay(String today);

    @Query("SELECT * FROM Note WHERE noteCategories == :category")
    List<Note> getAllNotesByCategory(String category);

}
