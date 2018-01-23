package com.xunevermore.androidgamestudy.ui;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2018/1/22 0022.
 */

public class LongPressButton extends AppCompatButton {


    //回调间隔
    private long interval = 500;
    private boolean isLastCallback = false;

    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

    public LongPressButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLongClickable(true);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isLastCallback = false;
                post(runnable);
                break;
            case MotionEvent.ACTION_UP:
                isLastCallback = true;
                break;
        }

        return super.onTouchEvent(event);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (onLongPressListener != null) {
                onLongPressListener.onPressed(LongPressButton.this, interval, isLastCallback);
                if (isLastCallback) {
                    removeCallbacks(runnable);
                } else {
                    postDelayed(this, interval);
                }
            }
        }
    };

    private OnLongPressListener onLongPressListener;

    public void setOnLongPressListener(OnLongPressListener onLongPressListener) {
        this.onLongPressListener = onLongPressListener;
    }

    public interface OnLongPressListener {
        /**
         * @param view           长按的view
         * @param inteval        回调间隔
         * @param isLastCallback 是否是最后一次回调
         */
        void onPressed(View view, long inteval, boolean isLastCallback);
    }
}
