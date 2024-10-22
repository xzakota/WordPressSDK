package com.xzakota.wordpress.route

import com.xzakota.net.ResponseTarget
import com.xzakota.wordpress.WPClient

abstract class WPV2Router<T : ResponseTarget>(
    client : WPClient, mainRoute : String, typeRef : Class<T>
) : WPRouter<T>(client, WPRoute.NAMESPACE_WP_V2 + mainRoute, typeRef)
