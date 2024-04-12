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

class ComputerCrashes : ComponentActivity() {
    private lateinit var palSays: Array<String>
    private lateinit var steps: Array<String>
    private lateinit var images: Array<Int>
    private var currentStepIndex: Int = 0
    private lateinit var stepTextView: TextView
    private lateinit var imageView: ImageView
    private val random = Random()

    // Variable to store issue type in SharedPreferences
    private val issuetype = "issue_type"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cc)

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

        // Define array of steps for computer crashing.
        steps = arrayOf(
            "Step 1: Check for overheating. Ensure that the computer's cooling system (fans and heatsinks) is functioning properly and that airflow is not obstructed.",
            "Step 2: Ensure that all device drivers, including graphics card drivers, are up-to-date. Outdated drivers can cause compatibility issues and system instability.",
            "Step 3: Run a full system scan using antivirus software to check for malware or viruses that may be causing the crashes.",
            "Step 4: Test hardware components such as RAM, hard drive, and graphics card for errors using diagnostic tools.",
            "Step 5: Determine if any software or hardware changes were made recently that may have triggered the crashes, and consider reverting those changes.",
            "Step 6: Identify and uninstall any recently installed programs that may be conflicting with existing software or drivers.",
            "Step 7: Update operating system. Make sure the operating system is up-to-date with the latest security patches and bug fixes.",
            "Step 8: Monitor system resources. Use task manager or resource monitoring tools to identify any processes or applications that may be consuming excessive system resources.",
            "Step 9: Test in safe mode. Restart the computer in safe mode (usually by pressing F8 or F12 during startup) to troubleshoot potential software conflicts or driver issues.",
            "Step 10: If the crashes persist and hardware diagnostics indicate issues, consider upgrading hardware components such as RAM or replacing the hard drive."
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
        editor.putString(issuetype, "CC")
        editor.apply()
        // END ISSUETYPE

        // START BUTTONS
        issueSolved.setOnClickListener {
            // Create an Intent to start NewActivity
            val intent = Intent(this@ComputerCrashes, IssueSolved::class.java)
            startActivity(intent)
        }

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
                val intent = Intent(this@ComputerCrashes, IssueFailed::class.java)
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
