package com.mvvm.demo.data.api

import com.mvvm.demo.core.http.ApiResponse
import com.mvvm.demo.data.entity.User
import io.reactivex.Single

import retrofit2.http.GET


interface UserApiService {

    @GET("/repos/googlesamples/android-architecture-components/contributors")
    fun getUsers(): Single<List<User>>


    @GET("/my/api/path")
    fun getUsersResponseWrapper(): Single<ApiResponse<List<User>>>

}