package com.example.arin.myapplication;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by arin on 2016-04-01.
 */
public class TextFileManager {
    private static final String FILE_NAME = "Memo.txt";

    Context mContext = null;

    public TextFileManager(Context context) {
        mContext = context;
    }

    public void save(String strData,String file_name) {
        if (strData == null || strData.equals("")){
            return;
    }

    FileOutputStream fosMemo = null;

    try{
        fosMemo = mContext.openFileOutput(file_name, Context.MODE_PRIVATE);


        fosMemo.write(strData.getBytes());
        fosMemo.close();
    }
    catch(Exception e){
        e.printStackTrace();
    }
}
    public  String load(String file_name){
        try{
             FileInputStream fisMemo = mContext.openFileInput(file_name);
             File f = new File(file_name);

             byte[] memoData = new byte[fisMemo.available()];

            while (fisMemo.read(memoData)!=-1){}
            return new String(memoData);
        } catch (IOException e){}

        return "";
    }

    public void delete(){
        mContext.deleteFile(FILE_NAME);

    }
}
