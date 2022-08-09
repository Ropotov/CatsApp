package ru.nikita.catsapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.nikita.catsapp.databinding.ItemCatBinding
import ru.nikita.catsapp.model.FavoritesDataItem

class FavoritesAdapter : RecyclerView.Adapter<FavoritesAdapter.ViewHolder>() {

    var onCatClickListener: OnCatClickListener? = null

    var favoritesList: ArrayList<FavoritesDataItem> = arrayListOf()
        set(newValue) {
            val diffCallback = DiffCallback(field, newValue)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = newValue
            diffResult.dispatchUpdatesTo(this)
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
        holder.itemView.setOnLongClickListener {
            onCatClickListener?.onCatClick(favoritesList[position])
            true
        }
    }

    override fun getItemCount(): Int = favoritesList.size

    interface OnCatClickListener {
        fun onCatClick(item: FavoritesDataItem) {

        }
    }

    class DiffCallback(
        private val oldList: ArrayList<FavoritesDataItem>,
        private val newList: ArrayList<FavoritesDataItem>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size
        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldCat = oldList[oldItemPosition]
            val newCat = newList[newItemPosition]
            return oldCat.id == newCat.id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return false
        }
    }
}
