package com.mvvm.demo.core.log

import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy


object XLogger: ISystemLogger {
    override fun setup(isLoggable: Boolean) {
        val formatStrategy = PrettyFormatStrategy.newBuilder()
            .methodOffset(1)
            .build()

        Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return isLoggable
            }
        })
    }
    override fun v(message: String) {
        Logger.v(message)
    }
    override fun d(message: String) {
        Logger.d(message)
    }
    override fun i(message: String) {
        Logger.i(message)
    }
    override fun w(message: String) {
        Logger.w(message)
    }
    override fun e(message: String) {
        Logger.e(message)
    }
    override fun json(message: String) {
        Logger.json(message)
    }
}
