package com.bantentranslator.network

import com.bantentranslator.models.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    
    @POST("api.php")
    @FormUrlEncoded
    suspend fun translateWord(
        @Field("action") action: String = "translate_word",
        @Field("word") word: String,
        @Field("direction") direction: String
    ): Response<TranslationResponse>
    
    @POST("api.php")
    @FormUrlEncoded
    suspend fun translateText(
        @Field("action") action: String = "translate_text",
        @Field("text") text: String,
        @Field("direction") direction: String
    ): Response<TranslationResponse>
    
    @GET("api.php")
    suspend fun searchWords(
        @Query("action") action: String = "search",
        @Query("keyword") keyword: String,
        @Query("limit") limit: Int = 20
    ): Response<SearchResponse>
    
    @GET("api.php")
    suspend fun getRandomWords(
        @Query("action") action: String = "random",
        @Query("limit") limit: Int = 10
    ): Response<RandomWordsResponse>
    
    @GET("api.php")
    suspend fun getStats(
        @Query("action") action: String = "stats"
    ): Response<StatsResponse>
    
    @POST("api.php")
    @FormUrlEncoded
    suspend fun addWord(
        @Field("action") action: String = "add_word",
        @Field("kata_jawa") kataJawa: String,
        @Field("kata_indonesia") kataIndonesia: String,
        @Field("kategori") kategori: String = "lainnya"
    ): Response<TranslationResponse>
}