package com.mvvm.demo

import android.app.Application
import com.mvvm.demo.core.http.ApiClient
import com.mvvm.demo.core.log.XLogger
import com.uber.rxdogtag.RxDogTag
import com.uber.rxdogtag.autodispose.AutoDisposeConfigurer

class App: Application() {
    override fun onCreate() {
        super.onCreate()

        val isResponseWrapperApi = false
        setupApp(isResponseWrapperApi)
    }

    private fun setupApp(isResponseWrapperApi: Boolean){
        XLogger.setup(BuildConfig.DEBUG)
        XLogger.i("begin")

        if(!isResponseWrapperApi){

            val httpClientBuilder = ApiClient.Builder()
                .connectTimeout(100)
                .url("https://api.github.com")
                .logger(XLogger)

            ApiClient.configureDefaultClientWith(httpClientBuilder)
        } else {
            val httpClientBuilder = ApiClient.Builder()
                .connectTimeout(100)
                .url("https://testone1.free.beeceptor.com")
                .logger(XLogger)

            ApiClient.configureDefaultClientWith(httpClientBuilder)
        }

        //Auto Dispose
        RxDogTag.builder()
            .configureWith(AutoDisposeConfigurer::configure)
            .install()
    }
}