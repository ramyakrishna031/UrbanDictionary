package com.example.urbandictionary.view

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.urbandictionary.R
import com.example.urbandictionary.datamodel.SearchListAdapter
import com.example.urbandictionary.datamodel.SearchResult
import com.example.urbandictionary.utility.Constants
import com.example.urbandictionary.utility.Constants.Companion.SearchResultKey
import kotlinx.android.synthetic.main.activity_search_result.*

class SearchResultActivity : AppCompatActivity() {

    private lateinit var adapter:SearchListAdapter
    private var searchResultList: ArrayList<SearchResult>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)

        var linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager

        // Retrieve passed search result object from intent
        searchResultList = intent.extras?.getSerializable(SearchResultKey) as ArrayList<SearchResult>

        if (searchResultList != null ) {
            adapter = SearchListAdapter(searchResultList!!)
            recyclerView.adapter = adapter
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val inflater:MenuInflater = menuInflater
        inflater.inflate(R.menu.filter_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.title == Constants.ThumbsUp_Menu) {
            applyThumbsUpFilter()
        }
        else if (item.title == Constants.ThumbsDown_Menu) {
            applyThumbsDownFilter()
        }

        return super.onOptionsItemSelected(item)
    }

    // This method sorts thumbup the list and reload recycle view
    fun applyThumbsUpFilter() {
        searchResultList?.sortBy { it.thumbsUp }
        //val sorted = searchResultList?.sortedWith(compareBy({ it.thumbsUp }))
        adapter.notifyDataSetChanged()
    }

    fun applyThumbsDownFilter() {
        searchResultList?.sortBy { it.thumbsDown }
        adapter.notifyDataSetChanged()
    }
}

