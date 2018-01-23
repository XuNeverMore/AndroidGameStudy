package com.xunevermore.androidgamestudy;

import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.xunevermore.androidgamestudy.ui.WuziView;

public class WuziqiActivity extends AppCompatActivity implements WuziView.ChessEventListener {

    private int fellId;
    private SoundPool soundPool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wuziqi);
        //屏幕常亮
        getWindow().getDecorView().setKeepScreenOn(true);

        WuziView wuzi = (WuziView) findViewById(R.id.wuzi);
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

    }
}
