package com.example.newsapp.util

object Constants {

    init {
        System.loadLibrary("native-lib")
    }

    @JvmStatic
    external fun getStringBaseUrlDevelopment(): String
}
