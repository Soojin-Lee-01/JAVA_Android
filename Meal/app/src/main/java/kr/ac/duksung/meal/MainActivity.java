package kr.ac.duksung.meal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String [] meals = {"Caprese Salad","Chicken and Potatoes",  "Pasta with Meatballs" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("메뉴");

        ListView movieList = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, meals);

        movieList.setAdapter(adapter);

        movieList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), meals[i], Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtra("image", i);
                intent.putExtra("name", i);
                startActivity(intent);
            }
        });
    }
}


