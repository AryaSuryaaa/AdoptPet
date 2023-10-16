package com.aryasurya.adoptpet.ui.account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.aryasurya.adoptpet.R
import com.aryasurya.adoptpet.databinding.FragmentAccountBinding
import com.aryasurya.adoptpet.databinding.FragmentListBinding
import com.aryasurya.adoptpet.ui.ViewModelFactory
import com.aryasurya.adoptpet.ui.list.ListStoryViewModel

class AccountFragment : Fragment() {

    private lateinit var binding: FragmentAccountBinding
    private val viewModel by viewModels<AccountViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater , container: ViewGroup? ,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAccountBinding.inflate(inflater , container , false)
        return binding.root
    }

    override fun onViewCreated(view: View , savedInstanceState: Bundle?) {
        super.onViewCreated(view , savedInstanceState)

        binding.btnLogout.setOnClickListener {
            viewModel.logout()
        }
    }
}