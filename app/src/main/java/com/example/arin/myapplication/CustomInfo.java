package com.example.arin.myapplication;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class CustomInfo extends Dialog {

    private TextView mTitleView;
    TextView Name1_text;
    TextView HandPhone1_text;
    TextView HomePhone1_text;
    private Button mOkButton;


    private String mHandP;
    private String mTitle;
    private String mName;
    private String mHomeP;
    private View.OnClickListener mOkClickListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 다이얼로그 외부 화면 흐리게 표현
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.activity_info);

        mTitleView = (TextView) findViewById(R.id.Infotitle);
        Name1_text =(TextView)findViewById(R.id.Name1_text);
        HandPhone1_text =(TextView)findViewById(R.id.HandP1_text);
        HomePhone1_text = (TextView) findViewById(R.id.HomeP1_text);
        mOkButton = (Button) findViewById(R.id.OK);


        // 제목과 내용을 생성자에서 셋팅한다.
        mTitleView.setText(mTitle);
        HandPhone1_text.setText(mHandP);
        Name1_text.setText(mName);
        HomePhone1_text.setText(mHomeP);


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

    public CustomInfo(Context context, String title,
                      String Name, String HandPhone, String HomePhone, View.OnClickListener OkListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);

        this.mName = Name;
        this.mTitle = title;
        this.mHandP = HandPhone;
        this.mHomeP = HomePhone;
        this.mOkClickListener = OkListener;

    }



}