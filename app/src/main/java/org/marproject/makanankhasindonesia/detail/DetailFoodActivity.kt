package org.marproject.makanankhasindonesia.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import org.marproject.makanankhasindonesia.R
import org.marproject.makanankhasindonesia.core.domain.model.Food
import org.marproject.makanankhasindonesia.core.ui.ViewModelFactory
import org.marproject.makanankhasindonesia.databinding.ActivityDetailFoodBinding

class DetailFoodActivity : AppCompatActivity() {

    // binding & view model
    private lateinit var viewModel: DetailFoodViewModel
    private lateinit var binding: ActivityDetailFoodBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // init binding
        binding = ActivityDetailFoodBinding.inflate(layoutInflater)

        // set action bar
        setSupportActionBar(binding.toolbar)

        // init view model
        val factory =  ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[DetailFoodViewModel::class.java]

        // get data intent
        val food = intent.getParcelableExtra<Food>(EXTRA_DATA)

        // init ui
        initUI(food)

        setContentView(binding.root)
    }

    private fun initUI(food: Food?) {
        food?.let {
            supportActionBar?.apply {
                title = food.name
                setDisplayHomeAsUpEnabled(true)
            }
            binding.contentDetail.tvDescription.text = food.description

            // food image
            Glide.with(this)
                .load(food.image)
                .into(binding.imageFood)

            // status favorite
            var status = food.isFavorite
            setStatusFavorite(status)

            // set favorite
            binding.fabFavorite.setOnClickListener {
                status = !status
                setStatusFavorite(status)
                viewModel.setFavoriteFood(food, status)
            }
        }
    }

    private fun setStatusFavorite(status: Boolean) {
        if (status)
            binding.fabFavorite.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_white))
        else
            binding.fabFavorite.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_not_favorite_white))
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}