package org.marproject.makanankhasindonesia.core.ui.adapter

import androidx.recyclerview.widget.RecyclerView

interface AdapterUtilsInterface<T> {
    // set layout
    fun setLayout(layout: Int): AdapterUtils<T>

    // append data
    fun addData(items: List<T>): AdapterUtils<T>

    // realtime change
    fun updateData(item: T): AdapterUtils<T>

    // adapter callback
    fun adapterCallback(adapterCallback: AdapterCallback<T>): AdapterUtils<T>

    // layout orientation
    fun isVerticalView(): AdapterUtils<T>
    fun isHorizontalView(): AdapterUtils<T>
    fun isGridView(spanCount: Int): AdapterUtils<T>

    // build recyclerview
    fun build(recyclerView: RecyclerView): AdapterUtils<T>

}