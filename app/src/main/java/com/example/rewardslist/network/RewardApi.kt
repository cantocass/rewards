package com.example.rewardslist.network

import com.squareup.moshi.JsonClass
import retrofit2.http.GET

interface RewardApi {

    @GET("hiring.json")
    suspend fun getRewards(): List<Reward>

}

@JsonClass(generateAdapter = true)
data class Reward(val id: Int, val listId: Int, val name: String?)