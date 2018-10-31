package com.example.administrator.easycure.FragmentSet;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.administrator.easycure.R;
import com.example.administrator.easycure.activities.DrawerActivity;
import com.example.administrator.easycure.adapters.FragmentPromotionLvAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/10/20 0020.
 */

//Promotion：文章界面
public class FragmentPromotion extends Fragment implements View.OnClickListener{

    private List<Map<String,Object>> list = new ArrayList<>();
    private Map<String,Object> map;

    private EditText fragment_promotion_et1;
    private ImageView fragment_promotion_iv1,fragment_promotion_iv3;
    private ListView fragment_promotion_lv;

    private FragmentPromotionLvAdapter fragmentPromotionLvAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_promotion,container,false);

        init(view);

        initData();

        fragmentPromotionLvAdapter = new FragmentPromotionLvAdapter(getContext(),list);

        fragment_promotion_lv.setAdapter(fragmentPromotionLvAdapter);

        return view;
    }

    public void init(View view){
        //侧滑菜单图标
        fragment_promotion_iv1 = (ImageView)view.findViewById(R.id.fragment_promotion_iv1);
        //清除输入的内容图标
        fragment_promotion_iv3 = (ImageView)view.findViewById(R.id.fragment_promotion_iv3);
        //EditText输入框
        fragment_promotion_et1 = (EditText)view.findViewById(R.id.fragment_promotion_et1);
        //ListView文章列表
        fragment_promotion_lv = (ListView)view.findViewById(R.id.fragment_promotion_lv);

        //侧栏按钮
        fragment_promotion_iv1.setOnClickListener(this);
        //清除输入框
        fragment_promotion_iv3.setOnClickListener(this);
    }

    public void onClick(View v){
        switch(v.getId()){
            case R.id.fragment_promotion_iv1:
                DrawerActivity.openDrawer();
                break;
            case R.id.fragment_promotion_iv3:
                //清空输入框内容
                fragment_promotion_et1.setText("");
                break;
        }
    }

    public void initData(){
        for(int i = 0;i<9;i++){
            map = new HashMap<>();

            map.put("iv",R.mipmap.lv_item);
            map.put("title","Medicine");
            map.put("message","Vitamin B is more often referred to today as the B vitamins to better relate that it is actually.");

            list.add(map);
        }
    }
}
