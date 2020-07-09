package com.example.arin.myapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Start extends Activity {


    private static final String TAG_RESULTS = "result";
    private static final String TAG_ID = "memberid";
    private static final String TAG_NAME = "name";
    private static final String TAG_PASSWD ="passwd";
    private static final String TAG_HANDP ="handphone";
    private static final String TAG_HOMEP ="homephone";
    JSONArray peoples = null;
    String myJSON;
    ArrayList<Comment_Item> c_arr = new ArrayList<Comment_Item>();

    private String Mypasswd = null;

    private EditText MName;
    private EditText MPasswd;
    private TextView MHandPhone;
    private EditText MHomePhone1;
    private EditText MHomePhone2;
    private EditText MHomePhone3;



    String MyPhoneNum = "010-1234-1234";
    String MyName;

    TextFileManager mTextFileManager= new TextFileManager(this);
    private CustomJoin mCustomJoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        getData("http://52.79.186.46/getdata.php");

    }


    protected void Join(){  //가입창 띄우기
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULTS);



            System.out.println(peoples);


//            String id = c.getString(TAG_ID);
//            String name = c.getString(TAG_NAME);
//            String passwd = c.getString(TAG_PASSWD);
//            String handphone = c.getString(TAG_HANDP);
//            String homephone = c.getString(TAG_HOMEP);



            if(myJSON.toString().contains(MyPhoneNum))
            {
                JSONObject c = peoples.getJSONObject(0);
                Mypasswd= c.getString(TAG_PASSWD);
                MyName = c.getString(TAG_NAME);
            }
            else
            {
                mCustomJoin = new CustomJoin(this,
                        "회원 가입", // 제목
                    MyPhoneNum , // 내용
                    OkListener);  //Ok 버튼
            mCustomJoin.show();
        }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void Onbutton1(View v)
    {

        ((EditText) findViewById(R.id.HomeP_1)).setText(
                ((EditText) findViewById(R.id.HomeP_1)).getText().toString() +
                        ((Button) v).getText());


    }
    public void Onbutton2(View v)
    {
        Intent intent = new Intent (getApplicationContext(), Activity_Menu.class);
        switch (v.getId()){
            case R.id.buttonA:
                ((EditText) findViewById(R.id.HomeP_1)).setText("");
                break;
            case R.id.buttonB:
                if (Mypasswd.equals(((EditText) findViewById(R.id.HomeP_1)).getText().toString()))
                {
                    intent.putExtra("MyName", MyName);
                    intent.putExtra("MyPhoneNum",MyPhoneNum);
                    intent.putExtra("c_arr", c_arr);
                    startActivity(intent);
                    finish();
                    break;
                }
                else
                {
                    ((EditText) findViewById(R.id.HomeP_1)).setText("");
                    Toast toast = Toast.makeText(this, "비밀번호가 틀렸습니다.",
                            Toast.LENGTH_SHORT);
                    toast.show();

                }
        }

    }
    private View.OnClickListener OkListener = new View.OnClickListener() {
        public void onClick(View v) {           //회원 가입창 Ok 버튼 누를때

            MName = (EditText) mCustomJoin.findViewById(R.id.Name_text);
            MPasswd = (EditText) mCustomJoin.findViewById(R.id.Passwd_text);
            MHandPhone = (TextView) mCustomJoin.findViewById(R.id.HandP_text);
            MHomePhone1 = (EditText) mCustomJoin.findViewById(R.id.HomeP_text1);
            MHomePhone2 = (EditText) mCustomJoin.findViewById(R.id.HomeP_text2);
            MHomePhone3 = (EditText) mCustomJoin.findViewById(R.id.HomeP_text3);

            MyName= MName.getText().toString();
            Mypasswd = MPasswd.getText().toString();
            String handphone =MHandPhone.getText().toString();
            String homephone = MHomePhone1.getText().toString()+'-'+MHomePhone2.getText().toString()+'-'+MHomePhone3.getText().toString();



            mCustomJoin.dismiss();
        }
    };

    private void insertToDatabase(String name, String passwd,String handphone,String homephone){
        class InsertData extends AsyncTask<String, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Start.this, "Please Wait", null, true, true);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            }
            @Override
            protected String doInBackground(String... params) {
                try{
                    String name = (String)params[0];
                    String passwd = (String)params[1];
                    String handphone = (String)params[2];
                    String homephone = (String)params[3];
                    String link="http://52.79.186.46/insert.php";
                    String data  = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8");
                    data += "&" + URLEncoder.encode("passwd", "UTF-8") + "=" + URLEncoder.encode(passwd, "UTF-8");
                    data += "&" + URLEncoder.encode("handphone", "UTF-8") + "=" + URLEncoder.encode(handphone, "UTF-8");
                    data += "&" + URLEncoder.encode("homephone", "UTF-8") + "=" + URLEncoder.encode(homephone, "UTF-8");
                    URL url = new URL(link);
                    URLConnection conn = url.openConnection();
                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                    wr.write( data );
                    wr.flush();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    // Read Server Response
                    while((line = reader.readLine()) != null)
                    {
                        sb.append(line);
                        break;
                    }
                    return sb.toString();
                }
                catch(Exception e){
                    return new String("Exception: " + e.getMessage());
                }
            }
        }
        InsertData task = new InsertData();
        task.execute(name,passwd,handphone,homephone);
    }


    public void getData(String url){
        class GetDataJSON extends AsyncTask<String, Void, String>{

            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];

                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    String data  =URLEncoder.encode("handphone", "UTF-8") + "=" + URLEncoder.encode(MyPhoneNum, "UTF-8");
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    con.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
                    wr.write( data );
                    wr.flush();

                    StringBuilder sb = new StringBuilder();
                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while((json = bufferedReader.readLine())!= null){
                        sb.append(json+"\n");
                    }

                    return sb.toString().trim();

                }catch(Exception e){
                    return null;
                }



            }

            @Override
            protected void onPostExecute(String result){
                myJSON = result;
                System.out.println(myJSON);
                Join();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }

}

