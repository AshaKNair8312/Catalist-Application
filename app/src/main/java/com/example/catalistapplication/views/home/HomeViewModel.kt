package com.example.catalistapplication.views.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.catalistapplication.model.HomeModel
import com.example.catalistapplication.model.SearchResponse
import com.example.catalistapplication.network.ConnectionManager
import com.example.catalistapplication.network.ResultCallBack
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList

class HomeViewModel : ViewModel() {

    private var list = MutableLiveData<ArrayList<HomeModel>>()
    private var searchList = MutableLiveData<ArrayList<HomeModel>>()
    var showProgress = MutableLiveData<Boolean>()
    var error = MutableLiveData<String>()
    private val viewModelJob = Job()
    private var coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    init {
        showProgress.value = false
        list.value = ArrayList()
        searchList.value = ArrayList()
        getHomeList()
    }

    fun getList(): LiveData<ArrayList<HomeModel>> {
        return list
    }


    fun search(newText: String) {
        showProgress.value = true
        Timber.d("searchtext $newText")
        if (newText == "") {
            showProgress.value=false
            list.value = searchList.value
        } else {
            coroutineScope.launch {
                ConnectionManager.getDataManager()
                    .getSearchData(newText, object : ResultCallBack<SearchResponse> {
                        override fun onError(code: Int, errorMessage: String) {
                            Timber.d("SearchResponse $errorMessage")
                            showProgress.value = false
                            error.value = errorMessage
                        }

                        override fun <T> onSuccess(response: T) {
                            Timber.d("SearchResponse $response")
                            showProgress.value = false
                            if (response != null) {
                                val resp = response as SearchResponse
                                val data = resp.items as ArrayList<HomeModel>
                                for (i in data) {
                                    i.login = i.login?.replaceFirstChar {
                                        if (it.isLowerCase()) it.titlecase(
                                            Locale.ROOT
                                        ) else it.toString()
                                    }
                                }
                                list.value = data
                            }

                        }
                    })
            }
        }
    }

    private fun getHomeList() {
        showProgress.value = true
        coroutineScope.launch {
            ConnectionManager.getDataManager()
                .getHomeData(object : ResultCallBack<ArrayList<HomeModel>> {
                    override fun onError(code: Int, errorMessage: String) {
                        Timber.d("HomeResponse $errorMessage")
                        showProgress.value = false
                        error.value = errorMessage
                    }

                    override fun <T> onSuccess(response: T) {
                        Timber.d("HomeResponse $response")
                        showProgress.value = false
                        if (response != null) {
                            val resp = response as ArrayList<HomeModel>
                            for (i in resp) {
                                i.login = i.login?.replaceFirstChar {
                                    if (it.isLowerCase()) it.titlecase(
                                        Locale.ROOT
                                    ) else it.toString()
                                }
                            }
                            list.value = resp
                            searchList.value = list.value
                        }

                    }
                })

        }

    }

}

