package com.lieni.library.easyfloat;

import android.app.Activity;
import android.app.Application;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lieni.library.easyfloat.utils.SPUtils;
import com.lieni.library.easyfloat.utils.ViewUtils;

import java.lang.ref.WeakReference;

public class EasyFloat {
    public static final String TAG="EasyFloat";

    private static volatile EasyFloat instance;
    private static Application application;

    private WeakReference <View> view;
    private boolean alwaysShow=true;
    public EasyFloat() {
        SPUtils.init(application);
        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
                Log.i(TAG,"onActivityCreated");
            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {
                if(alwaysShow){
                    if(view!=null){
                        detachView(getView());
                        attachView(activity.getWindow(),getView(),SPUtils.getLatestPoint("view"));
                    }
                }
            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {
            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {

            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {
            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {
//                activities.remove(activity);
                if(!alwaysShow){
                    if (view!=null){
                        detachView(getView());
                    }
                }
            }
        });
    }

    public static void init(Application app){
        application=app;
        getInstance();
    }
    private static EasyFloat getInstance(){
        if(instance==null){
            synchronized (EasyFloat.class){
                if(instance==null){
                    instance=new EasyFloat();
                }
            }
        }
        return instance;
    }
    public static void setView(Window window, View view,int x,int y){
        if(getInstance().view!=null){
            getInstance().detachView(getView());
        }
        getInstance().view=new WeakReference<>(view);
        getInstance().attachView(window,view,new Point(x,y));
        SPUtils.saveLatestPoint("view",new Point(x,y));
    }




//    private ViewGroup getDecorView(){
//        return  (FrameLayout) getLatestActivity().getWindow().getDecorView();
//    }
    public static View getView(){
        if(instance.view!=null){
            return instance.view.get();
        }else{
            return null;
        }
    }
    private void updateViewPosition(int x,int y){
        View view=getView();
        if(view!=null){
            view.setX(x);
            view.setY(y);
        }
    }
    private void attachView(Window window, View view, Point point){
        ViewGroup decorView=(FrameLayout)  window.getDecorView();
        Log.i(TAG,decorView.getChildCount()+"");
        if(!ViewUtils.isViewExist(decorView,view)){
            FrameLayout.LayoutParams params=new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,FrameLayout.LayoutParams.WRAP_CONTENT);
            view.setX(point.x);
            view.setY(point.y);
            decorView.addView(view,params);
        }
    }
    private void detachView(View view){
        ViewGroup viewGroup=(ViewGroup) view.getParent();
        if(viewGroup!=null){
            SPUtils.saveLatestPoint("view",ViewUtils.getViewPoint(view));
            viewGroup.removeView(view);
        }
//        ViewGroup decorView=getDecorView();
////        if(ViewUtils.isViewExist(decorView,view)){
////            decorView.removeView(view);
////        }
    }

}
