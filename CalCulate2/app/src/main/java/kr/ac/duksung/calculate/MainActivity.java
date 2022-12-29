package kr.ac.duksung.calculate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText edtNum1, edtNum2;
    RadioGroup rGroup1;
    Button btnCalc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("입력");

        edtNum1 = findViewById(R.id.editText1);
        edtNum2 = findViewById(R.id.editText2);
        rGroup1 = findViewById(R.id.rGroup);
        btnCalc = findViewById(R.id.button);

        btnCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String num1 = edtNum1.getText().toString();
                String num2 = edtNum2.getText().toString();

                if(num1.equals("")) {
                    Toast.makeText(getApplicationContext(), "첫번째 정수 입력", Toast.LENGTH_SHORT).show();
                }
                else if(rGroup1.getCheckedRadioButtonId() == -1){
                    Toast.makeText(getApplicationContext(), "연산자 선택", Toast.LENGTH_SHORT).show();
                }
                else if (num2.equals("")){
                    Toast.makeText(getApplicationContext(), "두번째 정수 입력", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(getApplicationContext(), CalculateActivity.class);

                    intent.putExtra("num1", num1);
                    intent.putExtra("num2", num2);

                    switch (rGroup1.getCheckedRadioButtonId()) {
                        case R.id.radioButton1:
                            intent.putExtra("operator", "+");
                            break;
                        case R.id.radioButton2:
                            intent.putExtra("operator", "-");
                            break;
                        case R.id.radioButton3:
                            intent.putExtra("operator", "*");
                            break;
                        case R.id.radioButton4:
                            intent.putExtra("operator", "/");
                    }
                    startActivity(intent);
                }
            }
        });
    }
}
