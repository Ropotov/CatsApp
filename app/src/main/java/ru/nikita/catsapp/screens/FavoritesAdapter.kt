package ru.nikita.catsapp.screens

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.nikita.catsapp.databinding.ItemCatBinding
import ru.nikita.catsapp.model.FavoritesDataItem

class FavoritesAdapter : RecyclerView.Adapter<FavoritesAdapter.ViewHolder>() {

    var favoritesList: ArrayList<FavoritesDataItem> = arrayListOf()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    class ViewHolder(private val binding: ItemCatBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: FavoritesDataItem) {
            Glide
                .with(binding.imgFavorites)
                .load(position.image.url)
                .into(binding.imgFavorites)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder((binding))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(favoritesList[position])
    }

    override fun getItemCount(): Int = favoritesList.size
}