package com.example.appsale19052023.presentation.view.activity

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.example.appsale19052023.R
import com.example.appsale19052023.common.AppSharePreference

class SplashActivity : AppCompatActivity() {

    private lateinit var lottieView: LottieAnimationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        lottieView = findViewById(R.id.lottie_animation_view)

        lottieView.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) { }
            override fun onAnimationCancel(animation: Animator) { }
            override fun onAnimationRepeat(animation: Animator) { }
            override fun onAnimationEnd(animation: Animator) {
                val token = AppSharePreference(this@SplashActivity).getString(AppSharePreference.TOKEN_KEY)
                val intent: Intent
                if (token.isNullOrEmpty()) {
                    intent = Intent(this@SplashActivity, SignInActivity::class.java)
                    startActivity(intent)
                } else {
                    intent = Intent(this@SplashActivity, ProductActivity::class.java)
                    startActivity(intent)
                }
                finish()
            }
        })
    }
}