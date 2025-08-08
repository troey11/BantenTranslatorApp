package com.bantentranslator.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bantentranslator.databinding.FragmentRandomWordsBinding
import com.bantentranslator.ui.adapters.WordListAdapter
import com.bantentranslator.viewmodel.TranslatorViewModel

class RandomWordsFragment : Fragment() {
    
    private var _binding: FragmentRandomWordsBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: TranslatorViewModel by viewModels()
    private lateinit var wordAdapter: WordListAdapter
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRandomWordsBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupRecyclerView()
        setupObservers()
        setupClickListeners()
        
        // Load initial random words
        viewModel.getRandomWords()
    }
    
    private fun setupRecyclerView() {
        wordAdapter = WordListAdapter()
        
        binding.recyclerViewWords.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = wordAdapter
        }
    }
    
    private fun setupObservers() {
        viewModel.randomWords.observe(viewLifecycleOwner) { response ->
            response?.let {
                if (it.status == "success" && it.data != null) {
                    wordAdapter.submitList(it.data.words)
                    binding.tvWordCount.text = "Menampilkan ${it.data.count} kata"
                    binding.tvWordCount.visibility = View.VISIBLE
                    
                    if (it.data.words.isEmpty()) {
                        binding.tvNoWords.visibility = View.VISIBLE
                        binding.recyclerViewWords.visibility = View.GONE
                    } else {
                        binding.tvNoWords.visibility = View.GONE
                        binding.recyclerViewWords.visibility = View.VISIBLE
                    }
                } else {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    binding.tvNoWords.visibility = View.VISIBLE
                    binding.recyclerViewWords.visibility = View.GONE
                }
            }
        }
        
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.btnRefresh.isEnabled = !isLoading
        }
        
        viewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
        }
    }
    
    private fun setupClickListeners() {
        binding.btnRefresh.setOnClickListener {
            val limit = binding.etLimit.text.toString().toIntOrNull() ?: 10
            val validLimit = limit.coerceIn(1, 50)
            viewModel.getRandomWords(validLimit)
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}