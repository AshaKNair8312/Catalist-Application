package com.example.catalistapplication.views.details

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.catalistapplication.databinding.DetailItemBinding
import com.example.catalistapplication.model.DetailModel


class DetailAdapter(
    private val clickListener: DetailClickListener
) : RecyclerView.Adapter<DetailAdapter.DetailViewHolder>() {

    private var repoList: ArrayList<DetailModel> = ArrayList()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: ArrayList<DetailModel>) {
        repoList = list
        notifyDataSetChanged()
    }

    override fun getItemId(position: Int): Long {
        val id = repoList[position].id
        if (id != null)
            return id.toLong()
        return 0

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        return DetailViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        holder.bind(repoList[position], clickListener)
    }

    class DetailViewHolder private constructor(private val binding: DetailItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DetailModel, clickListener: DetailClickListener) {
            binding.viewModel = item
            binding.clickListener=clickListener
        }

        companion object {
            fun from(parent: ViewGroup): DetailViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = DetailItemBinding.inflate(layoutInflater, parent, false)
                return DetailViewHolder(binding)
            }
        }

    }

    override fun getItemCount(): Int {
        return repoList.size
    }

}
class DetailClickListener(val clickListener: (data: DetailModel) -> Unit) {
    fun onClick(data: DetailModel) {
        clickListener(data)
    }
}


