package com.xunevermore.androidgamestudy;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = (ListView) findViewById(R.id.list_view);

        final ArrayAdapter<Page> adapter = new ArrayAdapter<Page>(this,android.R.layout.simple_list_item_1,android.R.id.text1);

        List<Page> list = new ArrayList<>();
        list.add(new Page(PaintBoardActivity.class,"画板"));
        list.add(new Page(LongClickActivity.class,"长按事件"));
        list.add(new Page(CircleTextActivity.class,"环绕文字"));
        list.add(new Page(SwimmingTextActivity.class,"坡上文字"));
//        list.add(new Page(LunarActivity.class,"游戏"));
        list.add(new Page(SoundActivity.class,"声音"));
        list.add(new Page(WuziqiActivity.class,"五子棋"));


        adapter.addAll(list);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Page item = adapter.getItem(position);
                startActivity(new Intent(MainActivity.this,item.getClazz()));
            }
        });



    }

    private class Page{
        private Class clazz;
        private String name;

        public Page(Class clazz, String name) {
            this.clazz = clazz;
            this.name = name;
        }

        public Class getClazz() {
            return clazz;
        }

        public void setClazz(Class clazz) {
            this.clazz = clazz;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
