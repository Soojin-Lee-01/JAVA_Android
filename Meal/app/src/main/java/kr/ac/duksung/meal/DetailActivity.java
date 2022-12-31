package kr.ac.duksung.meal;
import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {
    Integer[] images = {R.drawable.meal0, R.drawable.meal1, R.drawable.meal2,};
    String[] mealTitle = {"Caprese Salad","Chicken and Potatoes","Pasta with Meatballs", };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setTitle("상세정보");

        TextView textView = (TextView) findViewById(R.id.textView);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        Button button =(Button) findViewById(R.id.button);

        Intent intent = getIntent();
        Integer position = intent.getIntExtra("image", 0);
        Integer position1 = intent.getIntExtra("name",0);
        imageView.setImageResource(images[position]);
        textView.setText(mealTitle[position1]);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                intent.putExtra(SearchManager.QUERY, mealTitle[position1]);
                startActivity(intent);
            }
        });


    }

}


