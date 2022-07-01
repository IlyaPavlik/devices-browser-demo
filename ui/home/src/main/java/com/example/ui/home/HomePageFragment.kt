package com.example.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import com.example.feature.device.data.model.Device
import dagger.hilt.android.AndroidEntryPoint
import com.example.navigation.R as RNavigation

@AndroidEntryPoint
class HomePageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        setContent { HomePageScreen(onDeviceClick = ::onDeviceClick) }
    }

    private fun onDeviceClick(device: Device) {
        val uri = getString(RNavigation.string.nav_deeplink_details)
            .replace("{deviceId}", device.id.toString())
            .toUri()
        val request = NavDeepLinkRequest.Builder
            .fromUri(uri)
            .build()
        findNavController().navigate(request)
    }
}
