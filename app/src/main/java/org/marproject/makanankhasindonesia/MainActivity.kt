package org.marproject.makanankhasindonesia

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import org.marproject.makanankhasindonesia.databinding.ActivityMainBinding
import org.marproject.makanankhasindonesia.favorite.FavoriteFragment
import org.marproject.makanankhasindonesia.home.HomeFragment
import org.marproject.makanankhasindonesia.profile.ProfileFragment
import org.marproject.makanankhasindonesia.search.SearchFragment

class MainActivity : AppCompatActivity() {

    // binding
    private lateinit var binding: ActivityMainBinding

    // utils
    private var currentId: Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // init binding
        binding = ActivityMainBinding.inflate(layoutInflater)

        // setup bottom nav
        binding.bottomNav.apply {
            setItemSelected(R.id.home_menu)
            setOnItemSelectedListener(onItemSelectedListener)
        }

        // set home fragment
        supportFragmentManager.beginTransaction().replace(R.id.home_frame, HomeFragment()).commit()

        // set content view
        setContentView(binding.root)
    }

    private val onItemSelectedListener = object : ChipNavigationBar.OnItemSelectedListener {
        override fun onItemSelected(id: Int) {
            val fragmentCheck = fun(fragmentId: Int, fragment: Fragment) {
                if (currentId != fragmentId) {
                    supportFragmentManager.beginTransaction().replace(R.id.home_frame, fragment).commit()
                }
            }

            when (id) {
                R.id.home_menu -> fragmentCheck(R.id.home_menu, HomeFragment())
                R.id.search_menu -> fragmentCheck(R.id.search_menu, SearchFragment())
                R.id.favorites_menu -> fragmentCheck(R.id.favorites_menu, FavoriteFragment())
                R.id.profile_menu -> fragmentCheck(R.id.profile_menu, ProfileFragment())
            }

            currentId = id
        }

    }
}