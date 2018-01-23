package com.xunevermore.androidgamestudy.ui;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Animatable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.xunevermore.androidgamestudy.R;

/**
 * Created by Administrator on 2018/1/22 0022.
 */

public class CircleTextView extends View implements Animatable{

    private Path path;
    private final Paint paint;
    private String text;
    private  ValueAnimator valueAnimator;


    public CircleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setTextSize(40);
        paint.setStrokeWidth(10);
        paint.setAntiAlias(true);

        valueAnimator = ValueAnimator.ofFloat(180,-180);
        valueAnimator.setDuration(2000);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                degree = (float) animation.getAnimatedValue();
                invalidate();
            }
        });


    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        path = new Path();
        path.addCircle(w/2,h/2,100, Path.Direction.CW);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        canvas.rotate(degree,getMeasuredWidth()/2,getMeasuredHeight()/2);
        paint.setColor(Color.YELLOW);
        canvas.drawPath(path,paint);
        paint.setColor(ContextCompat.getColor(getContext(),R.color.colorPrimary));
        canvas.drawTextOnPath(text,path,0,-20,paint);
        canvas.restore();
    }

    private float degree = 180;


    @Override
    public void start() {
        valueAnimator.start();
    }

    @Override
    public void stop() {

        valueAnimator.cancel();
        degree =180;
        invalidate();
    }

    @Override
    protected void onDetachedFromWindow() {
        if(valueAnimator.isRunning()){
            valueAnimator.cancel();
        }
        super.onDetachedFromWindow();

    }

    @Override
    public boolean isRunning() {
        return valueAnimator.isRunning();
    }
}
