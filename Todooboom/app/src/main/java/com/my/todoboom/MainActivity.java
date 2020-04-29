package com.my.todoboom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TodoAdapter adapter;
    private EditText taskInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean reverseLayout = false;
        taskInput = findViewById(R.id.task_input);
        Button createBtn = findViewById(R.id.create_btn);
        Button clearBtn = findViewById(R.id.clear_btn);
        RecyclerView todoRecycler = findViewById(R.id.todo_recycler);

        adapter = new TodoAdapter();
        adapter.setTodoClickListener(new TodoAdapter.TodoClickListener() {
            @Override
            public void onTodoClicked(TodoItem todo) {
                todo.setDone(true);
            }
        });

        todoRecycler.setAdapter(adapter);
        todoRecycler.setLayoutManager(
                new LinearLayoutManager(this, RecyclerView.VERTICAL, reverseLayout));

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (taskInput.getText().length() == 0) {
                    return;
                }
                adapter.addTodo(new TodoItem(taskInput.getText().toString(), false));
                taskInput.getText().clear();
            }
        });

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.clearTodo();
            }
        });

        if (savedInstanceState != null) {
            taskInput.setText(savedInstanceState.getString("currentEditText"));
            ArrayList<String> todos = savedInstanceState.getStringArrayList("tasks");

            if (todos == null) return;

            for (String todo : todos) {
                adapter.addTodo(new TodoItem(todo));
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("currentEditText", taskInput.getText().toString());
        outState.putStringArrayList("tasks", adapter.getTodosAsStrings());
    }
}
