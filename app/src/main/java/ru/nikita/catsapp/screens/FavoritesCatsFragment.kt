package ru.nikita.catsapp.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import ru.nikita.catsapp.databinding.FragmentFavoritesCatsBinding

class FavoritesCatsFragment : Fragment() {

    lateinit var binding: FragmentFavoritesCatsBinding
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: FavoritesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesCatsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProvider(this)[FavoritesViewModel::class.java]
        rvInit()
        viewModel.getFavoritesList()
        viewModel.favoritesList.observe(viewLifecycleOwner, { response ->
            if (response.isSuccessful) {
                response.body()?.let { adapter.favoritesList = it }
                Log.d("TAG", response.code().toString())
            } else {
                Log.d("TAG", response.code().toString())
            }
        })
    }

    private fun rvInit() {
        recyclerView = binding.rvFavoritesFragment
        adapter = FavoritesAdapter()
        recyclerView.adapter = adapter
    }
}