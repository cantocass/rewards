package com.example.rewardslist

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rewardslist.network.Reward
import com.example.rewardslist.ui.theme.RewardsListTheme

@Composable
@Preview
fun previewRewardScreen() {
    val rewards =
        listOf(Reward(123, 1, "Item 123"), Reward(456, 2, "Item 456"), Reward(789, 3, "Item 789"))
    val state = RewardUiState(rewards = rewards, isError = false)
    RewardsListTheme {
        RewardScreen(modifier = Modifier, state = state)

    }
}

@Composable
fun RewardScreen(modifier: Modifier, state: RewardUiState) {
    when {
        state.isError -> ErrorScreen(modifier, state)
        else -> RewardScreenContent(modifier, state)
    }
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier, state: RewardUiState) {
    Text("Error: Rewards List has size of ${state.rewards.size}")
}

@Composable
private fun RewardScreenContent(modifier: Modifier = Modifier, state: RewardUiState) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
    ) {
        itemsIndexed(
            items = state.rewards,
            key = { _, item -> item.id },
        ) { index, reward ->
            Spacer(modifier = Modifier.padding(4.dp))
            RewardItem(
                modifier = modifier,
                reward = reward,
                index = index,
                localContext = LocalContext.current
            )
            Spacer(modifier = Modifier.padding(4.dp))
            HorizontalDivider(thickness = 1.dp, color = Color.Black)
        }
    }
}

@Composable
fun RewardItem(modifier: Modifier = Modifier, reward: Reward, index: Int, localContext: Context) {
    Row(modifier = Modifier.clickable(onClick = {
        Toast.makeText(
            localContext,
            "Tapped reward at list position $index with name ${reward.name}",
            Toast.LENGTH_SHORT
        ).show()
    })) {
        Text(
            text = "Name: " + (reward.name ?: "")
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = "List ID: " + reward.listId.toString()
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = "ID: " + reward.id.toString()
        )
    }
}

