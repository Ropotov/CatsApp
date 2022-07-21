package ru.nikita.catsapp.screens

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.snackbar.Snackbar
import ru.nikita.catsapp.R
import ru.nikita.catsapp.databinding.FragmentCatVoteBinding
import ru.nikita.catsapp.model.DataModelItem
import ru.nikita.catsapp.model.PostItem

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
        binding.btnAddFavorite.setOnClickListener {
            viewModel.postCat(PostItem(image_id = list[0].id))
            viewModel.postList.observe(viewLifecycleOwner, { response ->
                if (response.isSuccessful) {
                    showSnackBar(binding.clCatVoteFragment, getString(R.string.add_favorites))
                    Log.d("TAG", response.body().toString())
                    Log.d("TAG", response.code().toString())
                } else {
                    showSnackBar(binding.clCatVoteFragment, getString(R.string.not_add_favorites))
                    Log.d("TAG", response.body().toString())
                    Log.d("TAG", response.code().toString())
                }
            })
        }
    }

    private fun getCatImage(viewModel: CatVoteViewModel) {
        viewModel.getCat()
        viewModel.catList.observe(viewLifecycleOwner, { response ->
            response.body()?.let { list = it } ?: showSnackBar(
                binding.clCatVoteFragment,
                getString(R.string.repeat)
            )
            loadImage()
        })
    }

    private fun loadImage() {
        Glide.with(binding.imageCat)
            .load(list[0].url)
            .placeholder(createProgressDrawable())
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    clickableButton(true)
                    return false
                }
            })
            .into(binding.imageCat)
    }

    private fun createProgressDrawable(): CircularProgressDrawable {
        val progressDrawable = CircularProgressDrawable(requireContext())
        progressDrawable.strokeWidth = 5f
        progressDrawable.centerRadius = 30f
        progressDrawable.start()
        return progressDrawable
    }

    private fun updateCatImage(viewModel: CatVoteViewModel) {
        clickableButton(false)
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