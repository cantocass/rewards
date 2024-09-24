package com.example.rewardslist.data

import android.util.Log
import com.example.rewardslist.network.Reward
import com.example.rewardslist.network.RewardApi
import javax.inject.Inject

class RewardRepository @Inject constructor(private val rewardApi: RewardApi) {

    suspend fun getRewardsForDisplay(): List<Reward> {
        //Display all the items grouped by "listId"
        //Sort the results first by "listId" then by "name" when displaying.
        //Filter out any items where "name" is blank or null.
        Log.d("RewardRepository", "getRewardsForDisplay")
        return rewardApi.getRewards()
            .filter { !it.name.isNullOrBlank() }
            .sortedWith(compareBy<Reward> { it.listId }.thenBy { it.name })
    }
}