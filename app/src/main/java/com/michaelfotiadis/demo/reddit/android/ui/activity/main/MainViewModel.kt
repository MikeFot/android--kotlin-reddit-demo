package com.michaelfotiadis.demo.reddit.android.ui.activity.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.michaelfotiadis.demo.reddit.android.data.network.model.RedditResponse
import com.michaelfotiadis.demo.reddit.android.domain.CalculateColumnCountInteractor
import com.michaelfotiadis.demo.reddit.android.domain.FetchAndWritePostsInteractor
import com.michaelfotiadis.demo.reddit.android.domain.ReadLocalPostsInteractor
import com.michaelfotiadis.demo.reddit.android.repository.result.RepoResult
import com.michaelfotiadis.demo.reddit.android.ui.activity.main.mapper.UiPostsMapper
import com.michaelfotiadis.demo.reddit.android.ui.activity.main.model.MainUiState
import com.michaelfotiadis.demo.reddit.android.ui.activity.main.model.UiPost
import com.michaelfotiadis.demo.reddit.android.ui.activity.main.error.UiErrorMapper
import kotlinx.coroutines.launch
import timber.log.Timber

class MainViewModel(
    private val fetchAndWritePostsInteractor: FetchAndWritePostsInteractor,
    private val calculateColumnsInteractor: CalculateColumnCountInteractor,
    readLocalPostsInteractor: ReadLocalPostsInteractor,
    private val uiPostsMapper: UiPostsMapper,
    private val uiErrorMapper: UiErrorMapper
) : ViewModel() {

    val pagedItemsLiveData: LiveData<PagedList<UiPost>>
    val uiLiveData = MutableLiveData<MainUiState>()
    val columnsLiveData = MutableLiveData(1)

    private var nextPage: String? = null

    init {
        uiLiveData.value = MainUiState.Idle

        class UiPostsBoundaryCallback : PagedList.BoundaryCallback<UiPost>() {
            override fun onItemAtEndLoaded(itemAtEnd: UiPost) {
                super.onItemAtEndLoaded(itemAtEnd)
                loadPosts(false)
            }
        }

        pagedItemsLiveData = LivePagedListBuilder(
            readLocalPostsInteractor.readPosts().map(uiPostsMapper::map),
            1
        ).setBoundaryCallback(UiPostsBoundaryCallback())
            .build()

        refreshColumnCount()
    }

    fun onViewReady() {
        refreshColumnCount()
    }

    fun loadPosts(isRefresh: Boolean) {
        viewModelScope.launch {
            if (isRefresh) {
                nextPage = null
            }

            uiLiveData.postValue(MainUiState.Loading)

            when (val result = fetchAndWritePostsInteractor.getPosts(nextPage, isRefresh)) {
                is RepoResult.ErrorResult -> {
                    val uiError = uiErrorMapper.convert(result.dataSourceError)
                    uiLiveData.postValue(MainUiState.Error(uiError))
                }
                is RepoResult.SuccessResult<RedditResponse> -> {
                    Timber.d("Received ${result.payload.data.children.size} posts for page $nextPage")
                    nextPage = result.payload.data.after
                    uiLiveData.postValue(MainUiState.Idle)
                }
            }

        }
    }

    fun clearError() {
        uiLiveData.postValue(MainUiState.Idle)
    }

    fun refreshColumnCount() {
        val updateColumnCount = calculateColumnsInteractor.getColumnCount()
        if (updateColumnCount != columnsLiveData.value) {
            columnsLiveData.postValue(updateColumnCount)
        }
    }

}

