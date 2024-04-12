package com.flyxed.techpal

import android.content.Context
import android.content.Intent
import android.widget.TextView
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import android.widget.ImageView

import android.view.animation.AnimationUtils
import android.view.View
import java.util.Random

class InternetIsSlow : ComponentActivity() {
    private lateinit var palSays: Array<String>
    private lateinit var steps: Array<String>
    private lateinit var images: Array<Int> // Array of drawable resource IDs
    private var currentStepIndex: Int = 0
    private lateinit var stepTextView: TextView
    private lateinit var imageView: ImageView
    private val random = Random()

    // Variable to store issue type in SharedPreferences
    private val issuetype = "issue_type"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_iss)

        // == GRABBING VARIABLES FROM XML
        // Get imageView
        imageView = findViewById(R.id.imageView)
        // Get subText1 and 2
        val subText1 = findViewById<TextView>(R.id.subText1)
        val subText2 = findViewById<TextView>(R.id.subText2)
        // Get step
        stepTextView = findViewById(R.id.step)
        // Get issueSolved button ("That Helped!" button)
        val issueSolved: Button = findViewById(R.id.th)

        // Define array of palSays
        palSays = arrayOf(
            "Hmm, that didn't work, try this instead!",
            "If that didn't work, what about this solution?",
            "Maybe this will solve your problem!",
            "Still no luck? Let's try this solution!",
            "This is a tricky one... Maybe this will help!"
        )

        // Define array of images
        images = arrayOf(
            R.drawable.techpal_icon1,
            R.drawable.techpal_icon2,
            R.drawable.techpal_icon3,
            R.drawable.techpal_icon4,
            R.drawable.techpal_icon5
        )

        // Define array of steps
        steps = arrayOf(
            "Step 1: Check other devices. Determine if the slow internet is affecting all devices connected to the network or just one.",
            "Step 2: Restart the router and modem. Power cycle both the router and modem by unplugging them from the power source, waiting for 30 seconds, and then plugging them back in.",
            "Step 3: If you're using a wired connection, try connecting directly to the router using an Ethernet cable to see if the issue persists.",
            "Step 4: If you're using a Wi-Fi connection, move closer to the router to improve signal strength.",
            "Step 5: Check for interference. Ensure that the router is not placed near devices that may cause interference, such as cordless phones, microwave ovens, or other electronic devices.",
            "Step 6: Check if there are any firmware updates available for the router and apply them if necessary.",
            "Step 7: Clear the cache and cookies in your web browser to remove temporary files that may be slowing down internet performance.",
            "Step 8: Limit background processes. Close any unnecessary applications and background processes that may be consuming bandwidth.",
            "Step 9: Contact your internet service provider (ISP) to inquire about any bandwidth throttling or network congestion issues in your area.",
            "Step 10: Perform a speed test. Use online speed testing tools to measure your internet connection speed and compare it to the expected speed provided by your ISP."
        )

        // Set a random image to the ImageView when the activity is loaded
        val randomImageIndex = random.nextInt(images.size)
        imageView.setImageResource(images[randomImageIndex])

        // START IMAGE WOBBLE
        // Load wobble animation
        val wobbleAnimation = AnimationUtils.loadAnimation(this, R.anim.wobble_animation)
        // Apply wobble animation to ImageView
        imageView.startAnimation(wobbleAnimation)
        // END IMAGE WOBBLE

        // START ISSUETYPE
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(issuetype, "ISS")
        editor.apply()
        // END ISSUETYPE

        // START BUTTONS
        issueSolved.setOnClickListener {
            // Create an Intent to start NewActivity
            val intent = Intent(this@InternetIsSlow, IssueSolved::class.java)
            startActivity(intent)
        }


        // Get reference to the TextView
        stepTextView = findViewById(R.id.step)
        updateStepText()

        // Get reference to the "Didn't work..." button
        val didntWorkButton: Button = findViewById(R.id.dw)

        // Set OnClickListener for the "Didn't work..." button
        didntWorkButton.setOnClickListener {
            // Increment the currentStepIndex
            currentStepIndex++

            // Check if currentStepIndex is within bounds of the array
            if (currentStepIndex < steps.size) {
                // Update the TextView with the next step
                updateStepText()
            } else {
                // If there are no more steps, go to IssueFailed
                val intent = Intent(this@InternetIsSlow, IssueFailed::class.java)
                startActivity(intent)
            }

            // Update subText1
            val randomIndex = random.nextInt(palSays.size)
            subText1.text = palSays[randomIndex]

            // Set a random image to the ImageView
            setRandomImageWithAnimation()

            // Hide subText2
            subText2.visibility = View.GONE

            // END BUTTONS
        }
    }

    // Update the TextView with the current step text
    private fun updateStepText() {
        stepTextView.text = steps[currentStepIndex]
    }

    // Function to set a random image to the ImageView with animation
    private fun setRandomImageWithAnimation() {
        val randomImageIndex = random.nextInt(images.size)
        val newImageResource = images[randomImageIndex]

        // Remove previous animations
        imageView.clearAnimation()

        // Load scale animation
        val scaleAnimation = AnimationUtils.loadAnimation(this, R.anim.scale_animation)
        // Load wobble animation
        val wobbleAnimation = AnimationUtils.loadAnimation(this, R.anim.wobble_animation)

        // Create an AnimationSet to play both animations simultaneously
        val animationSet = android.view.animation.AnimationSet(true)
        animationSet.addAnimation(scaleAnimation)
        animationSet.addAnimation(wobbleAnimation)

        // Apply animation set to ImageView
        imageView.startAnimation(animationSet)

        // Set the new image resource after animation
        imageView.setImageResource(newImageResource)
    }

}
