package kr.ac.duksung.meal;
import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {
    String meal_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setTitle("상세정보");

        TextView textView = (TextView) findViewById(R.id.textView);
        TextView textView2 = (TextView) findViewById(R.id.textView2);
        Button button =(Button) findViewById(R.id.button);

        Intent intent = getIntent();
        Integer position = intent.getIntExtra("image", 0);

        meal_name = intent.getStringExtra("meal");
        textView.setText(meal_name+ " 선택!" );
        textView2.setText("추가 정보를 원하시면 More버튼 클릭!");


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                intent.putExtra(SearchManager.QUERY, meal_name);
                startActivity(intent);
            }
        });
    }
}


