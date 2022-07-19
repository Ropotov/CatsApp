package ru.nikita.catsapp.screens

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.snackbar.Snackbar
import ru.nikita.catsapp.R
import ru.nikita.catsapp.databinding.FragmentCatVoteBinding
import ru.nikita.catsapp.model.DataModelItem

class CatVoteFragment : Fragment() {

    private lateinit var binding: FragmentCatVoteBinding
    private var list: ArrayList<DataModelItem> = arrayListOf()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCatVoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProvider(this)[CatVoteViewModel::class.java]
        getCatImage(viewModel)
        binding.btnLike.setOnClickListener {
            updateCatImage(viewModel)
        }
        binding.btnDisLike.setOnClickListener {
            showAlertDialog(viewModel)
        }
    }

    private fun getCatImage(viewModel: CatVoteViewModel) {
        viewModel.getCat()
        viewModel.catList.observe(viewLifecycleOwner, { response ->
            response.body()?.let { list = it } ?: showSnackBar(
                binding.clCatVoteFragment,
                getString(R.string.repeat)
            )
            Glide.with(binding.imageCat)
                .load(list[0].url)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?, p1: Any?, p2: Target<Drawable>?, p3: Boolean
                    ): Boolean {
                        return false
                    }

                    override fun onResourceReady(
                        p1: Drawable?, p2: Any?, p3: Target<Drawable>?, p4: DataSource?, p5: Boolean
                    ): Boolean {
                        binding.progressBar.visibility = View.GONE
                        clickableButton(true)
                        return false
                    }
                })
                .into(binding.imageCat)
        })
    }

    private fun updateCatImage(viewModel: CatVoteViewModel) {
        clickableButton(false)
        binding.progressBar.visibility = View.VISIBLE
        viewModel.getCat()
    }

    private fun showAlertDialog(viewModel: CatVoteViewModel) {

        val listener = DialogInterface.OnClickListener { _, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> getCatImage(viewModel)
                DialogInterface.BUTTON_NEGATIVE -> {
                    showSnackBar(
                        binding.clCatVoteFragment,
                        getString(R.string.text_snack_bar),
                    )
                }
            }
        }

        val dialog = AlertDialog.Builder(requireContext())
            .setCancelable(false)
            .setTitle(getString(R.string.alert_title))
            .setMessage(getString(R.string.alert_message))
            .setNegativeButton(getString(R.string.no), listener)
            .setPositiveButton(getString(R.string.yes), listener)
            .create()
        dialog.show()
    }

    private fun clickableButton(boolean: Boolean) {
        binding.btnLike.isClickable = boolean
        binding.btnDisLike.isClickable = boolean
        binding.btnAddFavorite.isClickable = boolean
    }

    private fun showSnackBar(view: View, string: String) {
        Snackbar.make(view, string, Snackbar.LENGTH_LONG).show()
    }
}