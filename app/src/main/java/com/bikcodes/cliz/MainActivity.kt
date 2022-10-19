package com.bikcodes.cliz

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.view.WindowManager
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import eightbitlab.com.blurview.RenderScriptBlur

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        blur()
        transparentstatusbar()
        tologin()


    }

    private fun tologin() {
        findViewById<Button>(R.id.opencontdbtn).setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun blur() {
        val radius = 5f
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