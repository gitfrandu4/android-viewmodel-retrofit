package com.example.tmdb_app.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tmdb_app.R
import com.example.tmdb_app.models.MovieResume
import com.example.tmdb_app.models.Movies

class MoviesAdapter (val onClick: (MovieResume) -> Unit): RecyclerView.Adapter<MoviesAdapter.ViewHolder>(){

    private var data = mutableListOf<MovieResume>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movies, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    fun updateData(newData: Movies) {
        data.clear()
        data = newData.results.toMutableList()
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private val movieImageView: ImageView = itemView.findViewById(R.id.movieImageView)
        private val movieTitle: TextView = itemView.findViewById(R.id.movieTitle)
        private val movieReleaseDate: TextView = itemView.findViewById(R.id.movieReleaseDate)
        private val movieAverageRating: TextView = itemView.findViewById(R.id.movieAverageRate)


        fun bind(genre: MovieResume) {
            this.movieTitle.text = genre.title
            this.movieReleaseDate.text = genre.release_date
            this.movieAverageRating.text = genre.vote_average.toString()

            Glide.with(itemView.context)
                .load("https://image.tmdb.org/t/p/w500${genre.poster_path}")
                .into(movieImageView)

            itemView.setOnClickListener {
                Log.v("MoviesAdapter", "Movie clicked: ${genre.title}")
                onClick(genre)
            }
        }

    }

}