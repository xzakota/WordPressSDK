package com.xzakota.net.route

interface IRouter<T : Router<*>> {
    fun getRouter() : T
}
