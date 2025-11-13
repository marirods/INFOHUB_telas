package com.example.infohub_telas.network.models

import com.google.gson.annotations.SerializedName

// Base request marker
open class BaseRequest

// Base response with common fields
open class BaseResponse(
    @SerializedName("status")
    open val status: Boolean,
    @SerializedName("status_code")
    open val statusCode: Int,
    @SerializedName("message")
    open val message: String
)

// Generic API envelope when backend wraps data generically
data class ApiResponse<T>(
    @SerializedName("status") val status: Boolean,
    @SerializedName("status_code") val statusCode: Int? = null,
    @SerializedName("message") val message: String? = null,
    @SerializedName("data") val data: T? = null,
    @SerializedName("error") val error: String? = null
)
