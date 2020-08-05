package com.lieni.library.easyfloat.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lieni.library.easyfloat.R;

public class FloatView extends FrameLayout {
    private int marginLeft,marginRight,marginTop,marginBottom;

    public FloatView(@NonNull Context context) {
        this(context,null);
    }

    public FloatView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FloatView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }
    private void init(@Nullable AttributeSet attrs){
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.FloatView);
        int margin=typedArray.getLayoutDimension(R.styleable.FloatView_float_margin,0);
        int horizontalMargin=typedArray.getLayoutDimension(R.styleable.FloatView_float_marginHorizontal,margin);
        int verticalMargin=typedArray.getLayoutDimension(R.styleable.FloatView_float_marginVertical,margin);

        marginLeft=typedArray.getLayoutDimension(R.styleable.FloatView_float_marginStart,horizontalMargin);
        marginRight=typedArray.getLayoutDimension(R.styleable.FloatView_float_marginEnd,horizontalMargin);
        marginTop=typedArray.getLayoutDimension(R.styleable.FloatView_float_marginTop,verticalMargin);
        marginBottom=typedArray.getLayoutDimension(R.styleable.FloatView_float_marginBottom,verticalMargin);

        typedArray.recycle();
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
        handleEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
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
                    boolean xLeftOver=getX()<marginLeft;
                    boolean xRightOver=getX()>parentWidth-marginRight-getMeasuredWidth();
                    boolean yTopOver=getY()<marginTop;
                    boolean yBottomOver=getY()>parentHeight-marginBottom-getMeasuredHeight();

                    if (xLeftOver||xRightOver||yTopOver||yBottomOver ) {
                        //吸附
                        ViewPropertyAnimator animator= animate().setInterpolator(new DecelerateInterpolator()).setDuration(500);
                        if(xLeftOver||xRightOver){
                            if(xLeftOver) {
                                animator.x(marginLeft);
                            }
                            if(xRightOver){
                                animator.x(parentWidth-marginRight-getMeasuredWidth());
                            }
                        }
                        if(yTopOver||yBottomOver){
                            if(yTopOver){
                                animator.y(marginTop);
                            }
                            if(yBottomOver){
                                animator.y(parentHeight-marginBottom-getMeasuredHeight());
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

    public int getMarginLeft() {
        return marginLeft;
    }

    public void setMarginLeft(int marginLeft) {
        this.marginLeft = marginLeft;
    }

    public int getMarginRight() {
        return marginRight;
    }

    public void setMarginRight(int marginRight) {
        this.marginRight = marginRight;
    }

    public int getMarginTop() {
        return marginTop;
    }

    public void setMarginTop(int marginTop) {
        this.marginTop = marginTop;
    }

    public int getMarginBottom() {
        return marginBottom;
    }

    public void setMarginBottom(int marginBottom) {
        this.marginBottom = marginBottom;
    }
}
