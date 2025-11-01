package com.example.my_gnews.data.network.api
import com.example.my_gnews.data.network.dto.GNewsResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface GNewsApi {
    @GET ("top-headlines")
    suspend fun getTopHeadlines (
        @Query ("category") category: String,
        @Query ("lang") language: String,
        @Query ("country") country: String,
        @Query ("apikey") apiKey: String,
        @Query ("max") max: Int = 30,
    ): GNewsResponse

     @GET ("search")
     suspend fun searchNews (
         @Query ("q") query: String,
         @Query ("lang") language: String,
         @Query ("country") country: String,
         @Query ("apikey") apiKey: String,
         @Query ("max") max: Int = 30
     ): GNewsResponse
}