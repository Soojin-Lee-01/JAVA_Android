package kr.ac.duksung.dusthome;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private Document doc;
    ArrayList<String> login;
    ArrayAdapter<String> adapter;
    EditText editText, editText1;
    Button button;
    String urlString, urlString1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("서울 날씨 어때?");

        login = new ArrayList<String>();
        button = findViewById(R.id.button);
        editText = findViewById(R.id.editText1);
        editText1 = findViewById(R.id.editText2);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, login);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                urlString1 = urlString + editText.getText().toString() + "&passwd=" + editText1.getText().toString();
                System.out.println(urlString1);
                JsoupAsyncTask advisorTask = new JsoupAsyncTask();
                advisorTask.execute(urlString1);
            }

        });
        urlString = "http://203.252.213.36:8080/FinalProject/loginPro.jsp?id=";
    }
    void showAlertDialog() {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Login failed");
            builder.setMessage("please try again");
            builder.setPositiveButton("확인",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    builder.show();
    }

    private class JsoupAsyncTask extends AsyncTask<String, Void, Document> {

        @Override
        protected Document doInBackground(String... params) {
            try {
                doc = Jsoup.connect(params[0]).get();
            } catch (Exception e) {
                Toast.makeText(getBaseContext(), "network error",
                        Toast.LENGTH_SHORT).show();
            }
            return doc;
        }

        @Override
        protected void onPostExecute(Document doc) {
            Elements elements = doc.select("body");

            for(Element element : elements) {
                login.add(element.text().trim());

            }

            if(login.get(0).equals("-1") || login.get(0).equals("0")) {
                showAlertDialog();
                login.clear();
            }
            else {
                Intent i = new Intent(getApplicationContext(), WeatherActivity.class);
                startActivity(i);

            }

        }
    }
}