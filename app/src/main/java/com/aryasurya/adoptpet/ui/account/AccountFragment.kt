package com.aryasurya.adoptpet.ui.account

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.aryasurya.adoptpet.R
import com.aryasurya.adoptpet.data.Result
import com.aryasurya.adoptpet.data.remote.response.ListStoryItem
import com.aryasurya.adoptpet.databinding.FragmentAccountBinding
import com.aryasurya.adoptpet.ui.ViewModelFactory
import com.aryasurya.adoptpet.ui.detailpost.DetailPostActivity

class AccountFragment : Fragment() {

    private lateinit var binding: FragmentAccountBinding
    private val viewModel by viewModels<AccountViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }

    private var adapter = AccountAdapter()

    private var nameUser = ""

    override fun onCreateView(
        inflater: LayoutInflater , container: ViewGroup? ,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAccountBinding.inflate(inflater , container , false)
        return binding.root
    }

    override fun onViewCreated(view: View , savedInstanceState: Bundle?) {
        super.onViewCreated(view , savedInstanceState)

        binding.btnLogout.setOnClickListener {
            val builder = AlertDialog.Builder(requireActivity())
            builder.setMessage(getString(R.string.are_you_sure_you_want_to_log_out))
            builder.setPositiveButton(getString(R.string.yes)) { dialog , _ ->
                viewModel.logout()
                dialog.dismiss()
            }
            builder.setNegativeButton(getString(R.string.no)) { dialog , _ ->
                dialog.dismiss()
            }
            val dialog = builder.create()
            dialog.show()
        }

        val layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvMyListStory.layoutManager = layoutManager
        binding.rvMyListStory.adapter = adapter

        viewModel.getSession().observe(requireActivity()) {
            nameUser = it.username
        }

        viewModel.getMyStory(nameUser).observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.pbMyListStory.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.pbMyListStory.visibility = View.GONE

                    if (result.data.isNotEmpty()) {
                        binding.tvNoData.visibility = View.VISIBLE
                        setListMyStory(result.data)
                    } else {
                        binding.tvNoData.visibility = View.VISIBLE
                    }
                }
                is Result.Error -> {
                    binding.pbMyListStory.visibility = View.GONE
                }
                else -> {}
            }
        }
    }

    private fun setListMyStory(listStory: List<ListStoryItem>) {
        adapter.submitList(listStory)

        adapter.setOnItemClickCallback(object : AccountAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ListStoryItem) {
                val intent = Intent(requireContext(), DetailPostActivity::class.java)
                intent.putExtra("idUser", data.id)
                startActivity(intent)
            }
        })
    }


}