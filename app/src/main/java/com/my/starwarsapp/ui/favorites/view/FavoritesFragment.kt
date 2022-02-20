package com.my.starwarsapp.ui.favorites.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.my.starwarsapp.databinding.FragmentFavoritesBinding
import com.my.starwarsapp.ui.base.BaseFragment
import com.my.starwarsapp.ui.favorites.adapter.FavoritesAdapter
import com.my.starwarsapp.ui.favorites.viewmodel.FavoritesViewModel

class FavoritesFragment: BaseFragment<FragmentFavoritesBinding>() {

    private lateinit var favoritesViewModel: FavoritesViewModel

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentFavoritesBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoritesViewModel =
            ViewModelProvider(requireActivity())[FavoritesViewModel::class.java]

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        favoritesViewModel.allFavorites.observe(viewLifecycleOwner) { allFavorites ->
            binding.recyclerView.adapter = FavoritesAdapter(allFavorites)
        }
    }


}