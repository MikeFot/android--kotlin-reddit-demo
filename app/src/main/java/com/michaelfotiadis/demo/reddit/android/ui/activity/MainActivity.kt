package com.michaelfotiadis.demo.reddit.android.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.michaelfotiadis.demo.reddit.android.databinding.ActivityMainBinding
import com.michaelfotiadis.demo.reddit.android.repository.PostsRepository
import com.michaelfotiadis.demo.reddit.android.ui.error.UiError
import com.michaelfotiadis.demo.reddit.android.ui.error.UiErrorMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.activityRetainedScope
import org.koin.core.scope.Scope
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope, AndroidScopeComponent {

    override val scope: Scope by activityRetainedScope()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + SupervisorJob()

    private lateinit var binding: ActivityMainBinding

    private val postsRepository: PostsRepository by inject()
    private val uiErrorMapper: UiErrorMapper by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()

        launch {
            val result = postsRepository.getRedditPosts(null)

            when {
                result.payload != null -> binding.homePlaceholderText.text = "Received ${result.payload.data.children.size} posts."
                result.dataSourceError != null -> {
                    val error = uiErrorMapper.convert(result.dataSourceError)
                    binding.homePlaceholderText.setText(error.messageResId)
                }
            }
        }

    }
}