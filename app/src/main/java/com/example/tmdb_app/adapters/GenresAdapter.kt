package com.example.tmdb_app.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb_app.R
import com.example.tmdb_app.models.Genre
import com.example.tmdb_app.models.Genres

class GenresAdapter (val onClick: (Genre) -> Unit): RecyclerView.Adapter<GenresAdapter.ViewHolder>(){

    var data = mutableListOf<Genre>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_genres, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    fun updateData(newData: Genres) {
        data.clear()
        data = newData.genres.toMutableList()
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private val genre: TextView = itemView.findViewById(R.id.genreName)
        private val card: View = itemView.findViewById(R.id.genreCardView)

        fun bind(genre: Genre) {
            this.genre.text = genre.name
            card.setOnClickListener {
                Log.v("GenresAdapter", "Genre clicked: ${genre.name}")
                onClick(genre)
            }
        }
    }
}