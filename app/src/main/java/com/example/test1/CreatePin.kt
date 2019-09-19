package com.example.test1

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_create_pin.*
import android.content.SharedPreferences




class CreatePin : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_pin)

        var pin = editText_pin.text
        btn_pin.setOnClickListener{
            if(editText_pin.equals("")){
                Toast.makeText(this,"Enter Pin",Toast.LENGTH_SHORT)
            }else{
                val sharedPref = this?.getPreferences(Context.MODE_PRIVATE) ?: return@setOnClickListener



            }
        }


    }
}
