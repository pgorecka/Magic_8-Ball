package com.example.magic8ball

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.todo.shakeit.core.ShakeDetector
import com.todo.shakeit.core.ShakeIt
import com.todo.shakeit.core.ShakeListener


// Allows the user to shake the ball and view the result on the screen
open class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toggleDesign()
        ShakeIt.init(application)
        shakeToClick()

        val shakeBtn: Button = findViewById(R.id.ask_button)
        shakeBtn.setOnClickListener {
            shakeMagicBall()
        }

        val settingsBtn: Button = findViewById(R.id.settings_btn)
        settingsBtn.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }


    // Creates new ball object with 20 possible answers
    private fun shakeMagicBall() {

        val ball = MagicBall(20)
        val say = ball.answer()
        vibratePhone()

        // Updates the screen with the result
        val resultTextView: TextView = findViewById(R.id.response_window)
        resultTextView.text = say
    }

    // Detects device movement and shakes the ball
    private fun shakeToClick() {

        ShakeDetector.registerForShakeEvent(object : ShakeListener {
            override fun onShake() {
                shakeMagicBall()
            }
        })
    }

    // Vibrates phone on click
    private fun vibratePhone() {

        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        if (Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.run { 200 }
        }
    }

    // Allows sharing the app
    fun shareIntent(view: View ) {

        val shareBtn = findViewById<Button>(R.id.shareButton)

        shareBtn.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT, "Hey Check out this app:")
            intent.type = "text/plain"
            startActivity(Intent.createChooser(intent, "Share To:"))
        }
    }

    // Allows to switch between themes
    @SuppressLint("NewApi", "UseSwitchCompatOrMaterialCode")
    private fun toggleDesign() {

        val toggleBtn: Switch = this.findViewById(R.id.theme_switch)
        val ballImg: ImageView = this.findViewById(R.id.magic_ball_img)
        val textStyle: TextView = this.findViewById(R.id.response_window)
        val askBtn: Button = this.findViewById(R.id.ask_button)
        val background: View = this.findViewById(R.id.mainActivity)

        toggleBtn.setOnCheckedChangeListener { _, isChecked ->

            if (isChecked) {
                ballImg.setImageResource(R.drawable.magic_ball_2)
                textStyle.setTextColor(Color.parseColor("#000034"))
                askBtn.backgroundTintList = ColorStateList.valueOf(Color.rgb(158, 121, 129))
                background.setBackgroundResource(R.drawable.bg2)
            } else {
                ballImg.setImageResource(R.drawable.magic_ball)
                textStyle.setTextColor(Color.parseColor("#FFFFFFFF"))
                askBtn.backgroundTintList = ColorStateList.valueOf(Color.rgb(0, 24, 58))
                background.setBackgroundResource(R.drawable.bg1)
            }
        }
    }

}

/*

  @SuppressLint("NewApi")
    open fun vibratePhone() {

        fun c(): Boolean = SettingsActivity.SettingsFragment::vibrationOff()
        c()

        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
       // val vbSwitch: Preference = c.findViewById<Preference>("vibration_switch")

        if (c()){// (Build.VERSION.SDK_INT >= 26)  {

            vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
           // vibrator.run { 200 }
            vibrator.cancel()
        }
    }
 */