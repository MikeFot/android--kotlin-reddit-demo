/*
 * Developed by Michail Fotiadis.
 * Copyright (c) 2018.
 * All rights reserved.
 */

package com.michaelfotiadis.demo.reddit.android.repository.result

import com.michaelfotiads.xkcdreader.repo.error.DataSourceError

class RepoResult<T>(
    val payload: T? = null,
    val dataSourceError: DataSourceError? = null,
    val next: String? = null
)
