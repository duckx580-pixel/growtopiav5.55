package com.example.growtopialauncher

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val growtopiaPackageName = "com.rtsoft.growtopia"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.launchButton).setOnClickListener {
            launchGrowtopia()
        }
    }

    private fun launchGrowtopia() {
        val launchIntent = packageManager.getLaunchIntentForPackage(growtopiaPackageName)
        if (launchIntent != null) {
            launchIntent.addCategory(Intent.CATEGORY_LAUNCHER)
            startActivity(launchIntent)
        } else {
            Toast.makeText(
                this,
                getString(R.string.growtopia_not_installed),
                Toast.LENGTH_LONG
            ).show()
            openPlayStoreListing()
        }
    }

    private fun openPlayStoreListing() {
        val marketUri = Uri.parse("market://details?id=$growtopiaPackageName")
        try {
            startActivity(Intent(Intent.ACTION_VIEW, marketUri))
        } catch (e: ActivityNotFoundException) {
            val webUri = Uri.parse("https://play.google.com/store/apps/details?id=$growtopiaPackageName")
            startActivity(Intent(Intent.ACTION_VIEW, webUri))
        }
    }
}
