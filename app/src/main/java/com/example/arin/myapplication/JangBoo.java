package com.example.arin.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by arin on 2016-03-20.
 */
public class JangBoo extends Activity {

    private static final String TAG_RESULTS = "result";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_HANDP ="handphone";

    ArrayList<Comment_Jangboo_Item> c_arr = new ArrayList<Comment_Jangboo_Item>();

    JSONArray peoples = null;
    String myJSON;

    ArrayList<HashMap<String, String>> personList;

    ListView comment_list;

    Comment_Jangboo_Adapter ca;
    View header,footer;

    Comment_Item currently_party;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jangboo);


        Intent intent = getIntent();
        currently_party = (Comment_Item)intent.getSerializableExtra("currently_party");

        getData("http://52.79.186.46/getMember.php");
    }



    public void init(){
        comment_list = (ListView)findViewById(R.id.jrv_comment_list);


        header = getLayoutInflater().inflate(R.layout.activity_member_header, null, false);

        comment_list.addHeaderView(header);




        setList();  //listview 세팅 (아래 함수를 만들어 놓은곳 확인하세요)
        setHeader(); //activity_member_header 세팅
    }

    private void setHeader(){


/*헤더의 id 값을 받아오기 위해서는 평소에 findViewById를 바로 썻는데 그 앞에header.
  * 을 붙여서 header에 만들어 놓은 TextView의 아이디 값을 쓰겠다 이런 식으로 앞에
  * 꼭 붙여주셔야 합니다. 안그러면 id값을 받아 올 수가 없어요 ㅠㅠ
  */
        TextView jrv_title_text = (TextView)header.findViewById(R.id.jrv_title_text);
        TextView jrv_time_text = (TextView)header.findViewById(R.id.jrv_time_text);
        jrv_title_text.setText("계모임 명 : " + currently_party.getNickname());
        jrv_time_text.setText("우아아아ㅏㅇ");
        TextView jrv_content_text = (TextView)header.findViewById(R.id.jrv_content_text);
        String goal;
        if(currently_party.getGoal()=="1"){
            goal = "친목";
        }else{
            goal = "계타기";
        }
        jrv_content_text.setText("계모임 목적(친목,계타기) : "+goal+"\n계장 번호 : "+currently_party.getphone() +"\n회비 : " + "매월 " +currently_party.getRotation_date()+"일 "+
                currently_party.getFee()+"원");

    }


    private void setList(){
        ca = new Comment_Jangboo_Adapter(getApplicationContext(), c_arr);
        comment_list.setAdapter(ca);
    }

    protected void showList(){
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULTS);



            for(int i=0;i<peoples.length();i++){

                JSONObject c = peoples.getJSONObject(i);
                String id = c.getString(TAG_ID);
                String name = c.getString(TAG_NAME);
                String handphone = c.getString(TAG_HANDP);
                String fault = c.getString("fault");
                Comment_Jangboo_Item ci = new Comment_Jangboo_Item();
                ci.setId("a");
                ci.setName("A");
                ci.setCheck("ch");
                c_arr.add(ci);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void getData(String url){
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];

                BufferedReader bufferedReader = null;
                try {

                    String data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(currently_party.getId(), "UTF-8");

                    URL url = new URL(uri);

                    URLConnection con = url.openConnection();

                    con.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());

                    wr.write(data);
                    wr.flush();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    StringBuilder sb = new StringBuilder();

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
                init();
                showList();
                setList();

            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }
}
