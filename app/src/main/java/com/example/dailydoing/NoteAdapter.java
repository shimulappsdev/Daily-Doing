package com.example.dailydoing;

import static com.example.dailydoing.R.drawable.done_btn_back;
import static com.example.dailydoing.R.drawable.done_btn_back_red;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dailydoing.database.Note;
import com.example.dailydoing.database.NoteDatabase;
import com.example.dailydoing.database.NoteUpdateListener;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteViewHolder> {

    Context context;
    List<Note> noteList;
    NoteUpdateListener listener;

    public NoteAdapter(Context context, List<Note> noteList, NoteUpdateListener listener) {
        this.context = context;
        this.noteList = noteList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteViewHolder(LayoutInflater.from(context).inflate(R.layout.all_task_lit_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {

        Note note = noteList.get(position);
        holder.taskTitle.setText(note.getNoteTitle());
        holder.taskCategory.setText("Type: "+note.getNoteCategories());
        holder.taskDate.setText("Date: "+note.getNoteDoingDate());
        holder.taskTime.setText(" Time: "+note.getNoteDoingTime());
        holder.doneBtn.setText(note.getNoteStatus());

        String status = note.getNoteStatus();

        SharedPreferences preferences = context.getSharedPreferences("Important",Context.MODE_PRIVATE);

        holder.selectImportant.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean selctImp) {

                if (selctImp){


                    SharedPreferences.Editor editor = preferences.edit();
                    String impTitle = holder.taskTitle.getText().toString();
                    String impDate = holder.taskDate.getText().toString();
                    String impTime = holder.taskTime.getText().toString();

                    editor.putString("title",impTitle);
                    editor.putString("date",impDate);
                    editor.putString("time",impTime);
                    editor.apply();

                }
                Intent intent = new Intent(context,MainActivity.class);
                context.startActivity(intent);
            }
        });

        holder.doneBtn.setOnClickListener(view -> {

            if (status.equals("Not Done")){
                note.getId();
                note.setNoteStatus("Done");
                NoteDatabase.getInstance(context).getNoteDao().update(note);
                holder.doneBtn.setText(note.getNoteStatus());

            }else {
                note.getId();
                note.setNoteStatus("Not Done");
                NoteDatabase.getInstance(context).getNoteDao().update(note);
                holder.doneBtn.setText(note.getNoteStatus());
            }

            listener.doneNote(note);

        });

        holder.editBtn.setOnClickListener(view -> {
            listener.editNote(note);
        });

        holder.deleteBtn.setOnClickListener(view -> {
            listener.deleteNote(note);
        });



    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }
}
