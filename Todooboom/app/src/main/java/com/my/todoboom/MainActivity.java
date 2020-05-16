package com.my.todoboom;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private TodoAdapter adapter;
    private EditText taskInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TodoboomApp app = (TodoboomApp) getApplicationContext();
        taskInput = findViewById(R.id.task_input);
        Button createBtn = findViewById(R.id.create_btn);
        Button clearBtn = findViewById(R.id.clear_btn);
        RecyclerView todoRecycler = findViewById(R.id.todo_recycler);

        createAdapter(app);

        setRecycler(todoRecycler);

        setCreateButton(createBtn);

        setClearButton(clearBtn);

        restoreState(savedInstanceState);
    }

    private void restoreState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            taskInput.setText(savedInstanceState.getString("currentEditText"));
        }
    }

    private void setClearButton(Button clearBtn) {
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.clearTodos();
            }
        });
    }

    private void setCreateButton(Button createBtn) {
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
    }

    private void setRecycler(RecyclerView todoRecycler) {
        boolean reverseLayout = false;
        todoRecycler.setAdapter(adapter);
        todoRecycler.setLayoutManager(
                new LinearLayoutManager(this, RecyclerView.VERTICAL, reverseLayout));
    }

    private void createAdapter(final TodoboomApp app) {
        adapter = new TodoAdapter(app.todoListInfo);
        adapter.setTodoClickListener(new TodoAdapter.TodoClickListener() {
            @Override
            public void onTodoClicked(int id) {
                app.todoListInfo.setIsDone(id);
            }
        });
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        adapter.setTodoLongClickListener(new TodoAdapter.TodoLongClickListener() {
            @Override
            public void onTodoLongClickListener(final int itemID) {
                AlertDialog dialog = builder
                        .setMessage(R.string.delete_item_dialog)
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                adapter.deleteTodo(itemID);
                            }
                        })
                        .setNegativeButton("no", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        }).create();
                dialog.show();
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("currentEditText", taskInput.getText().toString());
    }
}
