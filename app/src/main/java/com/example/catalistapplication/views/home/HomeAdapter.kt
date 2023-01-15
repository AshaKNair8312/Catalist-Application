package com.example.catalistapplication.views.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.catalistapplication.databinding.HomeItemBinding
import com.example.catalistapplication.model.HomeModel


class HomeAdapter(
    private val clickListener: HomeClickListener
) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    private var homeList: ArrayList<HomeModel> = ArrayList()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: ArrayList<HomeModel>) {
        homeList = list
        notifyDataSetChanged()
    }

    override fun getItemId(position: Int): Long {
        val id = homeList[position].id
        if (id != null)
            return id.toLong()
        return 0

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(homeList[position], clickListener)
    }

    class HomeViewHolder private constructor(val binding: HomeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HomeModel, clickListener: HomeClickListener) {
            binding.viewModel = item
            binding.clickListener=clickListener
        }

        companion object {
            fun from(parent: ViewGroup): HomeViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = HomeItemBinding.inflate(layoutInflater, parent, false)
                return HomeViewHolder(binding)
            }
        }

    }

    override fun getItemCount(): Int {
        return homeList.size
    }

}

class HomeClickListener(val clickListener: (data: HomeModel) -> Unit) {
    fun onClick(data: HomeModel) {
        clickListener(data)
    }
}

