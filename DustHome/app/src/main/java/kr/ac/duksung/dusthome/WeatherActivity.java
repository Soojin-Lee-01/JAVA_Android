package kr.ac.duksung.dusthome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class WeatherActivity extends AppCompatActivity {

    TextView textView;
    Button button;
    static RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        setTitle("일기예보");

        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),AirActivity.class);
                startActivity(i);
            }
        });

        date();


    }

    public void date() {
        Date now = Calendar.getInstance().getTime();
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyyMMddHHmm");
        String formatedNow3 = formatter2.format(now);

        SimpleDateFormat formatter3 = new SimpleDateFormat("yyyyMMdd");
        String formatedNow4 = formatter3.format(now) + "0600";

        Date date1, date2;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm"); // 형식은 String 과 맞추면 yyyy-MM-dd 형식도 상관없음
        try {
            date1 = dateFormat.parse(formatedNow4);
            date2 = dateFormat.parse(formatedNow3);
            int compare = date1.compareTo(date2);
            if (compare <= 0) {
                makeRequest();
            }
            else {
                textView.setText("not available until 06:00");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void makeRequest () {

            Date now = Calendar.getInstance().getTime();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
            String formatedNow = formatter.format(now);

            if (requestQueue == null) {
                requestQueue = Volley.newRequestQueue(getApplicationContext());
            }

            String url = "http://apis.data.go.kr/1360000/MidFcstInfoService/getMidFcst" +
                    "?serviceKey=%2FHnNlrGWmmtGq8BJgAP9B89maE2ID77HsyvT7Te939NMG9y6hRQpfCLKIOTj9ozqKYwWXltL6LfZgy0Z38Ga6g" +
                    "%3D%3D&pageNo=1&numOfRows=10&dataType=json&stnId=108&tmFc=" + formatedNow + "0600";
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
        textView.append(data);
    }

    public void parseJson(String json) {
        try {
            JSONObject object1 = new JSONObject(json);
            JSONObject object2 = object1.getJSONObject("response");
            JSONObject object3 = object2.getJSONObject("body");
            JSONObject object4 = object3.getJSONObject("items");
            JSONArray array = object4.getJSONArray("item");
            for(int i=0; i<array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                String ku = obj.getString("wfSv");
                println(ku);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}