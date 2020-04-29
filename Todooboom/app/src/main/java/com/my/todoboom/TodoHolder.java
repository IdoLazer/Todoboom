package com.my.todoboom;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class TodoHolder extends RecyclerView.ViewHolder {

    ImageView tickBox;
    TextView todoText;

    TodoHolder(View view) {
        super(view);
        tickBox = view.findViewById(R.id.task_complete_icon);
        todoText = view.findViewById(R.id.todo_task_text);
    }
}
