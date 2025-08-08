package com.bantentranslator.models

import com.google.gson.annotations.SerializedName

data class TranslationResponse(
    @SerializedName("status")
    val status: String,
    
    @SerializedName("code")
    val code: Int,
    
    @SerializedName("message")
    val message: String,
    
    @SerializedName("data")
    val data: TranslationData?,
    
    @SerializedName("timestamp")
    val timestamp: String,
    
    @SerializedName("version")
    val version: String
)

data class TranslationData(
    @SerializedName("success")
    val success: Boolean,
    
    @SerializedName("original")
    val original: String,
    
    @SerializedName("translation")
    val translation: String,
    
    @SerializedName("direction")
    val direction: String,
    
    @SerializedName("translated_words")
    val translatedWords: List<WordTranslation>? = null
)

data class WordTranslation(
    @SerializedName("original")
    val original: String,
    
    @SerializedName("translation")
    val translation: String,
    
    @SerializedName("found")
    val found: Boolean
)