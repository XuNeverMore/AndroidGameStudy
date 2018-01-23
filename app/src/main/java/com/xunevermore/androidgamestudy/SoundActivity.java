package com.xunevermore.androidgamestudy;

import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.xunevermore.androidgamestudy.util.ToastUtil;

public class SoundActivity extends AppCompatActivity {

    private SoundPool soundPool;
    private int frog;
    private int jiongId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound);
        soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        frog = soundPool.load(this, R.raw.frog, 1);
        jiongId = soundPool.load(this, R.raw.jong, 1);

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_frog:
                soundPool.play(frog, 1.0f, 1.0f, 1, 0, 1);
                break;
            case R.id.btn_she:
                ToastUtil.showImage(this,R.mipmap.star);
                soundPool.play(jiongId, 1.0f, 1.0f, 1, 0, 1);

                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        soundPool.release();
    }
}
