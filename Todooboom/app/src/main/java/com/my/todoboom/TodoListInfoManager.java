package com.my.todoboom;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TodoListInfoManager {
    public static String TODO_LIST_SP = "com.my.todoboom.preferences";
    public static String KEY_ITEMS_COUNT = "my.todoboom.items_count";

    private static TodoListInfoManager manager;
    private TodoboomApp app;
    private FirebaseFirestore db;
    private List<TodoItem> todos = new ArrayList<>();
    private SharedPreferences sp;

    private TodoListInfoManager(Context context) {
        app = (TodoboomApp) context;
        db = FirebaseFirestore.getInstance();
        db.collection("tasks")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.d("tag", e.getMessage() != null ? e.getMessage() : "exception");
                            return;
                        }
                        TodoListInfoManager.this.todos.clear();
                        if (queryDocumentSnapshots == null) {
                            Log.d("tag", "query returned null");
                            return;
                        }
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            TodoListInfoManager.this.todos.add(document.toObject(TodoItem.class));
                        }
                        TodoListInfoManager.this.todos.sort(new Comparator<TodoItem>() {
                            @Override
                            public int compare(TodoItem o1, TodoItem o2) {
                                return o1.getCreationTimestamp().compareTo(o2.getCreationTimestamp());
                            }
                        });
                        app.adapter.notifyDataSetChanged();
                    }
                });
    }

    public static void init(Context context) {
        if (manager == null) {
            manager = new TodoListInfoManager(context);
        }
    }

    public static TodoListInfoManager getManager() throws Exception {
        if (manager == null) {
            throw new Exception("Must init TodoListInfoManager!");
        }
        return manager;
    }

    public TodoItem get(int id) {
        return todos.get(id);
    }

    public void add(TodoItem todo) {
        todos.add(todo);
        DocumentReference document = db.collection("tasks").document();
        todo.setId(document.getId());
        document.set(todo);
    }

    public void clear() {
        for (TodoItem item : todos) {
            db.collection("tasks").document(item.getId()).delete();
        }
    }

    public void delete(int id) {
        TodoItem item = todos.get(id);
        todos.remove(id);
        db.collection("tasks").document(item.getId()).delete();
    }

    public void setIsDone(int id, boolean done) {
        TodoItem todo = todos.get(id);
        todo.setDone(done);
        db.collection("tasks").document(todo.getId()).set(todo);
    }

    public void editItemContent(int id, String newContent) {
        TodoItem todo = todos.get(id);
        todo.setContent(newContent);
        db.collection("tasks").document(todo.getId()).set(todo);
    }

    public int size() {
        return todos.size();
    }
}
