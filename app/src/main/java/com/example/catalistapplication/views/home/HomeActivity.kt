package com.example.catalistapplication.views.home

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.catalistapplication.R
import com.example.catalistapplication.databinding.ActivityHomeBinding
import com.example.catalistapplication.model.HomeModel
import com.example.catalistapplication.views.details.DetailActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import timber.log.Timber
import kotlin.system.exitProcess

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    lateinit var viewModel: HomeViewModel
    private lateinit var adapter: HomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpBinding()
        setupRecyclerView()
        setUpObservers()
        setUpSearch()
    }

    private fun setUpSearch() {
        binding.homeSearch.setOnQueryTextListener(object :
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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
//        viewModelFactory= HomeViewModelFactory(UsersRepository(Use))
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun setupRecyclerView() {
        adapter = HomeAdapter(HomeClickListener { it: HomeModel ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("name",it.login)
            startActivity(intent)
        })
        binding.homeRecycler.adapter = adapter
    }

    override fun onBackPressed() {
        materialalertdialog("Press back again to exit","OK","CANCEL",{dialogInterface, i ->
            dialogInterface.cancel()
            System.exit(0)
        },{dialogInterface, i ->
            dialogInterface.cancel()
        })

    }

    private fun materialalertdialog(

         msg: String, postxt: String,
        negtxt: String, posclick: (DialogInterface, Int) -> Unit,
        negclick: ((DialogInterface, Int) -> Unit)?
    ) {
        var alertDialog: AlertDialog? = null

        alertDialog?.cancel()
                val builder =
                    MaterialAlertDialogBuilder(this, R.style.MaterialAlertDialog_Rounded)
                builder.setTitle("")
                builder.setCancelable(false)
                builder.setMessage(msg)
                if (postxt != "") {
                    builder.setPositiveButton(postxt, posclick)
                }

                if (negtxt != "") {
                    builder.setNegativeButton(negtxt, negclick)
                }
                alertDialog = builder.create()
                alertDialog.show()
            }



}