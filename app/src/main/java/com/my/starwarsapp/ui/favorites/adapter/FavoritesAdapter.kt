package com.my.starwarsapp.ui.favorites.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.my.starwarsapp.data.model.FavoriteCharacter
import com.my.starwarsapp.databinding.ListItemFavoriteBinding

class FavoritesAdapter (private val dataset: List<FavoriteCharacter>):
    RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>() {

    class FavoritesViewHolder(
        private var binding: ListItemFavoriteBinding
    ) : RecyclerView.ViewHolder(binding.root){
        fun bind(character: FavoriteCharacter) {
            binding.textViewName.text = character.name
            binding.textViewGender.text = character.gender
            binding.textViewBirthYear.text = character.birthYear
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return FavoritesViewHolder(
            ListItemFavoriteBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        val character = dataset[position]
        holder.bind(character)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}