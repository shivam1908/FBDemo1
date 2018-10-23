package com.example.main_hall.fbdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class StartActivity1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start1);
    }

    public void go(View v)
    {
        Intent in = new Intent(this,MainActivity.class);
        startActivity(in);
    }

    public void go2(View v)
    {
        Intent in = new Intent(this,StorageDemoActivity.class);
        startActivity(in);
    }

}
