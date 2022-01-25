package com.my.starwarsapp.ui.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.my.starwarsapp.data.model.Character
import com.my.starwarsapp.databinding.ActivityMainBinding
import com.my.starwarsapp.ui.base.ViewModelFactory
import com.my.starwarsapp.ui.main.adapter.MainAdapter
import com.my.starwarsapp.ui.main.viewmodel.MainViewModel
import com.my.starwarsapp.utils.Resource
import com.my.starwarsapp.utils.Status

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MainAdapter
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory()
        ).get(MainViewModel::class.java)

        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MainAdapter(arrayListOf())
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                binding.recyclerView.context,
                (binding.recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        binding.recyclerView.adapter = adapter

        mainViewModel.getCharacters().observe(this ,  Observer<Resource<List<Character>>> {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { characters -> renderList(characters) }
                }
                Status.ERROR -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
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

        setContentView(binding.root)
    }

    private fun renderList(characters: List<Character>) {
        adapter.addData(characters)
        adapter.notifyDataSetChanged()
    }

}