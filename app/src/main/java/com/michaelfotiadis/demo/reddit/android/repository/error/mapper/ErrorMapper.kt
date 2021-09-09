/*
 * Developed by Michail Fotiadis.
 * Copyright (c) 2018.
 * All rights reserved.
 */

package com.michaelfotiadis.demo.reddit.android.repository.error.mapper

import com.michaelfotiads.xkcdreader.repo.error.DataSourceError

sealed interface ErrorMapper<in T> {
    fun convert(error: T?): DataSourceError
}
