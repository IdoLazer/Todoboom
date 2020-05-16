package com.my.todoboom;

import android.app.Application;

public class TodoboomApp extends Application {

    public TodoListInfoManager todoListInfo;

    @Override
    public void onCreate() {
        super.onCreate();
        todoListInfo = new TodoListInfoManager(this);
    }
}
