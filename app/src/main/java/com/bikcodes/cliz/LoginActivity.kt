package com.bikcodes.cliz

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import java.util.concurrent.TimeUnit


class LoginActivity : AppCompatActivity() {
    val mAuth = FirebaseAuth.getInstance()
    lateinit var verificationId : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        findViewById<Button>(R.id.phonebtn).setOnClickListener {
            val phoneNumber = findViewById<EditText>(R.id.phonebox).text.toString()
            otppartone(phoneNumber)
            findViewById<Button>(R.id.verifyotpbtn).setOnClickListener {
                val otp = findViewById<EditText>(R.id.otpbox).text.toString()
                verifyCode(otp);
            }
        }
//        blur()
        transparentstatusbar()
    }

    private fun verifyCode(code: String) {
        val credential = PhoneAuthProvider.getCredential(verificationId, code)
        signInWithCredential(credential)
    }


    private fun otppartone(phoneNumber: String) {

            if (TextUtils.isEmpty(phoneNumber)) {
                Toast.makeText(
                    this,
                    "Please enter a valid phone number.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val phone = "+91" + phoneNumber

                sendVerificationCode(phone)
            }

    }

    private fun sendVerificationCode(phone: String) {



        val mCallBack = object : OnVerificationStateChangedCallbacks() {

            override fun onCodeSent(s: String, forceResendingToken: ForceResendingToken) {
                super.onCodeSent(s, forceResendingToken)

                verificationId = s
            }

            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {

                val code = phoneAuthCredential.smsCode

                if (code != null) {

                    val edtOTP = findViewById<EditText>(R.id.otpbox)
                    edtOTP.setText(code)
                    verifyCode(code)

                }

            }
        override fun onVerificationFailed(e: FirebaseException) {

                Toast.makeText(baseContext, e.toString(), Toast.LENGTH_SHORT).show()
            }
        }
        val options = PhoneAuthOptions.newBuilder(mAuth)
            .setPhoneNumber(phone) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this) // Activity (for callback binding)
            .setCallbacks(mCallBack) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun signInWithCredential(credential: PhoneAuthCredential) {
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val i = Intent(this, HomeActivity::class.java)
                    startActivity(i)
                    finish()
                } else {
                    Toast.makeText(this, task.exception!!.message, Toast.LENGTH_LONG)
                        .show()
                }
            }
    }


    //    private fun blur() {
//        val radius = 5f
//        val decorView = window.decorView
//
//        val windowBackground = decorView.background
//        val blurViewer= findViewById<eightbitlab.com.blurview.BlurView>(R.id.blurViewers)
//        val signupconstraintl= findViewById<ConstraintLayout>(R.id.landingconstraint)
//        blurViewer.setupWith(signupconstraintl, RenderScriptBlur(this)) // or RenderEffectBlur
////            .setFrameClearDrawable(windowBackground) // Optional
//            .setBlurRadius(radius)
//        blurViewer.outlineProvider = ViewOutlineProvider.BACKGROUND
//        blurViewer.clipToOutline = true
//
//    }
    private fun transparentstatusbar() {
        supportActionBar?.hide()
        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
            statusBarColor = Color.TRANSPARENT
        }
    }
}