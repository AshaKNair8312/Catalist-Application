package com.example.catalistapplication.views.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.catalistapplication.R
import com.example.catalistapplication.databinding.ActivityLoginBinding
import com.example.catalistapplication.utils.SessionManager
import com.example.catalistapplication.views.home.HomeActivity

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    lateinit var viewModel: LoginViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpBinding()
        setUpObservers()

    }

    private fun setUpObservers() {
        viewModel.getClicked().observe(this) {
            when (it) {
                1 -> {
                    val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                    SessionManager.setIsLogin(true)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    private fun setUpBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }
}



