package ru.nikita.catsapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.nikita.catsapp.screens.AboutFragment
import ru.nikita.catsapp.screens.CatVoteFragment
import ru.nikita.catsapp.screens.FavoritesCatsFragment

class PagerAdapter(fragmentActivity: FragmentActivity)
    : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> CatVoteFragment()
            1 -> FavoritesCatsFragment()
            else -> AboutFragment()
        }
    }
}