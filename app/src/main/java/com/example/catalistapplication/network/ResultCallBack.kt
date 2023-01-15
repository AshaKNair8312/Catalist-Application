package com.example.catalistapplication.network

interface ResultCallBack<T> {
    fun <T> onSuccess(response:T)
    fun onError(code:Int,errorMessage:String)
}