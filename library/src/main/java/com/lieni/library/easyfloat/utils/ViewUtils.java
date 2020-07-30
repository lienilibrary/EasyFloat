package com.lieni.library.easyfloat.utils;

import android.graphics.Point;
import android.view.View;
import android.view.ViewGroup;

public class ViewUtils {
    public static boolean isViewExist(ViewGroup viewGroup, View view){
        for (int i=0;i<viewGroup.getChildCount();i++){
            if(view==viewGroup.getChildAt(i)){
                return true;
            }
        }
        return false;
    }
    public static boolean isViewExist(ViewGroup viewGroup, String viewTag){
        for (int i=0;i<viewGroup.getChildCount();i++){
            if(viewTag==viewGroup.getChildAt(i).getTag()){
                return true;
            }
        }
        return false;
    }
    public static Point getViewPoint(View view){
        if(view==null) return new Point(0,0);
        int[] location = new int[2] ;
        view.getLocationInWindow(location);
        return new Point(location[0],location[1]);
    }
}
