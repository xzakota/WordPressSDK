package com.xzakota.wordpress.request

fun interface RequestCallback {
    fun callback(request : RouteRequest)
}