package com.example.dailydoing;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.dailydoing.database.Note;
import com.example.dailydoing.database.NoteDatabase;
import com.example.dailydoing.database.NoteUpdateListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NoteUpdateListener {

    CardView wrokCateBtn, personalCateBtn, shoppingCateBtn, healthCateBtn;
    ImageButton addBtn;
    RecyclerView allTaskRecyclerView;
    TextView importantTaskTV, importantDateTV, importantTimeTV,dailyWorkCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        allTaskRecyclerView = findViewById(R.id.allTaskRecyclerView);
        importantTaskTV = findViewById(R.id.importantTaskTV);
        importantDateTV = findViewById(R.id.importantDateTV);
        importantTimeTV = findViewById(R.id.importantTimeTV);
        wrokCateBtn = findViewById(R.id.wrokCateBtn);
        personalCateBtn = findViewById(R.id.personalCateBtn);
        shoppingCateBtn = findViewById(R.id.shoppingCateBtn);
        healthCateBtn = findViewById(R.id.healthCateBtn);
        addBtn = findViewById(R.id.addBtn);
        dailyWorkCount = findViewById(R.id.dailyWorkCount);

        wrokCateBtn.setOnClickListener(view -> {
            Intent workIntent = new Intent(MainActivity.this, WorkActivity.class);
            startActivity(workIntent);
        });

        personalCateBtn.setOnClickListener(view -> {
            Intent personalIntent = new Intent(MainActivity.this, PersonalActivity.class);
            startActivity(personalIntent);
        });

        shoppingCateBtn.setOnClickListener(view -> {
            Intent shoppingIntent = new Intent(MainActivity.this, ShoppingActivity.class);
            startActivity(shoppingIntent);
        });

        healthCateBtn.setOnClickListener(view -> {
            Intent healtIntent = new Intent(MainActivity.this, HealthActivity.class);
            startActivity(healtIntent);
        });

        addBtn.setOnClickListener(view -> {
            Intent addIntent = new Intent(MainActivity.this, InsertActivity.class);
            startActivity(addIntent);
        });


        SharedPreferences preferences = getSharedPreferences("Important",MODE_PRIVATE);
        String imTitle = preferences.getString("title",getString(R.string.app_name));
        String imDate = preferences.getString("date",getString(R.string.app_name));
        String imTime = preferences.getString("time",getString(R.string.app_name));
        importantTaskTV.setText(imTitle);
        importantDateTV.setText(imDate);
        importantTimeTV.setText(imTime);

        setDataToShow();
    }

    private void setDataToShow() {
        String today = getToday();
        NoteAdapter adapter = new NoteAdapter(this,NoteDatabase.getInstance(this).getNoteDao().getAllNotesBySameDay(today),this);
        allTaskRecyclerView.setAdapter(adapter);
        int countTask = NoteDatabase.getInstance(this).getNoteDao().getAllNotesBySameDay(today).size();
        dailyWorkCount.setText("You have "+countTask+" task to do for today.");

        Log.i("TAG", "setDataToShow: "+today);
    }

    private String getToday() {
        int date, month, year;
        Calendar calendar = Calendar.getInstance();
        date = calendar.get(Calendar.DATE);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        return date+" / "+(month+1)+" / "+year;
    }

    @Override
    public void editNote(Note note) {

        Intent intent = new Intent(MainActivity.this, EditActivity.class);
        intent.putExtra("id",note.getId());
        intent.putExtra("title",note.getNoteTitle());
        intent.putExtra("description",note.getNoteDescription());
        intent.putExtra("date",note.getNoteDoingDate());
        intent.putExtra("time",note.getNoteDoingTime());
        intent.putExtra("category",note.getNoteCategories());
        intent.putExtra("status",note.getNoteStatus());
        startActivity(intent);

        setDataToShow();
    }

    @Override
    public void deleteNote(Note note) {

        String tilte = note.getNoteTitle().toString();
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("Delete "+tilte);
        alertDialog.setMessage("Are you sure ?");

        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                NoteDatabase.getInstance(MainActivity.this).getNoteDao().delete(note);
                setDataToShow();
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
        setDataToShow();
    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("Exit");
        alertDialog.setMessage("Are you sure ?");

        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
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
}