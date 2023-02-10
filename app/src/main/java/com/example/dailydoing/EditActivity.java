package com.example.dailydoing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.dailydoing.database.Note;
import com.example.dailydoing.database.NoteDatabase;

import java.sql.Time;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EditActivity extends AppCompatActivity {

    ImageButton insetBackBtn;
    EditText editNoteTitle, editNoteDescription, editDoingDatePicker, editDoingTimePicker;
    RadioButton categoriesBtn;
    RadioGroup cateRadioGroup;
    AppCompatButton editDateBtn, editTimeBtn, editNoteBtn;

    private int addYear, addMonth, addDay, addHour, addMinute;
    long addDatemils;
    Calendar calendar;
    String categoriesString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        insetBackBtn = findViewById(R.id.insetBackBtn);
        editNoteTitle = findViewById(R.id.editNoteTitle);
        editNoteDescription = findViewById(R.id.editNoteDescription);
        editDoingDatePicker = findViewById(R.id.editDoingDatePicker);
        editDoingTimePicker = findViewById(R.id.editDoingTimePicker);
        cateRadioGroup = findViewById(R.id.cateRadioGroup);
        editDateBtn = findViewById(R.id.editDateBtn);
        editTimeBtn = findViewById(R.id.editTimeBtn);
        editNoteBtn = findViewById(R.id.editNoteBtn);

        calendar = Calendar.getInstance();

        editDateBtn.setOnClickListener(view -> {

            addYear = calendar.get(Calendar.YEAR);
            addMonth = calendar.get(Calendar.MONTH);
            addDay = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {

                    editDoingDatePicker.setText(dayOfMonth + " / " + (monthOfYear+1) + " / " + year);

//                    calendar.set(Calendar.YEAR, year);
//                    calendar.set(Calendar.MONTH, monthOfYear);
//                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//
//                    addDatemils = calendar.getTimeInMillis();
                }
            },addYear,addMonth,addDay);
            datePickerDialog.show();
        });

        editTimeBtn.setOnClickListener(view -> {

            addHour = calendar.get(Calendar.HOUR_OF_DAY);
            addMinute = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int hourOfDay, int minuteOfHour) {

                    editDoingTimePicker.setText(getTime(hourOfDay, minuteOfHour));
                }
            },addHour,addMinute,false);
            timePickerDialog.show();
        });

        Intent intent = getIntent();
        int id = intent.getIntExtra("id",0);
        String editTitle = intent.getStringExtra("title");
        String editDescription = intent.getStringExtra("description");
        String editDate = intent.getStringExtra("date");
        String editTime = intent.getStringExtra("time");
        String editCategory = intent.getStringExtra("category");

        editNoteTitle.setText(editTitle);
        editNoteDescription.setText(editDescription);
        editDoingDatePicker.setText(editDate);
        editDoingTimePicker.setText(editTime);

        cateRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

                categoriesString = editCategory;
                categoriesBtn = findViewById(checkedId);
                categoriesString = categoriesBtn.getText().toString();

            }
        });

        insetBackBtn.setOnClickListener(view -> {
            finish();
        });


        editNoteBtn.setOnClickListener(view -> {

            String title = editNoteTitle.getText().toString().trim();
            String description = editNoteDescription.getText().toString().trim();
            String date = editDoingDatePicker.getText().toString().trim();
            String time = editDoingTimePicker.getText().toString().trim();
            String category = categoriesString;


            if (!title.isEmpty() || !description.isEmpty() || !date.isEmpty() || !time.isEmpty() || !category.isEmpty()){
                Note note = new Note();
                note.setId(id);
                note.setNoteTitle(title);
                note.setNoteDescription(description);
                note.setNoteDoingDate(date);
                note.setNoteDoingTime(time);
                note.setNoteCategories(category);
                note.setNoteStatus("Not Done");

                NoteDatabase.getInstance(this).getNoteDao().update(note);

                Intent intent1 = new Intent(EditActivity.this, MainActivity.class);
                startActivity(intent1);


            }else {

                Toast.makeText(this, "Please schedule a task", Toast.LENGTH_SHORT).show();

            }

        });


    }

    private String getTime(int hour, int minute) {
        Time time = new Time(hour, minute,0);
        Format formatter = new SimpleDateFormat("h:mm a");
        return formatter.format(time);
    }
}