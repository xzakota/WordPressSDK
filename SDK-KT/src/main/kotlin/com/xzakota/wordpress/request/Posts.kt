package com.xzakota.wordpress.request

import com.xzakota.wordpress.WPClient
import com.xzakota.wordpress.model.Post
import com.xzakota.wordpress.route.CustomPostRouter
import com.xzakota.wordpress.route.WPRoute

class Posts(
    client : WPClient
) : CustomPostRouter<Post>(client, WPRoute.POSTS, Post::class.java)