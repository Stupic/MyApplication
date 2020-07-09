package com.example.arin.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class Comment_List_Adapter extends BaseAdapter{
    Context context;
    private ArrayList<Comment_Item> arr;
    ViewHolder viewholder;

    class ViewHolder{
        //여기로 이동
        TextView nickname_textView;
        TextView content_textView;
    }


    public Comment_List_Adapter(Context context, ArrayList<Comment_Item> list_itemArrayList) {
        this.context = context;
        this.arr = list_itemArrayList;
    }


    @Override
    public int getCount() {
         return this.arr.size();
}
    @Override
    public Object getItem(int position) {
            return this.arr.get(position);
    }
    public long getItemId(int position){ return position;}


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.comment_list_item,null);
            viewholder = new ViewHolder();
            viewholder.nickname_textView = (TextView)convertView.findViewById(R.id.ci_nickname_text);
            viewholder.content_textView = (TextView)convertView.findViewById(R.id.ci_content_text);
            convertView.setTag(viewholder);
        }else{
            viewholder = (ViewHolder)convertView.getTag();
        }

        viewholder.nickname_textView.setText(arr.get(position).getNickname());
        viewholder.content_textView.setText(arr.get(position).getphone());


        return convertView;
    }

}