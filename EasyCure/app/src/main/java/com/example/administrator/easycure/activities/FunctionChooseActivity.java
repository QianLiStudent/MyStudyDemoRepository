package com.example.administrator.easycure.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.easycure.R;
import com.example.administrator.easycure.adapters.FragmentAdviceLvAdapter;
import com.example.administrator.easycure.utils.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Administrator on 2018/10/22 0022.
 */

public class FunctionChooseActivity extends BaseActivity implements View.OnClickListener,AdapterView.OnItemClickListener{

    private ImageView activity_functionchoose_iv;
    private ListView activity_functionchoose_lv;
    private TextView activity_functionchoose_tv;

    private Intent intent;

    private List<Map<String,Object>> list = new ArrayList<>();
    private Map<String,Object> map;

    private FragmentAdviceLvAdapter fragmentAdviceLvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_functionchoose);

        init();

        initData();
    }

    public void init(){
        activity_functionchoose_tv = (TextView)findViewById(R.id.activity_functionchoose_tv);
        activity_functionchoose_iv = (ImageView)findViewById(R.id.activity_functionchoose_iv);
        activity_functionchoose_lv = (ListView)findViewById(R.id.activity_functionchoose_lv);

        activity_functionchoose_tv.setOnClickListener(this);
        activity_functionchoose_iv.setOnClickListener(this);
        activity_functionchoose_lv.setOnItemClickListener(this);

    }

    public void initData(){
        map = new HashMap<>();
        map.put("iv",R.mipmap.fragment_contact_img);
        map.put("tv",getResources().getString(R.string.contact));
        list.add(map);

        map = new HashMap<>();
        map.put("iv",R.mipmap.fragment_shop_img);
        map.put("tv",getResources().getString(R.string.shop));
        list.add(map);

        map = new HashMap<>();
        map.put("iv",R.mipmap.fragment_promotion_search);
        map.put("tv",getResources().getString(R.string.health_knowledge));
        list.add(map);

        map = new HashMap<>();
        map.put("iv",R.mipmap.fragment_advice_lv_item_img4);
        map.put("tv",getResources().getString(R.string.schedule));
        list.add(map);

        map = new HashMap<>();
        map.put("iv",R.mipmap.icon_main_dcotor);
        map.put("tv",getResources().getString(R.string.health_knowledge));
        list.add(map);

        map = new HashMap<>();
        map.put("iv",R.mipmap.fragment_advice_lv_item_img3);
        map.put("tv",getResources().getString(R.string.where));
        list.add(map);

        map = new HashMap<>();
        map.put("iv",R.mipmap.fragment_aboutus_communicate);
        map.put("tv",getResources().getString(R.string.about_us));
        list.add(map);

        //这里只是复用而已，因为item里面的格式是一样的，故直接复用advice的适配器
        fragmentAdviceLvAdapter = new FragmentAdviceLvAdapter(this,list);

        activity_functionchoose_lv.setAdapter(fragmentAdviceLvAdapter);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.activity_functionchoose_tv:
                intent = new Intent(this,PersonalActivity.class);
                startActivity(intent);
                break;
            case R.id.activity_functionchoose_iv:
                //返回上一个界面
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        intent = new Intent(this,DrawerActivity.class);

        switch(position){
            case 0:     //contact：备忘录设置
                passItemId(position);
                break;
            case 1:     //shop：商店
                passItemId(position);
                break;
            case 2:     //promotion：健康资讯（文章展示）
                passItemId(position);
                break;
            case 3:     //schedule：时间计划表
                passItemId(position);
                break;
            case 4:     //advice：关于身体健康的建议
                passItemId(position);
                break;
            case 5:     //where：定位
                passItemId(position);
                break;
            case 6:     //about us：关于我们
                passItemId(position);
                break;
        }
    }

    public void passItemId(int position){
        intent.putExtra("itemId",position);
        startActivity(intent);
    }
}
