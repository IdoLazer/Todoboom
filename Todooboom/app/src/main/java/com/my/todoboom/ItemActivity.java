package com.my.todoboom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ItemActivity extends AppCompatActivity {

    public static class ContentChangedDialogFragment extends DialogFragment {

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return new AlertDialog.Builder(getActivity())
                    .setMessage(R.string.content_changed_dialog)
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

    private EditText content;
    private boolean isInEditMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        final TodoboomApp app = (TodoboomApp) getApplicationContext();
        content = findViewById(R.id.task_content);
        TextView creationTime = findViewById(R.id.creation_time);
        final TextView lastEditTime = findViewById(R.id.last_edit_time);
        final Button applyBtn = findViewById(R.id.apply_content_change_btn);
        Button editBtn = findViewById(R.id.edit_content_btn);
        Button markDoneBtn = findViewById(R.id.mark_complete_btn);

        Intent intentCreatedMe = getIntent();
        int defaultValue = -1;
        final int id = intentCreatedMe.getIntExtra("id", defaultValue);
        if (id == -1)
            return;
        final TodoItem item = app.todoListInfo.get(id);

        creationTime.setText("Created On: " + item.getCreationTimestamp().toString());
        lastEditTime.setText("Last Edited On: " + item.getLastEditTimestamp().toString());
        if (savedInstanceState != null && savedInstanceState.getBoolean("isInEditing")) {
            content.setText(savedInstanceState.getString("currentContent"));
        }

        else {
            content.setText(item.getContent());
            content.setEnabled(false);
            applyBtn.setEnabled(false);
            applyBtn.setVisibility(View.INVISIBLE);
        }

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content.setEnabled(true);
                applyBtn.setVisibility(View.VISIBLE);
                applyBtn.setEnabled(true);
                isInEditMode = true;
            }
        });

        applyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app.todoListInfo.editItemContent(id, content.getText().toString());
                content.setEnabled(false);
                applyBtn.setVisibility(View.INVISIBLE);
                new ContentChangedDialogFragment().show(getSupportFragmentManager(), "tag");
                lastEditTime.setText("Last Edited On: " + item.getLastEditTimestamp().toString());
                app.adapter.notifyDataSetChanged();
                isInEditMode = false;
            }
        });

        markDoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app.todoListInfo.setIsDone(id, true);
                app.adapter.notifyDataSetChanged();
                goBackToMainActivity();
            }
        });
    }

    private void goBackToMainActivity() {
        finish();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("currentContent", content.getText().toString());
        outState.putBoolean("isInEditing", isInEditMode);
    }
}
