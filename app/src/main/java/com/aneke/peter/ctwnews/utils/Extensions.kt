package com.aneke.peter.ctwnews.utils

import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import java.net.UnknownHostException

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