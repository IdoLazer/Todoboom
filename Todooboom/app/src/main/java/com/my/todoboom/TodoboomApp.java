package com.my.todoboom;

import android.app.Application;
import android.util.Log;

public class TodoboomApp extends Application {

    public TodoListInfoManager todoListInfo;
    public TodoAdapter adapter;

    @Override
    public void onCreate(){
        super.onCreate();
        TodoListInfoManager.init(this);
        try {
            todoListInfo = TodoListInfoManager.getManager();
        } catch (Exception e) {
            Log.d("tag", e.getMessage());
            return;
        }
        adapter = new TodoAdapter(this);
    }
}
