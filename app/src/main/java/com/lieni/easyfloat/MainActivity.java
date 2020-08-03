package com.lieni.easyfloat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.lieni.library.easyfloat.EasyFloat;
import com.lieni.library.easyfloat.utils.ViewUtils;

public class MainActivity extends AppCompatActivity {

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
                hide();
            }
        });
        findViewById(R.id.getPosition).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Point point= ViewUtils.getViewPoint(EasyFloat.getView());
                Toast.makeText(getApplicationContext(),"x:"+point.x+" "+"y:"+point.y,Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
    private void add(){
        EasyFloat.setView(getWindow(), R.layout.view_test,200,200,true,false);
        View view=EasyFloat.getView();
        if(view!=null){
            view.findViewById(R.id.tvText).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Point point= ViewUtils.getViewPoint(EasyFloat.getView());
                    Toast.makeText(getApplicationContext(),"x:"+point.x+" "+"y:"+point.y,Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    private void hide(){
        EasyFloat.hide();
    }


}
