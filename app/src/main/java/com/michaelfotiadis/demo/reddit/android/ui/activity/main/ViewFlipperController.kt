package com.michaelfotiadis.demo.reddit.android.ui.activity.main

import android.widget.ViewFlipper
import timber.log.Timber

class ViewFlipperController(private val viewFlipper: ViewFlipper) {

    companion object {
        private const val INDEX_CONTENT = 0
        private const val INDEX_PROGRESS = 1
    }

    fun showContent() {
        Timber.d("Showing content")
        viewFlipper.post {
            viewFlipper.displayedChild = INDEX_CONTENT
        }

    }

    fun showProgress() {
        Timber.d("Showing progress")
        viewFlipper.post {
            viewFlipper.displayedChild = INDEX_PROGRESS
        }
    }

}