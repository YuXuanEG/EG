package com.eg.androideg.widgets;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.eg.androideg.R;

/**
 * Created by EdwardGu on 2017/4/16.
 */

public class PictureShowAnimation extends View implements Runnable {

    private Bitmap mBitmap;
    private Rect mShowRect;
    private Rect mSrcRect;
    private int mIndex;//显示的位置
    private Paint mShapePaint;
    private boolean running;
    // TODO: 加自定义属性
    private int mColumn, mRow;//图片行列
    private float mBlankTop, mBlankBottom, mBlankLeft, mBlankRight;//上下左右的空白距离

    public PictureShowAnimation(Context context) {
        this(context, null);
    }

    public PictureShowAnimation(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public PictureShowAnimation(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
//        mBlankTop = 0;
//        mBlankBottom = 0;
//        mBlankLeft = 0;
//        mBlankRight = 0;
//        mColumn = 0;
//        mRow = 0;
        if (attrs != null) {
            // 有属性
            // 1. 获取属性集
            // 2. 从属性集获取属性
            // 从布局属性中,找出 NotePad 自定义的属性集合
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.PictureShowAnimation);
            // 从当前属性集中,获取指定的属性
            mRow = array.getInt(R.styleable.PictureShowAnimation_row, 1);
            mColumn = array.getInt(R.styleable.PictureShowAnimation_column, 1);
            mBlankTop = array.getDimension(R.styleable.PictureShowAnimation_blankTop, 0);
            mBlankBottom = array.getDimension(R.styleable.PictureShowAnimation_blankBottom, 0);
            ;
            mBlankLeft = array.getDimension(R.styleable.PictureShowAnimation_blankLeft, 0);
            ;
            mBlankRight = array.getDimension(R.styleable.PictureShowAnimation_blankRight, 0);
            ;
            // 3. 回收属性集合
            array.recycle();
        }

        mBitmap =
                BitmapFactory.decodeResource(
                        context.getResources(),
                        R.mipmap.run
                );
        mShowRect = new Rect();
        mSrcRect = new Rect();
        mShapePaint = new Paint();
        //shader着色器
        BitmapShader shader =
                new BitmapShader(
                        mBitmap,
                        Shader.TileMode.CLAMP,//tile平铺，clamp裁剪
                        Shader.TileMode.CLAMP
                );
        mShapePaint.setShader(shader);
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mBitmap != null) {
            float bw = mBitmap.getWidth() - mBlankRight;//最右侧多出了一段80的空白，要去掉
            float bh = mBitmap.getHeight() - mBlankBottom;//最下边多出一段空白
            int numOfRow = mRow;//7
            int numOfCol = mColumn;//12

            float perW = bw / numOfCol;
            float perH = bh / numOfRow;

            int column = mIndex % mColumn;
            int row = mIndex / mColumn;

            mSrcRect.left = (int) (perW * column + mBlankLeft);//左边有一段空白
            mSrcRect.top = (int) (perH * row + mBlankTop);//上边有一段空白
            mSrcRect.right = (int) (mSrcRect.left + perW);
            mSrcRect.bottom = (int) (mSrcRect.top + perH);

            mShowRect.left = 0;
            mShowRect.top = 0;
            mShowRect.right = (int) (mShowRect.left + perW);
            mShowRect.bottom = (int) (mShowRect.top + perH);

            canvas.drawBitmap(mBitmap, mSrcRect, mShowRect, null);
        }
    }

    public void showNext() {
        mIndex++;
        if (mIndex >= 64) {
            mIndex = 0;
        }
        //invalidate();//必须在主线程中执行
        // postInvalidate 能够在子线程, 发起 Handler 消息; 更新UI
        postInvalidate();
    }

    @Override
    public void run() {
        running = true;
        try {
            while (running) {
                this.showNext();
                Thread.sleep(50);
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        running = false;
//        Toast.makeText(getContext(),"onDetachedFromWindow",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        switch (visibility) {
            case VISIBLE:
//                Toast.makeText(getContext(),"onWindowVisibilityChanged: VISIBLE",Toast.LENGTH_SHORT).show();
                break;
            case INVISIBLE:
                running = false;
//                Toast.makeText(getContext(),"onWindowVisibilityChanged: INVISIBLE",Toast.LENGTH_SHORT).show();
                break;
            case GONE:
                running = false;
//                Toast.makeText(getContext(),"onWindowVisibilityChanged: GONE",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
