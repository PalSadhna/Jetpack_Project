package com.example.hreactivejetpack.utils


sealed class Screens(val route: String) {
    object Home: Screens("home_screen")
    object AddEmployee: Screens("add_employee_screen")
    object Mail: Screens("mail_screen")
}