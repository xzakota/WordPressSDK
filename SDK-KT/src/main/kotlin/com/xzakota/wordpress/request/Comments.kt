package com.xzakota.wordpress.request

import com.xzakota.wordpress.WPClient
import com.xzakota.wordpress.model.Comment
import com.xzakota.wordpress.route.CustomPostRouter
import com.xzakota.wordpress.route.WPRoute

class Comments(
    client : WPClient
) : CustomPostRouter<Comment>(client, WPRoute.COMMENTS, Comment::class.java)