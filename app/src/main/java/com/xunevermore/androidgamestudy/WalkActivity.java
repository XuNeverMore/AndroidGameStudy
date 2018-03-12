package com.xunevermore.androidgamestudy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.xunevermore.androidgamestudy.ui.WalkView;

public class WalkActivity extends AppCompatActivity {

    private WalkView walk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk);

        walk = (WalkView) findViewById(R.id.walk_view);
    }

    public void walk(View view) {
        switch (view.getId()){
            case R.id.btn_front:
                walk.start(0);
            break;
            case R.id.btn_left:
                walk.start(1);
                break;
            case R.id.btn_right:
                walk.start(2);
                break;
            case R.id.btn_back:
                walk.start(3);
                break;
            case R.id.btn_ironman:
                walk.ironMan();
                break;
            case R.id.btn_greenman:
                walk.greenMan();
                break;
        }
    }
}
