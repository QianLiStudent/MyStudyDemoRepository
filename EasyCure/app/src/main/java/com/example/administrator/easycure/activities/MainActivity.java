package com.example.administrator.easycure.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.administrator.easycure.R;
import com.example.administrator.easycure.utils.BaseActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    private Button activity_main_btn_call;
    private RelativeLayout activity_main_shop,activity_main_rl_health_information,activity_main_rl_more;

    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    public void init(){
        //致电客服按钮
        activity_main_btn_call = (Button)findViewById(R.id.activity_main_btn_call);

        /**
         * activity_main_shop：商店
         * activity_main_btn_doctor：健康资讯
         * activity_main_btn_more：更多
         */
        activity_main_shop = (RelativeLayout)findViewById(R.id.activity_main_shop);
        activity_main_rl_health_information = (RelativeLayout)findViewById(R.id.activity_main_rl_health_information);
        activity_main_rl_more = (RelativeLayout)findViewById(R.id.activity_main_rl_more);

        activity_main_btn_call.setOnClickListener(this);
        activity_main_shop.setOnClickListener(this);
        activity_main_rl_health_information.setOnClickListener(this);
        activity_main_rl_more.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.activity_main_btn_call:
                //调用电话界面让用户打电话致电客服

                break;
            case R.id.activity_main_shop:
                //切换到商品界面
                intent = new Intent(this,DrawerActivity.class);
                intent.putExtra("itemId",1);
                startActivity(intent);
                break;
            case R.id.activity_main_rl_health_information:
                //切换到健康资讯模块
                intent = new Intent(this,DrawerActivity.class);
                intent.putExtra("itemId",2);
                startActivity(intent);
                break;
            case R.id.activity_main_rl_more:
                //切换到更多功能界面
                intent = new Intent(this,FunctionChooseActivity.class);
                startActivity(intent);
                break;
        }
    }
}
