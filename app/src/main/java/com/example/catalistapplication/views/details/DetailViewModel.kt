package com.example.catalistapplication.views.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.catalistapplication.model.DetailModel
import com.example.catalistapplication.model.HomeModel
import com.example.catalistapplication.network.ConnectionManager
import com.example.catalistapplication.network.ResultCallBack
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class DetailViewModel : ViewModel() {

    var userDetail = MutableLiveData<HomeModel>()
    var repoList = MutableLiveData<ArrayList<DetailModel>>()
    var searchList: ArrayList<DetailModel>
    var showProgress = MutableLiveData<Boolean>()
    var error = MutableLiveData<String>()
    private val viewModelJob = Job()
    private var coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        showProgress.value = false
        repoList.value = ArrayList()
        searchList = ArrayList()
    }

    fun setUserData(name: String) {
        getUserApi(name)
    }

    private fun getUserApi(name: String) {
        showProgress.value = true
        coroutineScope.launch {
            ConnectionManager.getDataManager()
                .getUser(name, object : ResultCallBack<HomeModel> {
                    override fun onError(code: Int, errorMessage: String) {
                        Timber.d("RepoResponse $errorMessage")
                        showProgress.value = false
                        error.value = errorMessage
                    }

                    override fun <T> onSuccess(response: T) {
                        Timber.d("RepoResponse $response")
                        showProgress.value = false
                        if (response != null) {
                            val resp = response as HomeModel
                            resp.login?.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
                            resp.location?.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
                            userDetail.value = resp
                            getUserRepository(name)
                        }

                    }
                })

        }
    }


    private fun getUserRepository(name: String?) {
        showProgress.value = true
        coroutineScope.launch {
            name?.let {
                ConnectionManager.getDataManager()
                    .getRepositories(it, object : ResultCallBack<ArrayList<DetailModel>> {
                        override fun onError(code: Int, errorMessage: String) {
                            Timber.d("RepoResponse $errorMessage")
                            showProgress.value = false
                            error.value = errorMessage
                        }

                        override fun <T> onSuccess(response: T) {
                            Timber.d("RepoResponse $response")
                            showProgress.value = false
                            if (response != null) {
                                val resp = response as ArrayList<DetailModel>
                                repoList.value = resp
                                searchList = resp
                            }

                        }
                    })
            }

        }

    }

    fun search(newText: String) {
        if (newText == "") {
            repoList.value = searchList
        } else {
            val temp: ArrayList<DetailModel> = ArrayList()
            for (i in repoList.value!!) {
                if (i.name?.contains(newText.lowercase(Locale.ROOT)) == true) {
                    temp.add(i)
                }
            }
            repoList.value = temp
        }
//        showProgress.value = true
//        Timber.d("searchtext $newText")
//        if (newText == "") {
//            showProgress.value=false
//            repoList.value = searchList.value
//        } else {
//            coroutineScope.launch {
//                ConnectionManager.getDataManager()
//                    .getRepositorySearchData(newText, object : ResultCallBack<DetailSearchResponse> {
//                        override fun onError(code: Int, errorMessage: String) {
//                            Timber.d("SearchResponse $errorMessage")
//                            showProgress.value = false
//                            error.value = errorMessage
//                        }
//
//                        override fun <T> onSuccess(response: T) {
//                            Timber.d("SearchResponse $response")
//                            showProgress.value = false
//                            if (response != null) {
//                                val resp = response as DetailSearchResponse
//                                val data = resp.items as ArrayList<DetailModel>
//                                repoList.value = data
//                            }
//
//                        }
//                    })
//            }
//        }
    }


    fun getList(): LiveData<ArrayList<DetailModel>> {
        return repoList
    }


}