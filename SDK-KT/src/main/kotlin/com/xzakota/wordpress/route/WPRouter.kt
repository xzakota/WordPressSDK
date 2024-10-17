package com.xzakota.wordpress.route

import com.xzakota.net.ResponseTarget
import com.xzakota.net.route.Router
import com.xzakota.wordpress.WPClient
import java.lang.RuntimeException

abstract class WPRouter<T : ResponseTarget>(
    client : WPClient, mainRoute : String, typeRef : Class<T>
) : Router<T>(client, WPRoute.API_REST_ROUTE + mainRoute, typeRef), IWPRouter<T>,
    IListable<T>, IRetrievable<T>, ICreatable<T>, IUpdatable<T>, IDeletable<T> {
    override fun getRouter() : WPRouter<T> {
        return this
    }

    protected fun checkRoute(route : String) : String {
        if (route.isEmpty()) {
            throw RuntimeException("Route is null or empty.")
        }

        return if (route[0] != '/') "/$route" else route
    }
}
