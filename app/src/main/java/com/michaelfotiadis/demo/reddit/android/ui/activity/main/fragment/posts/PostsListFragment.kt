package com.michaelfotiadis.demo.reddit.android.ui.activity.main.fragment.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.michaelfotiadis.demo.reddit.android.R
import com.michaelfotiadis.demo.reddit.android.databinding.FragmentPostsBinding
import com.michaelfotiadis.demo.reddit.android.ui.activity.main.MainViewModel
import com.michaelfotiadis.demo.reddit.android.ui.activity.main.fragment.posts.placeholder.PlaceholderContent
import com.michaelfotiadis.demo.reddit.android.ui.activity.main.model.MainUiState
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class PostsListFragment : Fragment() {

    private val navController: NavController by lazy {
        findNavController()
    }

    private lateinit var binding: FragmentPostsBinding
    private lateinit var adapter: PostsRecyclerViewAdapter

    private val mainViewModel: MainViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_posts, container, false)
        binding = FragmentPostsBinding.bind(view)


        adapter = PostsRecyclerViewAdapter()
        binding.postsListView.adapter = adapter

        mainViewModel.columnsLiveData.observe(viewLifecycleOwner, { columnCount ->
            Timber.d("New Column Count $columnCount")
            binding.postsListView.layoutManager = when (columnCount) {
                1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
        })

        mainViewModel.uiLiveData.observe(viewLifecycleOwner, { uiState ->
            when (uiState) {
                is MainUiState.Error -> goToErrorScreen()
                MainUiState.Idle -> {
                    // NOOP
                }
                MainUiState.Loading -> {
                    // NOOP
                }
                MainUiState.Success -> adapter.setItems(PlaceholderContent.ITEMS)
            }
        })

        return view
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.refreshColumnCount()
    }

    private fun goToErrorScreen() {
        navController.navigate(R.id.action_postsListFragment_to_errorFragment)
    }

}