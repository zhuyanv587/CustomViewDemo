package com.example.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

public class SimpleViewV3 extends View {
    //画笔
    private Paint paint;
    //图片drawable
    private Drawable drawable;
    //View宽度
    private int width;
    //View高度
    private int height;
    private Bitmap bitmap;

    public SimpleViewV3(Context context) {
        this(context,null);
    }

    public SimpleViewV3(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SimpleViewV3(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    private void initAttrs(AttributeSet attrs) {
        if (attrs != null){
            TypedArray array = null;
            try {
                array = getContext().obtainStyledAttributes(attrs, R.styleable.SimpleView);
                drawable = array.getDrawable(R.styleable.SimpleView_src);
                measureDrawable();
            }finally{
                if (array != null){
                    array.recycle();
                }
            }
        }
    }

    private void measureDrawable() {
        if (drawable == null){
            throw new RuntimeException("drawable不能为空");
        }
        width = drawable.getIntrinsicWidth();
        height = drawable.getIntrinsicHeight();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(measureWidth(widthMode,width),measureHeight(heightMode,height));
    }

    private int measureWidth(int widthMode,int width){
        switch (widthMode){
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.AT_MOST:
                break;
            case MeasureSpec.EXACTLY:
                this.width = width;
                break;
        }
        return this.width;
    }

    private int measureHeight(int heightMode,int height){
        switch (heightMode){
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.AT_MOST:
                break;
            case MeasureSpec.EXACTLY:
                this.height = height;
                break;
        }
        return this.height;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (bitmap == null){
            bitmap = Bitmap.createScaledBitmap(ImageUtils.drawableToBitmap(drawable),
                    getMeasuredWidth(),getMeasuredHeight(),true);
        }
        //绘制图片
        canvas.drawBitmap(bitmap,getLeft(),getTop(),paint);
        //保存画布状态
        canvas.save();
        //旋转90度
        canvas.rotate(90);
        paint.setColor(Color.BLACK);
        paint.setTextSize(100);
        //绘制文本
        canvas.drawText("动漫人物",getLeft()+100,getTop()-50,paint);
        //恢复原来的状态
        canvas.restore();
    }
}
