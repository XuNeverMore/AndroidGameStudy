package com.xunevermore.androidgamestudy.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by Administrator on 2018/1/23 0023.
 */

public class ToastUtil {

    public static void showImage(Context context,int imageRes){
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(imageRes);
        Toast toast = new Toast(context);
        toast.setView(imageView);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }
}
