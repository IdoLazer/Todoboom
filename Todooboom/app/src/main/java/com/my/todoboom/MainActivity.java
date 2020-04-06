package com.my.todoboom;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText taskInput = findViewById(R.id.task_input);
        Button createBtn = findViewById(R.id.create_btn);
        Button clearBtn = findViewById(R.id.clear_btn);
        final TextView taskList = findViewById(R.id.task_list);

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (taskInput.getText().length() == 0) {
                    return;
                }
                taskList.append(taskInput.getText() + "\n");
                taskInput.getText().clear();
            }
        });

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskList.setText("");
            }
        });
    }
}
