package com.xzakota.wordpress.request

import com.xzakota.net.ResponseTarget
import com.xzakota.wordpress.WPClient
import com.xzakota.wordpress.model.CustomPost
import com.xzakota.wordpress.request.RouteRequest.RouterCallback
import com.xzakota.wordpress.route.CustomPostRouter
import com.xzakota.wordpress.route.IWPRouter
import com.xzakota.wordpress.route.WPRouter
import com.xzakota.wordpress.route.WPV2Router

@Suppress("unused")
class RouteRequest private constructor(private val client : WPClient) {
    @JvmOverloads
    fun customPosts(
        mainRoute : String,
        block : RouterCallback<CustomPostRouter<CustomPost>> = RouterCallback {}
    ) : CustomPostRouter<CustomPost> {
        return CustomPostRouter<CustomPost>(client, mainRoute, CustomPost::class.java).apply {
            block.callback(this)
        }
    }

    @JvmOverloads
    fun posts(block : RouterCallback<Posts> = RouterCallback {}) : Posts {
        return Posts(client).apply {
            block.callback(this)
        }
    }

    @JvmOverloads
    fun pages(block : RouterCallback<Pages> = RouterCallback {}) : Pages {
        return Pages(client).apply {
            block.callback(this)
        }
    }

    @JvmOverloads
    fun comments(block : RouterCallback<Comments> = RouterCallback {}) : Comments {
        return Comments(client).apply {
            block.callback(this)
        }
    }

    @JvmOverloads
    fun media(block : RouterCallback<Media> = RouterCallback {}) : Media {
        return Media(client).apply {
            block.callback(this)
        }
    }

    @JvmOverloads
    fun users(block : RouterCallback<Users> = RouterCallback {}) : Users {
        return Users(client).apply {
            block.callback(this)
        }
    }

    @JvmOverloads
    fun terms(
        mainRoute : String,
        block : RouterCallback<Terms> = RouterCallback {}
    ) : Terms {
        return Terms(client, mainRoute).apply {
            block.callback(this)
        }
    }

    @JvmOverloads
    fun categories(block : RouterCallback<Categories> = RouterCallback {}) : Categories {
        return Categories(client).apply {
            block.callback(this)
        }
    }

    @JvmOverloads
    fun tags(block : RouterCallback<Tags> = RouterCallback {}) : Tags {
        return Tags(client).apply {
            block.callback(this)
        }
    }

    @JvmOverloads
    fun links(block : RouterCallback<Links> = RouterCallback {}) : Links {
        return Links(client).apply {
            block.callback(this)
        }
    }

    @JvmOverloads
    fun <T : ResponseTarget> router(
        mainRoute : String,
        typeRef : Class<T>,
        block : RouterCallback<WPRouter<T>> = RouterCallback {}
    ) : WPRouter<T> {
        return router<T>(object : WPV2Router<T>(client, mainRoute, typeRef) {}, block)
    }

    @JvmOverloads
    fun <T : ResponseTarget> router(
        router : IWPRouter<T>,
        block : RouterCallback<WPRouter<T>> = RouterCallback {}
    ) : WPRouter<T> {
        return router.getRouter().apply {
            block.callback(this)
        }
    }

    fun interface RouterCallback<T> {
        fun callback(router : T)
    }

    companion object {
        @JvmStatic
        fun withClient(client : WPClient) : RouteRequest {
            return RouteRequest(client)
        }
    }
}
