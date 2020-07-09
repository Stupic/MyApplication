package com.example.arin.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by arin on 2016-08-16.
 */
public class Comment_MakeList_Adapter extends BaseAdapter implements View.OnClickListener {


    private Context mContext;
    private CustomMakeList mCustomMakeList;
    private Activity mActivity;
    private ArrayList<Comment_Item> arr;
    private int pos;
    private CustomMakeList Makelist;
    private String MyPhoneNum= "010-7565-1743";


    //	private Typeface myFont;

    public Comment_MakeList_Adapter(Context mContext, CustomMakeList mCustomMakeList, CustomMakeList l, ArrayList<Comment_Item> arr_item) {
        this.mContext = mContext;
        this.mCustomMakeList = mCustomMakeList;
        this.arr = arr_item;
        this.Makelist = l;

    }

    @Override
    public int getCount() {
        return arr.size();
    }
    @Override
    public Object getItem(int position) {
        return arr.get(position);
    }
    public long getItemId(int position){ return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            int res = 0;
            res = com.example.arin.myapplication.R.layout.comment_item;
            LayoutInflater mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(res, parent, false);
        }
        pos = position;
        if(arr.size() != 0){
            TextView ci_nickname_text = (TextView)convertView.findViewById(R.id.ci_nickname_text);
            ci_nickname_text.setText(arr.get(pos).getNickname());
            TextView ci_content_text = (TextView)convertView.findViewById(R.id.ci_content_text);
            ci_content_text.setText(arr.get(pos).getphone());
            Button ci_delete_btn = (Button)convertView.findViewById(R.id.ci_delete_btn);
            this.MyPhoneNum= arr.get(pos).getphone();
            ci_delete_btn.setOnClickListener(this);
            ci_delete_btn.setTag(pos+"");

        }
        return convertView;
    }
    public void onClick(View v){

        final int tag = Integer.parseInt(v.getTag().toString());
        switch(v.getId()){
            case R.id.ci_delete_btn:
                AlertDialog.Builder alertDlg = new AlertDialog.Builder(mCustomMakeList.getContext());
                alertDlg.setPositiveButton("삭제", new DialogInterface.OnClickListener(){
                @Override
                public void onClick( DialogInterface dialog, int which ) {
                    mCustomMakeList.deleteArr(tag);
                    Toast.makeText(mContext, "회원이 삭제되었습니다.", 0).show();
                }});
                alertDlg.setNegativeButton("취소", null);
                alertDlg.setTitle("회원 삭제");
                alertDlg.setMessage("정말 삭제 하시겠습니까?");
                alertDlg.show();
                break;
        }
    }

}
