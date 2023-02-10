package com.example.dailydoing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class StartActivity extends AppCompatActivity {

    AppCompatButton startBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        startBtn = findViewById(R.id.startBtn);

        SharedPreferences preferences = getSharedPreferences("FirstTimeShow",MODE_PRIVATE);
        String firstTime = preferences.getString("firstTimeInstall","");

        if (firstTime.equals("yes")){
            Intent intent = new Intent(StartActivity.this, MainActivity.class);
            startActivity(intent);
        }else {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("firstTimeInstall","yes");
            editor.apply();
        }

        startBtn.setOnClickListener(view -> {
            Intent intent = new Intent(StartActivity.this, MainActivity.class);
            startActivity(intent);
        });

    }
}