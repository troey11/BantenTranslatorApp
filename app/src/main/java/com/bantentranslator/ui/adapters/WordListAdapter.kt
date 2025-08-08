package com.bantentranslator.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bantentranslator.databinding.ItemWordBinding
import com.bantentranslator.models.WordEntry

class WordListAdapter : ListAdapter<WordEntry, WordListAdapter.WordViewHolder>(WordDiffCallback()) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val binding = ItemWordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WordViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    
    class WordViewHolder(private val binding: ItemWordBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(word: WordEntry) {
            binding.apply {
                tvJavanese.text = word.kataJawa
                tvIndonesian.text = word.kataIndonesia
                tvCategory.text = word.kategori ?: "Lainnya"
            }
        }
    }
    
    class WordDiffCallback : DiffUtil.ItemCallback<WordEntry>() {
        override fun areItemsTheSame(oldItem: WordEntry, newItem: WordEntry): Boolean {
            return oldItem.id == newItem.id
        }
        
        override fun areContentsTheSame(oldItem: WordEntry, newItem: WordEntry): Boolean {
            return oldItem == newItem
        }
    }
}