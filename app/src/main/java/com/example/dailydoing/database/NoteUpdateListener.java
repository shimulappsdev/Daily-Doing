package com.example.dailydoing.database;

public interface NoteUpdateListener {

    void editNote(Note note);
    void deleteNote(Note note);
    void doneNote(Note note);

}
