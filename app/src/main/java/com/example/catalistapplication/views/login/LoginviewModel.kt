package com.example.catalistapplication.views.login

import android.app.Dialog
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.catalistapplication.model.HomeModel
import com.example.catalistapplication.network.ResultCallBack
import com.example.catalistapplication.utils.GithubConstants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit

class LoginViewModel : ViewModel() {

    private var clicked=MutableLiveData<Int>()
    var userName=MutableLiveData<String>()
    var password=MutableLiveData<String>()
    var error=MutableLiveData<String>()


    fun getClicked() :LiveData<Int> {
        return clicked
    }

    fun onClicked(i:Int){
        clicked.value=i
    }

    fun validate() {
        val username=userName.value
        val pass=password.value
        if(username==""){
            error.value="Enter UserName"
        }else if(pass==""){
            error.value="Enter Password"
        }else{
            if(username=="user1" && pass=="password"){
                clicked.value = 1
            }else{
                error.value="Incorrect UserName or Password"
            }

        }
        clicked.value = 1
    }



}