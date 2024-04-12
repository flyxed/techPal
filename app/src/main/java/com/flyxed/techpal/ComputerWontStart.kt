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

class ComputerWontStart : ComponentActivity() {
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
        setContentView(R.layout.activity_cws)

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
            "Step 1: Check if the power cable is properly connected to both the computer and the power outlet.",
            "Step 2: Test the power outlet is working by plugging in another device.",
            "Step 3: Check if the power button on your computer is functioning, sometimes dust or debris can prevent it working.",
            "Step 4: Check for loose connections! Ensure all internal components, such as the RAM, graphics card and power pins are properly seated and secured",
            "Step 5: Ensure the monitor is turned on and properly connected to the PC. If you have a graphics card, be sure to connect the HDMI/DP cable to graphics card, not onboard graphics.",
            "Step 6: Clear the CMOS by popping out the silver circular battery located on your motherboard. If you're not sure, refer to your motherboard manual.",
            "Step 7: Remove all non-essential components and peripherals which may be causing issues.",
            "Step 8: Test the RAM by removing and reseating the RAM modules one at a time. Note, if you only have one RAM module, this will be hard to test.",
            "Step 9: If all else fails, test the power supply with a power supply tester or by replacing your current one with a known working PSU."
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
        editor.putString(issuetype, "CWS")
        editor.apply()
        // END ISSUETYPE

        // START BUTTONS
        issueSolved.setOnClickListener {
            // Create an Intent to start NewActivity
            val intent = Intent(this@ComputerWontStart, IssueSolved::class.java)
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
                val intent = Intent(this@ComputerWontStart, IssueFailed::class.java)
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
