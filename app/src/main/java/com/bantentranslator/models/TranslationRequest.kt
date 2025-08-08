package com.bantentranslator.models

import com.google.gson.annotations.SerializedName

data class TranslationRequest(
    @SerializedName("action")
    val action: String,
    
    @SerializedName("word")
    val word: String? = null,
    
    @SerializedName("text")
    val text: String? = null,
    
    @SerializedName("direction")
    val direction: String
)