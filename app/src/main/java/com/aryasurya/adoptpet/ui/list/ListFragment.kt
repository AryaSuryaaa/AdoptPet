package com.aryasurya.adoptpet.ui.list

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.aryasurya.adoptpet.R
import com.aryasurya.adoptpet.data.Result
import com.aryasurya.adoptpet.data.remote.response.ListStoryItem
import com.aryasurya.adoptpet.databinding.FragmentListBinding
import com.aryasurya.adoptpet.helper.isInternetAvailable
import com.aryasurya.adoptpet.ui.ViewModelFactory
import com.aryasurya.adoptpet.ui.detailpost.DetailPostActivity
import com.aryasurya.adoptpet.ui.login.LoginActivity
import java.net.SocketTimeoutException

class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
    private val viewModel by viewModels<ListStoryViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }
    private var adapter = ListStoryAdapter()

    override fun onCreateView(
        inflater: LayoutInflater , container: ViewGroup? ,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater , container , false)
        return binding.root
    }

    override fun onViewCreated(view: View , savedInstanceState: Bundle?) {
        super.onViewCreated(view , savedInstanceState)

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvListStory.layoutManager = layoutManager
        binding.rvListStory.adapter = adapter

        showRecyclerView()
        binding.swipeRefreshLayout.setOnRefreshListener {
            showRecyclerView()
        }
    }

    private fun showRecyclerView() {
        binding.swipeRefreshLayout.isRefreshing = false

        if (!isInternetAvailable(requireContext())) {
            Toast.makeText(requireContext(), "No internet connection.", Toast.LENGTH_SHORT).show()
            return
        }
        try {
            viewModel.listStory().observe(viewLifecycleOwner) { story ->
                setListStory(story)
            }
        } catch (e: SocketTimeoutException) {
            Toast.makeText(requireContext(), getString(R.string.server_timeout), Toast.LENGTH_SHORT)
                .show()
        } catch (e: Exception) {
            Toast.makeText(requireContext(),
                getString(R.string.an_error_occurred_please_try_again_later), Toast.LENGTH_SHORT)
                .show()
        }
    }
    private fun setListStory(listStory: List<ListStoryItem>) {
        adapter.submitList(listStory)


        adapter.setOnItemClickCallback(object : ListStoryAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ListStoryItem) {
                val intent = Intent(requireContext(), DetailPostActivity::class.java)
                intent.putExtra("idUser", data.id)
                intent.putExtra("name", data.name)
                intent.putExtra("desc", data.description)
                intent.putExtra("photo", data.photoUrl)
                startActivity(intent)
            }
        })
    }
}