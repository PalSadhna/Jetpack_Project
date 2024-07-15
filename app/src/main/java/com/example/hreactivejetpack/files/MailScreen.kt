package com.example.hreactivejetpack.files

import androidx.activity.compose.BackHandler
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.hreactivejetpack.activity.HomePage
import com.example.hreactivejetpack.utils.onTabSelected

@Composable
fun MailScreen(navController: NavHostController){
    BackHandler {
        navController.navigate("Home") {
            popUpTo(navController.graph.startDestinationId) {
                inclusive = true
            }
        }
    }
    Text(text = "Mail Screen")
}