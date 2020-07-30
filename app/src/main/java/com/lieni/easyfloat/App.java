package com.lieni.easyfloat;

import android.app.Application;
import android.util.Log;

import com.lieni.library.easyfloat.EasyFloat;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("sssssss","ssss");
        EasyFloat.init(this);
    }
}
