package com.example.test1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUp : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        btn_otp.setOnClickListener{

            progressBar.visibility = View.VISIBLE
            Handler().postDelayed({
                editText_name.setText("Shivam Singh")
                editText_account_no.setText("200012012085")
                progressBar.visibility = View.INVISIBLE
            }, 4000)
        }

        btn_create_pin.setOnClickListener{
            Toast.makeText(this, editText_name.toString(), Toast.LENGTH_SHORT)
            var myIntent = Intent(this, CreatePin::class.java)
            startActivity(myIntent)
//            if(editText_name.equals("") || editText_account_no.equals("")){
//                Toast.makeText(this, "Empty Field Detected", Toast.LENGTH_SHORT)
//            }else{
//                var myIntent = Intent(this, CreatePin::class.java)
//                startActivity(myIntent)
//            }
        }

    }
}
