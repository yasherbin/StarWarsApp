package com.my.starwarsapp.ui.starships.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.my.starwarsapp.data.model.Starship
import com.my.starwarsapp.databinding.FragmentStarshipsBinding
import com.my.starwarsapp.ui.base.BaseFragment
import com.my.starwarsapp.ui.base.ViewModelFactory
import com.my.starwarsapp.ui.starships.adapter.StarshipsAdapter
import com.my.starwarsapp.ui.starships.viewmodel.StarshipsViewModel
import com.my.starwarsapp.utils.Status

class StarshipsFragment : BaseFragment<FragmentStarshipsBinding>() {
    private lateinit var adapter: StarshipsAdapter
    private lateinit var starshipsViewModel: StarshipsViewModel

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentStarshipsBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        starshipsViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory()
        ).get(StarshipsViewModel::class.java)

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = StarshipsAdapter(arrayListOf())
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                binding.recyclerView.context,
                (binding.recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        binding.recyclerView.adapter = adapter
        binding.button.setOnClickListener {
            starshipsViewModel.getStarships(binding.search.text.toString()).observe(
                viewLifecycleOwner, {
                    when (it.status) {
                        Status.SUCCESS -> {
                            it.data?.let { starships -> renderList(starships) }
                        }
                        Status.ERROR -> {
                            Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                        }
                    }
                })
        }
    }

    private fun renderList(characters: List<Starship>) {
        with(adapter) {
            addData(characters)
            notifyDataSetChanged()
        }
    }
}