package com.xunevermore.androidgamestudy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.xunevermore.androidgamestudy.ui.CircleTextView;

public class CircleTextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_text);
        final CircleTextView text = (CircleTextView) findViewById(R.id.text);
        text.setText("徐影魔真滴帅啊！！！");

        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(text.isRunning()){
                    text.stop();
                }else {
                    text.start();
                }
            }
        });
    }
}
