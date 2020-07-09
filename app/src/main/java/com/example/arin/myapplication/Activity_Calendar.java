package com.example.arin.myapplication;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by arin on 2016-03-20.
 */
public class Activity_Calendar extends Activity {

    Calendar m_Calendar;
    TextView Year_Month;
    TextView Day = null;
    Calendar preMm = Calendar.getInstance();
    private CustomDialog mCustomDialog;
    TextFileManager mTextFileManager= new TextFileManager(this);
    String memoData =null;
    EditText mMemoEdit = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        Year_Month = (TextView) findViewById(R.id.Year_Month);
        m_Calendar = Calendar.getInstance();

        Integer mm = m_Calendar.get(Calendar.MONTH) + 1;
        Integer dd = m_Calendar.get(Calendar.DAY_OF_MONTH) + 1;
        Integer yy = m_Calendar.get(Calendar.YEAR);
        int day = m_Calendar.get(Calendar.DAY_OF_WEEK);


        Year_Month.setText(String.valueOf(yy)+"년 "+String.valueOf(mm)+"월");

        //이전달 마지막날 구하기

        preMm.set(Calendar.DAY_OF_MONTH, 1); //현재 달의 일자를 1일로 변경 후
        preMm.add(Calendar.DAY_OF_YEAR, -1);

        TableLayout C_table = (TableLayout) findViewById(R.id.C_table);

        int count_Row = C_table.getChildCount();

        int Count = preMm.get(Calendar.DATE)-preMm.get(Calendar.DAY_OF_WEEK)+1;


        for (int i = 0; i < count_Row; i++) {

            if (C_table.getChildAt(i) instanceof TableRow) { //로우 들어감

                TableRow C_Row = (TableRow) C_table.getChildAt(i);

                int count_Linear = C_Row.getVirtualChildCount();

                for (int j = 0; j < count_Linear; j++) {
                    LinearLayout C_Linear = (LinearLayout) C_Row.getChildAt(j);
                    ((TextView) C_Linear.getChildAt(0)).setText(String.valueOf(Count));
                    if(preMm.get(Calendar.MONTH)+1!= mm)
                    {
                        ((TextView) C_Linear.getChildAt(0)).setTextColor(Color.GRAY);
                    }
                    if(Count==preMm.get(Calendar.DATE)){
                        preMm.add(Calendar.DAY_OF_YEAR,+1);
                        Count=preMm.get(Calendar.DATE);
                    }else{
                        Count++;
                    }


                }

            }
        }

    }

    public void DayClick(View v) {
        if (Day != null) {
            Day.setBackgroundColor(Color.WHITE);}

        if (Day == (TextView)v)
        {

            memoData = mTextFileManager.load("1");
            String Date= preMm.get(Calendar.YEAR)+"-"+(preMm.get(Calendar.MONTH))+"-"+Day.getText();
            String temp= null;


            if(memoData.indexOf(Date) > -1)
            {
                temp = memoData.substring(memoData.indexOf(Date)+Date.length()+1, memoData.indexOf("<END>"+Date));
                System.out.println(temp);
            }
            else
            {
                temp ="";
            }
            mCustomDialog = new CustomDialog(this,
                    Year_Month.getText()+" "+Day.getText()+"일\n스케줄", // 제목
                    temp , // 내용
                    leftListener, // 왼쪽 버튼 이벤트
                    rightListener,  // 오른쪽 버튼 이벤트
                    DeleteListener);  //삭제 버튼
            mCustomDialog.show();
            mMemoEdit =(EditText) mCustomDialog.findViewById(R.id.txt_content);

        }

        Day = (TextView) v;
        System.out.println("SSS");
        Day.setBackground(this.getResources().getDrawable(R.drawable.xml_border));
    }

    public void Before_Next(View v){   //< > 버튼 이전달 다음달 구하기
        TableLayout C_table = (TableLayout) findViewById(R.id.C_table);
        Integer mm = null;
        switch (v.getId())
        {
            case R.id.Left_btn:
            {
                preMm.add(Calendar.MONTH, -2);
                Integer yy = preMm.get(Calendar.YEAR);
                mm = preMm.get(Calendar.MONTH) + 1;
                Year_Month.setText(String.valueOf(yy) + "년 " + String.valueOf(mm) + "월");

                preMm.set(Calendar.DAY_OF_MONTH, 1); //현재 달의 일자를 1일로 변경 후
                preMm.add(Calendar.DAY_OF_YEAR, -1);

                break;
            }

            case R.id.Right_btn:
            {
                Integer yy = preMm.get(Calendar.YEAR);
                mm = preMm.get(Calendar.MONTH) + 1;
                Year_Month.setText(String.valueOf(yy) + "년 " + String.valueOf(mm) + "월");

                preMm.set(Calendar.DAY_OF_MONTH, 1); //현재 달의 일자를 1일로 변경 후
                preMm.add(Calendar.DAY_OF_YEAR, -1);

                break;
            }

        }

        int count_Row = C_table.getChildCount();

        int Count = preMm.get(Calendar.DATE)-preMm.get(Calendar.DAY_OF_WEEK)+1;

        for (int i = 0; i < count_Row; i++) {

            if (C_table.getChildAt(i) instanceof TableRow) { //로우 들어감

                TableRow C_Row = (TableRow) C_table.getChildAt(i);

                int count_Linear = C_Row.getVirtualChildCount();

                for (int j = 0; j < count_Linear; j++) {
                    LinearLayout C_Linear = (LinearLayout) C_Row.getChildAt(j);
                    ((TextView) C_Linear.getChildAt(0)).setText(String.valueOf(Count));
                    if(preMm.get(Calendar.MONTH)+1  != mm)
                    {
                        ((TextView) C_Linear.getChildAt(0)).setTextColor(Color.GRAY);
                    }
                    else if(preMm.get(Calendar.DAY_OF_WEEK)==1)
                    {
                        ((TextView) C_Linear.getChildAt(0)).setTextColor(Color.RED);
                    }
                    else if(preMm.get(Calendar.DAY_OF_WEEK)==7)
                    {
                        ((TextView) C_Linear.getChildAt(0)).setTextColor(Color.BLUE);
                    }
                    else
                    {
                        ((TextView) C_Linear.getChildAt(0)).setTextColor(Color.BLACK);
                    }

                    if(Count==preMm.get(Calendar.DATE)){
                        preMm.add(Calendar.DAY_OF_YEAR,+1);
                        Count=preMm.get(Calendar.DATE);
                    }else{
                        Count++;
                    }
                }
            }
        }
    }

    public void Delete(View v)
    {

    }


    private View.OnClickListener leftListener = new View.OnClickListener() {
        public void onClick(View v) {

            String Date = preMm.get(Calendar.YEAR) + "-" + (preMm.get(Calendar.MONTH)) + "-" + Day.getText();

            if (memoData.indexOf(Date) > -1) {
                String temp = memoData.substring(memoData.indexOf(Date) + Date.length() + 1, memoData.indexOf("<END>" + Date));
                temp = Date + "\n" + temp + "<END>" + Date + "\n";
                memoData = memoData.replace(temp, "");
                if (mMemoEdit.length() == 0) {
                    mCustomDialog.dismiss();
                } else {
                    char last = mMemoEdit.getText().charAt(mMemoEdit.length() - 1);
                    if (last != '\n') {
                        memoData = memoData + Date + "\n" + mMemoEdit.getText().toString() + "\n" + "<END>" + Date + "\n";
                    } else {
                        memoData = memoData + Date + "\n" + mMemoEdit.getText().toString() + "<END>" + Date + "\n";
                    }
                    mTextFileManager.save(memoData,"1");
                    mCustomDialog.dismiss();
                }
            } else {

                if (mMemoEdit.length() == 0) {
                    mCustomDialog.dismiss();
                } else {
                    char last = mMemoEdit.getText().charAt(mMemoEdit.length() - 1);


                    if (last != '\n') {
                        memoData = memoData + Date + "\n" + mMemoEdit.getText().toString() + "\n" + "<END>" + Date + "\n";
                    } else {
                        memoData = memoData + Date + "\n" + mMemoEdit.getText().toString() + "<END>" + Date + "\n";
                    }

                    mTextFileManager.save(memoData,"1");
                    mCustomDialog.dismiss();

                }
            }
        }
    };

    private View.OnClickListener rightListener = new View.OnClickListener() {
        public void onClick(View v) {

            mCustomDialog.dismiss();
        }
    };

    private View.OnClickListener DeleteListener = new View.OnClickListener() {
        public void onClick(View v) {

            String Date= preMm.get(Calendar.YEAR)+"-"+(preMm.get(Calendar.MONTH))+"-"+Day.getText();

            if(memoData.indexOf(Date) > -1) {
                String temp = memoData.substring(memoData.indexOf(Date) + Date.length() + 1, memoData.indexOf("<END>" + Date));
                temp = Date + "\n" + temp + "<END>" + Date + "\n";
                memoData = memoData.replace(temp, "");
            }
            else
            {}
            mTextFileManager.save(memoData,"1");
            mCustomDialog.dismiss();
        }
    };



}
