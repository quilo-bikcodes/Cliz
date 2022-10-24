package com.bikcodes.cliz.Activities

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.bikcodes.cliz.Framents.CouponFragment
import com.bikcodes.cliz.Framents.FitFragment
import com.bikcodes.cliz.Framents.MapsFragment
import com.bikcodes.cliz.Framents.ProfileFragment
import com.bikcodes.cliz.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        transparentstatusbar()
//        blur()
        navbar()
    }

    private fun navbar() {
        loadFragment(MapsFragment())
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home -> loadFragment(MapsFragment())
                R.id.coupon -> loadFragment(CouponFragment())
                R.id.fit -> loadFragment(FitFragment())
                R.id.profile -> loadFragment(ProfileFragment())
            }
            true
        }
    }
    private  fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.homeframe,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
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
//    private fun blur() {
//        val radius = 20f
//        val decorView = window.decorView
//
//        val windowBackground = decorView.background
//        val blurViewer= findViewById<eightbitlab.com.blurview.BlurView>(R.id.blurViewer)
//        val signupconstraintl= findViewById<ConstraintLayout>(R.id.homeconstraint)
//        blurViewer.setupWith(signupconstraintl, RenderScriptBlur(this)) // or RenderEffectBlur
////            .setFrameClearDrawable(windowBackground) // Optional
//            .setBlurRadius(radius)
//        blurViewer.outlineProvider = ViewOutlineProvider.BACKGROUND
//        blurViewer.clipToOutline = true
//
//    }
}