package com.example.hreactivejetpack.utils

import androidx.navigation.NavHostController

fun onTabSelected(
    navController: NavHostController,
    navTarget: String,
    home: Boolean = false,
    addEmployee: Boolean = false,
    mail: Boolean = false,
    activity: Boolean = false,
    updateState: (Boolean, Boolean, Boolean, Boolean) -> Unit
) {
    navController.navigate(navTarget) {
        popUpTo(navController.graph.id) {
            inclusive = true
        }
    }
    updateState(home, addEmployee, mail, activity)
}
