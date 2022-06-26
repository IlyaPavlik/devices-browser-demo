package com.example.ui.home.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.ui.home.R

class DeviceSteeringFragment : Fragment(R.layout.fragment_device_steering) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<View>(R.id.back_button).setOnClickListener {
            findNavController().popBackStack()
        }
    }
}
