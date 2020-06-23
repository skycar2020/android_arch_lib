package com.mvvm.demo.core.log

interface ISystemLogger {
    fun setup(isLoggable: Boolean){}
    fun v(message: String)
    fun d(message: String)
    fun i(message: String)
    fun w(message: String)
    fun e(message: String)
    fun json(message: String)
}