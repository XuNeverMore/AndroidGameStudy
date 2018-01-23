package com.xunevermore.androidgamestudy.ui;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.drawable.Animatable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.xunevermore.androidgamestudy.R;

/**
 * Created by Administrator on 2018/1/22 0022.
 */

public class SwimTextView extends View implements Animatable{



    private int padding = 50;
    private Path path;
    private final Paint paint;
    private PathMeasure pm;

    private Path pathSupport = new Path();
    private float fraction = 1;
    private float mLength;

    public SwimTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setTextSize(context.getResources().getDimensionPixelSize(R.dimen.textSize24));
        paint.setStrokeWidth(4);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int measuredHeight = getMeasuredHeight();
        int measuredWidth = getMeasuredWidth();
        int baseHeight = measuredHeight / 4;
        path = new Path();
        path.moveTo(padding,3*baseHeight);
        path.quadTo(measuredWidth/2,baseHeight,measuredWidth-padding,3*baseHeight);

        pm = new PathMeasure(path,false);
        mLength = pm.getLength();


    }

    private String text;

    public String getText() {
        return text;
    }



    public void setText(String text) {
        this.text = text;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        pathSupport.reset();
        paint.setAlpha(255);

        pm.getSegment(0,mLength*fraction,pathSupport,true);
        canvas.drawPath(pathSupport,paint);

        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(Color.GREEN);
        paint.setAlpha((int) (255*fraction));
        canvas.drawTextOnPath(text,path,(getMeasuredWidth()-paint.measureText(text))/2,-20,paint);

    }

    @Override
    public void start() {

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                fraction = (float) animation.getAnimatedValue();
                invalidate();
            }
        });

        valueAnimator.setInterpolator(new AccelerateInterpolator());
        valueAnimator.setDuration(700);
        valueAnimator.start();



    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isRunning() {
        return false;
    }
}
