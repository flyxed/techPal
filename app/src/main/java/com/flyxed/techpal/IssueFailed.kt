package com.flyxed.techpal

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import android.net.Uri
import android.view.animation.AnimationUtils
import android.widget.ImageView

class IssueFailed : ComponentActivity() {

    // Variable to store issue type in SharedPreferences
    private val issuetype = "issue_type"
    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_failed)

        // Get imageView
        imageView = findViewById(R.id.imageView)

        // START IMAGE WOBBLE
        // Load wobble animation
        val wobbleAnimation = AnimationUtils.loadAnimation(this, R.anim.wobble_animation)
        // Apply wobble animation to ImageView
        imageView.startAnimation(wobbleAnimation)
        // END IMAGE WOBBLE

        // Retrieve the issue type from SharedPreferences
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val issueType = sharedPreferences.getString(issuetype, "")

        // Set the web URL depending on the issue type
        val webUrl = when (issueType) {
            "CWS" -> "https://www.google.com/search?q=Computer+wont+start"
            "CRS" -> "https://www.google.com/search?q=Computer+running+slow"
            "MIB" -> "https://www.google.com/search?q=Monitor+is+blank"
            "IIS" -> "https://www.google.com/search?q=Internet+is+slow"
            "CKC" -> "https://www.google.com/search?q=Computer+crashing"
            else -> ""
        }

        val searchButton: Button = findViewById(R.id.if_search)

        // Set OnClickListener for 'Search'
        searchButton.setOnClickListener{
            // Open web browser using the URL saved via webUrl
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(webUrl))
            startActivity(intent)
        }


        val returnHome: Button = findViewById(R.id.if_rh)

        returnHome.setOnClickListener {
            // Create an Intent to start NewActivity
            val intent = Intent(this@IssueFailed, MainActivity::class.java)
            startActivity(intent)
        }

    }
}