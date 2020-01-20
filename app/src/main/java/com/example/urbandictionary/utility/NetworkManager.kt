package com.example.urbandictionary.utility

import com.example.urbandictionary.datamodel.SearchResultList
import com.example.urbandictionary.datamodel.SearchResult
import com.example.urbandictionary.utility.Constants.Companion.API_KEY
import com.google.gson.Gson
import okhttp3.*


class NetworkManager (private val client: OkHttpClient){

    /*
   private object HOLDER {
       val INSTANCE = NetworkManager()
   }

    companion object {
        val instance:NetworkManager by lazy {HOLDER.INSTANCE}
    }
    */

    // Method make a GET call to fetch searcj result from API
    fun getSearchResult(url: String, callback: Callback): Call {
        val request = Request.Builder()
            //.cacheControl( CacheControl.Builder().onlyIfCached().build())
            .url(url)
            .addHeader("x-rapidapi-host", "mashape-community-urban-dictionary.p.rapidapi.com")
            .addHeader("x-rapidapi-key", API_KEY)
            .get()
            .build()

        val call = client.newCall(request)
        call.enqueue(callback)
        return call
    }
}