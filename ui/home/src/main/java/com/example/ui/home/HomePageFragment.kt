package com.example.ui.home

import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import com.example.ui.home.databinding.FragmentHomePageBinding
import com.example.ui_core.viewbinding.viewBinding

class HomePageFragment : Fragment(R.layout.fragment_home_page) {

    private val viewBinding by viewBinding(FragmentHomePageBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.deviceDetailsButton.setOnClickListener {
            val request = NavDeepLinkRequest.Builder
                .fromUri(getString(com.example.navigation.R.string.nav_deeplink_details).toUri())
                .build()
            findNavController().navigate(request)
        }

        viewBinding.myAccountButton.setOnClickListener {
            val request = NavDeepLinkRequest.Builder
                .fromUri(getString(com.example.navigation.R.string.nav_deeplink_account).toUri())
                .build()
            findNavController().navigate(request)
        }
    }

}
