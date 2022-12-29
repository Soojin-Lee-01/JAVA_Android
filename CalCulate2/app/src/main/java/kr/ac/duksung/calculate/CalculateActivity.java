package kr.ac.duksung.calculate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CalculateActivity extends AppCompatActivity {

    Button buttonResult;
    TextView resultText;
    Integer result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate);
        setTitle("계산");

        buttonResult = findViewById(R.id.backBtn);
        resultText = findViewById(R.id.calResult);

        Intent intent = getIntent();

        final String num1 = intent.getStringExtra("num1");
        final String num2 = intent.getStringExtra("num2");
        final String operator = intent.getStringExtra("operator");

        if (operator.equals("+")) {
            result = Integer.parseInt(num1) + Integer.parseInt(num2);
        }
        if (operator.equals("-")) {
            result = Integer.parseInt(num1) - Integer.parseInt(num2);
        }
        if (operator.equals("*")) {
            result = Integer.parseInt(num1) * Integer.parseInt(num2);
        }
        if (operator.equals("/")) {
            result = Integer.parseInt(num1) / Integer.parseInt(num2);
        }
        resultText.setText("(" + num1 + ")"  + operator  + "(" + num2 + ")" + "=" + result.toString());

        buttonResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}