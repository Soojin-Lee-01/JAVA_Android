package kr.ac.duksung.meal;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> meals;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("메뉴");

        meals = new ArrayList<String>();
        meals.add("Caprese Salad"); meals.add("Chicken and Potatoes"); meals.add("Pasta with Meatballs");


        ListView mealList = (ListView) findViewById(R.id.listView);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, meals);

        mealList.setAdapter(adapter);

        final String[] name = {null};

        mealList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), meals.get(i), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtra("image", i);
                intent.putExtra("meal", meals.get(i));
                startActivity(intent);
            }
        });

        Button addBtn = (Button) findViewById(R.id.button);
        final EditText addEdit = (EditText) findViewById(R.id.editText);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                meals.add(addEdit.getText().toString());
                adapter.notifyDataSetChanged();
            }
        });

        Button cancleBtn = (Button) findViewById(R.id.button2);
        cancleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(meals.contains(name[0]))
                    return;
                else
                    meals.add(name[0]);
                adapter.notifyDataSetChanged();
            }
        });

        mealList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int which = i;
                AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
                dlg.setTitle("삭제?");
                dlg.setMessage(meals.get(i));
                name[0] = meals.get(i);
                dlg.setNegativeButton("취소", null);
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        meals.remove(which);
                        adapter.notifyDataSetChanged();
                    }
                });
                dlg.show();
                return true;
            }
        });
    }
}



