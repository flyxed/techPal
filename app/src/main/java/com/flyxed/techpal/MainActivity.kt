package com.flyxed.techpal

import android.os.Bundle
import androidx.activity.ComponentActivity
import android.widget.Button
import android.content.Intent


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Find  buttons by their IDs
        val computerWontStartButton: Button = findViewById(R.id.button_cws)
        val computerRunningSlowButton: Button = findViewById(R.id.button_crs)
        val monitorIsBlankButton: Button = findViewById(R.id.button_mib)
        val internetIsSlowButton: Button = findViewById(R.id.button_iss)
        val computerCrashesButton: Button = findViewById(R.id.button_cc)

        // Set OnClickListener for each button
        computerWontStartButton.setOnClickListener {

            // Create an Intent to start NewActivity
            val intent = Intent(this@MainActivity, ComputerWontStart::class.java)
            startActivity(intent)
        }

        computerRunningSlowButton.setOnClickListener {

            // Create an Intent to start NewActivity
            val intent = Intent(this@MainActivity, ComputerRunningSlow::class.java)
            startActivity(intent)
        }

        monitorIsBlankButton.setOnClickListener {

            // Create an Intent to start NewActivity
            val intent = Intent(this@MainActivity, MonitorIsBlank::class.java)
            startActivity(intent)
        }

        internetIsSlowButton.setOnClickListener {

            // Create an Intent to start NewActivity
            val intent = Intent(this@MainActivity, InternetIsSlow::class.java)
            startActivity(intent)
        }

        computerCrashesButton.setOnClickListener {

            // Create an Intent to start NewActivity
            val intent = Intent(this@MainActivity, ComputerCrashes::class.java)
            startActivity(intent)
        }
    }
}