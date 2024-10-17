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
    protected void onStart(){
        super.onStart();
        loadPreferences();}


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //-get views
        vHeight = (EditText) findViewById(R.id.heightET);
        vWeight = (EditText) findViewById(R.id.weightET);
        submitButton = (Button) findViewById(R.id.reportBtn);

        submitButton.setOnClickListener(new View.OnClickListener(){
            @Override
                    public void onClick(View v){
                String height = vHeight.getText().toString();
                String weight = vWeight.getText().toString();

                if (height.equals("") || weight.equals("")){
                    Toast.makeText(MainActivity.this, R.string.bmi_warning, Toast.LENGTH_LONG).show();
                }else{
                    Intent intent = new Intent(getApplicationContext(), ReportActivity.class);

                    Bundle bundle = new Bundle();
                    bundle.putString("height", height);
                    bundle.putString("weight", weight);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

                savePreferences(height,weight);



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
                        // Optional action
                    }
                });
                builder.create();
                builder.show();
            }
        });
    }


public void savePreferences(String h, String w){
    SharedPreferences pref = getSharedPreferences("BMI", MODE_PRIVATE);
    pref.edit().putString("height", h).apply();
    pref.edit().putString("weight", w).apply();
}
public void loadPreferences(){
        SharedPreferences pref = getSharedPreferences("BMI", MODE_PRIVATE);
        vHeight.setText(pref.getString("height","0"));
        vWeight.setText(pref.getString("weight","0"));
    }
}

