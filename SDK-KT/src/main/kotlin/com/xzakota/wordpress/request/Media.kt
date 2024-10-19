package com.xzakota.wordpress.request

import com.xzakota.collect.KStrVObj
import com.xzakota.wordpress.WPClient
import com.xzakota.wordpress.model.Medium
import com.xzakota.wordpress.route.CustomPostRouter
import com.xzakota.wordpress.route.WPRoute

class Media(
    client : WPClient
) : CustomPostRouter<Medium>(client, WPRoute.MEDIA, Medium::class.java) {
    override fun create(target : Medium, otherRequestBody : KStrVObj?) : Medium? {
        return super.create(target, otherRequestBody?.push("file", target.resource))
    }

    override fun update(
        secondaryRoute : String?,
        target : Medium,
        otherRequestBody : KStrVObj?
    ) : Medium? {
        return super.update(secondaryRoute, target, otherRequestBody?.push("file", target.resource))
    }
}