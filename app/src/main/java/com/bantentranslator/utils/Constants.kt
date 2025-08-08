package com.bantentranslator.utils

object Constants {
    // Update this with your actual API base URL
    const val BASE_URL = "http://115.85.65.42:8002/trans/"
    const val API_ENDPOINT = "api.php"
    
    // Translation directions
    const val DIRECTION_JV_TO_ID = "jv2id"
    const val DIRECTION_ID_TO_JV = "id2jv"
    
    // Request limits
    const val MAX_TEXT_LENGTH = 5000
    const val MAX_SEARCH_RESULTS = 100
    const val MAX_RANDOM_WORDS = 50
}