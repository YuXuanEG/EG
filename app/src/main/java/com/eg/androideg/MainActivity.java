package com.eg.androideg;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.eg.androideg.demo.PictureShowAnimationActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showCustomImageView(View view) {
        Intent intent = new Intent(this, PictureShowAnimationActivity.class);
        startActivity(intent);
    }
}
