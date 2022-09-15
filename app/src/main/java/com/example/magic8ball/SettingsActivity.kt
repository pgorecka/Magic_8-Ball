package com.example.magic8ball

import android.content.Intent
import android.media.MediaPlayer.create
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat


open class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)


        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    open class SettingsFragment : PreferenceFragmentCompat() {

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)

            sendEmail()
            playSound()
            vibrationOff()
        }

        // Connects to email apps on target device
        private fun sendEmail() {

            val sendBtn = findPreference<Preference>("feedback")

            sendBtn?.setOnPreferenceClickListener {
                val emailIntent = Intent(
                    Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "p.gorecka@zoho.com", null
                    )
                )
                emailIntent.putExtra(
                    Intent.EXTRA_SUBJECT,
                    context?.getString(R.string.label_subject_email)
                )
                startActivity(
                    Intent.createChooser(
                        emailIntent,
                        context?.getString(R.string.label_send_email)
                    )
                )
                true
            }
        }

        private fun playSound() {

            val mediaPlayer = create(context, R.raw.music)
            val musicSwitch = findPreference<SwitchPreferenceCompat>("music")

            musicSwitch?.setOnPreferenceClickListener {
                if (musicSwitch.isChecked) {
                    mediaPlayer.start()
                    println("hello,music on")
                }
                if (!musicSwitch.isChecked) {
                    mediaPlayer.stop()
                    mediaPlayer.reset()
                    mediaPlayer.release()
                    println("hello,music off")
                    playSound()
                }
                false
            }
        }

        open fun vibrationOff(): Boolean {
            // fun c() = MainActivity::vibratePhone
            // val v = requireContext().getSystemService(VIBRATOR_SERVICE) as Vibrator
            val vibrationSwitch = findPreference<SwitchPreferenceCompat>("vibration")

            vibrationSwitch?.setOnPreferenceClickListener {
                if (!vibrationSwitch.isChecked) {
                    //    v.cancel()
                    println("hello,vibration off")

                } else println("It is on")
                true

            }
            return false
        }

    }}


