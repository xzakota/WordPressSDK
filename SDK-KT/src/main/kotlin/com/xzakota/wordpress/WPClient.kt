package com.xzakota.wordpress

import com.xzakota.model.Authentication
import com.xzakota.net.Client
import com.xzakota.wordpress.model.User
import com.xzakota.wordpress.request.RequestCallback
import com.xzakota.wordpress.request.RouteRequest

class WPClient(server : String, auth : Authentication) : Client(server, auth) {
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

    @JvmSynthetic
    fun request(block : RouteRequest.() -> Unit) : RouteRequest {
        return request.also(block)
    }

    @Throws(Exception::class)
    override fun test() : Boolean {
        var result = false
        val oldDebug = debug
        debug = false

        var user : User?
        request.users { router ->
            print("Test basic connection...")
            try {
                user = router.retrieveMe()
                result = user != null
            } catch (e : Exception) {
                throw e
            } finally {
                if (result) {
                    println(" passed~")
                } else {
                    println(" failed!")
                }
            }

            print("Test user connection...")
            try {
                result = user?.let {
                    if (auth.type == Authentication.AuthType.APPLICATION) {
                        !router.listAppPwdByUser(it).isNullOrEmpty()
                    } else {
                        false
                    } && result
                } == true
            } catch (e : Exception) {
                result = false
                throw e
            } finally {
                if (result) {
                    println(" passed~")
                } else {
                    println(" failed, unauthorized!")
                }
            }
        }

        debug = oldDebug

        return result
    }

    companion object {
        private const val IDENTIFIER = "WordPress Kotlin SDK"
        private const val VERSION = "1.1"
    }
}
