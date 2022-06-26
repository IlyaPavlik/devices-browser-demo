package com.example.ui.home

import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController

class HomePageFragment : Fragment(R.layout.fragment_home_page) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<View>(R.id.device_details_button).setOnClickListener {
            val request = NavDeepLinkRequest.Builder
                .fromUri(getString(com.example.navigation.R.string.nav_deeplink_details).toUri())
                .build()
            findNavController().navigate(request)
        }

        view.findViewById<View>(R.id.my_account_button).setOnClickListener {
            val request = NavDeepLinkRequest.Builder
                .fromUri(getString(com.example.navigation.R.string.nav_deeplink_account).toUri())
                .build()
            findNavController().navigate(request)
        }
    }

}
