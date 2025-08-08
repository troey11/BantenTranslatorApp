package com.bantentranslator.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bantentranslator.databinding.ItemSearchResultBinding
import com.bantentranslator.models.WordEntry

class SearchResultAdapter(
    private val onItemClick: (WordEntry) -> Unit
) : ListAdapter<WordEntry, SearchResultAdapter.SearchResultViewHolder>(WordDiffCallback()) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        val binding = ItemSearchResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchResultViewHolder(binding, onItemClick)
    }
    
    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    
    class SearchResultViewHolder(
        private val binding: ItemSearchResultBinding,
        private val onItemClick: (WordEntry) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        
        fun bind(word: WordEntry) {
            binding.apply {
                tvJavanese.text = word.kataJawa
                tvIndonesian.text = word.kataIndonesia
                tvCategory.text = word.kategori ?: "Lainnya"
                
                root.setOnClickListener {
                    onItemClick(word)
                }
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

// ============================================
// LAYOUT FILES
// ============================================