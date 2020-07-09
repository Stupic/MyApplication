package com.example.arin.myapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by arin on 2016-03-20.
 */
public class Activity_Menu extends Activity {


    private CustomList mCustomList;
    private Comment_Item currently_party;
    TextView test;

    private static final String TAG_RESULTS = "result";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_PASSWD ="address";
    private static final String TAG_HANDP ="handphone";
    private static final String TAG_HOMEP ="homephone";


    JSONArray peoples = null;
    String myJSON;

    String MyPhoneNum;
    String MyName;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent intent = getIntent();
        MyPhoneNum = intent.getStringExtra("MyPhoneNum");
        MyName = intent.getStringExtra("MyName");

        getData("http://52.79.186.46/test3.php");

//
//
//
//

    }

    public void fini(){
        this.finish();
    }

    public void Onbutton2(View v){
        Intent intent2 = new Intent (getApplicationContext(), Member.class);  //회원정보
        Intent intent3 = new Intent (getApplicationContext(), JangBoo.class);  //금액
        Intent intent4 = new Intent (getApplicationContext(), Activity_Calendar.class); // 스케줄


        switch (v.getId()){
            case R.id.button11:  //회원정보로 이동
                intent2.putExtra("currently_party", currently_party);
                startActivity(intent2);
                break;
            case R.id.button12:  //금액으로 이동
                startActivity(intent3);
                break;
            case R.id.button13: //스케줄로 이동
                startActivity(intent4);
                break;
            case R.id.button14:
                getData("http://52.79.186.46/test3.php");
                break;
        }
    }


    private View.OnClickListener OkListener = new View.OnClickListener() {
        public void onClick(View v) {           //회원 가입창 Ok 버튼 누를때
            mCustomList.dismiss();
        }
    };

    private View.OnClickListener AddListener = new View.OnClickListener() {
        public void onClick(View v) {           //회원 가입창 Ok 버튼 누를때
            Intent make = new Intent (getApplicationContext(), CustomMake.class);
            make.putExtra("MyPhoneNum",MyPhoneNum);
            make.putExtra("MyName", MyName);
            startActivity(make);//회원정보
            mCustomList.dismiss();
            fini();

        }
    };


    protected void showList(){
        try {
            test= (TextView)findViewById(R.id.testview);
            JSONObject jsonObj = new JSONObject(myJSON);
//            peoples = jsonObj.getJSONArray(TAG_RESULTS);



//            for(int i=0;i<peoples.length();i++){
//                Comment_Item ci = new Comment_Item();
//                JSONObject c = peoples.getJSONObject(i);
//                String id = c.getString(TAG_ID);
//                String name = c.getString(TAG_NAME);
//                String handphone = c.getString(TAG_HANDP);
//
//                ci.setphone("핸드폰 :" + handphone);
//                ci.setNickname("이름 :" + name);
//                c_arr.add(ci);
//            }


            mCustomList = new CustomList(Activity_Menu.this,this,
                    OkListener,
                    AddListener,MyName,MyPhoneNum,myJSON);  //Ok 버튼
            mCustomList.show();


//			ListAdapter adapter = new SimpleAdapter(
//					MainActivity.this, personList, R.layout.list_item,
//					new String[]{TAG_ID,TAG_NAME,TAG_PASSWD},
//					new int[]{R.id.id, R.id.name, R.id.address}
//			);

//			list.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }




    public void getData(String url){
        class GetDataJSON extends AsyncTask<String, Void, String> {
            ProgressDialog loading;

            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];
                BufferedReader bufferedReader = null;

                try {

                    URL url = new URL(uri);

                    String data  = URLEncoder.encode("handphone", "UTF-8") + "=" + URLEncoder.encode(MyPhoneNum, "UTF-8");
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
                myJSON=result;
                showList();

            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }


    public void setCurrently_party(Comment_Item i)
    { this.currently_party = i;}
    public Comment_Item getCurrently_party()
    { return currently_party;}

}
