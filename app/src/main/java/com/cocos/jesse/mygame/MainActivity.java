package com.cocos.jesse.mygame;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyView myView = findViewById(R.id.my);
        myView.setOnClickListener(v-> {
           Intent intent= new Intent(this,MyAidlService.class);
           startService(intent);
        });
    }
}
