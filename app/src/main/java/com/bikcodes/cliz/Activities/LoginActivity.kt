package com.bikcodes.cliz.Activities

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.bikcodes.cliz.R
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
//        findViewById<Button>(R.id.phonebtn).setOnClickListener {
//            val phoneNumber = findViewById<EditText>(R.id.phonebox).text.toString()
//            otppartone(phoneNumber)
//            findViewById<Button>(R.id.verifyotpbtn).setOnClickListener {
//                val otp = findViewById<EditText>(R.id.otpbox).text.toString()
//                verifyCode(otp);
//            }
//        }
//        blur()
        transparentstatusbar()
        animation()
        nightchanger()
        numberotpchanger()
        otpdetailchanger()

    }

    private fun otpdetailchanger() {
                    findViewById<Button>(R.id.verifyotpbtn).setOnClickListener {
                val otp = findViewById<EditText>(R.id.pin_view2).text.toString()
                verifyCode(otp);
            }
    }

    private fun numberotpchanger() {
        findViewById<Button>(R.id.opencontbtn2).setOnClickListener {
            val phoneNumber = findViewById<EditText>(R.id.loginphonebox).text.toString()
            val loginll1 = findViewById<LinearLayout>(R.id.loginll1)
            val loginll3 = findViewById<LinearLayout>(R.id.loginll3)


            val animation1 = AnimationUtils.loadAnimation(this, R.anim.animateotpprog)
            val animation2 = AnimationUtils.loadAnimation(this, R.anim.goneloginllone)
            val animation3 = AnimationUtils.loadAnimation(this, R.anim.getloginllthree)
            loginll1.startAnimation(animation2)
            animation2.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {}
                override fun onAnimationEnd(animation: Animation) {
                    loginll1.translationY = 2000000f
                    otpitemsvisible(true)
                    loginll3.startAnimation(animation3)
                    val loginimg = findViewById<ImageView>(R.id.loginimg)
                    loginimg.startAnimation(animation1)

                }
                override fun onAnimationRepeat(animation: Animation) {}
            })


            findViewById<TextView>(R.id.alternatelogintext).setTextColor(resources.getColor(R.color.white))

            otppartone(phoneNumber)

        }
    }

    private fun otpitemsvisible(b: Boolean) {
        if(b) {
            val loginll1 = findViewById<LinearLayout>(R.id.loginll1)
            val loginll3 = findViewById<LinearLayout>(R.id.loginll3)
            loginll3.visibility = View.VISIBLE
            loginll1.visibility = View.GONE
        }
    }


    private fun nightchanger() {
        val opennightswitch = findViewById<SwitchCompat>(R.id.opennightswitch)
        opennightswitch.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton, b ->
            run {
                if (b) {
                    animationmasknight()
                    val isNight = true
                    val sharedPrefs: SharedPreferences = getSharedPreferences("nightmode", MODE_PRIVATE)
                    sharedPrefs.edit().putBoolean("NightMode", isNight).apply()

                }else{
                    animationmasklight()
                    val isNight = false
                    val sharedPrefs: SharedPreferences = getSharedPreferences("nightmode", MODE_PRIVATE)
                    sharedPrefs.edit().putBoolean("NightMode", isNight).apply()
                }
            }
        })
    }
    private fun animationmasklight() {
        val animation = AnimationUtils.loadAnimation(this, R.anim.lightmodeexpandcircle)
        val openmaska = findViewById<ImageView>(R.id.openmaska)
        val openmaskb = findViewById<ImageView>(R.id.openmaskb)
        findViewById<ImageView>(R.id.line1).setImageDrawable(ContextCompat.getDrawable(this,
            R.drawable.linelight
        ))
        findViewById<ImageView>(R.id.line2).setImageDrawable(ContextCompat.getDrawable(this,
            R.drawable.linelight
        ))
        findViewById<TextView>(R.id.alternatelogintext).setTextColor(resources.getColor(R.color.black))
        openmaska.startAnimation(animation)
        openmaskb.startAnimation(animation)
    }

    private fun animationmasknight() {
        val animation = AnimationUtils.loadAnimation(this, R.anim.nightmodeexpandcircle)
        val openmaska = findViewById<ImageView>(R.id.openmaska)
        val openmaskb = findViewById<ImageView>(R.id.openmaskb)
        openmaska.isVisible = true
        openmaskb.isVisible = true
        findViewById<ImageView>(R.id.line1).setImageDrawable(ContextCompat.getDrawable(this,
            R.drawable.line
        ))
        findViewById<ImageView>(R.id.line2).setImageDrawable(ContextCompat.getDrawable(this,
            R.drawable.line
        ))
        findViewById<TextView>(R.id.alternatelogintext).setTextColor(resources.getColor(R.color.white))
        openmaska.startAnimation(animation)
        openmaskb.startAnimation(animation)
    }

    private fun animation() {
        val animation = AnimationUtils.loadAnimation(this, R.anim.animatelogin)
        val animation2 = AnimationUtils.loadAnimation(this, R.anim.animatelogintwo)
        val logininfo = findViewById<ImageView>(R.id.logininfo)
        val loginimg = findViewById<ImageView>(R.id.loginimg)
        val loginswitch = findViewById<SwitchCompat>(R.id.opennightswitch)
        val loginll1 = findViewById<LinearLayout>(R.id.loginll1)
        val loginll2 = findViewById<LinearLayout>(R.id.loginll2)
        logininfo.startAnimation(animation)
        loginimg.startAnimation(animation)
        loginswitch.startAnimation(animation)
        loginll1.startAnimation(animation)
        loginll2.startAnimation(animation2)

    }
//
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

                    val edtOTP = findViewById<com.chaos.view.PinView>(R.id.pin_view2)
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
                    //Task on Verification successfully completed
                    val loginll3 = findViewById<LinearLayout>(R.id.loginll3)
                    val loginll4 = findViewById<LinearLayout>(R.id.loginll4)
                    val loginll2 = findViewById<LinearLayout>(R.id.loginll2)
                    val animation1 = AnimationUtils.loadAnimation(this, R.anim.animatedetailprog)
                    val animation2 = AnimationUtils.loadAnimation(this, R.anim.goneloginllone)
                    val animation3 = AnimationUtils.loadAnimation(this, R.anim.getloginllthree)
                    loginll3.startAnimation(animation2)
                    animation2.setAnimationListener(object : Animation.AnimationListener {
                        override fun onAnimationStart(animation: Animation) {}
                        override fun onAnimationEnd(animation: Animation) {
                            loginll3.translationX = 3000000f
                            loginll2.translationX = 3000000f
                            loginll4.visibility = View.VISIBLE
                            loginll4.startAnimation(animation3)
                            val loginimg = findViewById<ImageView>(R.id.loginimg)
                            loginimg.startAnimation(animation1)
                            findViewById<Button>(R.id.doneloginbtn).setOnClickListener{
                                startActivity(Intent(baseContext, HomeActivity::class.java))
                            }

                        }
                        override fun onAnimationRepeat(animation: Animation) {}
                    })
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