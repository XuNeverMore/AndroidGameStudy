package com.xunevermore.androidgamestudy.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.xunevermore.androidgamestudy.R;

/**
 * Created by Administrator on 2018/3/12 0012.
 */

public class WalkView extends SurfaceView implements SurfaceHolder.Callback{

    private Bitmap bitmap;
    private Rect srcRt;
    private Rect dstRt;
    private Paint paint;

    public WalkView(Context context) {
        super(context);
        init();
    }

    public WalkView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.greenman);
        srcRt = new Rect();
        dstRt = new Rect();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(Color.WHITE);

        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
    }

    public void greenMan(){
        t=0;
        isRun=false;
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.greenman);
        start(this.orientation);
    }

    public void ironMan(){
        isRun = false;
        t=0;
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ironman);
        start(this.orientation);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    Thread thread = null;

    private boolean isRun = false;
    private int frame =0;
    private int orientation =0;

    private int t = 0;

    public void start(int orientation){
        this.orientation = orientation;
        t=0;
        isRun = true;
        if (thread==null) {
            thread = new Thread(){
                @Override
                public void run() {
                    while (isRun){
                        draw();
                        frame++;
                        t+=10;
                        if(frame==4){
                            frame=0;
                        }
                        draw();
                        try {
                            sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };

            thread.start();
        }

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        isRun=false;
    }


    private void draw(){

        SurfaceHolder holder = getHolder();
        Canvas canvas = holder.lockCanvas();
        canvas.save();
        canvas.drawColor(Color.WHITE);

        int height = bitmap.getHeight();
        int heightFrame = height / 4;
        int width = bitmap.getWidth();
        int widthFrame = width / 4;

        int startX = 0;
        int startY = 0;

        if(orientation==0){//下
            startX=canvas.getWidth()/2-widthFrame/2;
            startY+=t;
            if(startY>=canvas.getHeight()){
                t=0;
            }

        }else if(orientation==3){//上
            startX=canvas.getWidth()/2-widthFrame/2;

            startY=canvas.getHeight();
            startY-=t;
            if(startY<=0){
                t=0;
            }
        }else if(orientation==1){//左
            startY=canvas.getHeight()/2-heightFrame/2;
            startX=canvas.getWidth();

            startX-=t;
            if(startX<=0){
                t=0;
            }

        }else if(orientation==2){
            startY=canvas.getHeight()/2-heightFrame/2;
            startX=0;
            startX+=t;
            if(startX>=canvas.getWidth()){
                t=0;
            }

        }


        srcRt.set(frame*widthFrame,orientation*heightFrame,frame*widthFrame+widthFrame,orientation*heightFrame+heightFrame);


        dstRt.set(startX,startY,startX+widthFrame,startY+heightFrame);
        canvas.drawBitmap(bitmap,srcRt,dstRt,paint);


        canvas.restore();
        holder.unlockCanvasAndPost(canvas);
    }
}
