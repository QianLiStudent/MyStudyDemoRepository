package com.example.administrator.easycure.FragmentSet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.easycure.R;
import com.example.administrator.easycure.activities.DrawerActivity;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2018/10/25 0025.
 */

public class FragmentShop extends Fragment implements TagFlowLayout.OnSelectListener,View.OnClickListener {


    public final int maxSelected = 5;

    private TagFlowLayout fragment_shop_tfl;
    private ImageView fragment_shop_iv1;
    //    private List mList = new ArrayList();
    private List mList = Arrays.asList("China","Canada","America","Afraic","Japan","adfadf","adfa","5451531","adfadfads");
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_shop,container,false);

        init(view);

        initData();
        
        return view;
    }

    public void init(View v){
        fragment_shop_tfl = (TagFlowLayout) v.findViewById(R.id.fragment_shop_tfl);
        fragment_shop_iv1 = (ImageView)v.findViewById(R.id.fragment_shop_iv1);

        fragment_shop_iv1.setOnClickListener(this);
    }

    public void initData(){
        readDB();

        fragment_shop_tfl.setAdapter(new TagAdapter<String>(mList)
        {
            @Override
            public View getView(FlowLayout parent, int position, String s)
            {
                TextView tv = (TextView) View.inflate(getContext(),R.layout.tag, null);
                tv.setText(s);
                return tv;
            }
        });

        fragment_shop_tfl.setOnSelectListener(this);
    }

    public void onClick(View v){
        switch(v.getId()){
            case R.id.fragment_shop_iv1:
                DrawerActivity.openDrawer();
                break;
        }
    }

    public void readDB(){

    }

    @Override
    public void onSelected(Set<Integer> selectPosSet) {
        if(fragment_shop_tfl.getSelectedList().size() == maxSelected){
            Toast.makeText(getContext(),"已达最大选中数5",Toast.LENGTH_SHORT).show();
        }

    }
}
