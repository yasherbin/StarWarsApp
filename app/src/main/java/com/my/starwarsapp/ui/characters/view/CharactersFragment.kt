package com.my.starwarsapp.ui.characters.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.my.starwarsapp.data.model.FavoriteCharacter
import com.my.starwarsapp.databinding.FragmentCharactersBinding
import com.my.starwarsapp.ui.base.BaseFragment
import com.my.starwarsapp.ui.characters.adapter.CharactersAdapter
import com.my.starwarsapp.ui.characters.adapter.OnListItemClickListener
import com.my.starwarsapp.ui.characters.viewmodel.CharactersViewModel
import com.my.starwarsapp.utils.Resource
import com.my.starwarsapp.utils.Status

class CharactersFragment : BaseFragment<FragmentCharactersBinding>() {
    private lateinit var adapter: CharactersAdapter
    private lateinit var charactersViewModel: CharactersViewModel

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentCharactersBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        charactersViewModel =
        ViewModelProvider(requireActivity())[CharactersViewModel::class.java]

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = CharactersAdapter(arrayListOf(), onItemFavoriteClickListener)
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                binding.recyclerView.context,
                (binding.recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        binding.recyclerView.adapter = adapter

        charactersViewModel.getCharacters().observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { characters -> renderList(characters) }
                }
                Status.ERROR -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                }
            }
        }

        binding.search.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })
    }

    private val onItemFavoriteClickListener: OnListItemClickListener =
        object : OnListItemClickListener {

            override fun onItemClick(character: FavoriteCharacter) {
                charactersViewModel.isFavorite(character.name).observe(viewLifecycleOwner, ){
                    if(it==1) {
                        charactersViewModel.deleteFavoriteCharacter(character)
                    } else {
                        charactersViewModel.addFavoriteCharacter(character)
                    }
                }
            }
        }

    private fun renderList(characters: List<com.my.starwarsapp.data.model.Character>) {
        with(adapter) {
            addData(characters)
            notifyDataSetChanged()
        }
    }
}