package ru.nikita.catsapp.screens

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import ru.nikita.catsapp.R
import ru.nikita.catsapp.databinding.FragmentFavoritesCatsBinding
import ru.nikita.catsapp.model.FavoritesDataItem
import ru.nikita.catsapp.utils.showSnackBar

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
        updateUI(viewModel)
        deleteItem(viewModel)
    }

    override fun onResume() {
        super.onResume()
        val viewModel = ViewModelProvider(this)[FavoritesViewModel::class.java]
        updateUI(viewModel)
        deleteItem(viewModel)
    }
    private fun rvInit() {
        recyclerView = binding.rvFavoritesFragment
        adapter = FavoritesAdapter()
        recyclerView.adapter = adapter
    }

    private fun deleteItem(viewModel: FavoritesViewModel) {
        adapter.onCatClickListener = object : FavoritesAdapter.OnCatClickListener {
            override fun onCatClick(item: FavoritesDataItem) {
                super.onCatClick(item)
                val listener = DialogInterface.OnClickListener { _, which ->
                    when (which) {
                        DialogInterface.BUTTON_POSITIVE -> {
                            viewModel.deleteCarFromFavoritesList(item.id.toString())
                            viewModel.getFavoritesList()
                            updateUI(viewModel)
                        }
                        DialogInterface.BUTTON_NEGATIVE -> {
                            showSnackBar(
                                binding.rvFavoritesFragment,
                                getString(R.string.not_delete_cat)
                            )
                        }
                    }
                }

                val dialog = AlertDialog.Builder(requireContext())
                    .setCancelable(false)
                    .setTitle(getString(R.string.alert_title))
                    .setMessage(getString(R.string.alert_delete_dialog))
                    .setNegativeButton(getString(R.string.no), listener)
                    .setPositiveButton(getString(R.string.yes), listener)
                    .create()
                dialog.show()
            }
        }
    }

    private fun updateUI(viewModel: FavoritesViewModel){
        viewModel.getFavoritesList()
        viewModel.favoritesList.observe(viewLifecycleOwner, { response ->
            if (response.isSuccessful) {
                response.body()?.let { adapter.favoritesList = it }
            }
        })
    }
}