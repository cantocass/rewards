package com.example.rewardslist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rewardslist.data.RewardRepository
import com.example.rewardslist.network.Reward
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RewardViewModel @Inject constructor(private val rewardRepository: RewardRepository) :
    ViewModel() {

    private val _uiState = MutableStateFlow(RewardUiState())
    val uiState: StateFlow<RewardUiState> = _uiState.asStateFlow()

    init {
        Log.d("RewardViewModel", "init")
        getRewards()
    }

    private fun getRewards() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.d("RewardViewModel", "getRewards")
                val rewards = rewardRepository.getRewardsForDisplay()
                _uiState.value = _uiState.value.copy(rewards = rewards)
            } catch (ex: Exception) {
                Log.e("RewardViewModel", "getRewards", ex)
                _uiState.value = _uiState.value.copy(isError = true)
            }
        }
    }
}

data class RewardUiState(
    val rewards: List<Reward> = emptyList(),
    val isError: Boolean = false
)