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
import android.widget.TextView;

public class CompletedItemActivity extends AppCompatActivity {

    public static class DeleteItemDialogFragment extends DialogFragment {
        private int itemID;

        public void setItemID(int itemID) {
            this.itemID = itemID;
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            if (savedInstanceState != null) {
                itemID = savedInstanceState.getInt("itemID");
            }
            return new AlertDialog.Builder(getActivity())
                    .setMessage(R.string.delete_item_dialog)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ((CompletedItemActivity) getActivity()).RemoveItem(itemID);
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .create();
        }

        @Override
        public void onSaveInstanceState(@NonNull Bundle outState) {
            super.onSaveInstanceState(outState);
            outState.putInt("itemID", itemID);
        }
    }

    private TodoboomApp app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_item);

        app = (TodoboomApp) getApplicationContext();
        TextView content = findViewById(R.id.completed_task_content);
        Button unmarkBtn = findViewById(R.id.unmark_complete_btn);
        Button deleteBtn = findViewById(R.id.delete_task_btn);

        Intent intentCreatedMe = getIntent();
        int defaultValue = -1;
        final int id = intentCreatedMe.getIntExtra("id", defaultValue);
        if (id == -1)
            return;
        final TodoItem item = app.todoListInfo.get(id);
        content.setText(item.getContent());

        unmarkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app.todoListInfo.setIsDone(id, false);
                app.adapter.notifyDataSetChanged();
                goBackToMainActivity();
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteItemDialogFragment dialog = new DeleteItemDialogFragment();
                dialog.setItemID(id);
                dialog.show(getSupportFragmentManager(), "tag");
            }
        });
    }

    private void RemoveItem(int itemID) {
        app.todoListInfo.delete(itemID);
        app.adapter.notifyDataSetChanged();
        goBackToMainActivity();
    }

    private void goBackToMainActivity() {
        finish();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
