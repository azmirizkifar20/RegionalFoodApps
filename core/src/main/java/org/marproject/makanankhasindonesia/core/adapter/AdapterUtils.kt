package org.marproject.makanankhasindonesia.core.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.properties.Delegates

class AdapterUtils<T>(
    private var context: Context
) : RecyclerView.Adapter<AdapterUtils<T>.ViewHolder>(),
    AdapterUtilsInterface<T>,
    Filterable {

    // utils
    var listData = mutableListOf<T>()
    var currentList = mutableListOf<T>()
    private var filterable: Boolean = false
    private var layout by Delegates.notNull<Int>()
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapterCallback: AdapterCallback<T>

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun getItemCount(): Int {
        return if (filterable) currentList.size
        else listData.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(this.layout, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (filterable) {
            adapterCallback.initComponent(holder.itemView, currentList[position], position)
            holder.itemView.setOnClickListener {
                adapterCallback.onItemClicked(it, currentList[position], position)
            }
        } else {
            adapterCallback.initComponent(holder.itemView, listData[position], position)
            holder.itemView.setOnClickListener {
                adapterCallback.onItemClicked(it, listData[position], position)
            }
        }
    }

    override fun setLayout(layout: Int): AdapterUtils<T> {
        this.layout = layout
        return this
    }

    override fun filterable(): AdapterUtils<T> {
        this.filterable = true
        return this
    }

    override fun addData(items: List<T>): AdapterUtils<T> {
        listData = items as MutableList<T>
        currentList = listData
        notifyDataSetChanged()
        return this
    }

    override fun updateData(item: T): AdapterUtils<T> {
        if (!listData.contains(item)) {
            listData.add(item)
        } else {
            val index = listData.indexOf(item)
            listData[index] = item
        }

        notifyDataSetChanged()
        return this
    }

    override fun adapterCallback(adapterCallback: AdapterCallback<T>): AdapterUtils<T> {
        this.adapterCallback = adapterCallback
        return this
    }

    override fun isVerticalView(): AdapterUtils<T> {
        layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.VERTICAL, false)
        return this
    }

    override fun isHorizontalView(): AdapterUtils<T> {
        layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.HORIZONTAL, false)
        return this
    }

    override fun isGridView(spanCount: Int): AdapterUtils<T> {
        layoutManager = GridLayoutManager(context,
            spanCount, GridLayoutManager.VERTICAL, false)
        return this
    }

    override fun build(recyclerView: RecyclerView): AdapterUtils<T> {
        recyclerView.apply {
            this.adapter = this@AdapterUtils
            this.layoutManager = this@AdapterUtils.layoutManager
        }
        return this
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val search = constraint?.toString()

                search?.let {
                    val resultList = mutableListOf<T>()
                    for (row in listData) {
                        if (row.toString().toLowerCase(Locale.ROOT)
                                .contains(search.toLowerCase(Locale.ROOT))) {
                            resultList.add(row)
                        }
                    }
                    currentList = resultList
                } ?: run {
                    currentList = listData
                }

                val filterResults = FilterResults()
                filterResults.values = currentList

                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                currentList = results?.values as MutableList<T>
                notifyDataSetChanged()
            }

        }
    }

}