package com.lieni.library.easyfloat.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;

public class SPUtils {
    private static volatile SPUtils instance;
    private SharedPreferences sp;

    public SPUtils(SharedPreferences sp) {
        this.sp = sp;
    }

    public static void init(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences("EasyFloat", Context.MODE_PRIVATE);
        instance=new SPUtils(sharedPreferences);
    }

    public static Point getLatestPoint(String tag){
        Point point=new Point();
        point.x=instance.sp.getInt(tag+"_x",0);
        point.y=instance.sp.getInt(tag+"_y",0);
        return point;
    }
    public static void saveLatestPoint(String tag,Point point){
        instance.sp.edit()
                .putInt(tag+"_x",point.x)
                .putInt(tag+"_y",point.y)
                .apply();
    }
}
