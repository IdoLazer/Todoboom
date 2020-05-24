package com.my.todoboom;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private TodoAdapter adapter;
    private EditText taskInput;
    private TodoboomApp app;

    public static class CantCreateEmptyTaskDialogFragment extends DialogFragment {

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            return new AlertDialog.Builder(getActivity())
                    .setMessage(R.string.cant_create_empty_task_dialog)
                    .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .create();
        }

        @Override
        public void onSaveInstanceState(@NonNull Bundle outState) {
            super.onSaveInstanceState(outState);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        app = (TodoboomApp) getApplicationContext();
        taskInput = findViewById(R.id.task_input);
        Button createBtn = findViewById(R.id.create_btn);
        Button clearBtn = findViewById(R.id.clear_btn);
        RecyclerView todoRecycler = findViewById(R.id.todo_recycler);

        createAdapter();

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
                app.todoListInfo.clear();
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void setCreateButton(Button createBtn) {
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (taskInput.getText().length() == 0) {
                    new CantCreateEmptyTaskDialogFragment()
                            .show(getSupportFragmentManager(), "tag");
                    return;
                }
                app.todoListInfo.add(new TodoItem(taskInput.getText().toString(), false));
                adapter.notifyDataSetChanged();
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

    private void createAdapter() {
        adapter = app.adapter;
        adapter.setTodoClickListener(new TodoAdapter.TodoClickListener() {
            @Override
            public void onTodoClicked(int id) {
                if (app.todoListInfo.get(id).isDone()) {
                    openCompleteItemActivity(id);
                } else {
                    openItemActivity(id);
                }
            }
        });
    }

    private void openCompleteItemActivity(int id) {
        Intent intent = new Intent(this, CompletedItemActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    private void openItemActivity(int id) {
        Intent intent = new Intent(this, ItemActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("currentEditText", taskInput.getText().toString());
    }
}
