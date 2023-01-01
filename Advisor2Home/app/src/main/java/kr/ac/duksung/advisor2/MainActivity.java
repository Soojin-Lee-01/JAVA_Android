package kr.ac.duksung.advisor2;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;


public class MainActivity extends AppCompatActivity {
    RadioButton rdoWeb, rdoNative;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        super .onCreateOptionsMenu(menu);
        MenuInflater mInflator = getMenuInflater();
        mInflator.inflate(R.menu.menu_option, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.native_menu:
                Intent intent = new Intent(getApplicationContext(), ProfessorActivity.class);
                startActivity(intent);
                return true;
            case R.id.web_menu:
                Uri uri = Uri.parse("http://203.252.213.36:8080/FinalProject/advisorForm.jsp");
                Intent intent1 = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent1);
        }
        return false;
    }
}



