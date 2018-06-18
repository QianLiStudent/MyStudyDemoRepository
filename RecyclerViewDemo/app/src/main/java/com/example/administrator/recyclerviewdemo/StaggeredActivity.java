package com.example.administrator.recyclerviewdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class StaggeredActivity extends Activity {

    private RecyclerView recyclerView;
    private List<String> list;
    private UserDefinedAdapter userDefinedAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setData();

        init();

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));

        userDefinedAdapter.setOnItemClickListener(new SimpleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

            }

            @Override
            public void onItemLongClick(View v, int position) {
                userDefinedAdapter.delete(position);
            }
        });
    }

    public void init(){
        userDefinedAdapter = new UserDefinedAdapter(this,list);

        recyclerView = findViewById(R.id.rv_activity_main);

        recyclerView.setAdapter(userDefinedAdapter);


    }

    public void setData(){
        this.list = new ArrayList<>();

        for(int i = 'A';i<='z';i++){
            this.list.add((char)i + "");
        }
    }
}
