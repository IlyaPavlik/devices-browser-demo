package com.example.ui.home.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.ui.home.R
import com.example.ui.home.databinding.FragmentDeviceSteeringBinding
import com.example.ui_core.viewbinding.viewBinding

class DeviceSteeringFragment : Fragment(R.layout.fragment_device_steering) {

    private val viewBinding by viewBinding(FragmentDeviceSteeringBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}
