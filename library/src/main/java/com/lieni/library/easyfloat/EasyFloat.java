package com.lieni.library.easyfloat;

import android.app.Activity;
import android.app.Application;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lieni.library.easyfloat.utils.SPUtils;
import com.lieni.library.easyfloat.utils.ViewUtils;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EasyFloat {
    private static final String TAG_VIEW = "view";

    private static volatile EasyFloat instance;
    private static Application application;
    //缓存临时数据
    private static Map<String, Object> data = new HashMap<>();
    //缓存不显示的activity
    private static Set<Class> invalidActivities = new HashSet<>();
    //缓存不显示的activity名称
    private static Set<String> invalidActivityNames = new HashSet<>();

    //缓存只显示的activity
    private static Set<Class> validActivities = new HashSet<>();
    //缓存只显示的activity名称
    private static Set<String> validActivityNames = new HashSet<>();

    //只在已设置的activity显示
    private static boolean onlyValidActivityShow = false;

    //已显示的时候不熄屏
    private static boolean keepScreenOn = false;

    //状态监听
    private static OnFloatStateChanged onFloatStateChanged;

    private WeakReference<View> view;

    private EasyFloat() {
        SPUtils.init(application);
        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {
                if (view != null && view.get() != null && isActivityValid(activity) && !isViewExist(activity)) {
                    if (onFloatStateChanged != null) {
                        boolean intercept = onFloatStateChanged.beforeShow(activity, view.get());
                        if (intercept) return;
                    }
                    detachView(getView());
                    attachView(activity.getWindow(), getView(), SPUtils.getLatestPoint("view"));

                    if (keepScreenOn && !activity.isFinishing()) {
                        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                    }

                    if (onFloatStateChanged != null) {
                        onFloatStateChanged.afterShow(view.get());
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
                if (isViewExist(activity)) {
                    detachView(getView());
                }
            }
        });
    }

    public static void init(Application app) {
        application = app;
        getInstance();
        invalidActivityNames.add("GrantPermissionsActivity");//忽略系统授权页面
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

    public static void setView(Window window, int layoutId) {
        setView(window, layoutId);
    }

    public static void setView(Window window, int layoutId, int x, int y) {
        setView(window, layoutId, x, y, true);
    }

    public static void setView(Window window, int layoutId, int x, int y, boolean attach) {
        View view = window.getLayoutInflater().inflate(layoutId, (ViewGroup) window.getDecorView(), false);
        setView(window, view, x, y, attach);
    }

    public static void setView(Window window, View view, int x, int y, boolean attach) {
        if (getInstance().view != null) {
            getInstance().detachView(getView());
        }
        getInstance().view = new WeakReference<>(view);
        if (attach) getInstance().attachView(window, view, new Point(x, y));
        SPUtils.saveLatestPoint(TAG_VIEW, new Point(x, y));
    }

    public static View getView() {
        if (instance.view != null) {
            return instance.view.get();
        } else {
            return null;
        }
    }

    public static void hide() {
        instance.detachView(getView());
    }

    public static void destroy(boolean clearData) {
        hide();
        instance.view = null;
        if (clearData) {
            data.clear();
            onFloatStateChanged = null;
        }
    }

    public static void destroy() {
        destroy(true);
    }

    public static void show(Window window) {
        View view = getView();
        if (view != null) {
            instance.detachView(view);
            instance.attachView(window, view, SPUtils.getLatestPoint(TAG_VIEW));
        }
    }

    /**
     * activity 该activity是否不显示浮窗
     *
     * @param activity
     * @return
     */
    public static boolean isActivityValid(Activity activity) {
        if (onlyValidActivityShow) {
            for (String className : validActivityNames) {
                if (className.equals(activity.getLocalClassName())) {
                    return true;
                }
            }
            for (Class clz : validActivities) {
                if (clz.isInstance(activity)) {
                    return true;
                }
            }
            return false;
        } else {
            for (String className : invalidActivityNames) {
                if (className.equals(activity.getLocalClassName())) {
                    return false;
                }
            }
            for (Class clz : invalidActivities) {
                if (clz.isInstance(activity)) {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * view是否已经存在activity中
     *
     * @param activity
     * @return
     */

    public static boolean isViewExist(Activity activity) {
        if (instance.view != null && instance.view.get() != null) {
            ViewParent parent = instance.view.get().getParent();
            View decorView = activity.getWindow().getDecorView();
            if (parent instanceof FrameLayout && decorView instanceof FrameLayout) {
                FrameLayout parentContainer = (FrameLayout) parent;
                FrameLayout decorContainer = (FrameLayout) decorView;
                return parentContainer == decorContainer;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private void updateViewPosition(int x, int y) {
        View view = getView();
        if (view != null) {
            view.setX(x);
            view.setY(y);
        }
    }

    private void attachView(Window window, View view, Point point) {
        ViewGroup decorView = (FrameLayout) window.getDecorView();
        if (view != null && !ViewUtils.isViewExist(decorView, view)) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            view.setX(point.x);
            view.setY(point.y);
            decorView.addView(view, params);
        }
    }

    private void detachView(View view) {
        if (view == null) return;
        ViewGroup viewGroup = (ViewGroup) view.getParent();
        if (viewGroup != null) {
            SPUtils.saveLatestPoint(TAG_VIEW, ViewUtils.getViewPoint(view));
            viewGroup.removeView(view);
        }
    }

    public static boolean isOnlyValidActivityShow() {
        return onlyValidActivityShow;
    }

    public static void setOnlyValidActivityShow(boolean onlyValidActivityShow) {
        EasyFloat.onlyValidActivityShow = onlyValidActivityShow;
    }

    public static boolean isKeepScreenOn() {
        return keepScreenOn;
    }

    public static void setKeepScreenOn(boolean keepScreenOn) {
        EasyFloat.keepScreenOn = keepScreenOn;
    }

    public static void putData(String key, Object value) {
        data.put(key, value);
    }

    public static Object getData(String key) {
        return data.get(key);
    }

    public static <T> T getData(String key, Class<T> clz) {
        Object object = data.get(key);
        if (clz.isInstance(object)) {
            return clz.cast(object);
        } else {
            return null;
        }

    }

    public static void addInvalidActivity(Class clz) {
        invalidActivities.add(clz);
    }

    public static void removeInvalidActivity(Class clz) {
        invalidActivities.remove(clz);
    }

    public static void addInvalidActivityName(String name) {
        invalidActivityNames.add(name);
    }

    public static void removeInvalidActivityName(String name) {
        invalidActivityNames.remove(name);
    }

    public static void addValidActivity(Class clz) {
        validActivities.add(clz);
    }

    public static void removeValidActivity(Class clz) {
        validActivities.remove(clz);
    }

    public static void addValidActivityName(String name) {
        validActivityNames.add(name);
    }

    public static void removeValidActivityName(String name) {
        validActivityNames.remove(name);
    }

    public static OnFloatStateChanged getOnFloatStateChanged() {
        return onFloatStateChanged;
    }

    public static void setOnFloatStateChanged(OnFloatStateChanged onFloatStateChanged) {
        EasyFloat.onFloatStateChanged = onFloatStateChanged;
    }

    public interface OnFloatStateChanged {
        /**
         * @return false 继续进行;true 拦截
         */
        boolean beforeShow(Activity activity, View view);

        void afterShow(View view);

    }

}
