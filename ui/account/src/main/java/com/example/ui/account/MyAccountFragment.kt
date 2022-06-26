package com.example.ui.account

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class MyAccountFragment : Fragment(R.layout.fragment_my_account) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<View>(R.id.back_button).setOnClickListener {
            findNavController().popBackStack()
        }
    }
}
