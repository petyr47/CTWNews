package com.aneke.peter.ctwnews.utils

import android.app.Activity
import android.content.Intent
import android.os.Build.VERSION.SDK_INT
import android.os.Parcelable
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.aneke.peter.ctwnews.BuildConfig
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import java.net.UnknownHostException
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*

fun Exception.resolveMessage() : String {
    var message = ""
    try {
        if (this is HttpException) {
            if (this.code() == 500){
                message = "Oops! Something went wrong on our servers"
            } else {
                try {
                    val body = this.response()?.errorBody()?.string()?.let { JSONObject(it) }
                    if (body != null) {
                        message = body.getString("message")
                    }
                } catch (e : JSONException) {
                  message = "The application has encountered an unknown error"
                }
            }
        } else if (this is UnknownHostException) {
            message = "Could not connect to network, please check your internet connection"
        } else {
            message = this.message ?: "The application has encountered an unknown error"
        }
    } catch (e : Exception) {
        message = "The application has encountered an unknown error"
    }
    return message
}

fun Activity.showAlert(title : String = "Error", message : String, action: (() -> Unit)? = null) {
    val builder = AlertDialog.Builder(this).apply {
        setTitle(title)
        setMessage(message)
        setPositiveButton("OK"){ dialog, _ ->
            action?.invoke()
            dialog.dismiss()
        }
    }
    if (!isFinishing) {
        val dialog = builder.create()
        dialog.show()
    }
}

fun String.convertToTimestamp() : Long {
   val date =  when (BuildConfig.FLAVOR) {
        Flavours.bbcFlavour, Flavours.buzzFeedFlavour ->  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'", Locale.getDefault()).parse(this)
        else -> SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault()).parse(this)
    }
    return date?.time ?: Instant.now().epochSecond
}

fun String.convertToDate() : String {
    val date =  when (BuildConfig.FLAVOR) {
        Flavours.bbcFlavour, Flavours.buzzFeedFlavour ->  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'", Locale.getDefault()).parse(this)
        else -> SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault()).parse(this)
    }
    val format = SimpleDateFormat("EEE, d MMM yyyy HH:mm", Locale.getDefault())
    return date?.let { format.format(it) } ?: "--"
}

inline fun <reified T : Parcelable> Intent.parcelable(key: String): T? = when {
    SDK_INT >= 33 -> getParcelableExtra(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? T
}

fun Activity.deviceHasBiometric() : Boolean {
    val biometricManager = BiometricManager.from(this)
    return when (biometricManager.canAuthenticate(
        BiometricManager.Authenticators.BIOMETRIC_STRONG
            or BiometricManager.Authenticators.DEVICE_CREDENTIAL)) {
        BiometricManager.BIOMETRIC_SUCCESS -> {
            true
        }
        BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
            false
        }
        BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
            false
        }
        BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
            false
        }
        BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED,
        BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED, BiometricManager.BIOMETRIC_STATUS_UNKNOWN -> {
            false
        }
        else -> {
            false
        }
    }
}

fun FragmentActivity.setupBiometricPromptInfo(assign : (BiometricPrompt, BiometricPrompt.PromptInfo) -> Unit, success : () -> Unit) {
    val executor = ContextCompat.getMainExecutor(this)

    val biometricPrompt = BiometricPrompt(this, executor,
        object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int,
                                               errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                Toast.makeText(applicationContext,
                    "Authentication error: $errString", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onAuthenticationSucceeded(
                result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                Toast.makeText(applicationContext,
                    "Authentication succeeded!", Toast.LENGTH_SHORT)
                    .show()
                success()
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Toast.makeText(applicationContext, "Authentication failed",
                    Toast.LENGTH_SHORT)
                    .show()
                finishAffinity()
            }
        })

    val promptInfo = BiometricPrompt.PromptInfo.Builder()
        .setTitle("Biometric login for News App")
        .setNegativeButtonText("Cancel")
        .build()

    assign(biometricPrompt, promptInfo)

}