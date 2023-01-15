package com.example.catalistapplication.views.details

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.catalistapplication.R
import com.example.catalistapplication.databinding.ActivityDetailBinding
import com.example.catalistapplication.model.DetailModel
import com.example.catalistapplication.model.HomeModel
import com.example.catalistapplication.views.home.HomeClickListener
import timber.log.Timber

class DetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailBinding
    lateinit var viewModel: DetailViewModel
    private lateinit var adapter: DetailAdapter
    var name=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(intent!=null){
            name= intent.getStringExtra("name").toString()
        }
        setUpBinding()
        viewModel.setUserData(name)
        setupRecyclerView()
        setUpObservers()
        setUpSearch()
    }

    private fun setUpSearch() {
        binding.detailSearch.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                Timber.d("text submit")
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.search(newText)
                return true
            }


        })
    }

    private fun setUpObservers() {
        viewModel.getList().observe(this) {
            if (it.isNotEmpty()) {
                adapter.submitList(it)
            }
        }
    }

    private fun setUpBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun setupRecyclerView() {
        adapter = DetailAdapter(DetailClickListener { it: DetailModel ->
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(it.html_url)
            startActivity(intent)
        })
        binding.detailRecycler.adapter = adapter

    }
}