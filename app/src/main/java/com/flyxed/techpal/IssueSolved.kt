package com.flyxed.techpal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import android.view.animation.AnimationUtils
import android.view.View

class IssueSolved : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_solved)

        // Load animations
        val slamAndWobbleAnimation = AnimationUtils.loadAnimation(this, R.anim.slam_wobble_animation)
        val fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in)

        // Apply animation to ImageView
        val imageView = findViewById<ImageView>(R.id.imageView)
        imageView.startAnimation(slamAndWobbleAnimation)

        // Apply animation to text elements and buttons after the slam and wobble animation finishes
        slamAndWobbleAnimation.setAnimationListener(object : android.view.animation.Animation.AnimationListener {
            override fun onAnimationStart(animation: android.view.animation.Animation?) {}

            override fun onAnimationEnd(animation: android.view.animation.Animation?) {
                // Apply the fade-in animation to each text element and button
                val textView1 = findViewById<TextView>(R.id.Title)
                textView1.visibility = View.VISIBLE
                textView1.startAnimation(fadeInAnimation)

                val textView2 = findViewById<TextView>(R.id.subText1)
                textView2.visibility = View.VISIBLE
                textView2.startAnimation(fadeInAnimation)

                val button1 = findViewById<Button>(R.id.rh)
                button1.visibility = View.VISIBLE
                button1.startAnimation(fadeInAnimation)
            }

            override fun onAnimationRepeat(animation: android.view.animation.Animation?) {}
        })

        // Set OnClickListener for the return home button
        val returnHome: Button = findViewById(R.id.rh)
        returnHome.setOnClickListener {
            // Create an Intent to start MainActivity
            val intent = Intent(this@IssueSolved, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
