package com.bantentranslator.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bantentranslator.R
import com.bantentranslator.databinding.FragmentTranslateBinding
import com.bantentranslator.utils.Constants
import com.bantentranslator.viewmodel.TranslatorViewModel
import android.app.Activity
import android.content.Intent
import android.speech.RecognizerIntent
import androidx.activity.result.contract.ActivityResultContracts


class TranslateFragment : Fragment() {

    private var _binding: FragmentTranslateBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TranslatorViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTranslateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSpinner()
        setupObservers()
        setupClickListeners()
    }

    private fun setupSpinner() {
        val directions = arrayOf("Nalek Banten → Indonesia", "Indonesia → Nalek Banten")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, directions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerDirection.adapter = adapter
    }

    private val voiceInputLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK && result.data != null) {
            val matches = result.data!!.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            matches?.let {
                if (it.isNotEmpty()) {
                    binding.etText.setText(it[0]) // Set hasil suara ke EditText
                }
            }
        }
    }

    private fun setupObservers() {
        viewModel.translationResult.observe(viewLifecycleOwner) { response ->
            response?.let {
                if (it.status == "success" && it.data != null) {
                    binding.tvResult.text = it.data.translation
                    binding.tvOriginal.text = it.data.original
                    binding.cardResult.visibility = View.VISIBLE
                } else {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    binding.cardResult.visibility = View.GONE
                }
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.btnTranslateText.isEnabled = !isLoading
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setupClickListeners() {
        binding.btnTranslateText.setOnClickListener {
            val text = binding.etText.text.toString().trim()
            if (text.isNotEmpty()) {
                if (text.length <= Constants.MAX_TEXT_LENGTH) {
                    val direction = getSelectedDirection()
                    viewModel.translateText(text, direction)
                } else {
                    Toast.makeText(context, "Teks terlalu panjang. Maksimal ${Constants.MAX_TEXT_LENGTH} karakter", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Masukkan teks yang ingin diterjemahkan", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnVoiceInput.setOnClickListener {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                putExtra(RecognizerIntent.EXTRA_LANGUAGE, "id-ID") // bahasa Indonesia
                putExtra(RecognizerIntent.EXTRA_PROMPT, "Ucapkan teks yang ingin diterjemahkan")
            }
            voiceInputLauncher.launch(intent)
        }

        binding.btnClear.setOnClickListener {
            binding.etText.text?.clear()
            binding.cardResult.visibility = View.GONE
        }

        binding.btnSwapDirection.setOnClickListener {
            val currentSelection = binding.spinnerDirection.selectedItemPosition
            val newSelection = if (currentSelection == 0) 1 else 0
            binding.spinnerDirection.setSelection(newSelection)
        }
    }

    private fun getSelectedDirection(): String {
        return when (binding.spinnerDirection.selectedItemPosition) {
            0 -> Constants.DIRECTION_JV_TO_ID // Jawa → Indonesia
            1 -> Constants.DIRECTION_ID_TO_JV // Indonesia → Jawa
            else -> Constants.DIRECTION_JV_TO_ID
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
