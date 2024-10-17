package com.xzakota.net

import com.xzakota.model.Authentication
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import kotlin.jvm.Throws

open class Client(val server : String, val auth : Authentication) {
    var userAgent = "Client"
    var debug = false
    var timeout = 60000L

    protected val internalClientBuilder = OkHttpClient().newBuilder()
    var internalClient : OkHttpClient? = null
        private set

    init {
        internalClientBuilder.connectTimeout(timeout, TimeUnit.MILLISECONDS)
            .readTimeout(timeout, TimeUnit.MILLISECONDS)
            .writeTimeout(timeout, TimeUnit.MILLISECONDS)
    }

    open fun connect() {
        internalClient = internalClientBuilder.build()
        println("Connecting $server")
    }

    @Throws(Exception::class)
    open fun connectTest() : Boolean {
        connect()
        return test()
    }

    open fun disconnect() {
        println("Disconnected $server")
        internalClient = null
    }

    @Throws(Exception::class)
    protected open fun test() : Boolean = true
}
