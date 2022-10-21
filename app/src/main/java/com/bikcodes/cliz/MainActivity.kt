package com.bikcodes.cliz

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Switch
import androidx.appcompat.widget.SwitchCompat
import androidx.constraintlayout.widget.ConstraintLayout
import eightbitlab.com.blurview.RenderScriptBlur

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        animation()
//        blur()
        transparentstatusbar()
        tologin()





    }
    private fun animation() {
        val animation = AnimationUtils.loadAnimation(this, R.anim.animatelanding)
        val animation2 = AnimationUtils.loadAnimation(this, R.anim.animatelandingtwo)
        val landing = findViewById<ImageView>(R.id.landimage)
        val landingll = findViewById<LinearLayout>(R.id.landll2)
        val desc = findViewById<eightbitlab.com.blurview.BlurView>(R.id.blurViewers)
        val contdbtn = findViewById<Button>(R.id.opencontbtn)
        landing.startAnimation(animation)
        landingll.startAnimation(animation2)
        desc.startAnimation(animation)
        contdbtn.startAnimation(animation)
    }

    private fun tologin() {
        findViewById<Button>(R.id.opencontbtn).setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun blur() {
        val radius = 20f
        val decorView = window.decorView

        val windowBackground = decorView.background
        val blurViewer= findViewById<eightbitlab.com.blurview.BlurView>(R.id.blurViewers)
        val signupconstraintl= findViewById<ConstraintLayout>(R.id.landingconstraint)
        blurViewer.setupWith(signupconstraintl, RenderScriptBlur(this)) // or RenderEffectBlur
//            .setFrameClearDrawable(windowBackground) // Optional
            .setBlurRadius(radius)
        blurViewer.outlineProvider = ViewOutlineProvider.BACKGROUND
        blurViewer.clipToOutline = true

    }
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