package ru.nikita.catsapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.tabs.TabLayoutMediator
import ru.nikita.catsapp.adapters.PagerAdapter
import ru.nikita.catsapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initial()

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun initial() {
        binding.viewPager.adapter = PagerAdapter(this)
        binding.tabLayout.tabIconTint = null
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.icon = getDrawable(R.drawable.ic_vote)
                    tab.icon?.setTint(ContextCompat.getColor(this, R.color.orange))
                }
                1 -> {
                    tab.icon = getDrawable(R.drawable.ic_favorites)
                    tab.icon?.setTint(ContextCompat.getColor(this, R.color.red))
                }
                else -> {
                    tab.icon = getDrawable(R.drawable.ic_info)
                    tab.icon?.setTint(ContextCompat.getColor(this, R.color.green))
                }
            }
        }.attach()
    }
}