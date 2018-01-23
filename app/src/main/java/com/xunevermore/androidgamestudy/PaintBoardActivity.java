package com.xunevermore.androidgamestudy;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.xunevermore.androidgamestudy.ui.BoardView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class PaintBoardActivity extends AppCompatActivity {

    private BoardView boardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint_board);
        boardView = (BoardView) findViewById(R.id.board_view);


        findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                capture();

            }
        });

    }

    private void capture() {
        boardView.setDrawingCacheEnabled(true);
        boardView.buildDrawingCache();
        Bitmap bitmap = boardView.getDrawingCache();


//        String dir = Environment.getExternalStorageDirectory().getAbsolutePath()
//                + File.separator
//                + Environment.DIRECTORY_DCIM
//                + File.separator
//                + "徐影魔"
//                + File.separator;
        String dir = getExternalCacheDir().getAbsolutePath();
        File file1 = new File(dir);
        if (!file1.exists()) {
            file1.mkdirs();
        }

        File file = new File(dir, "img" + System.currentTimeMillis() + ".jpg");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, os);
            Toast.makeText(PaintBoardActivity.this, "保存成功", Toast.LENGTH_SHORT).show();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.flush();
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        boardView.setDrawingCacheEnabled(false);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            capture();
        }
    }

    private static final String TAG = "PaintBoardActivity";

    @Override
    public void onBackPressed() {

        if (boardView.clearLastPath()) {

            Log.i(TAG, "onBackPressed: success");

        } else {
            super.onBackPressed();
        }
    }
}
