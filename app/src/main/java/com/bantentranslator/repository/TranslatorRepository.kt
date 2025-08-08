package com.bantentranslator.repository

import com.bantentranslator.models.*
import com.bantentranslator.network.RetrofitClient
import retrofit2.Response

class TranslatorRepository {
    
    private val apiService = RetrofitClient.apiService
    
    suspend fun translateWord(word: String, direction: String): Response<TranslationResponse> {
        return apiService.translateWord(word = word, direction = direction)
    }
    
    suspend fun translateText(text: String, direction: String): Response<TranslationResponse> {
        return apiService.translateText(text = text, direction = direction)
    }
    
    suspend fun searchWords(keyword: String, limit: Int): Response<SearchResponse> {
        return apiService.searchWords(keyword = keyword, limit = limit)
    }
    
    suspend fun getRandomWords(limit: Int): Response<RandomWordsResponse> {
        return apiService.getRandomWords(limit = limit)
    }
    
    suspend fun getStats(): Response<StatsResponse> {
        return apiService.getStats()
    }
    
    suspend fun addWord(kataJawa: String, kataIndonesia: String, kategori: String): Response<TranslationResponse> {
        return apiService.addWord(kataJawa = kataJawa, kataIndonesia = kataIndonesia, kategori = kategori)
    }
}