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

class MonitorIsBlank : ComponentActivity() {
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
        setContentView(R.layout.activity_mib)

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
            "Step 1: Ensure that the monitor's power cable is securely plugged into both the monitor and the power outlet, and that the video cable (e.g., HDMI, VGA, DisplayPort) is properly connected to both the monitor and the computer.",
            "Step 2: Connect the monitor to another computer or device to determine if the issue is with the monitor or the computer.",
            "Step 3: Verify that the monitor's brightness and contrast settings are adjusted properly. Some monitors also have an input selection button that may need to be adjusted to the correct input source.",
            "Step 4: Restart the computer. Sometimes a simple restart can resolve display issues caused by temporary glitches.",
            "Step 5: If possible, try using a different video cable to connect the monitor to the computer, as the cable may be faulty.",
            "Step 6: Check if the graphics card is properly seated in the computer's PCIe slot and that any power connectors are securely connected.",
            "Step 7: Update the graphics card drivers and operating system to the latest versions. If using multiple monitors, adjust the display settings to ensure proper detection and configuration.",
            "Step 8: Try booting into safe mode. Restart the computer and enter safe mode (usually by pressing F8 or F12 during startup) to troubleshoot potential driver or software conflicts.",
            "Step 9: If available, use the monitor's menu buttons to reset the display settings to their default values.",
            "Step 10: If possible, connect a different monitor to the computer to determine if the issue is with the monitor itself or another component."
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
        editor.putString(issuetype, "MIB")
        editor.apply()
        // END ISSUETYPE

        // START BUTTONS
        issueSolved.setOnClickListener {
            // Create an Intent to start NewActivity
            val intent = Intent(this@MonitorIsBlank, IssueSolved::class.java)
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
                val intent = Intent(this@MonitorIsBlank, IssueFailed::class.java)
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
