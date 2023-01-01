package kr.ac.duksung.advisor2;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
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
import java.util.Arrays;

public class StudentActivity extends AppCompatActivity {
    private Document doc;
    ArrayList<String> students;
    ArrayList<String> students_name;
    ArrayList<String> students_id;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        students = new ArrayList<String>();
        students_name = new ArrayList<String>();
        students_id = new ArrayList<String>();
        ListView listView = (ListView) findViewById(R.id.listView);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, students);
        listView.setAdapter(adapter);

        String urlString = "http://203.252.213.36:8080/FinalProject/advisorPro2.jsp";
        Intent intent = getIntent();
        String professor = intent.getStringExtra("professor");

        urlString = urlString + "?advisor=" + professor;
        JsoupAsyncTask advisorTask = new JsoupAsyncTask();
        advisorTask.execute(urlString);
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

            Elements id = doc.select("i");
            for(Element element1 : id) {
                 students_id.add(element1.text());
            }
            Elements elements = doc.select("h5");
            for(Element element : elements) {
                students_name.add(element.text());
            }
            for(int i = 0 ; i < elements.size(); i++)
                students.add(students_name.get(i) + " / " + students_id.get(i));
                adapter.notifyDataSetChanged();
        }
    }
}
