package com.example.bmi

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.bmi.ui.theme.BMITheme
import androidx.appcompat.widget.Toolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class MainActivity : ComponentActivity() {
    lateinit var heightET:EditText
    lateinit var weightET:EditText
    fun savePreferences(h:String,w:String){
        val pref=getSharedPreferences("BMI", Context.MODE_PRIVATE)
        pref.edit().putString("height",h).apply()
        pref.edit().putString("weight",w).apply()
    }
    fun loadPreferences(){
        val pref=getSharedPreferences("BMI",Context.MODE_PRIVATE)
        heightET.setText(pref.getString("height","0"))
        weightET.setText(pref.getString("weight","0"))
    }
    fun about_BMI_Dialog(){
        MaterialAlertDialogBuilder(this,R.style.Theme_Material)
            .setTitle(R.string.about_btn)
            .setMessage(R.string.about_msg)
            .setPositiveButton("OK"){dialog,which->Toast.makeText(this@MainActivity,"OK",Toast.LENGTH_SHORT).show()
            }.show()
    }
    override fun onStart(){
        super.onStart()
        loadPreferences()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater=menuInflater
        inflater.inflate(R.menu.menu_options,menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar:Toolbar=findViewById(R.id.toolbar)
        registerForContextMenu(toolbar)
        heightET=findViewById(R.id.heightET)
        weightET=findViewById(R.id.weightET)
        registerForContextMenu(weightET)
        val reportBtn: Button =findViewById(R.id.reportBtn)
        reportBtn.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                val height=heightET.text.toString()
                val weight=weightET.text.toString()
                if(height==""||weight==""||height=="0"||weight=="0"){
                    Toast.makeText(this@MainActivity,R.string.bmi_warning,Toast.LENGTH_LONG).show()
//
                }else {
                    val intent = Intent(applicationContext, ReportActivity::class.java)
                    val bundle = Bundle()
                    bundle.putString("height", height)
                    bundle.putString("weight", weight)
                    intent.putExtras(bundle)
                    startActivity(intent)
                }
                savePreferences(height,weight)
                }

        })
        val aboutBtn : Button=findViewById(R.id.aboutBtn)
        aboutBtn.setOnClickListener{
            about_BMI_Dialog()
        }
    }
}

