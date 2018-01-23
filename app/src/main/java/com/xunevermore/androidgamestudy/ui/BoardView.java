package com.xunevermore.androidgamestudy.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2018/1/22 0022.
 */

public class BoardView extends View {

    private final Paint paint;
    private float x1, y1;
    private Path path;

    private List<Path> pathList = new ArrayList<>();

    public BoardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path = new Path();
                pathList.add(path);
                path.moveTo(x, y);
                x1 = x;
                y1 = y;
                break;
            case MotionEvent.ACTION_MOVE:
                path.quadTo(x1, y1, x, y);
                invalidate();

                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        x1 = x;
        y1 = y;

        return true;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        for (int i = 0; i < pathList.size(); i++) {
            canvas.drawPath(pathList.get(i), paint);
        }
    }

    /**
     * 清除上一条路径
     * @return
     */
    public boolean clearLastPath(){
        if(pathList.size()>0){
            pathList.remove(pathList.size()-1);
            invalidate();
            return true;
        }

        return false;
    }
}
