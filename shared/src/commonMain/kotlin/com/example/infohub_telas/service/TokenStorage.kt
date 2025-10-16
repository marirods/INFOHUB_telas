package com.example.infohub_telas.service

import com.example.infohub_telas.util.SettingsFactory
import com.russhwolf.settings.Settings
import com.russhwolf.settings.set

class TokenStorage {
    private val settings: Settings by lazy { SettingsFactory().create() }

    fun saveToken(token: String) {
        settings["auth_token"] = token
    }

    fun getToken(): String? {
        return settings.getStringOrNull("auth_token")
    }
}
