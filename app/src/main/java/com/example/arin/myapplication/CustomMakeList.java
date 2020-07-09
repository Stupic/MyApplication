package com.example.arin.myapplication;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class CustomMakeList extends Dialog {



    String myJSON;
    String MyPhoneNum;

    Comment_MakeList_Adapter ca;
    ArrayList<Comment_Item> c_arr = new ArrayList<Comment_Item>();
    ListView comment_list2;



    private View.OnClickListener mAddClickListener;
    private View.OnClickListener mAddOkClickListener;


    private Button mAddButton;
    private Button mAddOkbutton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 다이얼로그 외부 화면 흐리게 표현
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.activity_addmember);

        mAddButton = (Button) findViewById(R.id.Add);
        mAddButton.setOnClickListener(mAddClickListener);
        mAddOkbutton = (Button) findViewById(R.id.AddOk);
        mAddOkbutton.setOnClickListener(mAddOkClickListener);


        init();

    }

    private void setList(){
        ca = new Comment_MakeList_Adapter(getContext(), CustomMakeList.this, CustomMakeList.this, c_arr);
        comment_list2.setAdapter(ca);
        comment_list2.setSelection(c_arr.size()-1);
        comment_list2.setDivider(null);
        comment_list2.setSelectionFromTop(0, 0);
    }


    public void init(){

        comment_list2 = (ListView)findViewById(R.id.jrv_comment_list2);

        setList();
    }




    //클릭버튼이 3가지 일때 생성자함수로 클릭 이벤트르 받는다
    public CustomMakeList(Context context, View.OnClickListener AddListener,View.OnClickListener AddOkListener,ArrayList<Comment_Item> c_arr) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);

        this.mAddClickListener = AddListener;
        this.mAddOkClickListener = AddOkListener;
        this.c_arr =c_arr;
    }





    public void resetAdapter(){
        ca.notifyDataSetChanged();
        //Adapter의 데이터 값이 변화가 있을 때 사용하는 함수.

    }
    public void deleteArr(int p){
        //Adapter에서 이 함수를 지울 떄 호출합니다. 지우고자 하는 댓글의 id 값을 넘겨주면 c_arr 의 데이터를 삭제!
        c_arr.remove(p);
        //마찬가지로 변화가 있었기 때문에 Adapter 초기화(?)
        ca.notifyDataSetChanged();
    }


}