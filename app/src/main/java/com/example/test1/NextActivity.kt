package com.example.test1

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_next.*

class NextActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_next)
        val User = intent.getStringExtra("username")
        text_user.setText(User)
        btn_logout.setOnClickListener{
            finish();
            System.exit(0);
        }

        btn_facebook.setOnClickListener{
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse("https://www.facebook.com/yeahShivam")
            startActivity(openURL)
        }

        btn_instagram.setOnClickListener{
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse("https://www.instagram.com/_.jack__of__all__trades._")
            startActivity(openURL)
        }
    }
}



