package com.bantentranslator.models

import com.google.gson.annotations.SerializedName

data class SearchRequest(
    @SerializedName("action")
    val action: String = "search",
    
    @SerializedName("keyword")
    val keyword: String,
    
    @SerializedName("limit")
    val limit: Int = 20
)

data class SearchResponse(
    @SerializedName("status")
    val status: String,
    
    @SerializedName("code")
    val code: Int,
    
    @SerializedName("message")
    val message: String,
    
    @SerializedName("data")
    val data: SearchData?,
    
    @SerializedName("timestamp")
    val timestamp: String,
    
    @SerializedName("version")
    val version: String
)

data class SearchData(
    @SerializedName("results")
    val results: List<WordEntry>,
    
    @SerializedName("count")
    val count: Int,
    
    @SerializedName("keyword")
    val keyword: String
)