package org.marproject.makanankhasindonesia.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.android.synthetic.main.item_favorite.view.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.marproject.makanankhasindonesia.core.adapter.AdapterCallback
import org.marproject.makanankhasindonesia.core.adapter.AdapterUtils
import org.marproject.makanankhasindonesia.core.domain.model.Food
import org.marproject.makanankhasindonesia.favorite.databinding.ActivityFavoriteBinding
import org.marproject.makanankhasindonesia.ui.detail.DetailFoodActivity

class FavoriteActivity : AppCompatActivity() {

    // init binding & view model
    private lateinit var binding: ActivityFavoriteBinding
    private val viewModel: FavoriteViewModel by viewModel()

    // adapter
    private lateinit var adapter: AdapterUtils<Food>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)

        binding.toolbar.apply {
            title = "Favorite food"
            setSupportActionBar(this)
        }

        // visible back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // load modules
        loadKoinModules(favoriteModule)

        // init adapter
        adapter = AdapterUtils(this)

        // setup adapter
        setupAdapter(binding.rvFavorite)

        viewModel.favoriteFood.observe(this, {
            Log.i("testing", it.toString())
            adapter.addData(it)
            view_empty.visibility = if (it.isNotEmpty()) View.GONE else View.VISIBLE
        })

        // set content view
        setContentView(binding.root)
    }

    private fun setupAdapter(recyclerView: RecyclerView) {
        adapter.adapterCallback(adapterCallback)
            .setLayout(R.layout.item_favorite)
            .isVerticalView()
            .build(recyclerView)
    }

    private val adapterCallback = object : AdapterCallback<Food> {
        override fun initComponent(itemView: View, data: Food, itemIndex: Int) {
            itemView.tv_name.text = data.name
            itemView.tv_place.text = data.place
            Glide.with(this@FavoriteActivity)
                .load(data.image)
                .into(itemView.image_food)
        }

        override fun onItemClicked(itemView: View, data: Food, itemIndex: Int) {
            startActivity(
                Intent(this@FavoriteActivity, DetailFoodActivity::class.java)
                    .putExtra(DetailFoodActivity.EXTRA_DATA, data)
            )
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}