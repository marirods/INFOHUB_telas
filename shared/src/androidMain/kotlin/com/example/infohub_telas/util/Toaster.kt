package com.example.infohub_telas.util

import android.widget.Toast

actual fun showToast(message: String) {
    Toast.makeText(AppContext.context, message, Toast.LENGTH_SHORT).show()
}
