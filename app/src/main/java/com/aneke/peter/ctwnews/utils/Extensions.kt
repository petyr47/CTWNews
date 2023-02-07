package com.aneke.peter.ctwnews.utils

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import com.aneke.peter.ctwnews.BuildConfig
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import java.net.UnknownHostException
import java.text.SimpleDateFormat

fun Exception.resolveMessage() : String {
    var message = ""
    try {
        if (this is HttpException) {
            if (this.code() == 500){
                message = "Oops! Something went wrong on our servers"
            } else {
                try {
                    val body = JSONObject(this.response()?.errorBody()?.string())
                    message = body.getString("message")
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
        "bbc", "buzzFeed" ->  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'").parse(this)
        else -> SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(this)
    }
    return date.time
}

fun String.convertToDate() : String {
    val date =  when (BuildConfig.FLAVOR) {
        "bbc" , "buzzFeed" ->  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'").parse(this)
        else -> SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(this)
    }
    val format = SimpleDateFormat("EEE, d MMM yyyy HH:mm")
    return format.format(date)
}