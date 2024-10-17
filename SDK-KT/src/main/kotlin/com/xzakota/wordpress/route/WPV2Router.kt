package com.xzakota.wordpress.route

import com.xzakota.net.ResponseTarget
import com.xzakota.wordpress.WPClient

abstract class WPV2Router<T : ResponseTarget>(
    client : WPClient, mainRoute : String, typeRef : Class<T>
) : WPRouter<T>(client, WPRoute.V2_ROUTE_PREFIX + mainRoute, typeRef)
