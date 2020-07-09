package com.example.arin.myapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by arin on 2016-03-20.
 */
public class CustomMake extends Activity {

    private static final String TAG_RESULTS = "result";
    private static final String TAG_NAME = "name";
    JSONArray peoples = null;
    String myJSON;
    String AddPhoneNum;
    private CustomMakeList mCustomMakeList;
    ArrayList<Comment_Item> c_arr = new ArrayList<Comment_Item>();
    EditText phone_text;
    Spinner mSpinner;
    String MyPhoneNum;
    String MyName;
    Comment_Item makeci = new Comment_Item();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make);

        mSpinner = (Spinner) findViewById(R.id.spinner);


    }


    public void SelectClicked(View v) {

        CheckBox check1 = (CheckBox) findViewById(R.id.checkBox);
        CheckBox check2 = (CheckBox) findViewById(R.id.checkBox4);
        switch (v.getId()) {
            case R.id.checkBox: {
                if (check1.isChecked()) {
                    findViewById(R.id.target1).setEnabled(true);
                    findViewById(R.id.target2).setEnabled(true);
                    findViewById(R.id.target3).setEnabled(true);
                    findViewById(R.id.target4).setEnabled(true);
                    check2.setChecked(false);
                    break;
                } else if (!check1.isChecked()) {
                    findViewById(R.id.target1).setEnabled(false);
                    findViewById(R.id.target2).setEnabled(false);
                    findViewById(R.id.target3).setEnabled(false);
                    findViewById(R.id.target4).setEnabled(false);
                    check2.setChecked(false);
                    break;
                }
            }
            case R.id.checkBox4: {
                if (check2.isChecked()) {
                    findViewById(R.id.target1).setEnabled(false);
                    findViewById(R.id.target2).setEnabled(false);
                    findViewById(R.id.target3).setEnabled(false);
                    findViewById(R.id.target4).setEnabled(false);
                    check1.setChecked(false);
                    break;
                } else if (!check2.isChecked()) {
                    findViewById(R.id.target1).setEnabled(false);
                    findViewById(R.id.target2).setEnabled(false);
                    findViewById(R.id.target3).setEnabled(false);
                    findViewById(R.id.target4).setEnabled(false);
                    check1.setChecked(false);
                    break;
                }


            }

        }
    } //계모임 종류 체크박스 on/off

    public void MakeClicked(View v) {

        CheckBox check1 = (CheckBox) findViewById(R.id.checkBox);
        switch (v.getId()) {
            case R.id.MakeButton: {
                if (((EditText) findViewById(R.id.partyname)).getText().toString().equals("") || ((EditText) findViewById(R.id.fee)).getText().toString().equals("")) {

                    Toast.makeText(CustomMake.this, "비어있는 칸이 있습니다.", 0).show();
                    break;

                } else if (check1.isChecked() && ((EditText) findViewById(R.id.target3)).getText().toString().equals("")) {
                    Toast.makeText(CustomMake.this, "비어있는 칸이 있습니다.", 0).show();
                    break;
                } else if (c_arr.size() == 0) {
                    Toast.makeText(CustomMake.this, "계 회원을 추가해주세요.", 0).show();
                    break;
                } else {
                    Intent intent = getIntent();
                    MyPhoneNum = intent.getStringExtra("MyPhoneNum");
                    MyName = intent.getStringExtra("MyName");
                    int goal = 1;
                    if (((CheckBox) findViewById(R.id.checkBox)).isChecked()) {
                        goal = 0;
                    }

                    String partyname = ((EditText) findViewById(R.id.partyname)).getText().toString();
                    String rotation_date = (String) mSpinner.getSelectedItem();
                    String target_amount = ((EditText) findViewById(R.id.target3)).getText().toString();
                    String fee = ((EditText) findViewById(R.id.fee)).getText().toString();
                    String leader_number = MyPhoneNum;
                    insertToDatabase(String.valueOf(goal), partyname, rotation_date, target_amount, fee, leader_number);

                }
            }
        }

    }


    public void AddMember(View v) {
        mCustomMakeList = new CustomMakeList(this,
                AddListener, AddOkListener, c_arr);  //Ok 버튼
        mCustomMakeList.show();
    }

    private View.OnClickListener AddListener = new View.OnClickListener() {
        public void onClick(View v) {           //회원 가입창 Ok 버튼 누를때

            phone_text = (EditText) mCustomMakeList.findViewById(R.id.phoneText);

            AddPhoneNum = phone_text.getText().toString();


            if (AddPhoneNum.equals("")) {

                Toast.makeText(CustomMake.this, "입력해주세요.", 0).show();

            } else {

                getData("http://52.79.186.46/getdata.php");
            }


        }
//            mCustomList.dismiss();

    };

    protected void Search() {
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULTS);


//            String id = c.getString(TAG_ID);
//            String passwd = c.getString(TAG_PASSWD);
//            String handphone = c.getString(TAG_HANDP);
//            String homephone = c.getString(TAG_HOMEP);


            if (myJSON.toString().contains(AddPhoneNum)) {

                JSONObject c = peoples.getJSONObject(peoples.length() - 1);
                String name = c.getString(TAG_NAME);
                Comment_Item ci = new Comment_Item();


                ci.setNickname(name);
                ci.setphone(AddPhoneNum);

                mCustomMakeList.c_arr.add(ci);

                mCustomMakeList.resetAdapter();

                phone_text.setText("");
            } else if (!myJSON.toString().contains(AddPhoneNum)) {
                Toast.makeText(CustomMake.this, "없는 번호 입니다.", 0).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    protected void CreatePartyMemberList() {  //가입창 띄우기
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULTS);
            JSONObject c = peoples.getJSONObject(peoples.length() - 1);

            String id = c.getString("id");

            CreateTable(id);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    protected void InsertPartyMemberList() {  //가입창 띄우기

        try {
            String name;
            String handphone;
            Comment_Item ci = new Comment_Item();
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULTS);
            JSONObject c = peoples.getJSONObject(peoples.length() - 1);

            String id = c.getString("id");
            makeci.setId(id);


            insertToDatabase(MyName, MyPhoneNum, id);

            for (int i = 0; i < c_arr.size(); i++) {
                ci = c_arr.get(i);
                name = ci.getNickname();
                handphone = ci.getphone();
                insertToDatabase(name, handphone, id);
            }


            Intent Menu = new Intent(getApplicationContext(), Activity_Menu.class);
            Menu.putExtra("MyPhoneNum", MyPhoneNum);
            Menu.putExtra("MyName", MyName);
            startActivity(Menu);


            finish();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private View.OnClickListener AddOkListener = new View.OnClickListener() {
        public void onClick(View v) {           //회원 가입창 Ok 버튼 누를때

            c_arr = mCustomMakeList.c_arr;
            mCustomMakeList.dismiss();
            ((TextView) findViewById(R.id.textView6)).setText(c_arr.size() + "명");
        }
//            mCustomList.dismiss();

    };


    //  ------------------- 데이터 저장----------------------------------------

    private void insertToDatabase(String goal, String partyname, String rotation_date, String target_amount, String fee, String leader_number) {

        class InsertData extends AsyncTask<String, Void, String> {
            ProgressDialog loading;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(CustomMake.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                myJSON = s;
                CreatePartyMemberList();
            }

            @Override
            protected String doInBackground(String... params) {

                try {
                    String goal = (String) params[0];
                    String partyname = (String) params[1];
                    String rotation_date = (String) params[2];
                    String target_amount = (String) params[3];
                    String fee = (String) params[4];
                    String leader_number = (String) params[5];

                    String link = "http://52.79.186.46/makeParty.php";
                    String data = URLEncoder.encode("goal", "UTF-8") + "=" + URLEncoder.encode(goal, "UTF-8");
                    data += "&" + URLEncoder.encode("partyname", "UTF-8") + "=" + URLEncoder.encode(partyname, "UTF-8");
                    data += "&" + URLEncoder.encode("rotation_date", "UTF-8") + "=" + URLEncoder.encode(rotation_date, "UTF-8");
                    data += "&" + URLEncoder.encode("target_amount", "UTF-8") + "=" + URLEncoder.encode(target_amount, "UTF-8");
                    data += "&" + URLEncoder.encode("fee", "UTF-8") + "=" + URLEncoder.encode(fee, "UTF-8");
                    data += "&" + URLEncoder.encode("leader_number", "UTF-8") + "=" + URLEncoder.encode(leader_number, "UTF-8");

                    URL url = new URL(link);
                    URLConnection conn = url.openConnection();

                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                    wr.write(data);
                    wr.flush();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    // Read Server Response
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                        break;
                    }
                    return sb.toString();
                } catch (Exception e) {
                    return new String("Exception: " + e.getMessage());
                }

            }
        }

        InsertData task = new InsertData();
        task.execute(goal, partyname, rotation_date, target_amount, fee, leader_number);
    }

    private void insertToDatabase(String name, String handphone, String id) {

        class InsertData extends AsyncTask<String, Void, String> {
            ProgressDialog loading;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(CustomMake.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {

                try {
                    String name = (String) params[0];
                    String handphone = (String) params[1];
                    String id = (String) params[2];

                    String link = "http://52.79.186.46/makePartyMemberInsert.php";
                    String data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
                    data += "&" + URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8");
                    data += "&" + URLEncoder.encode("handphone", "UTF-8") + "=" + URLEncoder.encode(handphone, "UTF-8");


                    URL url = new URL(link);
                    URLConnection conn = url.openConnection();

                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                    wr.write(data);
                    wr.flush();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    // Read Server Response
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                        break;
                    }
                    return sb.toString();
                } catch (Exception e) {
                    return new String("Exception: " + e.getMessage());
                }

            }
        }

        InsertData task = new InsertData();
        task.execute(name, handphone, id);
    }

    private void CreateTable(String id) {

        class InsertData extends AsyncTask<String, Void, String> {
            ProgressDialog loading;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(CustomMake.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                InsertPartyMemberList();
            }

            @Override
            protected String doInBackground(String... params) {

                try {
                    String id = (String) params[0];


                    String link = "http://52.79.186.46/makePartyMemberListSchedule.php";
                    String data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");

                    URL url = new URL(link);
                    URLConnection conn = url.openConnection();

                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                    wr.write(data);
                    wr.flush();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    // Read Server Response
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                        break;
                    }
                    return sb.toString();
                } catch (Exception e) {
                    return new String("Exception: " + e.getMessage());
                }

            }
        }

        InsertData task = new InsertData();
        task.execute(id);
    }


    // ------------------------------- 데이터 조회------------------------------------


    public void getData(String url) {
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];

                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    String data = URLEncoder.encode("handphone", "UTF-8") + "=" + URLEncoder.encode(AddPhoneNum, "UTF-8");
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    con.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
                    wr.write(data);
                    wr.flush();

                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }

                    return sb.toString().trim();

                } catch (Exception e) {
                    return null;
                }


            }

            @Override
            protected void onPostExecute(String result) {
                myJSON = result;
                System.out.println(myJSON);
                Search();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }


}










