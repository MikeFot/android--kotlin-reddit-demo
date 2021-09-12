/*
 * Developed by Michail Fotiadis.
 * Copyright (c) 2018.
 * All rights reserved.
 */

package com.michaelfotiadis.demo.reddit.android.repository.result

import com.michaelfotiads.xkcdreader.repo.error.DataSourceError

sealed class RepoResult<out T : Any> {

    data class SuccessResult<out T : Any>(val payload: T) : RepoResult<T>()
    data class ErrorResult(val dataSourceError: DataSourceError) : RepoResult<Nothing>()

}
