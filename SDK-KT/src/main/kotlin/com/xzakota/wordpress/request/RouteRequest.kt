package com.xzakota.wordpress.request

import com.xzakota.collect.KStrVObj
import com.xzakota.net.ResponseTarget
import com.xzakota.wordpress.WPClient
import com.xzakota.wordpress.model.ApplicationPassword
import com.xzakota.wordpress.model.Comment
import com.xzakota.wordpress.model.CustomPost
import com.xzakota.wordpress.model.Link
import com.xzakota.wordpress.model.Medium
import com.xzakota.wordpress.model.Page
import com.xzakota.wordpress.model.Post
import com.xzakota.wordpress.model.Term
import com.xzakota.wordpress.model.User
import com.xzakota.wordpress.request.RouteRequest.Comments
import com.xzakota.wordpress.request.RouteRequest.Pages
import com.xzakota.wordpress.request.RouteRequest.Posts
import com.xzakota.wordpress.request.RouteRequest.RouterCallback
import com.xzakota.wordpress.request.RouteRequest.Terms
import com.xzakota.wordpress.request.RouteRequest.Users
import com.xzakota.wordpress.route.CustomPostRouter
import com.xzakota.wordpress.route.IDRouter
import com.xzakota.wordpress.route.IWPRouter
import com.xzakota.wordpress.route.StatusRouter
import com.xzakota.wordpress.route.WPRoute
import com.xzakota.wordpress.route.WPRouter
import com.xzakota.wordpress.route.WPV2Router

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

    class Pages(
        client : WPClient
    ) : CustomPostRouter<Page>(client, WPRoute.PAGES, Page::class.java)

    class Posts(
        client : WPClient
    ) : CustomPostRouter<Post>(client, WPRoute.POSTS, Post::class.java)

    class Comments(
        client : WPClient
    ) : CustomPostRouter<Comment>(client, WPRoute.COMMENTS, Comment::class.java)

    class Media(
        client : WPClient
    ) : CustomPostRouter<Medium>(client, WPRoute.MEDIA, Medium::class.java)

    class Users(
        client : WPClient
    ) : IDRouter<User>(client, WPRoute.USERS, User::class.java) {
        fun listAppPwdByUserId(userID : Long) : List<ApplicationPassword>? {
            return listAppPwdByUserId(userID.toString())
        }

        @JvmOverloads
        fun listAppPwdByUserId(
            identifier : String,
            queryParams : KStrVObj? = null
        ) : List<ApplicationPassword>? {
            return list(
                getRouter().uri + checkRoute("$identifier/application-passwords"),
                ApplicationPassword::class.java,
                queryParams
            )
        }
    }

    open class Terms(
        client : WPClient,
        mainRoute : String
    ) : WPV2Router<Term>(client, mainRoute, Term::class.java)

    class Categories(
        client : WPClient
    ) : Terms(client, WPRoute.CATEGORIES)

    class Tags(
        client : WPClient
    ) : Terms(client, WPRoute.TAGS)

    class Links(
        client : WPClient
    ) : StatusRouter<Link>(client, WPRoute.LINKS, Link::class.java)

    companion object {
        @JvmStatic
        fun withClient(client : WPClient) : RouteRequest {
            return RouteRequest(client)
        }
    }
}
