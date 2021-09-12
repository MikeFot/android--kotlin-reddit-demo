/*
 * Developed by Michail Fotiadis.
 * Copyright (c) 2018.
 * All rights reserved.
 */

package com.michaelfotiadis.demo.reddit.android.ui.error

import androidx.annotation.StringRes
import com.michaelfotiadis.demo.reddit.android.R

enum class UiError(
    @StringRes val messageResId: Int,
    @StringRes val actionResId: Int = R.string.ui_error_action_retry
) {

    COMMUNICATION(R.string.ui_error_communication),
    INTERNAL_SERVER_ERROR(R.string.ui_error_internal_server_error),
    UNEXPECTED(R.string.ui_error_unexpected),
    REQUEST_FAILED(R.string.ui_error_request_failed),
    NO_NETWORK(R.string.ui_error_no_network),
    INVALID_REQUEST_PARAMETERS(R.string.ui_error_invalid_request),
    NOT_FOUND(R.string.ui_error_not_found),
    AUTHENTICATION(R.string.ui_error_authentication),
    AUTHORISATION(R.string.ui_error_authorisation),
    BAD_REQUEST(R.string.ui_error_bad_request, R.string.ui_error_action_retry),
    PAYLOAD_TOO_LARGE(R.string.ui_error_payload_too_large),
    NOT_PERMITTED(R.string.ui_error_no_permission),
    SEARCH_NOT_FOUND(R.string.message_page_not_found);

    fun hasAction() = actionResId != 0
}
