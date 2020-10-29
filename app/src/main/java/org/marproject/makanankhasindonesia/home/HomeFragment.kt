package org.marproject.makanankhasindonesia.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.item_food.view.*
import kotlinx.android.synthetic.main.view_error.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.marproject.makanankhasindonesia.R
import org.marproject.makanankhasindonesia.core.data.Resource
import org.marproject.makanankhasindonesia.core.domain.model.Food
import org.marproject.makanankhasindonesia.core.ui.adapter.AdapterCallback
import org.marproject.makanankhasindonesia.core.ui.adapter.AdapterUtils
import org.marproject.makanankhasindonesia.databinding.FragmentHomeBinding
import org.marproject.makanankhasindonesia.detail.DetailFoodActivity

class HomeFragment : Fragment() {

    // init view model & binding
    private val viewModel: HomeViewModel by viewModel()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // adapter
    private lateinit var adapter: AdapterUtils<Food>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        // init adapter
        adapter = AdapterUtils(requireContext())

        // setup adapter
        setupAdapter(binding.rvFood)

        viewModel.food.observe(viewLifecycleOwner, {
            if (it != null) {
                when (it) {
                    is Resource.Loading ->  binding.progressBar.visibility = View.VISIBLE
                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        it.data?.let { it1 -> adapter.addData(it1) }
                    }
                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        view_error.visibility = View.VISIBLE
                        tv_error.text = it.message ?: getString(R.string.something_wrong)
                    }
                }
            }
        })

        return binding.root
    }

    private fun setupAdapter(recyclerView: RecyclerView) {
        adapter.adapterCallback(adapterCallback)
            .setLayout(R.layout.item_food)
            .isGridView(2)
            .build(recyclerView)
    }

    private val adapterCallback = object : AdapterCallback<Food> {
        override fun initComponent(itemView: View, data: Food, itemIndex: Int) {
            itemView.tv_name.text = data.name
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

