package com.example.dailydoing;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import com.example.dailydoing.database.Note;
import com.example.dailydoing.database.NoteDatabase;
import com.example.dailydoing.database.NoteUpdateListener;

public class PersonalActivity extends AppCompatActivity implements NoteUpdateListener {

    ImageButton personalBackBtn;
    RecyclerView personalRecycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        personalBackBtn = findViewById(R.id.personalBackBtn);
        personalRecycleView = findViewById(R.id.personalRecycleView);
        personalBackBtn.setOnClickListener(view -> {
            Intent intent = new Intent(PersonalActivity.this, MainActivity.class);
            startActivity(intent);
        });

        dataShow();

    }

    private void dataShow() {
        NoteAdapter adapter = new NoteAdapter(this, NoteDatabase.getInstance(this).getNoteDao().getAllNotesByCategory("Personal"),this);
        personalRecycleView.setAdapter(adapter);
    }

    @Override
    public void editNote(Note note) {

        Intent intent = new Intent(PersonalActivity.this, EditActivity.class);
        intent.putExtra("id",note.getId());
        intent.putExtra("title",note.getNoteTitle());
        intent.putExtra("description",note.getNoteDescription());
        intent.putExtra("date",note.getNoteDoingDate());
        intent.putExtra("time",note.getNoteDoingTime());
        intent.putExtra("category",note.getNoteCategories());
        intent.putExtra("status",note.getNoteStatus());
        startActivity(intent);

        dataShow();

    }

    @Override
    public void deleteNote(Note note) {

        String tilte = note.getNoteTitle().toString();
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(PersonalActivity.this);
        alertDialog.setTitle("Delete "+tilte);
        alertDialog.setMessage("Are you sure ?");

        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                NoteDatabase.getInstance(PersonalActivity.this).getNoteDao().delete(note);
                dataShow();
            }
        });

        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog dialog = alertDialog.create();
        dialog.show();

    }

    @Override
    public void doneNote(Note note) {

    }
}