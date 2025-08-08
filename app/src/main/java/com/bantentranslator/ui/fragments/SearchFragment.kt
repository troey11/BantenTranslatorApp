package com.bantentranslator.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bantentranslator.databinding.FragmentSearchBinding
import com.bantentranslator.models.WordEntry
import com.bantentranslator.ui.adapters.SearchResultAdapter
import com.bantentranslator.viewmodel.TranslatorViewModel

class SearchFragment : Fragment() {
    
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: TranslatorViewModel by viewModels()
    private lateinit var searchAdapter: SearchResultAdapter
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupRecyclerView()
        setupObservers()
        setupClickListeners()
    }
    
    private fun setupRecyclerView() {
        searchAdapter = SearchResultAdapter { word ->
            // Handle word item click
            Toast.makeText(context, "${word.kataJawa} = ${word.kataIndonesia}", Toast.LENGTH_SHORT).show()
        }
        
        binding.recyclerViewResults.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = searchAdapter
        }
    }
    
    private fun setupObservers() {
        viewModel.searchResults.observe(viewLifecycleOwner) { response ->
            response?.let {
                if (it.status == "success" && it.data != null) {
                    searchAdapter.submitList(it.data.results)
                    binding.tvResultCount.text = "Ditemukan ${it.data.count} hasil"
                    binding.tvResultCount.visibility = View.VISIBLE
                    
                    if (it.data.results.isEmpty()) {
                        binding.tvNoResults.visibility = View.VISIBLE
                        binding.recyclerViewResults.visibility = View.GONE
                    } else {
                        binding.tvNoResults.visibility = View.GONE
                        binding.recyclerViewResults.visibility = View.VISIBLE
                    }
                } else {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    binding.tvNoResults.visibility = View.VISIBLE
                    binding.recyclerViewResults.visibility = View.GONE
                    binding.tvResultCount.visibility = View.GONE
                }
            }
        }
        
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.btnSearch.isEnabled = !isLoading
        }
        
        viewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                binding.tvNoResults.visibility = View.VISIBLE
                binding.recyclerViewResults.visibility = View.GONE
                binding.tvResultCount.visibility = View.GONE
            }
        }
    }
    
    private fun setupClickListeners() {
        binding.btnSearch.setOnClickListener {
            val keyword = binding.etSearch.text.toString().trim()
            if (keyword.isNotEmpty()) {
                val limit = binding.etLimit.text.toString().toIntOrNull() ?: 20
                val validLimit = limit.coerceIn(1, 100)
                viewModel.searchWords(keyword, validLimit)
            } else {
                Toast.makeText(context, "Masukkan kata kunci pencarian", Toast.LENGTH_SHORT).show()
            }
        }
        
        binding.btnClear.setOnClickListener {
            binding.etSearch.text?.clear()
            binding.etLimit.text?.clear()
            searchAdapter.submitList(emptyList())
            binding.tvNoResults.visibility = View.GONE
            binding.recyclerViewResults.visibility = View.GONE
            binding.tvResultCount.visibility = View.GONE
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}