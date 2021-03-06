package org.marproject.makanankhasindonesia.ui.home

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.item_food.view.*
import kotlinx.android.synthetic.main.view_error.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.marproject.makanankhasindonesia.R
import org.marproject.makanankhasindonesia.core.adapter.AdapterCallback
import org.marproject.makanankhasindonesia.core.adapter.AdapterUtils
import org.marproject.makanankhasindonesia.core.data.Resource
import org.marproject.makanankhasindonesia.core.domain.model.Food
import org.marproject.makanankhasindonesia.databinding.FragmentHomeBinding
import org.marproject.makanankhasindonesia.ui.detail.DetailFoodActivity

class HomeFragment : Fragment() {

    // init view model & binding
    private val viewModel: HomeViewModel by viewModel()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // adapter
    private lateinit var drawable: Drawable
    private lateinit var adapter: AdapterUtils<Food>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        // set action bar
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar?.apply {
            title = getString(R.string.app_name)
        }

        // Set Option Menu
        setHasOptionsMenu(true)

        // init adapter
        adapter = AdapterUtils(requireContext())

        // setup adapter
        setupAdapter(binding.rvFood)

        viewModel.food.observe(viewLifecycleOwner, {
            if (it != null) {
                when (it) {
                    is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.action_menu_favorite, menu)
        drawable = menu.getItem(0).icon

        // set icon color
        binding.appBar.addOnOffsetChangedListener(offsetListener)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private val offsetListener =
        AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            var scrollRange = -1
            val wrappedDrawable = DrawableCompat.wrap(drawable)

            if (scrollRange == -1)
                scrollRange = appBarLayout.totalScrollRange

            if (scrollRange + verticalOffset == 0)
                DrawableCompat.setTint(wrappedDrawable, ContextCompat.getColor(requireContext(), android.R.color.white))
             else
                DrawableCompat.setTint(wrappedDrawable, ContextCompat.getColor(requireContext(), R.color.color_primary))
        }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.item_favorite -> {
            val uri = Uri.parse("makanankhasindonesia://favorite")
            startActivity(Intent(Intent.ACTION_VIEW, uri))
            requireActivity().finish()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.appBar.removeOnOffsetChangedListener(offsetListener)
        _binding = null
    }

}

