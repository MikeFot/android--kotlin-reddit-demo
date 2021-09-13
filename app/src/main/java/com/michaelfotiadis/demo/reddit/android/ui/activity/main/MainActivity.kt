package com.michaelfotiadis.demo.reddit.android.ui.activity.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.michaelfotiadis.demo.reddit.android.R
import com.michaelfotiadis.demo.reddit.android.databinding.ActivityMainBinding
import com.michaelfotiadis.demo.reddit.android.ui.activity.main.adapter.PostsRecyclerViewAdapter
import com.michaelfotiadis.demo.reddit.android.ui.activity.main.model.MainUiState
import io.noties.markwon.Markwon
import org.koin.android.ext.android.inject
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.activityRetainedScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.scope.Scope
import timber.log.Timber

class MainActivity : AppCompatActivity(), AndroidScopeComponent {

    override val scope: Scope by activityRetainedScope()

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModel()
    private val markwon: Markwon by inject()

    private lateinit var viewFlipperController: ViewFlipperController
    private val adapter: PostsRecyclerViewAdapter by lazy {
        PostsRecyclerViewAdapter(markwon)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        viewFlipperController = ViewFlipperController(binding.viewFlipper)

        binding.postsListView.adapter = adapter

        binding.swipeRefreshLayout.apply {
            setColorSchemeColors(
                ContextCompat.getColor(context, R.color.color_on_primary),
                ContextCompat.getColor(context, R.color.primary)
            )
            setOnRefreshListener {
                viewModel.loadPosts(true)
            }
        }

        viewModel.columnsLiveData.observe(this, { columnCount ->
            Timber.d("New Column Count $columnCount")
            binding.postsListView.layoutManager = when (columnCount) {
                1 -> LinearLayoutManager(this)
                else -> GridLayoutManager(this, columnCount)
            }
        })

        viewModel.pagedItemsLiveData.observe(this, { pagedList ->
            Timber.d("List submitted to adapter ${pagedList.size}")
            adapter.submitList(pagedList)
            if (pagedList.isEmpty()) {
                viewModel.loadPosts(true)
            }
        })

        viewModel.uiLiveData.observe(this, { uiState ->
            when (uiState) {
                is MainUiState.Error -> {
                    Timber.d("UISTATE: Received Error")
                    binding.swipeRefreshLayout.post {
                        binding.swipeRefreshLayout.isEnabled = true
                        binding.swipeRefreshLayout.isRefreshing = false
                    }
                }
                MainUiState.Idle -> {
                    Timber.d("UISTATE:Main screen is idle")
                    binding.swipeRefreshLayout.post {
                        binding.swipeRefreshLayout.isEnabled = true
                        binding.swipeRefreshLayout.isRefreshing = false
                    }
                }
                MainUiState.Loading -> {
                    Timber.d("UISTATE:Loading...")
                    binding.swipeRefreshLayout.post {
                        binding.swipeRefreshLayout.isEnabled = false
                    }
                }
            }
        })

        viewModel.onViewReady()

    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshColumnCount()
    }

}