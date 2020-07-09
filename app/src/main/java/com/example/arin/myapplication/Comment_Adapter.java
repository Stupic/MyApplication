package com.example.arin.myapplication;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


public class Comment_Adapter extends BaseAdapter implements OnClickListener{
private CustomInfo mCustomInfo;
private Context mContext;
private Activity mActivity;
private ArrayList<Comment_Item> arr;
private int pos;
private Member ma;
private String MyPhoneNum= "010-7565-1743";

//	private Typeface myFont;
public Comment_Adapter(Context mContext, Member mActivity, Member mc, ArrayList<Comment_Item> arr_item) {
    this.mContext = mContext;
    this.mActivity = mActivity;
    this.arr = arr_item;
    this.ma = mc;
//		myFont = Typeface.createFromAsset(mContext.getAssets(), "BareunDotum.ttf");
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
    private View.OnClickListener OkListener = new View.OnClickListener() {
        public void onClick(View v) {           //회원 가입창 Ok 버튼 누를때
            mCustomInfo.dismiss();
        }
    };

    public void onClick(View v){

        int tag = Integer.parseInt(v.getTag().toString());
        switch(v.getId()){
            case R.id.ci_delete_btn:
                 mCustomInfo = new CustomInfo(mActivity,
                    "개인 정보",
                    arr.get(tag).getNickname(), // 제목
                    arr.get(tag).getphone(),// 내용
                    "없음",
                    OkListener);  //Ok 버튼
                mCustomInfo.show();

        break;
    }
}


}