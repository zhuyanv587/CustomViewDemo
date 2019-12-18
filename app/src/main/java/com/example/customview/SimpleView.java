package com.example.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

public class SimpleView extends View {
    //画笔
    private Paint paint;
    //图片drawable
    private Drawable drawable;
    //View宽度
    private int width;
    //View高度
    private int height;

    public SimpleView(Context context) {
        this(context,null);
    }

    public SimpleView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SimpleView(Context context, AttributeSet attrs, int defStyleAttr) {
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
        //测量Drawable对象的宽、高
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (drawable == null){
            return;
        }
        canvas.drawBitmap(ImageUtils.drawableToBitmap(drawable),getLeft(),getTop(),paint);
    }
}
