package com.xzakota.wordpress.route

import com.xzakota.collect.KStrVObj
import com.xzakota.net.HttpMethod
import com.xzakota.net.ResponseTarget
import com.xzakota.wordpress.model.HeaderLink
import com.xzakota.wordpress.response.PagedResponse
import okhttp3.Headers
import java.util.Arrays

@Suppress("UNCHECKED_CAST")
interface IPageable<T : ResponseTarget> : IWPRouter<T> {
    fun getPagedResponse(
        uri : String,
        queryParams : KStrVObj?
    )  : PagedResponse<T> {
        return getPagedResponse(uri, queryParams, getRouter().resRef)
    }

    fun <E : ResponseTarget> getPagedResponse(
        uri : String,
        queryParams : KStrVObj?,
        typeRef : Class<E>,
    ) : PagedResponse<E> {
        val clazz = Class.forName("[L${typeRef.name};") as Class<Array<E>>

        val entity = getRouter().doExchange<Array<E>>(
            HttpMethod.GET,
            uri,
            null,
            clazz,
            queryParams,
            null
        )

        val headers = entity.headers
        val links = parseHeaderLinks(headers)
        val first = Arrays.stream<E>(entity.target).toList()

        return PagedResponse.Builder<E>(typeRef)
            .withPages(headers)
            .withPosts(first)
            .withSelf(uri)
            .withNext(link(links, PagedResponse.HEADER_LINK_NEXT))
            .withPrevious(link(links, PagedResponse.HEADER_LINK_PREV))
            .build()
    }

    private fun link(links : List<HeaderLink>, flag: String) : String? {
        return links.stream().filter { link ->
            flag == link.rel
        }.map { link : HeaderLink ->
            link.href
        }.findFirst().orElse(null)
    }

    private fun parseHeaderLinks(headers : Headers?) : List<HeaderLink> {
        // link: <https://blog.example.com/?rest_route=/wp/v2/posts&status=publish&page=1>; rel="prev", <https://blog.example.com/?rest_route=/wp/v2/posts&status=publish&page=3>; rel="next"
        val linkHeader = headers?.get(PagedResponse.HEADER_LINK) ?: return listOf()
        return linkHeader.split(", ").map { link : String ->
            val linkData = link.split("; ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val href = linkData[0].replace("<", "").replace(">", "")
            val rel = linkData[1].substring(4).replace("\"", "")
            HeaderLink(href, rel)
        }
    }
}
