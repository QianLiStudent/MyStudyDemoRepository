package com.example.administrator.easycure.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import java.util.Map;

/**
 * Created by Administrator on 2018/10/26 0026.
 */

public class PersonalActivity extends BaseActivity implements View.OnClickListener,AdapterView.OnItemClickListener{

    private TextView activity_personal_tv;
    private ImageView activity_personal_iv;
    private ListView activity_personal_lv;

    private FragmentAdviceLvAdapter fragmentAdviceLvAdapter;

    private List<Map<String,Object>> list = new ArrayList<>();
    private Map<String,Object> map;

    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        init();

        initData();
    }

    public void init(){
        activity_personal_tv = (TextView)findViewById(R.id.activity_personal_tv);
        activity_personal_iv = (ImageView)findViewById(R.id.activity_personal_iv);
        activity_personal_lv = (ListView)findViewById(R.id.activity_personal_lv);

        activity_personal_iv.setOnClickListener(this);
        activity_personal_lv.setOnItemClickListener(this);
    }

    public void initData(){
        //钱包
        map = new HashMap<>();
        map.put("iv",R.mipmap.activity_personal_img1);
        map.put("tv",getResources().getString(R.string.wallet));
        list.add(map);

        //信用
        map = new HashMap<>();
        map.put("iv",R.mipmap.activity_personal_img2);
        map.put("tv",getResources().getString(R.string.credit));
        list.add(map);

        //安全设置
        map = new HashMap<>();
        map.put("iv",R.mipmap.activity_personal_img3);
        map.put("tv",getResources().getString(R.string.security_settings));
        list.add(map);

        //紧急联系人
        map = new HashMap<>();
        map.put("iv",R.mipmap.activity_personal_img4);
        map.put("tv",getResources().getString(R.string.emergency_contact));
        list.add(map);

        //版本信息
        map = new HashMap<>();
        map.put("iv",R.mipmap.activity_personal_img5);
        map.put("tv",getResources().getString(R.string.version_information));
        list.add(map);

        //关于我们
        map = new HashMap<>();
        map.put("iv",R.mipmap.activity_personal_img6);
        map.put("tv",getResources().getString(R.string.about_us));
        list.add(map);


        //这里只是复用而已，因为item里面的格式是一样的，故直接复用advice的适配器
        fragmentAdviceLvAdapter = new FragmentAdviceLvAdapter(this,list);

        activity_personal_lv.setAdapter(fragmentAdviceLvAdapter);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.activity_personal_iv:
                finish();
                break;
            case R.id.activity_personal_btn:
                new AlertDialog.Builder(this)
                        .setTitle(getResources().getString(R.string.signout_dialog_title))
                        .setMessage(getResources().getString(R.string.signout_dialog_message))
                        .setPositiveButton(getResources().getString(R.string.confirm), new DialogInterface.OnClickListener(){

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                intent = new Intent(PersonalActivity.this,LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }).setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch(position){
            case 0:     //钱包

                break;
            case 1:     //信用

                break;
            case 2:     //安全设置

                break;
            case 3:     //紧急联系人

                break;
            case 4:     //版本信息

                break;
            case 5:     //关于我们

                break;

        }
    }
}
