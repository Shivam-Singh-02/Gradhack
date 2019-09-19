package com.example.test1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.biometric.BiometricConstants.ERROR_NEGATIVE_BUTTON
import androidx.biometric.BiometricPrompt

import com.example.test1.FingerprintHandler
import com.amazonaws.mobile.client.AWSMobileClient
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;
import com.amazonaws.mobileconnectors.lex.interactionkit.Response;
import com.amazonaws.mobileconnectors.lex.interactionkit.config.InteractionConfig;
import com.amazonaws.mobileconnectors.lex.interactionkit.ui.InteractiveVoiceView;
import com.amazonaws.auth.AWSCredentialsProvider
import com.amazonaws.mobile.auth.core.IdentityHandler
import com.amazonaws.mobile.auth.core.IdentityManager
import com.amazonaws.mobile.config.AWSConfiguration
import kotlinx.android.synthetic.main.activity_main.*







class Login : AppCompatActivity() {
    private var credentialsProvider: AWSCredentialsProvider? = null
    private var awsConfiguration: AWSConfiguration? = null
    private var biometricPrompt: BiometricPrompt? = null
    private var fingerprintHandler = FingerprintHandler(this)
    companion object {
        private val TAG: String = this::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AWSMobileClient.getInstance().initialize(this) {
            credentialsProvider = AWSMobileClient.getInstance().credentialsProvider
            awsConfiguration = AWSMobileClient.getInstance().configuration

            IdentityManager.getDefaultIdentityManager().getUserID(object : IdentityHandler {
                override fun handleError(exception: Exception?) {
                    Log.e(TAG, "Retrieving identity")
                }

                override fun onIdentityId(identityId: String?) {
                    Log.d(TAG, "Identity = $identityId")
                    val cachedIdentityId = IdentityManager.getDefaultIdentityManager().cachedUserID
                    // Do something with the identity here
                }
            })
        }.execute()

        init()

        loginbutton.setOnClickListener{

            var Username = username.text.toString()
            var Password = password.text.toString()
            if(Password.equals("Password")){
                var myIntent = Intent(this, NextActivity::class.java)
                myIntent.putExtra("username", Username)
                startActivity(myIntent)
            }
            else{
                Toast.makeText(getApplicationContext(),"Wrong Credentials",Toast.LENGTH_SHORT).show();
            }

            username.setText("")
            password.setText("")

        }

        selectFingerPrintButton.setOnClickListener{
            fingerprintHandler.setNextActivity(NextActivity())
            biometricPrompt = BiometricPrompt( this, fingerprintHandler.executor,  fingerprintHandler.callback )

            findViewById<View>(R.id.selectFingerPrintButton).setOnClickListener { view ->
                val promptInfo = fingerprintHandler.buildBiometricPrompt()
                var res = biometricPrompt!!.authenticate(fingerprintHandler.promptInfo)
            }
        }
    }

    fun init() {
        voiceInterface.setInteractiveVoiceListener(
        object : InteractiveVoiceView.InteractiveVoiceListener {
            override fun dialogReadyForFulfillment(
                slots: MutableMap<String, String>?,
                intent: String?
            ) {

                Log.d(TAG, "Dialog ready for fulfillment:\n\tIntent: $intent")
            }


            override fun onResponse(response: Response) {
                Log.d(TAG, "Bot response: ${response.textResponse}")
            }

            override fun onError(responseText: String, e: Exception) {
                Log.e(TAG, "Error: ${e.message}")
            }
        })
        with (voiceInterface.viewAdapter) {
            credentialsProvider = AWSMobileClient.getInstance().credentialsProvider
            setInteractionConfig(InteractionConfig("BankBot","v_one"))
            awsRegion = applicationContext.getString(R.string.aws_region)
            setCredentialProvider(credentialsProvider)
        }
    }

}
