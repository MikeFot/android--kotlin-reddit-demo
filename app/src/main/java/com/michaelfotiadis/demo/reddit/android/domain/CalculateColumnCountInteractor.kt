package com.michaelfotiadis.demo.reddit.android.domain

import com.michaelfotiadis.demo.reddit.android.repository.ConfigurationRepository

class CalculateColumnCountInteractor(private val configurationRepository: ConfigurationRepository) {

    fun getColumnCount(): Int {
        return when {
            configurationRepository.isLandscape && configurationRepository.isTablet -> 3
            configurationRepository.isLandscape -> 2
            configurationRepository.isTablet -> 2
            configurationRepository.is1080dp -> 2
            configurationRepository.is720dp -> 2
            else -> 1
        }
    }

}