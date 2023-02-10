package com.example.dailydoing;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

public class NoteViewHolder extends RecyclerView.ViewHolder {

    TextView taskTitle, taskCategory,taskTime,taskDate, status;
    AppCompatButton doneBtn;
    CheckBox selectImportant;
    ImageButton editBtn, deleteBtn;

    public NoteViewHolder(@NonNull View itemView) {
        super(itemView);

        taskTitle = itemView.findViewById(R.id.taskTitle);
        taskCategory = itemView.findViewById(R.id.taskCategory);
        taskTime = itemView.findViewById(R.id.taskTime);
        taskDate = itemView.findViewById(R.id.taskDate);
        doneBtn = itemView.findViewById(R.id.doneBtn);
        selectImportant = itemView.findViewById(R.id.selectImportant);
        editBtn = itemView.findViewById(R.id.editBtn);
        deleteBtn = itemView.findViewById(R.id.deleteBtn);

    }
}
