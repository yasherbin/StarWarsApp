package com.my.starwarsapp.ui.characters.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.my.starwarsapp.databinding.FragmentCharactersBinding
import com.my.starwarsapp.ui.base.ViewModelFactory
import com.my.starwarsapp.ui.characters.adapter.CharactersAdapter
import com.my.starwarsapp.ui.characters.viewmodel.CharactersViewModel
import com.my.starwarsapp.utils.Resource
import com.my.starwarsapp.utils.Status

class CharactersFragment : Fragment() {
    private var _binding: FragmentCharactersBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: CharactersAdapter
    private lateinit var charactersViewModel: CharactersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        charactersViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory()
        ).get(CharactersViewModel::class.java)

        _binding = FragmentCharactersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = CharactersAdapter(arrayListOf())
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                binding.recyclerView.context,
                (binding.recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        binding.recyclerView.adapter = adapter

        charactersViewModel.getCharacters().observe(viewLifecycleOwner ,  Observer<Resource<List<com.my.starwarsapp.data.model.Character>>> {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { characters -> renderList(characters) }
                }
                Status.ERROR -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })

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

    private fun renderList(characters: List<com.my.starwarsapp.data.model.Character>) {
        with(adapter) {
            addData(characters)
            notifyDataSetChanged()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}