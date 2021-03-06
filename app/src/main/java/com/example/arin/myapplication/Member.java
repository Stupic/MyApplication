package com.example.arin.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
public class Member extends Activity {


    private static final String TAG_RESULTS = "result";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_HANDP ="handphone";

    JSONArray peoples = null;
    String myJSON;

    ArrayList<HashMap<String, String>> personList;

    ListView list;
    Comment_Item currently_party;

    ListView comment_list;
    TextView time_text;
    EditText comment_edit;
    ImageView jrv_image_img;
    Comment_Adapter ca;
    ArrayList<Comment_Item> c_arr = new ArrayList<Comment_Item>();
    View header,footer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);

        Intent intent = getIntent();
        currently_party = (Comment_Item)intent.getSerializableExtra("currently_party");

        getData("http://52.79.186.46/getMember.php");

    }

    public void init(){
        comment_list = (ListView)findViewById(R.id.jrv_comment_list);

		 /*
 		  * 이부분이 오늘의 핵심. headr와 footer를 설정하는 겁니다.
		  * header는 View 변수로 layout에 만들어 놓은 xml 을 인플레이터 시킵니다!
   		  * footer도 마찬가지.
		  * inflater 한 View를 listview에 헤더와 푸터로 설정합니다.
		  * addHeadrView 와 addFooterView 메소드를 사용해서 설정합니다.
		  * 쉽게 말하면 Header 는 리스트뷰 위에 달릴것 Footer는 리스트뷰 아래에 달릴것!
		 */
        header = getLayoutInflater().inflate(R.layout.activity_member_header, null, false);
//        footer = getLayoutInflater().inflate(R.layout.footer, null, false);
        comment_list.addHeaderView(header);
//        comment_list.addFooterView(footer);

        time_text = (TextView)header.findViewById(R.id.jrv_time_text);


        setList();  //listview 세팅 (아래 함수를 만들어 놓은곳 확인하세요)
        setHeader(); //activity_member_header 세팅
//		setFooter(); //footer 세팅
    }

    private void setHeader(){


/*헤더의 id 값을 받아오기 위해서는 평소에 findViewById를 바로 썻는데 그 앞에header.
  * 을 붙여서 header에 만들어 놓은 TextView의 아이디 값을 쓰겠다 이런 식으로 앞에
  * 꼭 붙여주셔야 합니다. 안그러면 id값을 받아 올 수가 없어요 ㅠㅠ
  */
        TextView jrv_title_text = (TextView)header.findViewById(R.id.jrv_title_text);
        TextView jrv_time_text = (TextView)header.findViewById(R.id.jrv_time_text);
        jrv_title_text.setText("계모임 명 : "+currently_party.getNickname());
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
        jrv_image_img = (ImageView)header.findViewById(R.id.jrv_image_img);

    }
    //	private void setFooter(){
//		//Footer도 마찬가지로 앞에 footer.를 붙여줄것!
//		comment_edit = (EditText)footer.findViewById(R.id.jrv_comment_edit);
//		Button commentinput_btn = (Button)footer.findViewById(R.id.jrv_commentinput_btn);
//
//		//implements를 맨위에 선언해 줬기 떄문에, setOnClickListener를 여기서 설정할 수 있습니다.
//		commentinput_btn.setOnClickListener(this);
//	}
    private void setList(){
        ca = new Comment_Adapter(getApplicationContext(), Member.this, Member.this, c_arr);
        comment_list.setAdapter(ca);
        comment_list.setSelection(c_arr.size() - 1);
        comment_list.setDivider(null);
        comment_list.setSelectionFromTop(0,0);
    }

//    @Override
//    public void onClick(View v) {
//        //Click 되었을때 Id값으로 클릭처리를 할 수 있습니다.
//        //아까 setFooter에서 commentinput_btn 에 setOnClickListener를 달아 주었기 때문에 onClick이 사용 가능합니다.
//        switch(v.getId()){
//            case R.id.jrv_commentinput_btn:
//                String temp = comment_edit.getText().toString();
//                if(temp.equals("")){
//                    Toast.makeText(Member.this, "빈칸이 있습니다.", 0).show();
//                }else{
//                    //EditText의 빈칸이 없을 경우 등록!
//                    Comment_Item ci = new Comment_Item();
//                    ci.setContent(temp);
//                    ci.setNickname("닉네임");
//                    c_arr.add(ci);
//                    resetAdapter();
//                    comment_edit.setText("");
//                }
//                break;
//        }
//    }

    public void resetAdapter(){
        ca.notifyDataSetChanged();
        //Adapter의 데이터 값이 변화가 있을 때 사용하는 함수.

    }
    public void deleteArr(int p){
        //Adapter에서 이 함수를 지울 떄 호출합니다. 지우고자 하는 댓글의 id 값을 넘겨주면 c_arr 의 데이터를 삭제!
        c_arr.remove(p);
        //마찬가지로 변화가 있었기 때문에 Adapter 초기화(?)
        ca.notifyDataSetChanged();
    }


    /////////////////////////// 여기서 부터는 getdata //////////////////////////////////////////////////////
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
                Comment_Item ci = new Comment_Item(name,handphone,id);
                ci.setGoal(fault);
                c_arr.add(ci);
            }



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
                setList();  //listview 세팅 (아래 함수를 만들어 놓은곳 확인하세요)

            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }

}