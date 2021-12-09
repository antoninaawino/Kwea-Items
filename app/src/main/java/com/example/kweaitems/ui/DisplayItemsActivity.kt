package com.example.kweaitems.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kweaitems.R
import com.example.kweaitems.configs.Datafactory
import com.example.kweaitems.databinding.ActivityDisplayItemsBinding
import com.example.kweaitems.models.ItemsDataModel
import com.example.kweaitems.models.ItemsResponseModel
import com.example.kweaitems.ui.adapters.ItemsAdapter
import com.example.kweaitems.webservices.IExecute
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import retrofit2.Response

class DisplayItemsActivity : AppCompatActivity() {

    private val TAG = "DisplayItemsActivity"

    private lateinit var binding: ActivityDisplayItemsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_display_items)
        binding.refresh.setOnRefreshListener { getItems() }
        getItems()
    }

    private fun getItems() {
        try {
            binding.refresh.isRefreshing = true
            Log.e(TAG, "getItems: token : ${Datafactory.getToken()}", )
            Datafactory.getKweaRepo().fetchKweaItems(
                object : IExecute<ItemsResponseModel?>{
                    override fun run(result: Response<ItemsResponseModel?>?, t: Throwable?) {
                        try {
                            Log.e(TAG, "run: result: $result" )
                            binding.refresh.isRefreshing = false
                            if (result != null) {
                                if (result.body() != null) {
                                    feedAdapter(result.body()?.data!!)
                                } else {
                                    showError()
                                }
                            } else {
                                showError()
                            }
                        } catch (e : Exception) {
                            Log.e(TAG, "run: ", e)
                            showError()
                        }
                    }
                })

        } catch (e : Exception) {
            Log.e(TAG, "getItems: ", e)
        }
    }

    @SuppressLint("ShowToast")
    private fun showError() {
        try {
            Snackbar.make(binding.rwItems,"Request failed", Snackbar.LENGTH_SHORT).show()
        } catch (e : Exception) {
            Log.e(TAG, "showError: ", e)
        }
    }

    private fun feedAdapter(list: MutableList<ItemsDataModel>) {
        try {
            val adapter = ItemsAdapter(list)
            val gridLayoutManager = GridLayoutManager(this, 1)
            binding.rwItems.layoutManager = gridLayoutManager
            binding.rwItems.adapter = adapter
        } catch (e : Exception) {
            Log.e(TAG, "feedAdapter: ", e)
        }
    }
}