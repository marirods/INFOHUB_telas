package com.example.infohub_telas.util

import android.content.Context

object AppContext {
    lateinit var context: Context
        private set

    fun initialize(context: Context) {
        this.context = context.applicationContext
    }
}
