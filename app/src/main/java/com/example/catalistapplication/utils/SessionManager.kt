package com.example.catalistapplication.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager {

    companion object {
        lateinit var sharedPref: SharedPreferences
        lateinit var editor: SharedPreferences.Editor
        lateinit var context: Context

        private val PREF_NAME = "GithubSearcher"
        private val ACCESS_TOKEN="accessToken"
        private val IS_LOGIN="isLogin"

        fun initializeval(context: Context) {
            this.context = context
            sharedPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            editor = sharedPref.edit()
        }

        fun saveAccessToken(token:String){
            editor.putString(ACCESS_TOKEN, token)
            editor.apply()
        }

        fun getAccessToken(): String? {
            return sharedPref.getString(ACCESS_TOKEN, "")
        }

        fun getIsLogin(): Boolean {
            return sharedPref.getBoolean(IS_LOGIN, false)
        }

        fun setIsLogin(i: Boolean) {
            editor.putBoolean(IS_LOGIN, i)
            editor.apply()
        }

    }
}