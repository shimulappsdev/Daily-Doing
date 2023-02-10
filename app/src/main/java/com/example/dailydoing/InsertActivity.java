package com.example.dailydoing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class InsertActivity extends AppCompatActivity {

    ImageButton insetBackBtn;
    EditText addNoteTitle, addNoteDescribtion, addDoingDatePicker, addDoingTimePicker;
    RadioButton categoriesBtn;
    RadioGroup cateRadioGroup;
    AppCompatButton addDateBtn, addTimeBtn, addNoteBtn;

    private int addYear, addMonth, addDay, addHour, addMinute;
    long addDatemils;
    Calendar calendar;
    String categoriesString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        insetBackBtn = findViewById(R.id.insetBackBtn);
        addNoteTitle = findViewById(R.id.addNoteTitle);
        addNoteDescribtion = findViewById(R.id.addNoteDescribtion);
        addDoingDatePicker = findViewById(R.id.addDoingDatePicker);
        addDoingTimePicker = findViewById(R.id.addDoingTimePicker);
        cateRadioGroup = findViewById(R.id.cateRadioGroup);
        addDateBtn = findViewById(R.id.addDateBtn);
        addTimeBtn = findViewById(R.id.addTimeBtn);
        addNoteBtn = findViewById(R.id.addNoteBtn);

        calendar = Calendar.getInstance();

        addDateBtn.setOnClickListener(view -> {

            addYear = calendar.get(Calendar.YEAR);
            addMonth = calendar.get(Calendar.MONTH);
            addDay = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {

                    addDoingDatePicker.setText(dayOfMonth + " / " + (monthOfYear+1) + " / " + year);

                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, monthOfYear);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    addDatemils = calendar.getTimeInMillis();
                }
            },addYear,addMonth,addDay);
            datePickerDialog.show();
        });

        addTimeBtn.setOnClickListener(view -> {

            addHour = calendar.get(Calendar.HOUR_OF_DAY);
            addMinute = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int hourOfDay, int minuteOfHour) {

                    addDoingTimePicker.setText(getTime(hourOfDay, minuteOfHour));
                }
            },addHour,addMinute,false);
            timePickerDialog.show();
        });

        cateRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

                categoriesBtn = findViewById(checkedId);
                categoriesString = categoriesBtn.getText().toString();

//                Log.i("TAG", "onCheckedChanged: "+categoriesString);

            }
        });

        insetBackBtn.setOnClickListener(view -> {
            finish();
        });
        addNoteBtn.setOnClickListener(view -> {

            String title = addNoteTitle.getText().toString().trim();
            String description = addNoteDescribtion.getText().toString().trim();
            String date = addDoingDatePicker.getText().toString().trim();
            String time = addDoingTimePicker.getText().toString().trim();
            String category = categoriesString;


            if (!title.isEmpty() || !description.isEmpty() || !date.isEmpty() || !time.isEmpty() || !category.isEmpty()){
                Note note = new Note();
                note.setNoteTitle(title);
                note.setNoteDescription(description);
                note.setNoteDoingDate(date);
                note.setNoteDoingTime(time);
                note.setNoteCategories(category);
                note.setNoteStatus("Not Done");

                NoteDatabase.getInstance(this).getNoteDao().insert(note);

                Intent intent = new Intent(InsertActivity.this, MainActivity.class);
                startActivity(intent);

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