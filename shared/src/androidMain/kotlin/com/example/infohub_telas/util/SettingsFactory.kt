package com.example.infohub_telas.util

import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings

actual class SettingsFactory {
    actual fun create(): Settings {
        return SharedPreferencesSettings(AppContext.context)
    }
}
