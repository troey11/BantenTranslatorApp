package com.bantentranslator.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bantentranslator.models.*
import com.bantentranslator.repository.TranslatorRepository
import kotlinx.coroutines.launch

class TranslatorViewModel : ViewModel() {
    
    private val repository = TranslatorRepository()
    
    private val _translationResult = MutableLiveData<TranslationResponse>()
    val translationResult: LiveData<TranslationResponse> = _translationResult
    
    private val _searchResults = MutableLiveData<SearchResponse>()
    val searchResults: LiveData<SearchResponse> = _searchResults
    
    private val _randomWords = MutableLiveData<RandomWordsResponse>()
    val randomWords: LiveData<RandomWordsResponse> = _randomWords
    
    private val _stats = MutableLiveData<StatsResponse>()
    val stats: LiveData<StatsResponse> = _stats
    
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error
    
    fun translateWord(word: String, direction: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.translateWord(word, direction)
                if (response.isSuccessful) {
                    _translationResult.value = response.body()
                } else {
                    _error.value = "Error: ${response.code()} ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = "Network error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun translateText(text: String, direction: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.translateText(text, direction)
                if (response.isSuccessful) {
                    _translationResult.value = response.body()
                } else {
                    _error.value = "Error: ${response.code()} ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = "Network error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun searchWords(keyword: String, limit: Int = 20) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.searchWords(keyword, limit)
                if (response.isSuccessful) {
                    _searchResults.value = response.body()
                } else {
                    _error.value = "Error: ${response.code()} ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = "Network error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun getRandomWords(limit: Int = 10) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.getRandomWords(limit)
                if (response.isSuccessful) {
                    _randomWords.value = response.body()
                } else {
                    _error.value = "Error: ${response.code()} ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = "Network error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun getStats() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.getStats()
                if (response.isSuccessful) {
                    _stats.value = response.body()
                } else {
                    _error.value = "Error: ${response.code()} ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = "Network error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun addWord(kataJawa: String, kataIndonesia: String, kategori: String = "lainnya") {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.addWord(kataJawa, kataIndonesia, kategori)
                if (response.isSuccessful) {
                    _translationResult.value = response.body()
                } else {
                    _error.value = "Error: ${response.code()} ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = "Network error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}