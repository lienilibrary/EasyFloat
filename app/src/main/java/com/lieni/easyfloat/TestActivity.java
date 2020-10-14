package com.lieni.easyfloat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Parcelable;
import android.transition.ChangeBounds;
import android.transition.ChangeTransform;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.SharedElementCallback;

import com.lieni.library.easyfloat.EasyFloat;

import java.util.List;
import java.util.Map;

public class TestActivity extends AppCompatActivity {
    private TextView tvTest;
    private FrameLayout flContainer;
    String TAG_EXIT="sssss exit activity B";
    String TAG_ENTER="sssss enter activity B";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        flContainer=findViewById(R.id.flContainer);
        tvTest=findViewById(R.id.tvTest);
        tvTest.setText("nav to test2");
//        tvTest.setTransitionName("shareElement");
        tvTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TestActivity.this,Test2Activity.class);
                startActivity(intent);
            }
        });
//        tvTest.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
//            @Override
//            public void onViewAttachedToWindow(View v) {
//                Log.i("sssssssss","onViewAttachedToWindow");
//            }
//
//            @Override
//            public void onViewDetachedFromWindow(View v) {
//                Log.i("sssssssss","onViewDetachedFromWindow");
//            }
//        });
//        initTran();
//        initTranCallback();
    }

    private void initTran(){
//        getWindow().setSharedElementReturnTransition(null);
//        getWindow().setSharedElementEnterTransition(new ChangeTransform());
//        getWindow().setSharedElementExitTransition(new ChangeTransform());
//        transitionSet.addTransition(new ChangeTransform());
    }
    private void initTranCallback(){
        setEnterSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onSharedElementStart(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
                Log.i(TAG_ENTER,"onSharedElementStart");
                super.onSharedElementStart(sharedElementNames, sharedElements, sharedElementSnapshots);
            }

            @Override
            public void onSharedElementEnd(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
                Log.i(TAG_ENTER,"onSharedElementEnd");
                super.onSharedElementEnd(sharedElementNames, sharedElements, sharedElementSnapshots);
            }

            @Override
            public void onRejectSharedElements(List<View> rejectedSharedElements) {
                Log.i(TAG_ENTER,"onRejectSharedElements");
                super.onRejectSharedElements(rejectedSharedElements);
            }

            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                Log.i(TAG_ENTER,"onMapSharedElements");
                super.onMapSharedElements(names, sharedElements);
            }

            @Override
            public Parcelable onCaptureSharedElementSnapshot(View sharedElement, Matrix viewToGlobalMatrix, RectF screenBounds) {
                Log.i(TAG_ENTER,"onCaptureSharedElementSnapshot");
                return super.onCaptureSharedElementSnapshot(sharedElement, viewToGlobalMatrix, screenBounds);
            }

            @Override
            public View onCreateSnapshotView(Context context, Parcelable snapshot) {
                Log.i(TAG_ENTER,"onCreateSnapshotView");
                return super.onCreateSnapshotView(context, snapshot);
            }

            @Override
            public void onSharedElementsArrived(List<String> sharedElementNames, List<View> sharedElements, OnSharedElementsReadyListener listener) {
                Log.i(TAG_ENTER,"onSharedElementsArrived");
                super.onSharedElementsArrived(sharedElementNames, sharedElements, listener);
            }
        });
        setExitSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onSharedElementStart(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
                Log.i(TAG_EXIT,"onSharedElementStart");
                super.onSharedElementStart(sharedElementNames, sharedElements, sharedElementSnapshots);
            }

            @Override
            public void onSharedElementEnd(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
                Log.i(TAG_EXIT,"onSharedElementEnd");
                super.onSharedElementEnd(sharedElementNames, sharedElements, sharedElementSnapshots);
            }

            @Override
            public void onRejectSharedElements(List<View> rejectedSharedElements) {
                Log.i(TAG_EXIT,"onRejectSharedElements");
                super.onRejectSharedElements(rejectedSharedElements);
            }

            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                Log.i(TAG_EXIT,"onMapSharedElements");
                super.onMapSharedElements(names, sharedElements);
            }

            @Override
            public Parcelable onCaptureSharedElementSnapshot(View sharedElement, Matrix viewToGlobalMatrix, RectF screenBounds) {
                Log.i(TAG_EXIT,"onCaptureSharedElementSnapshot");
                return super.onCaptureSharedElementSnapshot(sharedElement, viewToGlobalMatrix, screenBounds);
            }

            @Override
            public View onCreateSnapshotView(Context context, Parcelable snapshot) {
                Log.i(TAG_EXIT,"onCreateSnapshotView");
                return super.onCreateSnapshotView(context, snapshot);
            }

            @Override
            public void onSharedElementsArrived(List<String> sharedElementNames, List<View> sharedElements, OnSharedElementsReadyListener listener) {
                Log.i(TAG_EXIT,"onSharedElementsArrived");
                super.onSharedElementsArrived(sharedElementNames, sharedElements, listener);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG_ENTER,"onResume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG_ENTER,"onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG_ENTER,"onRestart");
    }

    @Override
    protected void onPause() {

        super.onPause();
        Log.i(TAG_ENTER,"onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG_ENTER,"onStop");
    }
}
