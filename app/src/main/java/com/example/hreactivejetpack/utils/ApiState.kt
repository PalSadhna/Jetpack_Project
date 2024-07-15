package com.example.hreactivejetpack.utils

sealed class ApiState<T>(val data: T? = null, val message: String? = null) {
    class Loading<T> : ApiState<T>()
    class Success<T>(data: T? = null) : ApiState<T>(data)
    class Error<T>(message: String?, data: T? = null) : ApiState<T>(data, message)
    class NoInternet<T>(message: String?) : ApiState<T>(message = message)
}
