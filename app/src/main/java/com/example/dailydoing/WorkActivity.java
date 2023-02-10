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

public class WorkActivity extends AppCompatActivity implements NoteUpdateListener {

    ImageButton workBackBtn;
    RecyclerView workRecycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);

        workBackBtn = findViewById(R.id.workBackBtn);
        workRecycleView = findViewById(R.id.workRecycleView);

        workBackBtn.setOnClickListener(view -> {
            Intent intent = new Intent(WorkActivity.this, MainActivity.class);
            startActivity(intent);
        });

        dataShow();

    }

    private void dataShow() {
        NoteAdapter adapter = new NoteAdapter(WorkActivity.this, NoteDatabase.getInstance(this).getNoteDao().getAllNotesByCategory("Work"),this);
        workRecycleView.setAdapter(adapter);
    }

    @Override
    public void editNote(Note note) {

        Intent intent = new Intent(WorkActivity.this, EditActivity.class);
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
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(WorkActivity.this);
        alertDialog.setTitle("Delete "+tilte);
        alertDialog.setMessage("Are you sure ?");

        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                NoteDatabase.getInstance(WorkActivity.this).getNoteDao().delete(note);
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