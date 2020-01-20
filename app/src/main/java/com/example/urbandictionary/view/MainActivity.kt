package com.example.urbandictionary.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.urbandictionary.R
import com.example.urbandictionary.datamodel.SearchResultList
import com.example.urbandictionary.utility.Constants
import com.example.urbandictionary.utility.NetworkManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Response
import java.io.IOException
import android.content.Intent
import android.graphics.Color
import android.view.View
import android.widget.SearchView
import com.example.urbandictionary.R.color.search_button_background_disabled


class MainActivity : AppCompatActivity() {

    lateinit var searchText:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        disableSearchButton()

        // Query listener which determines search button enable or disable status
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(str: String?): Boolean {
                if (str?.length != 0) {
                    enableSearchButton()
                    return true
                }
                else
                {
                    disableSearchButton()
                    return true
                }
            }

        })

        // Set search button action
        searchButton.setOnClickListener {
            progressBar.visibility = View.VISIBLE

            // Get entered searc text from Search Widget
            searchText = searchView.query.toString()
            if (searchText.length == 0 || searchText.contentEquals(""))
            {
                // Show error message
                Toast.makeText(applicationContext, Constants.error_message_empty, Toast.LENGTH_SHORT)
            }
            else
            {
                retrieveSearchResult(searchText)
            }


        }
    }

    // Method to enable search button when user entered some text in search view
    fun enableSearchButton(){
        searchButton.setBackgroundResource(R.drawable.search_button_enabled)
        searchButton.isEnabled = true
        searchButton.isClickable = true
    }

    // Method to enable search button when search text is empty
    fun disableSearchButton() {
        searchButton.setBackgroundResource(R.drawable.search_button_disabled)
        //searchButton.setBackground(resources.getDrawable(R.drawable.search_button_disabled))
        searchButton.isEnabled = false
        searchButton.isClickable = false
    }

    // Method to retrieve search result when search button clicked
    fun retrieveSearchResult(searchText: String) {
        Thread(BackgroundFetcher()).start()
    }

    inner class BackgroundFetcher:Runnable {
        override fun run() {
            val serverURLWithQueryString = String.format("%s?term=%s", Constants.SERVER_URL, searchText)
            var client = OkHttpClient()
            var networkManager = NetworkManager(client)
            networkManager.getSearchResult(serverURLWithQueryString, object: Callback {

                // Failure callback method
                override fun onFailure(call: Call, e: IOException) {

                    runOnUiThread {

                        progressBar.visibility = View.GONE

                        // Show error message
                        Toast.makeText(applicationContext, Constants.NETWORK_ERROR, Toast.LENGTH_SHORT)
                    }

                }
                // Success callback method
                override fun onResponse(call: Call, response: Response) {

                    // Parse response and convert the response into data model
                    System.out.println("Response code: " + response.code)
                    val responseJson = response.body?.string()
                    val gson = Gson()
                    val resultList = gson.fromJson(responseJson, SearchResultList::class.java)

                    runOnUiThread {

                        progressBar.visibility = View.GONE

                        // Show search result activity
                        val searchResultActivityIntent = Intent(applicationContext, SearchResultActivity::class.java)
                        searchResultActivityIntent.putExtra(Constants.SearchResultKey, resultList.list)
                        startActivity(searchResultActivityIntent)
                    }

                }
            })
        }
    }
}
