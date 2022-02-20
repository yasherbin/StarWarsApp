package com.my.starwarsapp.ui.characters.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.my.starwarsapp.data.model.FavoriteCharacter
import com.my.starwarsapp.databinding.ListItemCharacterBinding
import java.util.*

class CharactersAdapter(
    private val characters: ArrayList<com.my.starwarsapp.data.model.Character>,
    val onItemFavoriteClickListener: OnListItemClickListener
) : RecyclerView.Adapter<CharactersAdapter.DataViewHolder>(), Filterable {

    var charactersFilterList = ArrayList<com.my.starwarsapp.data.model.Character>()

    inner class DataViewHolder(private val itemBinding: ListItemCharacterBinding) : RecyclerView.ViewHolder (itemBinding.root) {
        fun bind(character: com.my.starwarsapp.data.model.Character) {
            itemBinding.textViewName.text = character.name
            itemBinding.textViewGender.text = character.gender
            itemBinding.textViewBirthYear.text = character.birthYear
            itemBinding.favButton.setOnClickListener{
                val favoriteCharacter=FavoriteCharacter(0, character.name, character.gender, character.birthYear)
                onItemFavoriteClickListener.onItemClick(favoriteCharacter)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : DataViewHolder {
        val itemBinding = ListItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(itemBinding)
    }

    override fun getItemCount(): Int = charactersFilterList.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(charactersFilterList[position])
    }

    fun addData(list: List<com.my.starwarsapp.data.model.Character>) {
        characters.addAll(list)
        charactersFilterList.addAll(list)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                charactersFilterList = if (charSearch.isEmpty()) {
                    characters
                } else {
                    val resultList = ArrayList<com.my.starwarsapp.data.model.Character>()
                    for (row in characters) {
                        if (row.name.lowercase(Locale.ROOT).contains(charSearch.lowercase(Locale.ROOT))) {
                            resultList.add(row)
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = charactersFilterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                charactersFilterList = results?.values as ArrayList<com.my.starwarsapp.data.model.Character>
                notifyDataSetChanged()
            }
        }
    }
}

interface OnListItemClickListener {
    fun onItemClick(character: FavoriteCharacter)
}
