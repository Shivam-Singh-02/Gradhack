package com.example.test1


import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_first_menu.*
import com.amazonaws.regions.Regions
import com.amazonaws.auth.CognitoCachingCredentialsProvider

import android.media.MediaPlayer
import android.util.Log
import java.io.IOException
import android.media.AudioAttributes
import android.view.KeyEvent
import android.widget.Button
import android.widget.Toast
import com.amazonaws.services.polly.AmazonPollyPresigningClient
import com.amazonaws.services.polly.model.OutputFormat
import com.amazonaws.services.polly.model.SynthesizeSpeechPresignRequest


class FirstMenu : AppCompatActivity() {


    val buttons = arrayOf(R.id.login_button_first_menu,R.id.signup_button)
    var index: Int = 0;
    var buttonSelected = ""
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_VOLUME_UP -> {

                Toast.makeText(this,index.toString(),Toast.LENGTH_SHORT).show()
                findViewById<Button>(buttons[index]).setBackgroundColor(3)
                findViewById<Button>(buttons[(index+1)%2]).setBackgroundColor(0)
                buttonSelected = findViewById<Button>(buttons[index]).text.toString()
                Log.d("ChessApp",buttonSelected )
                index = (index+1) % 2;
                return true
            }
            KeyEvent.KEYCODE_VOLUME_DOWN -> {
                buttonSelected = buttons[index].toString()
                Log.d("ChessApp",buttonSelected )
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_menu)




        login_button_first_menu.setOnClickListener {
//             var myIntent = Intent(this, Login::class.java)
//             startActivity(myIntent)
            val credentialsProvider = CognitoCachingCredentialsProvider(
                applicationContext,
                "us-east-1:94319f4e-b9f8-48af-a52c-48ee4ca1348a", // Identity pool ID
                Regions.US_EAST_1 // Region
            )
            val client = AmazonPollyPresigningClient(credentialsProvider)


            var synthesizeSpeechPresignRequest = SynthesizeSpeechPresignRequest()
//            // Set the text to synthesize.
                .withText("Welcome to the coolest bank. You can Login or Sign up.")
//            // Select voice for synthesis.
                .withVoiceId("Raveena") // "Joanna"
//            // Set format to MP3.
                .withOutputFormat(OutputFormat.Mp3)
            val mediaPlayer = MediaPlayer()

            mediaPlayer.setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
            )
            val presignedSynthesizeSpeechUrl =
                client.getPresignedSynthesizeSpeechUrl(synthesizeSpeechPresignRequest)


            try {
                // Set media player's data source to previously obtained URL.
                mediaPlayer.setDataSource(presignedSynthesizeSpeechUrl.toString())
            } catch (e: IOException) {
                Log.d(

                    "Unable to set data source for the media player! ", e.message
                )
            }

            mediaPlayer.prepareAsync()
            mediaPlayer.setOnPreparedListener { mp -> mp.start() }
            mediaPlayer.setOnCompletionListener { mp -> mp.release() }

        }

        signup_button.setOnClickListener {
            var myIntent = Intent(this, SignUp::class.java)
            startActivity(myIntent)
        }
    }

}