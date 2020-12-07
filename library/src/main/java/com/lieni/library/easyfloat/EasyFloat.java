package com.lieni.library.easyfloat;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lieni.library.easyfloat.utils.SPUtils;

import java.util.ArrayList;
import java.util.List;

public class EasyFloat {
    private static volatile EasyFloat instance;
    private List<EasyFloatView> list=new ArrayList<>();
    private Application.ActivityLifecycleCallbacks callbacks;

    private int getPosition(String tag){
        for (int i=0;i<list.size();i++){
            if(list.get(i).getTag().equals(tag)){
                return i;
            }
        }
        return -1;
    }
    private void register(Application application){
        if(callbacks==null){
            callbacks=new Application.ActivityLifecycleCallbacks() {
                @Override
                public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
                }

                @Override
                public void onActivityStarted(@NonNull Activity activity) {
                    List<EasyFloatView> data=getInstance().list;
                    for (int i=0;i<data.size();i++){
                        EasyFloatView view=data.get(i);
                        if(view.isActivityValid(activity)) view.attachToRoot(activity);
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
                    for (int i=0;i<getInstance().list.size();i++){
                        EasyFloatView view=getInstance().list.get(i);
                        if(view.isActivityValid(activity)) view.detach(activity);
                    }
                }

                @Override
                public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

                }

                @Override
                public void onActivityDestroyed(@NonNull Activity activity) {
                }
            };
            application.registerActivityLifecycleCallbacks(callbacks);
        }
    }

    public static void init(Application application){
        SPUtils.init(application);
        getInstance().register(application);
    }
    public static void add(String tag,EasyFloatView easyFloatView){
        easyFloatView.setTag(tag);
        easyFloatView.onAdd();
        getInstance().list.add(easyFloatView);
    }
    public static void addImmediately(String tag,EasyFloatView easyFloatView,Activity activity){
        add(tag,easyFloatView);
        if(activity!=null){
            if(easyFloatView.isActivityValid(activity)) easyFloatView.attachToRoot(activity);
        }
    }
    public static void remove(String tag){
        int position=getInstance().getPosition(tag);
        if(position>-1) {
            getInstance().list.get(position).onRemove();
            getInstance().list.remove(position);
        }
    }
    public static void removeImmediately(String tag){
        int position=getInstance().getPosition(tag);
        if(position>-1) getInstance().list.get(position).detach();
        remove(tag);
    }

    private static EasyFloat getInstance() {
        if (instance == null) {
            synchronized (EasyFloat.class) {
                if (instance == null) {
                    instance = new EasyFloat();
                }
            }
        }
        return instance;
    }
}
