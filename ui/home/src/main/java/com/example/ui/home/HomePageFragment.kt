package com.example.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ui.home.databinding.FragmentHomePageBinding
import com.example.ui_core.viewbinding.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

@AndroidEntryPoint
class HomePageFragment : Fragment(R.layout.fragment_home_page) {

    private val viewBinding by viewBinding(FragmentHomePageBinding::bind)
    private val viewModel by viewModels<HomePageViewModel>()

    private val devicesAdapter = DevicesAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("!!! > onViewCreated")
        viewBinding.deviceRecycle.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = devicesAdapter
        }

        lifecycleScope.launchWhenStarted {
            viewModel.homePageState.onEach { onHomePageState(it) }.launchIn(this)
        }
    }

    private fun onHomePageState(state: HomePageState) {
        when (state) {
            is HomePageState.Content -> {
                viewBinding.deviceRecycle.visibility = View.VISIBLE
                devicesAdapter.items = state.devices
                listOf(viewBinding.progressBar, viewBinding.errorText)
                    .map { it.visibility = View.GONE }
            }
            is HomePageState.Error -> {
                viewBinding.errorText.visibility = View.VISIBLE
                listOf(viewBinding.deviceRecycle, viewBinding.progressBar)
                    .map { it.visibility = View.GONE }
            }
            is HomePageState.Loading -> {
                viewBinding.progressBar.visibility = View.VISIBLE
                listOf(viewBinding.deviceRecycle, viewBinding.errorText)
                    .map { it.visibility = View.GONE }
            }
        }
    }
}
