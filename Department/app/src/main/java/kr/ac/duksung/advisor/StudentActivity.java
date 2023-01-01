package kr.ac.duksung.advisor;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class StudentActivity extends AppCompatActivity {
    private Document doc;
    ArrayList<String> students;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        students = new ArrayList<String>();
        ListView listView = (ListView) findViewById(R.id.listView);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, students);
        listView.setAdapter(adapter);

        String urlString = "http://203.252.213.36:8080/FinalProject/advisorProHome.jsp";
        Intent intent = getIntent();
        String dept = intent.getStringExtra("dept");

        urlString = urlString + "?dept=" + dept;
        JsoupAsyncTask advisorTask = new JsoupAsyncTask();
        advisorTask.execute(urlString);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Elements elements1 = doc.select("body>h6:nth-child(" + ((2*i)+1) + ")");
                Toast.makeText(getApplicationContext(), "사번 : " + elements1.text(), Toast.LENGTH_SHORT).show();
            }
        });
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
            Elements elements = doc.select("h5");
            for(Element element : elements) {
                students.add(element.text());
            }
            adapter.notifyDataSetChanged();
        }
    }
}
