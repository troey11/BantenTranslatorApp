package com.bantentranslator.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bantentranslator.databinding.FragmentStatsBinding
import com.bantentranslator.viewmodel.TranslatorViewModel

class StatsFragment : Fragment() {
    
    private var _binding: FragmentStatsBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: TranslatorViewModel by viewModels()
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatsBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupObservers()
        setupClickListeners()
        
        // Load initial stats
        viewModel.getStats()
    }
    
    private fun setupObservers() {
        viewModel.stats.observe(viewLifecycleOwner) { response ->
            response?.let {
                if (it.status == "success" && it.data != null) {
                    binding.apply {
                        tvTotalWords.text = it.data.totalWords.toString()
                        tvSupportedLanguages.text = it.data.supportedLanguages.toString()
                        tvDirections.text = it.data.directions.joinToString(", ")
                        tvApiVersion.text = it.data.appVersion
                        
                        cardStats.visibility = View.VISIBLE
                    }
                } else {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    binding.cardStats.visibility = View.GONE
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
            viewModel.getStats()
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}