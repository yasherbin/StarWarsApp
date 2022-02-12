package com.my.starwarsapp.ui.starships.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.my.starwarsapp.data.model.Starship
import com.my.starwarsapp.databinding.ListItemStarshipBinding

class StarshipsAdapter(
    private val starships: ArrayList<Starship>
) : RecyclerView.Adapter<StarshipsAdapter.DataViewHolder>(){

    class DataViewHolder(private val itemBinding: ListItemStarshipBinding) : RecyclerView.ViewHolder (itemBinding.root) {
        fun bind(starship: Starship) {
            itemBinding.shipName.text = starship.name
            itemBinding.shipModel.text = starship.model
            itemBinding.shipClass.text = starship.starshipClass
            itemBinding.shipCapacity.text = starship.capacity
            itemBinding.shipManufacturer.text = starship.manufacturer
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : DataViewHolder {
        val itemBinding = ListItemStarshipBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(itemBinding)
    }

    override fun getItemCount(): Int = starships.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(starships[position])
    }

    fun addData(list: List<Starship>) {
        starships.addAll(list)
    }

}
