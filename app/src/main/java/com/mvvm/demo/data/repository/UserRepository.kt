package com.mvvm.demo.data.repository

import com.mvvm.demo.core.http.ApiClient
import com.mvvm.demo.core.http.ApiResponse
import com.mvvm.demo.data.entity.User
import com.mvvm.demo.data.api.UserApiService
import io.reactivex.Single


interface UserRepository  {

    fun getUsers(age: Int): Single<List<User>>
    fun getUsersResponseWrapper(age: Int): Single<ApiResponse<List<User>>>
}

class UserRepositoryImpl : UserRepository {

    override fun getUsers(age: Int): Single<List<User>> {
        return  ApiClient.create(UserApiService::class.java).getUsers()
    }

    override fun getUsersResponseWrapper(age: Int): Single<ApiResponse<List<User>>> {
        return  ApiClient.create(UserApiService::class.java).getUsersResponseWrapper()
    }

}