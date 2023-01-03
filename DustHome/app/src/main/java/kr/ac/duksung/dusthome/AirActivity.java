package kr.ac.duksung.dusthome;

import static kr.ac.duksung.dusthome.WeatherActivity.requestQueue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class AirActivity extends AppCompatActivity {
    ArrayList<String> items, data;
    ArrayAdapter<String> adapter;
    TextView textView, textView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_air);
        setTitle("자치구별 대기환경 오염 정보");

        items = new ArrayList<String>();
        data = new ArrayList<String>();
        ListView listView = (ListView) findViewById(R.id.listView);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);

        textView = findViewById(R.id.textView2);
        textView1 = findViewById(R.id.textView1);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                textView.setText(data.get(i));
            }
        });


        makeRequest();

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super .onCreateOptionsMenu(menu);
        MenuInflater mInflator = getMenuInflater();
        mInflator.inflate(R.menu.menu_option, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                items.clear();
                data.clear();
                textView1.setText(null);
                textView.setText(null);
                makeRequest();
                return true;
        }
        return false;
    }

    public void makeRequest() {


        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        String url = "http://openapi.seoul.go.kr:8088/4c6d536e69736f6a33366861506b76/json" +
                "/ListAirQualityByDistrictService/1/25/";
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseJson(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        println("에러 -> " + error.getMessage());
                    }
                }
        );

        request.setShouldCache(false);
        requestQueue.add(request);
    }

    public void println(String data) {
        textView1.append(data + "\n");
    }

    public void parseJson(String json) {
        try {
            JSONObject object1 = new JSONObject(json);
            JSONObject object2 = object1.getJSONObject("ListAirQualityByDistrictService");
            JSONArray array = object2.getJSONArray("row");

            for(int i=0; i<array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                String ku = obj.getString("MSRSTENAME"); //측정소명
                String pm10 = "미세먼지:"+ obj.getString("PM10"); //미세먼지
                String grade = "통합대기환경지수 등급:" ; //등급 null
                String ozone = "오존:" + obj.getString("OZONE"); //오존
                String nitrogen = "이산화질소:" + obj.getString("NITROGEN"); //이산화질소
                String carbon = "일산화탄소:" + obj.getString("CARBON"); // 일산화탄소
                String sulfurous = "아황산가스:" + obj.getString("SULFUROUS"); //아황산가스
                String pm25 = "초미세먼지:" +obj.getString("PM25"); //초미세먼지
                items.add(ku);
                data.add(ozone + "\n" + nitrogen + "\n" + carbon + "\n" + sulfurous + "\n" + pm10 + "\n" + pm25 + "\n" + grade);
            }

            JSONObject obj = array.getJSONObject(0);
            String ku1 = obj.getString("MSRDATE");
            String y = ku1.substring(0,4);
            String m = ku1.substring(4,6);
            String d = ku1.substring(6,8);
            String h = ku1.substring(8,10);
            println(y + "년 " + m + "월 " + d + "일 " + h + "시 기준");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapter.notifyDataSetChanged();
    }
    }
