package com.lieni.easyfloat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.lieni.library.easyfloat.EasyFloat;
import com.lieni.library.easyfloat.utils.ViewUtils;

public class MainActivity extends AppCompatActivity {
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.mainTest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,TestActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.mainTest1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add();
            }
        });
        findViewById(R.id.getPosition).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Point point= ViewUtils.getViewPoint(EasyFloat.getView());
                Log.i("ssssss","x:"+point.x+" "+"y:"+point.y);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
    private void add(){
        if(view==null) {
            view = getLayoutInflater().inflate(R.layout.view_test, null);
        }
        EasyFloat.setView(getWindow(), view,200,200);

    }
}
