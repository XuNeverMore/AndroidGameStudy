package com.xunevermore.androidgamestudy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xunevermore.androidgamestudy.ui.LongPressButton;

public class LongClickActivity extends AppCompatActivity {


    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_long_click);

        final TextView textView = (TextView) findViewById(R.id.tv_count);
        LongPressButton btn = (LongPressButton) findViewById(R.id.btn_long_click);

        btn.setOnLongPressListener(new LongPressButton.OnLongPressListener() {
            @Override
            public void onPressed(View view, long inteval, boolean isLastCallback) {
                if (isLastCallback) {
                    Toast.makeText(LongClickActivity.this, "结束", Toast.LENGTH_SHORT).show();
                    count = 0;
                } else {
                    count++;
                }
                textView.setText(String.valueOf(count));
            }
        });
    }
}
