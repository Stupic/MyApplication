package com.example.arin.myapplication;

import android.widget.Button;

/**
 * Created by arin on 2016-08-30.
 */
public class Comment_Jangboo_Item {

    private String name;
    private String id;
    private String check;   //납부 미납부 체크.
    private Button JangBoonap; //납부 버튼
    private Button info; // 정보 버튼

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public Button getJangBoonap() {
        return JangBoonap;
    }

    public void setJangBoonap(Button jangBoonap) {
        JangBoonap = jangBoonap;
    }

    public Button getInfo() {
        return info;
    }

    public void setInfo(Button info) {
        this.info = info;
    }
}
