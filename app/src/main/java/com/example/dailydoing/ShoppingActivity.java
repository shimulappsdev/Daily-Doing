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

public class ShoppingActivity extends AppCompatActivity implements NoteUpdateListener {

    ImageButton shoppingBackBtn;
    RecyclerView shoppingRecycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);

        shoppingBackBtn = findViewById(R.id.shoppingBackBtn);
        shoppingRecycleView = findViewById(R.id.shoppingRecycleView);
        shoppingBackBtn.setOnClickListener(view -> {
            Intent intent = new Intent(ShoppingActivity.this, MainActivity.class);
            startActivity(intent);
        });

        dataShow();

    }

    private void dataShow() {
        NoteAdapter adapter = new NoteAdapter(this, NoteDatabase.getInstance(this).getNoteDao().getAllNotesByCategory("Shopping"),this);
        shoppingRecycleView.setAdapter(adapter);
    }

    @Override
    public void editNote(Note note) {

        Intent intent = new Intent(ShoppingActivity.this, EditActivity.class);
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
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ShoppingActivity.this);
        alertDialog.setTitle("Delete "+tilte);
        alertDialog.setMessage("Are you sure ?");

        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                NoteDatabase.getInstance(ShoppingActivity.this).getNoteDao().delete(note);
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