package com.mvvm.demo.core.http

data class ApiResponse<T>(
    val status: String?,
    val data: T?,
    val error: Throwable?
    //val errorCode: String?
)




