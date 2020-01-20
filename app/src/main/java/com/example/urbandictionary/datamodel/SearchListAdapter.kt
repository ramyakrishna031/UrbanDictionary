package com.example.urbandictionary.datamodel

import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.urbandictionary.R

class SearchListAdapter (private val resultList: ArrayList<SearchResult>): RecyclerView.Adapter<SearchResultHolder>() {

    override fun getItemCount(): Int {
        return resultList.size
    }

    override fun onBindViewHolder(holder: SearchResultHolder, position: Int) {
        val searchResult:SearchResult = resultList[position]
        holder.bind(searchResult)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultHolder {
        val inflater = LayoutInflater.from(parent.context)
        return SearchResultHolder(inflater, parent)

    }
}

// View holder class for recycle view
public class SearchResultHolder(inflater: LayoutInflater, parent:ViewGroup): RecyclerView.ViewHolder(inflater.inflate(R.layout.search_result_row_item, parent, false)), View.OnClickListener {

    private var definitionLbl:TextView? = null
    private var thumbsUpLbl:TextView? = null
    private var thumbsDownLbl:TextView? = null

    init {
        definitionLbl = itemView.findViewById(R.id.definitionLbl)
        thumbsUpLbl = itemView.findViewById(R.id.thumbsUpCountLbl)
        thumbsDownLbl = itemView.findViewById(R.id.thumbsDownCountLbl)
    }

    override fun onClick(view: View?) {
    }


    fun bind(search: SearchResult)
    {
        definitionLbl?.text = search.definition
        thumbsUpLbl?.text = search.thumbsUp.toString()
        thumbsDownLbl?.text = search.thumbsDown.toString()
    }

}

