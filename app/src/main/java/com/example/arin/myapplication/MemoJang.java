package com.example.arin.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

public class MemoJang extends AppCompatActivity {

    EditText mMemoEdit = null;
    TextFileManager mTextFileManager= new TextFileManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);


        String memoData = mTextFileManager.load("Memo.txt");
        mMemoEdit.setText(memoData);
        Toast.makeText(getApplicationContext(), "불러오기 완료", Toast.LENGTH_LONG).show();


        memoData = mMemoEdit.getText().toString();
        mTextFileManager.save(memoData,"Memo");
        mMemoEdit.setText("");

        Toast.makeText(this,"저장 완료",Toast.LENGTH_LONG).show();



        mTextFileManager.delete();
        mMemoEdit.setText("");

        Toast.makeText(this,"삭제 완료",Toast.LENGTH_LONG).show();

    }
}
