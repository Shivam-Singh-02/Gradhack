package com.example.test1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_first_menu.*

class FirstMenu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_menu)

        login_button_first_menu.setOnClickListener{
            var myIntent = Intent(this, Login::class.java)
            startActivity(myIntent)
        }


        signup_button.setOnClickListener{
            var myIntent = Intent(this, SignUp::class.java)
            startActivity(myIntent)
        }
    }
}
