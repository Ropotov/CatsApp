package ru.nikita.catsapp.screens

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import ru.nikita.catsapp.R
import ru.nikita.catsapp.databinding.FragmentCatVoteBinding
import ru.nikita.catsapp.model.DataModelItem
import ru.nikita.catsapp.model.PostItem
import ru.nikita.catsapp.utils.showSnackBar

class CatVoteFragment : Fragment() {

    private lateinit var binding: FragmentCatVoteBinding
    private val viewModel: CatVoteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCatVoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadCatImage(viewModel)
        binding.btnLike.setOnClickListener {
            clickableButton(false)
            loadCatImage(viewModel)
        }
        binding.btnDisLike.setOnClickListener {
            clickableButton(false)
            showAlertDialog(viewModel)
        }
        binding.btnAddFavorite.setOnClickListener {
            clickableButton(false)
            postCatImage(viewModel)
        }
    }

    private fun postCatImage(viewModel: CatVoteViewModel) {
        lateinit var postItem: PostItem
        viewModel.catList.observe(viewLifecycleOwner, { response ->
            response.body()?.let { postItem = PostItem(image_id = it[0].id) }
        })
        viewModel.postCat(postItem)
        viewModel.postList.observe(viewLifecycleOwner, { response ->
            if (response.isSuccessful) {
                showSnackBar(binding.clCatVoteFragment, getString(R.string.add_favorites))
            } else {
                showSnackBar(binding.clCatVoteFragment, getString(R.string.not_add_favorites))
            }
        })
        loadCatImage(viewModel)
    }

    private fun loadImage(catUrl: String?) {
        Glide.with(binding.imageCat)
            .load(catUrl)
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

    private fun loadCatImage(viewModel: CatVoteViewModel) {
        viewModel.getCat()
        viewModel.catList.observe(viewLifecycleOwner, { response ->
            response.body()?.let { loadImage(it[0].url) }
        })

    }

    private fun showAlertDialog(viewModel: CatVoteViewModel) {

        val listener = DialogInterface.OnClickListener { _, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> loadCatImage(viewModel)
                DialogInterface.BUTTON_NEGATIVE -> {
                    showSnackBar(
                        binding.clCatVoteFragment,
                        getString(R.string.text_snack_bar),
                    )
                    clickableButton(true)
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
        binding.btnLike.isEnabled = boolean
        binding.btnDisLike.isEnabled = boolean
        binding.btnAddFavorite.isEnabled = boolean
    }
}