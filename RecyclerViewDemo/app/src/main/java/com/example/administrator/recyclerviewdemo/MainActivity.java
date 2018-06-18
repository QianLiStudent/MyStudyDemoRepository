package com.example.administrator.recyclerviewdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SimpleAdapter simpleAdapter;
    private List<String> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setData();

        init();

        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
    }

    public void init(){
        recyclerView = (RecyclerView)findViewById(R.id.rv_activity_main);

        simpleAdapter = new SimpleAdapter(this,datas);
        recyclerView.setAdapter(simpleAdapter);

        simpleAdapter.setOnItemClickListener(new SimpleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Toast.makeText(MainActivity.this,"这里是点击：" + position +  "Item",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View v, int position) {
                Toast.makeText(MainActivity.this,"这里是长按：" + position +  "Item",Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void setData(){

        datas = new ArrayList<>();
        for(int i = 'A';i<='z';i++){
            this.datas.add((char)i + "");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.actionbar_menu,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.item1:        //添加
                simpleAdapter.add(datas.size());
                break;
            case R.id.item2:                 //删除
                simpleAdapter.delete(datas.size()-1);
                break;
            case R.id.item3:                //ListView
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                break;
            case R.id.item4:                //GridView
                recyclerView.setLayoutManager(new GridLayoutManager(this,3));
                break;
            case R.id.item5:                //StraggeredGridView
                Intent intent = new Intent(this,StaggeredActivity.class);
                startActivity(intent);
                break;
            case R.id.item6:                //HorListView
                recyclerView.setLayoutManager(new LinearLayoutManager(this,
                        LinearLayoutManager.HORIZONTAL,false));
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
