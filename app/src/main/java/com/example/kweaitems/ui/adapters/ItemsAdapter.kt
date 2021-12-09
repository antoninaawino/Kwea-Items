package com.example.kweaitems.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.kweaitems.R
import com.example.kweaitems.databinding.ListRecyclerItemsBinding
import com.example.kweaitems.models.ItemsDataModel
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import java.text.DecimalFormat

class ItemsAdapter( private var itemsList: MutableList<ItemsDataModel>
) :
    RecyclerView.Adapter<ItemsAdapter.ItemsAdapterViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsAdapterViewHolder {
        val binding: ListRecyclerItemsBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.list_recycler_items, parent, false)
        return ItemsAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemsAdapterViewHolder, position: Int) {
        try {
            val current = itemsList[position]
            holder.binding.model = current
            holder.binding.twPrice.text = DecimalFormat("#,###")
                .format(current.item_price)
            if (current.images != null && current.images!!.isNotEmpty()) {
                Picasso.get().load(current.images!![0].image).into(holder.binding.iwItem)
            }
        } catch (e : Exception) {
            Log.e(TAG, "onBindViewHolder: ", e)
        }
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    class ItemsAdapterViewHolder(val binding: ListRecyclerItemsBinding) : RecyclerView.ViewHolder(binding.root)
    companion object {
        private const val TAG = "ItemsAdapter"
    }
}