package com.example.arin.myapplication;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CustomJoin extends Dialog {

    private TextView mTitleView;
    TextView Name;
    private EditText Name_text;

    private TextView Passwd;
    private EditText Passwd_text;
    private TextView Passwd1;
    private EditText Passwd1_text;
    private TextView HandPhone;
    private TextView HandPhone_text;
    private TextView HomePhone;
    private EditText HomePhone_1;
    private EditText HomePhone_2;
    private EditText HomePhone_3;

    private Button mOkButton;
    private String mHandP;
    private String mTitle;
    private View.OnClickListener mOkClickListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 다이얼로그 외부 화면 흐리게 표현
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.activity_join);

        mTitleView = (TextView) findViewById(R.id.Infotitle);
        Name = (TextView) findViewById(R.id.Name);
        Name_text =(EditText)findViewById(R.id.Name_text);
        Passwd = (TextView) findViewById(R.id.Passwd);
        Passwd_text =(EditText)findViewById(R.id.Passwd_text);
        Passwd1 = (TextView) findViewById(R.id.Passwd1);
        Passwd1_text =(EditText)findViewById(R.id.Passwd1_text);
        HandPhone = (TextView) findViewById(R.id.HandP);
        HandPhone_text =(TextView)findViewById(R.id.HandP_text);
        HomePhone = (TextView) findViewById(R.id.HomeP);
        mOkButton = (Button) findViewById(R.id.OK);



        // 제목과 내용을 생성자에서 셋팅한다.
        mTitleView.setText(mTitle);
        HandPhone_text.setText(mHandP);


        // 클릭 이벤트 셋팅
//        if (mLeftClickListener != null && mRightClickListener != null) {
//            mLeftButton.setOnClickListener(mLeftClickListener);
//            mRightButton.setOnClickListener(mRightClickListener);
//        } else if (mLeftClickListener != null && mRightClickListener == null) {
//            mLeftButton.setOnClickListener(mLeftClickListener);
//        } else {
//
//        }
        mOkButton.setOnClickListener(mOkClickListener);

    }


    //클릭버튼이 3가지 일때 생성자함수로 클릭 이벤트르 받는다
    public CustomJoin(Context context, String title,
                      String HandPhone, View.OnClickListener OkListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);

        this.mTitle = title;
        this.mHandP = HandPhone;
        this.mOkClickListener = OkListener;

    }




}