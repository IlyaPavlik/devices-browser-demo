package com.example.ui.account

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.ui.account.databinding.FragmentMyAccountBinding
import com.example.uicore.viewbinding.viewBinding

class MyAccountFragment : Fragment(R.layout.fragment_my_account) {

    private val binding by viewBinding(FragmentMyAccountBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}
