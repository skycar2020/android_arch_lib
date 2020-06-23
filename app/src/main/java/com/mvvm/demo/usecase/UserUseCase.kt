package com.mvvm.demo.usecase

import androidx.lifecycle.LiveData
import com.mvvm.demo.core.http.ApiResponse
import com.mvvm.demo.core.http.rx.toLiveData
import com.mvvm.demo.core.http.rx.toLiveDataCatchError
import com.mvvm.demo.core.http.rx.toLiveDataResponse
import com.mvvm.demo.data.entity.User
import com.mvvm.demo.data.repository.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UserUseCase(private val repository: UserRepository)  {

    fun getUsers(age: Int): LiveData<List<User>> {

        return repository.getUsers(age)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .toLiveData()
    }


    fun getUsersResponseWrapper(age: Int): LiveData<ApiResponse<List<User>>> {
        return repository.getUsersResponseWrapper(age)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .toLiveDataResponse()

    }

    fun getUsersResponseWrapperNoError(age: Int): LiveData<ApiResponse<List<User>>> {
        return repository.getUsersResponseWrapper(age)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .toLiveDataCatchError()
    }

}



