package com.xzakota.wordpress.request

import com.xzakota.wordpress.WPClient
import com.xzakota.wordpress.model.Page
import com.xzakota.wordpress.route.CustomPostRouter
import com.xzakota.wordpress.route.WPRoute

class Pages(
    client : WPClient
) : CustomPostRouter<Page>(client, WPRoute.PAGES, Page::class.java)