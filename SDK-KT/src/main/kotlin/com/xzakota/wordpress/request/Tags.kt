package com.xzakota.wordpress.request

import com.xzakota.wordpress.WPClient
import com.xzakota.wordpress.route.WPRoute

class Tags(
    client : WPClient
) : Terms(client, WPRoute.TAGS)