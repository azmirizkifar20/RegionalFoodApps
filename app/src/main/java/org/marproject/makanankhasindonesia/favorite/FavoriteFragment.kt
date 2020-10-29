package org.marproject.makanankhasindonesia.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_favorite.*
import kotlinx.android.synthetic.main.item_favorite.view.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.marproject.makanankhasindonesia.R
import org.marproject.makanankhasindonesia.core.domain.model.Food
import org.marproject.makanankhasindonesia.core.ui.adapter.AdapterCallback
import org.marproject.makanankhasindonesia.core.ui.adapter.AdapterUtils
import org.marproject.makanankhasindonesia.databinding.FragmentFavoriteBinding
import org.marproject.makanankhasindonesia.detail.DetailFoodActivity

class FavoriteFragment : Fragment() {

    // init view model & binding
    private val viewModel: FavoriteViewModel by viewModel()
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    // adapter
    private lateinit var adapter: AdapterUtils<Food>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        // init adapter
        adapter = AdapterUtils(requireContext())

        // setup adapter
        setupAdapter(binding.rvFavorite)

        viewModel.favoriteFood.observe(viewLifecycleOwner, {
            adapter.addData(it)
            view_empty.visibility = if (it.isNotEmpty()) View.GONE else View.VISIBLE
        })

        return binding.root
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
            Glide.with(requireContext())
                .load(data.image)
                .into(itemView.image_food)
        }

        override fun onItemClicked(itemView: View, data: Food, itemIndex: Int) {
            startActivity(
                Intent(activity, DetailFoodActivity::class.java)
                    .putExtra(DetailFoodActivity.EXTRA_DATA, data)
            )
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}