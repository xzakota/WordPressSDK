package com.xzakota.wordpress.request

import com.xzakota.wordpress.WPClient
import com.xzakota.wordpress.route.WPRoute

class Categories(
    client : WPClient
) : Terms(client, WPRoute.CATEGORIES)