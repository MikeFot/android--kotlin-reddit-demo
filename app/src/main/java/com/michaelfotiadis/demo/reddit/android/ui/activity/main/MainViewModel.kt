package com.michaelfotiadis.demo.reddit.android.ui.activity.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.michaelfotiadis.demo.reddit.android.data.network.model.RedditResponse
import com.michaelfotiadis.demo.reddit.android.domain.GetColumnCountInteractor
import com.michaelfotiadis.demo.reddit.android.domain.GetPostsInteractor
import com.michaelfotiadis.demo.reddit.android.repository.result.RepoResult
import com.michaelfotiadis.demo.reddit.android.ui.activity.main.mapper.UiPostsMapper
import com.michaelfotiadis.demo.reddit.android.ui.activity.main.model.MainUiState
import com.michaelfotiadis.demo.reddit.android.ui.error.UiErrorMapper
import kotlinx.coroutines.launch
import timber.log.Timber

class MainViewModel(
    private val getPostsInteractor: GetPostsInteractor,
    private val getColumnsInteractor: GetColumnCountInteractor,
    private val uiPostsMapper: UiPostsMapper,
    private val uiErrorMapper: UiErrorMapper
) : ViewModel() {

    init {
        Timber.d("VIEWMODEL HASH : ${this.hashCode()}")
    }

    val uiLiveData = MutableLiveData<MainUiState>()
    val columnsLiveData = MutableLiveData<Int>()

    private var nextPage: String? = null

    init {
        uiLiveData.postValue(MainUiState.Idle)
        refreshColumnCount()
    }

    fun getPosts() {
        viewModelScope.launch {

            uiLiveData.postValue(MainUiState.Loading)

            when (val result = getPostsInteractor.getPosts(nextPage)) {
                is RepoResult.ErrorResult -> {
                    val uiError = uiErrorMapper.convert(result.dataSourceError)
                    uiLiveData.postValue(MainUiState.Error(uiError))
                }
                is RepoResult.SuccessResult<RedditResponse> -> {
                    Timber.d("Received ${result.payload.data.children.size} posts for page $nextPage")
                    nextPage = result.payload.data.after

                    uiPostsMapper.map(result.payload.data.children)

                    uiLiveData.postValue(MainUiState.Success)
                }
            }

        }
    }

    fun clearError() {
        uiLiveData.postValue(MainUiState.Idle)
    }

    fun refreshColumnCount() {
        columnsLiveData.postValue(getColumnsInteractor.getColumnCount())
    }

}

