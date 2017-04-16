package com.eg.androideg.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.eg.androideg.R;
import com.eg.androideg.widgets.PictureShowAnimation;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PictureShowAnimationActivity extends AppCompatActivity {


    @BindView(R.id.picShowAnimation)
    PictureShowAnimation mPicShowAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_show_animation);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item != null) {
            switch (item.getItemId()) {
                case android.R.id.home:
                    finish();
                    return true;
            }

        }
        return false;
    }

    public void clickToVisible(View view) {
        mPicShowAnimation.setVisibility(View.VISIBLE);
    }

    public void clickToInvisible(View view) {
        mPicShowAnimation.setVisibility(View.INVISIBLE);
    }

    public void clickToGone(View view) {
        mPicShowAnimation.setVisibility(View.GONE);
    }

    @OnClick(R.id.picShowAnimation)
    public void onClick() {
        Toast.makeText(this,"onClick",Toast.LENGTH_SHORT).show();
    }
}
