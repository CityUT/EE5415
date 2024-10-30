package com.example.bmi

import android.icu.text.DecimalFormat
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ReportActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)
        val resultTV:TextView=findViewById(R.id.report_result)
        val adviceTV:TextView=findViewById(R.id.report_advice)
        val resultImage: ImageView =findViewById(R.id.report_image)
        val bundle=intent.extras
        val height=java.lang.Double.parseDouble(bundle!!.getString("height")!!)/100
        val weight=java.lang.Double.parseDouble(bundle.getString("weight")!!)
        val bmi=weight/(height*height)
        val nf=DecimalFormat("0.00")
        val bmiString=getString(R.string.bmi_result)+" "+nf.format(bmi)
        resultTV.setText(bmiString)
        if(bmi>25){
            resultImage.setImageResource(R.drawable.bot_fat)
            adviceTV.setText(R.string.advice_heavy)
        }else if(bmi<20){
            resultImage.setImageResource(R.drawable.bot_thin)
            adviceTV.setText(R.string.advice_light)
        }else{
            resultImage.setImageResource(R.drawable.bot_fit)
            adviceTV.setText(R.string.advice_average)
        }
        enableEdgeToEdge()

    }
}