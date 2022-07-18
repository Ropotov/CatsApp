package ru.nikita.catsapp.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
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
        viewModel.getCat()
        viewModel.catList.observe(viewLifecycleOwner, { response ->
            response.body()?.let { list = it }
            Glide.with(binding.imageCat).load(list[0].url).into(binding.imageCat)
        })
        binding.btnLike.setOnClickListener {
            viewModel.getCat()
        }
    }
}