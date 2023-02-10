package com.example.dailydoing.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Note {

    @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo
    private String noteTitle, noteDescription, noteCategories, noteDoingDate, noteDoingTime, noteStatus;

    private  long dateMilis;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }

    public String getNoteCategories() {
        return noteCategories;
    }

    public void setNoteCategories(String noteCategories) {
        this.noteCategories = noteCategories;
    }

    public String getNoteDoingDate() {
        return noteDoingDate;
    }

    public void setNoteDoingDate(String noteDoingDate) {
        this.noteDoingDate = noteDoingDate;
    }

    public String getNoteDoingTime() {
        return noteDoingTime;
    }

    public void setNoteDoingTime(String noteDoingTime) {
        this.noteDoingTime = noteDoingTime;
    }

    public String getNoteStatus() {
        return noteStatus;
    }

    public void setNoteStatus(String noteStatus) {
        this.noteStatus = noteStatus;
    }

    public long getDateMilis() {
        return dateMilis;
    }

    public void setDateMilis(long dateMilis) {
        this.dateMilis = dateMilis;
    }
}
