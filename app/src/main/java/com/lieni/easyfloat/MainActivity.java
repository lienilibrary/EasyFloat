package com.lieni.easyfloat;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.SharedElementCallback;

import android.content.Context;
import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.lieni.library.easyfloat.EasyFloat;
import com.lieni.library.easyfloat.EasyFloatView;

import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    String TAG_EXIT="sssss exit activity A";
    String TAG_ENTER="sssss enter activity A";
    private TextView navToNextWithAnim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.navToNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,TestActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.addView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add();
            }
        });
        findViewById(R.id.hideView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EasyFloat.removeImmediately("test1");
            }
        });
        findViewById(R.id.showDialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view=getLayoutInflater().inflate(R.layout.view_test,(FrameLayout)getWindow().getDecorView(),false);
                AlertDialog dialog=new  AlertDialog.Builder(v.getContext()).setView(view).create();
                dialog.show();
            }
        });
//        navToNextWithAnim=findViewById(R.id.navToNextWithAnim);
//        navToNextWithAnim.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this, TestActivity.class),
//                    ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, navToNextWithAnim, "shareElement").toBundle());
//            }
//        });
//        initTranCallback();
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

    private void add(){
        EasyFloat.addImmediately("test", new EasyFloatView() {
            @Override
            public View onCreateView(ViewGroup rootView) {
                View view= LayoutInflater.from(rootView.getContext()).inflate(R.layout.view_test,rootView,false);

                return view;
            }

            @Override
            public void onConfig() {
                super.onConfig();
                setInitPosition(200,200);
            }
        },this);

        EasyFloat.addImmediately("test1", new EasyFloatView() {
            @Override
            public View onCreateView(ViewGroup rootView) {
                View view= LayoutInflater.from(rootView.getContext()).inflate(R.layout.view_test,rootView,false);
                return view;
            }

            @Override
            public void onConfig() {
                super.onConfig();
                setInitPosition(400,400);
                addInvalidActivityName(TestActivity.class.getName());
            }
        },this);

//        EasyFloat.setOnlyValidActivityShow(true);
//        EasyFloat.addValidActivityName("Test2Activity");
//        EasyFloat.addInvalidActivityName("Test2Activity");
//        EasyFloat.addInvalidActivity(MainActivity.class);
//
//        EasyFloat.setView(getWindow(), R.layout.view_test,200,200,false);
//        View view=EasyFloat.getView();
//        if(view!=null){
//            view.findViewById(R.id.tvText).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Point point= ViewUtils.getViewPoint(EasyFloat.getView());
//                    Toast.makeText(getApplicationContext(),"x:"+point.x+" "+"y:"+point.y,Toast.LENGTH_SHORT).show();
//                }
//            });
//            view.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
//                @Override
//                public void onViewAttachedToWindow(View v) {
//                    Log.i("sssssssss","float onViewAttachedToWindow");
//                }
//
//                @Override
//                public void onViewDetachedFromWindow(View v) {
//                    Log.i("sssssssss","float onViewDetachedFromWindow");
//                }
//            });
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG_EXIT,"onResume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG_EXIT,"onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG_ENTER,"onRestart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG_EXIT,"onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG_EXIT,"onStop");
    }
}
