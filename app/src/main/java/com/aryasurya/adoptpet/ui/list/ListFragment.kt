package com.aryasurya.adoptpet.ui.list

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
        // Inflate the layout for this fragment
        binding = FragmentListBinding.inflate(inflater , container , false)
        return binding.root
    }


    override fun onViewCreated(view: View , savedInstanceState: Bundle?) {
        super.onViewCreated(view , savedInstanceState)
        viewModel.listStory.observe(viewLifecycleOwner) { result ->


            val layoutManager = LinearLayoutManager(requireContext())
            binding.rvListStory.layoutManager = layoutManager
            binding.rvListStory.adapter = adapter

            when (result) {
                is Result.Loading -> {
                    binding.pbListStory.visibility = View.VISIBLE
                    Toast.makeText(activity, "Loading", Toast.LENGTH_SHORT).show()
                }
                is Result.Success -> {
                    binding.pbListStory.visibility = View.GONE
                    setListStory(result.data)
                    Toast.makeText(activity, "Sukses", Toast.LENGTH_SHORT).show()
                }
                is Result.Error -> {
                    binding.pbListStory.visibility = View.GONE
                    Toast.makeText(requireContext(), "Eror ${result.error}", Toast.LENGTH_SHORT).show()
                }

                else -> {}
            }
        }
    }
    private fun setListStory(listStory: List<ListStoryItem>) {
        adapter.submitList(listStory)


        adapter.setOnItemClickCallback(object : ListStoryAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ListStoryItem) {
                Toast.makeText(requireContext(), "Clicked ${data.name}", Toast.LENGTH_SHORT).show()
            }

        })
    }


}