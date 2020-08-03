package com.lieni.library.easyfloat;

import android.app.Activity;
import android.app.Application;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lieni.library.easyfloat.utils.SPUtils;
import com.lieni.library.easyfloat.utils.ViewUtils;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class EasyFloat {
    private static final String TAG_VIEW="view";

    private static volatile EasyFloat instance;
    private static Application application;
    private static Map<String,Object> data=new HashMap<>();

    private WeakReference <View> view;
    private static boolean alwaysShow=true;
    private EasyFloat() {
        SPUtils.init(application);
        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
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
    public static void setView(Window window,int layoutId){
        setView(window,layoutId);
    }
    public static void setView(Window window,int layoutId,int x,int y){
        setView(window,layoutId,x,y,true,true);
    }
    public static void setView(Window window,int layoutId,int x,int y,boolean alwaysShow,boolean attach){
        View view=window.getLayoutInflater().inflate(layoutId,(ViewGroup) window.getDecorView(),false);
        setView(window,view,x,y,alwaysShow,attach);
    }

    public static void setView(Window window, View view,int x,int y,boolean alwaysShow,boolean attach){
        EasyFloat.alwaysShow=alwaysShow;
        if(getInstance().view!=null){
            getInstance().detachView(getView());
        }
        getInstance().view=new WeakReference<>(view);
        if(attach) getInstance().attachView(window,view,new Point(x,y));
        SPUtils.saveLatestPoint(TAG_VIEW,new Point(x,y));
    }

    public static View getView(){
        if(instance.view!=null){
            return instance.view.get();
        }else{
            return null;
        }
    }

    public static void setAlwaysShow(boolean alwaysShow) {
        EasyFloat.alwaysShow = alwaysShow;
    }

    public static void hide(){
        alwaysShow=false;
        instance.detachView(getView());
    }
    public static void destroy(boolean clearData){
        hide();
        instance.view=null;
        if(clearData){
            data.clear();
        }
    }
    public static void destroy(){
        destroy(true);
    }
    public static void show(Window window,boolean alwaysShow){
        EasyFloat.alwaysShow=alwaysShow;
        View view=getView();
        if(view!=null){
            instance.detachView(view);
            instance.attachView(window,view,SPUtils.getLatestPoint(TAG_VIEW));
        }
    }
    public static void show(Window window){
        show(window,true);
    }

    public static void putData(String key,Object value){
        data.put(key,value);
    }
    public static Object getData(String key){
        return data.get(key);
    }
    public static <T> T getData(String key,Class<T> clz){
        Object object=data.get(key);
        if(clz.isInstance(object)){
            return clz.cast(object);
        }else {
            return null;
        }

    }

    private void updateViewPosition(int x, int y){
        View view=getView();
        if(view!=null){
            view.setX(x);
            view.setY(y);
        }
    }
    private void attachView(Window window, View view, Point point){
        ViewGroup decorView=(FrameLayout)  window.getDecorView();
        if(view!=null&&!ViewUtils.isViewExist(decorView,view)){
            FrameLayout.LayoutParams params=new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,FrameLayout.LayoutParams.WRAP_CONTENT);
            view.setX(point.x);
            view.setY(point.y);
            decorView.addView(view,params);
        }
    }
    private void detachView(View view){
        if(view==null) return;
        ViewGroup viewGroup=(ViewGroup) view.getParent();
        if(viewGroup!=null){
            SPUtils.saveLatestPoint(TAG_VIEW,ViewUtils.getViewPoint(view));
            viewGroup.removeView(view);
        }
    }

}
