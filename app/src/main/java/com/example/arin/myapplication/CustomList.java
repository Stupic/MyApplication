package com.example.arin.myapplication;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class CustomList extends Dialog {


    private static final String TAG_RESULTS = "result";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_PASSWD ="address";
    private static final String TAG_HANDP ="handphone";
    private static final String TAG_HOMEP ="homephone";


    Comment_List_Adapter ca;
    ArrayList<Comment_Item> c_arr = new ArrayList<Comment_Item>();
    ListView comment_list;
    String MyPhoneNumber;
    JSONArray peoples = null;
    JSONArray peoples1 = null;
    String myJSON;
    String myJSON1;
    String MyName;
    Activity_Menu Menu;

    private View.OnClickListener mOkClickListener;
    private View.OnClickListener mAddClickListener;


    private Button mOkButton;
    private Button mAddButton;


    public void setmyJSON1(String myJSON1)
    {
        this.myJSON1= myJSON1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 다이얼로그 외부 화면 흐리게 표현
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);
        setContentView(R.layout.activity_list);


        getData("http://52.79.186.46/getlist.php");


    }

    private void setList(){
        ca = new Comment_List_Adapter(getContext(), c_arr);
        comment_list.setAdapter(ca);

        comment_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder alertDlg = new AlertDialog.Builder(getContext());
                alertDlg.setPositiveButton("선택", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Comment_Item i =c_arr.get(position);
                        Menu.setCurrently_party(c_arr.get(position));
                        Menu.test.setText(i.getId() + "\n" + i.getNickname() + "\n" + i.getphone()+"\n" + i.getGoal()+"\n" + i.getFee()+"\n" + i.getTarget_amount()+"\n" + i.getRotation_date());
                        dismiss();
                        Toast.makeText(getContext(),c_arr.get(position).getId().toString() +"선택완료.", 0).show();
                    }});
                alertDlg.setNegativeButton("취소", null);
                alertDlg.setTitle("계모임 선택");
                alertDlg.setMessage("정말 선택 하시겠습니까?");
                alertDlg.show();
//                Toast.makeText(getContext(), c_arr.get(position).getId(), Toast.LENGTH_LONG).show();
//                System.out.println(c_arr.get(position).getId().toString());

            }
        });


    }


    public void init(){
        comment_list = (ListView)findViewById(R.id.listView);
        setList();

    }



    //클릭버튼이 3가지 일때 생성자함수로 클릭 이벤트르 받는다
    public CustomList(Activity_Menu Menu,Context context, View.OnClickListener OkListener, View.OnClickListener AddListener,String MyName,String MyPhoneNumber,String myJSON) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.Menu= Menu;
        this.mOkClickListener = OkListener;
        this.mAddClickListener = AddListener;
        this.MyPhoneNumber = MyPhoneNumber;
        this.MyName = MyName;
        this.myJSON1 = myJSON;
    }



    protected void showList(){
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULTS);

            JSONObject jsonObj1 = new JSONObject(myJSON1);
            peoples1 = jsonObj1.getJSONArray(TAG_RESULTS);
            int max = peoples1.length();
            int j= 0;
            for(int i=0;i<peoples.length();i++){
                JSONObject b = peoples1.getJSONObject(j);
                JSONObject c = peoples.getJSONObject(i);
                String id = c.getString(TAG_ID);
                String tempid = b.getString(TAG_ID);

                if(id.equals(tempid))
                {
                    Comment_Item item =new Comment_Item(c.getString("partyname"),c.getString("leader_number"),id);
                    item.setFee(c.getString("fee"));
                    item.setGoal(c.getString("goal"));
                    item.setRotation_date(c.getString("rotation_date"));
                    item.setTarget_amount(c.getString("target_amount"));
                    c_arr.add(item);
                    if(j<max-1)
                    {j++;}
                    else
                    {break;}
                }

            }

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
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
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

                mOkButton = (Button) findViewById(R.id.OK);
                mOkButton.setOnClickListener(mOkClickListener);
                mAddButton = (Button) findViewById(R.id.Add);
                mAddButton.setOnClickListener(mAddClickListener);
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



