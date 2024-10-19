package com.xzakota.wordpress.request

import com.xzakota.wordpress.WPClient
import com.xzakota.wordpress.model.Link
import com.xzakota.wordpress.route.StatusRouter
import com.xzakota.wordpress.route.WPRoute

class Links(
    client : WPClient
) : StatusRouter<Link>(client, WPRoute.LINKS, Link::class.java)