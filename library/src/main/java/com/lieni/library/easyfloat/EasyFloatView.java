package com.lieni.library.easyfloat;

import android.app.Activity;
import android.graphics.Point;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.lieni.library.easyfloat.utils.SPUtils;
import com.lieni.library.easyfloat.utils.ViewUtils;

import java.util.HashSet;
import java.util.Set;

public abstract class EasyFloatView<T> {
    private T data;
    //已显示的时候不熄屏
    private boolean keepScreenOn = false;
    private String tag = "EasyFloatView";
    private View view;

    //缓存只显示的activity名称
    private Set<String> validActivityNames = new HashSet<>();
    //缓存不显示的activity名称
    private Set<String> invalidActivityNames = new HashSet<>();

    //只在已设置的activity显示
    private boolean onlyValidActivityShow = false;

    private Point positionPoint;

    public EasyFloatView(T data) {
        this.data=data;
        onConfig();
    }

    public EasyFloatView(){
        this(null);
    }

    /**
     * used to config default data,such as addInvalidActivityName(),setInitPosition() and so on
     * @see #addInvalidActivityName(String)
     * @see #setInitPosition(int, int)
     */
    public void onConfig() {
        invalidActivityNames.add("com.android.packageinstaller.permission.ui.GrantpermissionsActivity");
    }

    /**
     * it will be running when view is attached to activity
     */
    public abstract View onCreateView(ViewGroup rootView);


    /**
     * it will be running when view is detached from activity
     */
    public void onDestroyView() {

    }

    /**
     * it will be running when you invoke add() or addImmediately()
     * @see EasyFloat#add(String, EasyFloatView)
     * @see EasyFloat#addImmediately(String, EasyFloatView, Activity)
     */
    public void onAdd(){

    }

    /**
     * it will be running when you invoke remove() or removeImmediately()
     * @see EasyFloat#remove(String)
     * @see EasyFloat#removeImmediately(String)
     */
    public void onRemove(){

    }

    public void attachToRoot(Activity activity) {
        if (view != null) detach();
        ViewGroup decorView = (FrameLayout) activity.getWindow().getDecorView();
        view = onCreateView(decorView);
        if (positionPoint == null) positionPoint = new Point(0, 0);
        view.setTag(tag);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        view.setX(positionPoint.x);
        view.setY(positionPoint.y);
        decorView.addView(view, params);
        if (keepScreenOn) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }

    public void detach() {
        detach(null);
    }

    public void detach(Activity activity) {
        if (view == null) return;
        if (activity != null && !isViewExist(activity)) return;
        ViewGroup viewGroup = (ViewGroup) view.getParent();
        if (viewGroup != null) {
            onDestroyView();
            positionPoint = ViewUtils.getViewPoint(view);
            SPUtils.saveLatestPoint(tag, positionPoint);
            viewGroup.removeView(view);
            view = null;
        }
    }

    /**
     * 该activity是否显示浮窗
     *
     * @param activity
     * @return
     */
    public boolean isActivityValid(Activity activity) {
        if (onlyValidActivityShow) {
            for (String className : validActivityNames) {
                if (className.equals(activity.getComponentName().getClassName())) {
                    return true;
                }
            }
            return false;
        } else {
            for (String className : invalidActivityNames) {
                if (className.equals(activity.getComponentName().getClassName())) {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * 视图是否已存在
     *
     * @param activity
     * @return
     */
    public boolean isViewExist(Activity activity) {
        if (view != null) {
            ViewParent parent = view.getParent();
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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void addInvalidActivityName(String name) {
        invalidActivityNames.add(name);
    }

    public void removeInvalidActivityName(String name) {
        invalidActivityNames.remove(name);
    }

    public void addValidActivityName(String name) {
        validActivityNames.add(name);
    }

    public void removeValidActivityName(String name) {
        validActivityNames.remove(name);
    }

    public boolean isKeepScreenOn() {
        return keepScreenOn;
    }

    public void setKeepScreenOn(boolean keepScreenOn) {
        this.keepScreenOn = keepScreenOn;
    }

    public View getView() {
        return view;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setOnlyValidActivityShow(boolean onlyValidActivityShow) {
        this.onlyValidActivityShow = onlyValidActivityShow;
    }

    public void setInitPosition(int x, int y) {
        this.positionPoint = new Point(x, y);
    }
}
