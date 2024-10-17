package com.xzakota.wordpress.response

import okhttp3.Headers
import java.util.Optional
import java.util.function.Consumer

class PagedResponse<T> {
    val clazz : Class<T>
    val self : String?
    val next : String?
    val previous : String?
    val pages : Int?
    val entity : List<T>?

    constructor(
        clazz : Class<T>,
        self : String?,
        next : String?,
        previous : String?,
        pages : Int?,
        list : List<T>?
    ) {
        this.clazz = clazz
        this.self = self
        this.next = next
        this.previous = previous
        this.pages = pages
        this.entity = list
    }

    class Builder<BT>(private val t1 : Class<BT>) {
        private var next : String? = null
        private var self : String? = null
        private var previous : String? = null
        private var posts : MutableList<BT>? = null
        private var pages = 0

        fun withNext(next : String?) : Builder<BT> {
            Optional.ofNullable(next).ifPresent(Consumer { n : String -> this.next = n })
            return this
        }

        fun withSelf(self : String) : Builder<BT> {
            this.self = self
            return this
        }

        fun withPrevious(previous : String?) : Builder<BT> {
            Optional.ofNullable(previous).ifPresent(Consumer { p : String -> this.previous = p })
            return this
        }

        fun withPosts(posts : MutableList<BT>) : Builder<BT> {
            this.posts = posts
            return this
        }

        fun build() : PagedResponse<BT> {
            return PagedResponse<BT>(t1, self, next, previous, pages, posts)
        }

        fun withPages(pages : Int) : Builder<BT> {
            this.pages = pages
            return this
        }

        fun withPages(headers : Headers?) : Builder<BT> {
            val totalPages : Optional<String> = Optional.ofNullable<String>(headers?.get(HEADER_TOTAL_PAGES))
            totalPages.ifPresent(Consumer { t : String -> withPages(t.toInt()) })
            return this
        }
    }

    companion object {
        const val HEADER_TOTAL_PAGES : String = "X-WP-TotalPages"
        const val HEADER_LINK : String = "link"
        const val HEADER_LINK_NEXT : String = "next"
        const val HEADER_LINK_PREV : String = "prev"
    }
}