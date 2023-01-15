package com.example.catalistapplication.network

import com.example.catalistapplication.BuildConfig
import com.example.catalistapplication.R
import com.example.catalistapplication.model.DetailModel
import com.example.catalistapplication.model.DetailSearchResponse
import com.example.catalistapplication.model.HomeModel
import com.example.catalistapplication.model.SearchResponse
import com.example.catalistapplication.utils.Constants
import com.example.catalistapplication.utils.SessionManager
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList


fun getClient(): Retrofit {
    val interceptor = HttpLoggingInterceptor()
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    val httpClient = OkHttpClient.Builder()
    httpClient.connectTimeout(Constants.CONNECT_TIMEOUT.toLong(), TimeUnit.SECONDS)
    httpClient.readTimeout(Constants.CONNECT_TIMEOUT.toLong(), TimeUnit.SECONDS)
    httpClient.callTimeout(Constants.CONNECT_TIMEOUT.toLong(), TimeUnit.SECONDS)
    httpClient.addInterceptor(interceptor)
    httpClient.addInterceptor { chain ->
        val original = chain.request()

        val request = original.newBuilder()
            .method(original.method, original.body)
            .addHeader("Accept", "application/vnd.github+json")
            .addHeader("User-Agent", "android")
            //.addHeader("Authorization", "Bearer ${BuildConfig.api_access_token}")
            .addHeader("Authorization", "Bearer ${SessionManager.context.getString(R.string.API_ACCESS_TOKEN)}")
            .build()
        val response = chain.proceed(request)
        val rawJson = response.body?.string()
        response.newBuilder().body(rawJson?.toResponseBody(response.body?.contentType())).build()
    }

    return Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(httpClient.build())
        .baseUrl(Constants.BASE_URL)
        .build()
}

interface ApiService {

    @GET("users")
    fun getHomeDataAsync(
        @Query("per_page") per_page: Int
    ): Deferred<Response<ArrayList<HomeModel>>>

    @GET("search/users")
    fun getSearchDataAsync(
        @Query("q") key: String
    ): Deferred<Response<SearchResponse>>

    @GET("users/{username}")
    fun getUserAsync(
        @Path("username") name: String
    ): Deferred<Response<HomeModel>>


    @GET("users/{username}/repos")
    fun getRepositoriesAsync(
        @Path("username") name: String
    ): Deferred<Response<ArrayList<DetailModel>>>

    @GET("search/repositories")
    fun getSeaRepositoriesAsync(
        @Query("q") key: String
    ): Deferred<Response<DetailSearchResponse>>

}

object RetroApi {
    val retrofitService: ApiService by lazy {
        getClient().create(ApiService::class.java)
    }
}