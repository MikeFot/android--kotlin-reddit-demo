
package com.michaelfotiadis.demo.reddit.android.network.error

import com.google.gson.annotations.SerializedName
import com.michaelfotiadis.demo.reddit.android.network.error.RestError

class RestErrorBody(
    @SerializedName("message") val message: String?,
    @SerializedName("errors") val errors: List<RestError?>?,
    @SerializedName("status_code") val statusCode: Int?
)
