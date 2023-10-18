package com.aryasurya.adoptpet.ui.list

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.aryasurya.adoptpet.data.Result
import com.aryasurya.adoptpet.data.remote.response.ListStoryItem
import com.aryasurya.adoptpet.databinding.FragmentListBinding
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

        showRecyclerView()
        binding.swipeRefreshLayout.setOnRefreshListener {
            showRecyclerView()
        }
    }

    private fun showRecyclerView() {
        binding.swipeRefreshLayout.isRefreshing = false
        try {
            viewModel.listStory().observe(viewLifecycleOwner) { story ->
                setListStory(story)
            }
        } catch (e: SocketTimeoutException) {
            Toast.makeText(requireContext(), "Waktu habis saat mengambil data dari server.", Toast.LENGTH_SHORT)
                .show()
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Terjadi kesalahan. Silakan coba lagi nanti.", Toast.LENGTH_SHORT)
                .show()
        }
    }
    private fun setListStory(listStory: List<ListStoryItem>) {
        adapter.submitList(listStory)
        binding.rvListStory.adapter = adapter

        adapter.setOnItemClickCallback(object : ListStoryAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ListStoryItem) {
                val intent = Intent(requireContext(), DetailPostActivity::class.java)
                intent.putExtra("idUser", data.id)
                startActivity(intent)
            }
        })
    }
}