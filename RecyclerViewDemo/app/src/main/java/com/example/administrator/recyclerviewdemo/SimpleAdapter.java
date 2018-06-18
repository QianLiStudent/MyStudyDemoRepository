package com.example.administrator.recyclerviewdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class SimpleAdapter extends RecyclerView.Adapter<MyViewHolder>{

    private Context context;
    private List<String> datas;

    public interface OnItemClickListener{
        void onItemClick(View v,int position);
        void onItemLongClick(View v,int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public SimpleAdapter(Context context,List<String> datas){
        this.context = context;
        this.datas = datas;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(this.context).inflate(R.layout.adapter_item,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder,final int position) {

        holder.tv.setText(this.datas.get(position));

        setUpClick(holder);

    }

    public void setUpClick(final MyViewHolder holder){
        if(this.onItemClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){

                    SimpleAdapter.this.onItemClickListener.onItemClick(holder.itemView,holder.getLayoutPosition());
                }
            });
        }

        if(this.onItemClickListener != null){
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    SimpleAdapter.this.onItemClickListener.onItemLongClick(holder.itemView,holder.getLayoutPosition());
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return this.datas.size();
    }

    public void add(int position){
        datas.add(position,"Inster here");

        notifyItemInserted(position);
    }

    public void delete(int position){
        datas.remove(position);

        notifyItemRemoved(position);
    }

}

class MyViewHolder extends RecyclerView.ViewHolder {

    TextView tv;

    public MyViewHolder(View itemView) {
        super(itemView);

        tv = (TextView)itemView.findViewById(R.id.tv);
    }
}

