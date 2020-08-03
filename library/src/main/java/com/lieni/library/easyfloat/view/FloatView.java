package com.lieni.library.easyfloat.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class FloatView extends FrameLayout {
    public FloatView(@NonNull Context context) {
        this(context,null);
    }

    public FloatView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FloatView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init(){
        setClickable(true);
    }

    private int lastX;
    private int lastY;
    private boolean isDrag;
    private int parentHeight;
    private int parentWidth;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i("sssssssss","dispatchTouchEvent");
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
//                pressedTime=System.currentTimeMillis();
                Log.i("sssssssss","ACTION_DOWN");
                break;
            case MotionEvent.ACTION_UP:
                Log.i("sssssssss","ACTION_UP");
                break;
        }
        handleEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i("sssssssss","onInterceptTouchEvent");

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void handleEvent(MotionEvent event){
        int rawX = (int) event.getRawX();
        int rawY = (int) event.getRawY();
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                Log.i("sssssssss","onTouchEvent ACTION_DOWN");
                setPressed(true);
                isDrag = false;
                getParent().requestDisallowInterceptTouchEvent(true);
                lastX = rawX;
                lastY = rawY;
                ViewGroup parent;
                if (getParent() != null) {
                    parent = (ViewGroup) getParent();
                    parentHeight = parent.getHeight();
                    parentWidth = parent.getWidth();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (parentHeight <= 0 || parentWidth == 0) {
                    isDrag = false;
                    break;
                } else {
                    isDrag = true;
                }
                int dx = rawX - lastX;
                int dy = rawY - lastY;
                //这里修复一些华为手机无法触发点击事件
                int distance = (int) Math.sqrt(dx * dx + dy * dy);
                if (distance == 0) {
                    isDrag = false;
                    break;
                }
                float x = getX() + dx;
                float y = getY() + dy;
                setX(x);
                setY(y);
                lastX = rawX;
                lastY = rawY;
                break;
            case MotionEvent.ACTION_UP:
                if (isDrag) {
                    //恢复按压效果
                    setPressed(false);
                    boolean xLeftOver=getX()<0;
                    boolean xRightOver=getX()+getMeasuredWidth()>parentWidth;
                    boolean yTopOver=getY()<0;
                    boolean yBottomOver=getY()+getMeasuredHeight()>parentHeight;

                    if (xLeftOver||xRightOver||yTopOver||yBottomOver ) {
                        //吸附
                        ViewPropertyAnimator animator= animate().setInterpolator(new DecelerateInterpolator()).setDuration(500);
                        if(xLeftOver||xRightOver){
                            if(xLeftOver) {
                                animator.x(0);
                            }
                            if(xRightOver){
                                animator.x(parentWidth-getMeasuredWidth());
                            }
                        }
                        if(yTopOver||yBottomOver){
                            if(yTopOver){
                                animator.y(0);
                            }
                            if(yBottomOver){
                                animator.y(parentHeight-getMeasuredHeight());
                            }
                        }
                        animator.start();

                    }
                }
                break;
            default:
                break;
        }
    }
}
