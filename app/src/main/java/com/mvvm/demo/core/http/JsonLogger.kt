package com.mvvm.demo.core.http

import com.mvvm.demo.core.log.ISystemLogger
import okhttp3.logging.HttpLoggingInterceptor

class JsonLogger(private val systemLogger: ISystemLogger,
                 private val ignoreHeaders: Boolean? = null) : HttpLoggingInterceptor.Logger {

    private val mIgnoreHeaders: Boolean

    init {
        this.mIgnoreHeaders = ignoreHeaders ?: false
    }

    override fun log(message: String) {
        val trimMessage = message.trim { it <= ' ' }
        if (trimMessage.startsWith("{") && trimMessage.endsWith("}")
            || trimMessage.startsWith("[") && trimMessage.endsWith("]")) {
            systemLogger.json(message)
            return
        }
        //(request & response)first row and last row
        if(this.mIgnoreHeaders && (!message.startsWith("--> ") || !message.startsWith("<-- "))){
            return
        }
        println("HttpLogging: $message")
    }
}


