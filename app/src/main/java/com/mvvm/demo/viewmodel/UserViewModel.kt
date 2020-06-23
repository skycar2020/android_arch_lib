package com.mvvm.demo.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.mvvm.demo.core.http.ApiResponse
import com.mvvm.demo.data.entity.User
import com.mvvm.demo.data.repository.UserRepositoryImpl
import com.mvvm.demo.usecase.UserUseCase
import jp.dhc.supplement.core.presentation.base.BaseViewModel


class UserViewModel(application: Application)  : BaseViewModel(application) {
    var userList : LiveData<List<User>> = MutableLiveData<List<User>>()

    private var useCase = UserUseCase(UserRepositoryImpl())

    fun fetchDataList(age: Int) {
        val list = useCase.getUsers(age)
        userList = list
    }

    fun getUsers(age: Int): LiveData<List<User>> {
        val list = useCase.getUsers(age)
        return list
    }

    fun getUsersResponseWrapper(age: Int): LiveData<ApiResponse<List<User>>> {
        return useCase.getUsersResponseWrapper(age)
    }

}

