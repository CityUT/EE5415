package com.example.bmi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText vHeight, vWeight;
    Button submitButton;

    @Override
    protected void onStart() {
        super.onStart();
        loadPreferences();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 获取视图
        vHeight = findViewById(R.id.heightET);
        vWeight = findViewById(R.id.weightET);
        submitButton = findViewById(R.id.reportBtn);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String height = vHeight.getText().toString();
                String weight = vWeight.getText().toString();

                if (height.isEmpty() || weight.isEmpty()) {
                    Toast.makeText(MainActivity.this, R.string.bmi_warning, Toast.LENGTH_LONG).show();
                } else {
                    // 保存输入的身高和体重
                    savePreferences(height, weight);

                    // 启动报告活动
                    Intent intent = new Intent(MainActivity.this, ReportActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("height", height);
                    bundle.putString("weight", weight);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });

        Button aboutButton = findViewById(R.id.aboutBtn);
        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(R.string.about_button);
                builder.setMessage(R.string.about_msg);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 可选操作
                    }
                });
                builder.create().show();
            }
        });
    }

    // 保存数据到 SharedPreferences
    public void savePreferences(String h, String w) {
        SharedPreferences pref = getSharedPreferences("BMI", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("height", h);
        editor.putString("weight", w);
        editor.apply();
    }

    // 从 SharedPreferences 中加载数据
    public void loadPreferences() {
        SharedPreferences pref = getSharedPreferences("BMI", MODE_PRIVATE);
        vHeight.setText(pref.getString("height", "0"));
        vWeight.setText(pref.getString("weight", "0"));
    }
}