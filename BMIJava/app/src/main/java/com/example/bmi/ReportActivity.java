package com.example.bmi;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class ReportActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        Bundle bundle = getIntent().getExtras();
        double height = Double.parseDouble(bundle.getString("height")) / 100;
        double weight = Double.parseDouble(bundle.getString("weight"));
        double bmi = weight / (height * height);
        DecimalFormat nf = new DecimalFormat("0.00");
        TextView result = (TextView) findViewById(R.id.report_result);
        result.setText("Your BMI Value is"+" "+nf.format(bmi));
        //Give health advice
        ImageView image = (ImageView) findViewById(R.id.report_image);
        TextView advice = (TextView) findViewById(R.id.report_advice);
        if (bmi > 25) {
            image.setImageResource(R.drawable.bot_fat);
            advice.setText("You should be on diet!");
        } else if (bmi < 20){
            image.setImageResource(R.drawable.bot_thin);
            advice.setText("You need more calories!");
        } else {
            image.setImageResource(R.drawable.bot_fit);
            advice.setText("You look great!");
        }
    }
}