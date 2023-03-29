package com.aneke.peter.ctwnews

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.biometric.BiometricPrompt
import com.aneke.peter.ctwnews.news.NewsActivity
import com.aneke.peter.ctwnews.utils.deviceHasBiometric
import com.aneke.peter.ctwnews.utils.setupBiometricPromptInfo

class MainActivity : AppCompatActivity() {

    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupPrompt()
        checkDeviceHasBiometric()
    }

    private fun setupPrompt() {
        setupBiometricPromptInfo({ _biometricPrompt, _promptInfo ->
            biometricPrompt = _biometricPrompt
            promptInfo = _promptInfo
        }, {showHeadlines()})
    }

    private fun checkDeviceHasBiometric() {
        if (deviceHasBiometric()) {
            promptBiometrics()
        }else {
            showHeadlines()
        }
    }

    private fun promptBiometrics() {
        biometricPrompt.authenticate(promptInfo)
    }

    private fun showHeadlines() {
        val intent = Intent(this, NewsActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}