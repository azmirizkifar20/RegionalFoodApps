package org.marproject.makanankhasindonesia.core.adapter

import android.view.View

interface AdapterCallback<T> {
    fun initComponent(itemView: View, data: T, itemIndex: Int)
    fun onItemClicked(itemView: View, data: T, itemIndex: Int)
}