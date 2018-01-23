package com.xunevermore.androidgamestudy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.xunevermore.androidgamestudy.ui.SwimTextView;

public class SwimmingTextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swimming_text);

        final SwimTextView text = (SwimTextView) findViewById(R.id.text);
        text.setText("为往圣而继绝学");
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.start();
            }
        });
    }
}
