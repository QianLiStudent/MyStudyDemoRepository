package com.example.administrator.recyclerviewdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class UserDefinedAdapter extends SimpleAdapter{

    private Context context;
    private List<String> datas;
    private List<Integer> list_height;

    public UserDefinedAdapter(Context context,List<String> datas){
        super(context,datas);
        this.context = context;
        this.datas = datas;

        this.list_height = new ArrayList<>();

        for(int i = 0;i<datas.size();i++){
            this.list_height.add((int)(Math.random()*300) + 100);
        }
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        layoutParams.height = this.list_height.get(position);
        holder.itemView.setLayoutParams(layoutParams);
        holder.tv.setText(this.datas.get(position));

        setUpClick(holder);
    }

}
