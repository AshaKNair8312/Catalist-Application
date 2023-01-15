package com.example.catalistapplication.model


data class HomeModel(
    val id: Int?,
    val avatar_url: String?,
    var login: String?,
    val repos_url: String?,
    val fork:Int?,
    val star:Int?,
    val location:String?,
    val email:String?,
    val followers:Int?,
    val following:Int?,
    val created_at:String?,
    val bio:String?
)


data class SearchResponse(
    val total_count: Int?,
    val items: ArrayList<HomeModel>?
)

data class DetailSearchResponse(
    val total_count: Int?,
    val items: ArrayList<DetailModel>?
)