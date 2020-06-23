package com.mvvm.demo.core.http


import com.mvvm.demo.core.log.ISystemLogger
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


//TODO loggable(true)

class ApiClient private constructor(
    var baseUrl: String,
    val connectTimeout: Long?,
    val writeTimeout: Long?,
    val readTimeout: Long? = null,
    var logger: ISystemLogger? = null
) {

    data class Builder(
        var baseUrl: String? = null,
        var connectTimeout: Long? = null,
        var writeTimeout: Long? = null,
        var readTimeout: Long? = null,
        var logger: ISystemLogger? = null,
        var debug: Boolean? = null
    ) {

        fun url(baseUrl: String) = apply { this.baseUrl = baseUrl }
        fun connectTimeout(seconds: Long) = apply { this.connectTimeout = seconds }
        fun writeTimeout(seconds: Long) = apply { this.writeTimeout = seconds }
        fun readTimeout(seconds: Long) = apply { this.readTimeout = seconds }
        fun logger(logger: ISystemLogger?) = apply { this.logger = logger }
        fun build(): ApiClient {
            return ApiClient(
                baseUrl!!,
                connectTimeout,
                writeTimeout,
                readTimeout,
                logger
            )
        }
    }

    fun <T> create(serviceClass: Class<T>): T {
        return client.create(serviceClass)
    }

    private val client: Retrofit get() {
        val clientBuilder = OkHttpClient.Builder()
        connectTimeout?.let { clientBuilder.connectTimeout(it, TimeUnit.SECONDS) }
        writeTimeout?.let { clientBuilder.writeTimeout(it, TimeUnit.SECONDS) }
        readTimeout?.let { clientBuilder.readTimeout(it, TimeUnit.SECONDS) }
        logger?.let {
            val logging = HttpLoggingInterceptor(JsonLogger(it, true))
            logging.level = HttpLoggingInterceptor.Level.BODY
            clientBuilder.addInterceptor(logging)
        }

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(clientBuilder.build())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }


    companion object Factory {
        private var defaultClient: ApiClient? = null
        fun configureDefaultClientWith(builder: Builder){
            defaultClient = builder.build()
        }

        fun <T> create(serviceClass: Class<T>): T {
            return defaultClient!!.create(serviceClass)
        }
    }


}

