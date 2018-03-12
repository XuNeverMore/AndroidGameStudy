package com.xunevermore.androidgamestudy;

import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.xunevermore.androidgamestudy.ui.WuziView;

public class WuziqiActivity extends AppCompatActivity implements WuziView.ChessEventListener {

    private int fellId;
    private SoundPool soundPool;
    private WuziView wuzi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wuziqi);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //屏幕常亮
        getWindow().getDecorView().setKeepScreenOn(true);

        wuzi = (WuziView) findViewById(R.id.wuzi);
        soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        fellId = soundPool.load(this, R.raw.fellvoice, 1);
        wuzi.setChessEventListener(this);

    }


    @Override
    public void onChessFell(int chessType) {
        soundPool.play(fellId, 1.0f, 1.0f, 1, 0, 1);
    }

    @Override
    public void onVictory(int chessType) {

//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        String msg = chessType==WuziView.CHESS_BLACK?"黑":"白";
//        msg = msg.concat("棋获胜\n再来一局？");
//        builder.setMessage(msg);
//        builder.setCancelable(false);
//        builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                finish();
//            }
//        });
//
//        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//                wuzi.reset();
//            }
//        });
//
//        builder.show();

    }
}
