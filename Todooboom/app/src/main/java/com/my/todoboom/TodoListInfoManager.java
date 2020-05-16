package com.my.todoboom;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class TodoListInfoManager {
    public static String TODO_LIST_SP = "com.my.todoboom.preferences";
    public static String KEY_ITEMS_COUNT = "my.todoboom.items_count";

    private List<TodoItem> todos = new ArrayList<>();
    private Gson gson;
    private SharedPreferences sp;

    public TodoListInfoManager(Context context) {
        sp = context.getSharedPreferences(TODO_LIST_SP, MODE_PRIVATE);
        int itemCount = sp.getInt(KEY_ITEMS_COUNT, 0);
        Log.i(null, "Items in list: " + itemCount);
        gson = new Gson();
        for (int id = 0; id < itemCount; id++) {
            String storedJson = sp.getString(Integer.toString(id), null);
            todos.add(gson.fromJson(storedJson, TodoItem.class));
        }
    }

    public TodoItem get(int id) {
        return todos.get(id);
    }

    public void add(TodoItem todo) {
        todos.add(todo);
        sp.edit()
                .putString(Integer.toString(size() - 1), gson.toJson(todo))
                .putInt(KEY_ITEMS_COUNT, size())
                .apply();
    }

    public void clear() {
        todos.clear();
        int itemCount = sp.getInt(KEY_ITEMS_COUNT, 0);
        for (int id = 0; id < itemCount; id++) {
            sp.edit()
                    .remove(Integer.toString(id))
                    .apply();
        }
        sp.edit().putInt(KEY_ITEMS_COUNT, 0).apply();
    }

    public void delete(int id) {
        for (int i = id + 1; i < size(); i++) {
            sp.edit().putString(Integer.toString(i - 1), gson.toJson(todos.get(i))).apply();
        }
        todos.remove(id);
        sp.edit().putInt(KEY_ITEMS_COUNT, size()).apply();
    }

    public void setIsDone(int id) {
        TodoItem todo = todos.get(id);
        todo.setDone(true);
        sp.edit()
                .putString(Integer.toString(id), gson.toJson(todo))
                .apply();
    }

    public int size() {
        return todos.size();
    }
}
