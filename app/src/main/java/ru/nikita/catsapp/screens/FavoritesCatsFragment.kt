package ru.nikita.catsapp.screens

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import ru.nikita.catsapp.R
import ru.nikita.catsapp.adapters.FavoritesAdapter
import ru.nikita.catsapp.databinding.FragmentFavoritesCatsBinding
import ru.nikita.catsapp.model.FavoritesDataItem
import ru.nikita.catsapp.utils.showSnackBar

class FavoritesCatsFragment : Fragment() {

    lateinit var binding: FragmentFavoritesCatsBinding
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: FavoritesAdapter
    private val viewModel: FavoritesViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesCatsBinding.inflate(inflater, container, false)
        rvInit()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        updateUI(viewModel)
        adapter.onCatClickListener = object : FavoritesAdapter.OnCatClickListener {
            override fun onCatClick(item: FavoritesDataItem) {
                super.onCatClick(item)
                showAlertDialog(item)
            }
        }
    }

    private fun rvInit() {
        recyclerView = binding.rvFavoritesFragment
        adapter = FavoritesAdapter()
        recyclerView.adapter = adapter
        val itemAnimator = binding.rvFavoritesFragment.itemAnimator
        if (itemAnimator is DefaultItemAnimator) {
            itemAnimator.supportsChangeAnimations = false
        }
    }


    private fun updateUI(viewModel: FavoritesViewModel) {
        viewModel.getFavoritesList()
        viewModel.favoritesList.observe(viewLifecycleOwner, { response ->
            if (response.isSuccessful) {
                response.body()?.let { adapter.favoritesList = it }
            }
        })
    }

    private fun showAlertDialog(item: FavoritesDataItem) {
        val listener = DialogInterface.OnClickListener { _, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    viewModel.deleteCarFromFavoritesList(item.id.toString())
                    viewModel.deleteResponse.observe(viewLifecycleOwner, { response ->
                        if (response.isSuccessful) updateUI(viewModel)
                    })
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
