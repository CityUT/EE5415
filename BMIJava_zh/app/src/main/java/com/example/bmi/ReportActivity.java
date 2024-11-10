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
        if (bundle != null) {
            // 获取身高和体重
            double height = Double.parseDouble(bundle.getString("height")) / 100;
            double weight = Double.parseDouble(bundle.getString("weight"));
            double bmi = weight / (height * height);

            // 格式化 BMI 值
            DecimalFormat nf = new DecimalFormat("0.00");
            TextView result = findViewById(R.id.report_result);
            result.setText(getString(R.string.bmi_result) + " " + nf.format(bmi));

            // 健康建议
            ImageView image = findViewById(R.id.report_image);
            TextView advice = findViewById(R.id.report_advice);

            if (bmi > 25) {
                image.setImageResource(R.drawable.bot_fat);
                advice.setText(R.string.advice_heavy);
            } else if (bmi < 20) {
                image.setImageResource(R.drawable.bot_thin);
                advice.setText(R.string.advice_light);
            } else {
                image.setImageResource(R.drawable.bot_fit);
                advice.setText(R.string.advice_average);
            }
        }
    }
}