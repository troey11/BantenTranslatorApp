package com.bantentranslator.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class WordEntry(
    @SerializedName("id")
    val id: Int? = null,
    
    @SerializedName("kata_jawa")
    val kataJawa: String,
    
    @SerializedName("kata_indonesia")
    val kataIndonesia: String,
    
    @SerializedName("kategori")
    val kategori: String? = null
) : Parcelable

data class RandomWordsResponse(
    @SerializedName("status")
    val status: String,
    
    @SerializedName("code")
    val code: Int,
    
    @SerializedName("message")
    val message: String,
    
    @SerializedName("data")
    val data: RandomWordsData?,
    
    @SerializedName("timestamp")
    val timestamp: String,
    
    @SerializedName("version")
    val version: String
)

data class RandomWordsData(
    @SerializedName("words")
    val words: List<WordEntry>,
    
    @SerializedName("count")
    val count: Int
)

data class StatsResponse(
    @SerializedName("status")
    val status: String,
    
    @SerializedName("code")
    val code: Int,
    
    @SerializedName("message")
    val message: String,
    
    @SerializedName("data")
    val data: StatsData?,
    
    @SerializedName("timestamp")
    val timestamp: String,
    
    @SerializedName("version")
    val version: String
)

data class StatsData(
    @SerializedName("total_words")
    val totalWords: Int,
    
    @SerializedName("supported_languages")
    val supportedLanguages: Int,
    
    @SerializedName("directions")
    val directions: List<String>,
    
    @SerializedName("app_version")
    val appVersion: String
)