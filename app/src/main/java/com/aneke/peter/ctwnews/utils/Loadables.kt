package com.aneke.peter.ctwnews.utils

import androidx.appcompat.app.AppCompatActivity

open class LoadableActivity : AppCompatActivity() {

    private val loader : LoadingDialog by lazy { LoadingDialog() }

    fun showLoader() {
        if (!loader.isAdded) {
            hideLoader()
            loader.show(supportFragmentManager, loader.tag)
        } else {
            return
        }
    }

    fun hideLoader() {
        if (loader.isAdded){
            loader.dismiss()
        }
    }

}