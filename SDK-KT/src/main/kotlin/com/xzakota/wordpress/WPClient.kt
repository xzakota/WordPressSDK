package com.xzakota.wordpress

import com.xzakota.model.Authentication
import com.xzakota.net.Client
import com.xzakota.wordpress.model.User
import com.xzakota.wordpress.request.RequestCallback
import com.xzakota.wordpress.request.RouteRequest

class WPClient(server : String, admin : Authentication) : Client(server, admin) {
    private val request = RouteRequest.withClient(this)

    init {
        userAgent = "$IDENTIFIER V$VERSION"
    }

    @JvmOverloads
    fun request(block : RequestCallback = RequestCallback {}) : RouteRequest {
        return request.also {
            block.callback(it)
        }
    }

    fun request(block : RouteRequest.() -> Unit) : RouteRequest {
        return request.also(block)
    }

    @Throws(Exception::class)
    override fun test() : Boolean {
        var result = false
        val oldDebug = debug
        debug = false

        var user : User? = null
        request.users {
            println("Test basic connectivity...")
            try {
                val list = it.listByPage(1, 1).entity
                result = if (!list.isNullOrEmpty()) {
                    user = list[0]
                    true
                } else {
                    false
                }
            } catch (e : Exception) {
                result = false
                throw e
            } finally {
                if (result) {
                    println("Basic connectivity passed")
                } else {
                    println("Basic connectivity failed")
                }
            }

            println("Test user connectivity...")
            try {
                result = if (user != null) {
                    !it.listAppPwdByUserId(user.id!!).isNullOrEmpty() && result
                } else {
                    false
                }
            } catch (e : Exception) {
                result = false
                throw e
            } finally {
                if (result) {
                    println("User connectivity passed")
                } else {
                    println("User connectivity failed, unauthorized")
                }
            }
        }

        debug = oldDebug

        return result
    }

    companion object {
        private const val IDENTIFIER = "WordPress JAVA SDK"
        private const val VERSION = "1.0"
    }
}
