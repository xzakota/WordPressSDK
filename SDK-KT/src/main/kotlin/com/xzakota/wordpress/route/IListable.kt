package com.xzakota.wordpress.route

import com.xzakota.collect.KStrVObj
import com.xzakota.net.ResponseTarget
import com.xzakota.wordpress.response.PagedResponse

@Suppress("UNCHECKED_CAST")
interface IListable<T : ResponseTarget> : IPageable<T> {
    fun list(queryParams : KStrVObj?) : List<T> {
        return list(getRouter().uri, getRouter().resRef, queryParams)
    }

    fun <E : ResponseTarget> list(
        uri : String,
        typeRef : Class<E>,
        queryParams : KStrVObj?
    ) : List<E> {
        return list(
            uri,
            typeRef,
            DEFAULT_START_PAGE,
            DEFAULT_PRE_PAGE,
            DEFAULT_TOTAL_PAGE,
            queryParams
        )
    }

    fun list(
        startPage : Int,
        perPage : Int,
        totalPage : Int,
        otherQueryParams : KStrVObj?
    ) : List<T> {
        return list(
            getRouter().uri,
            getRouter().resRef,
            startPage,
            perPage,
            totalPage,
            otherQueryParams
        )
    }

    fun <E : ResponseTarget> list(
        uri : String,
        typeRef : Class<E>,
        startPage : Int,
        perPage : Int,
        totalPage : Int,
        otherQueryParams : KStrVObj?
    ) : List<E> {
        var totalPage = totalPage
        var getPage = 0

        if (totalPage < 0) {
            totalPage = DEFAULT_TOTAL_PAGE
        }

        var pagedResponse = listByPage(uri, typeRef, startPage, perPage, otherQueryParams)
        val collected = mutableListOf<E>()
        pagedResponse.entity?.let { collected.addAll(it) }
        while (getPage <= totalPage && pagedResponse.next != null) {
            pagedResponse = getPagedResponse(pagedResponse.next, null, typeRef)
            pagedResponse.entity?.let { collected.addAll(it) }
            getPage++
        }
        return collected
    }

    fun listByPage(page : Int) : PagedResponse<T> {
        return listByPage(page, DEFAULT_PRE_PAGE)
    }

    fun listByPage(page : Int, perPage : Int) : PagedResponse<T> {
        return listByPage(page, perPage, null)
    }

    fun listByPage(
        page : Int,
        perPage : Int,
        otherQueryParams : KStrVObj?
    ) : PagedResponse<T> {
        return listByPage(getRouter().uri, getRouter().resRef, page, perPage, otherQueryParams)
    }

    fun <E : ResponseTarget> listByPage(
        uri : String,
        typeRef : Class<E>,
        page : Int
    ) : PagedResponse<E> {
        return listByPage(uri, typeRef, page, DEFAULT_PRE_PAGE)
    }

    fun <E : ResponseTarget> listByPage(
        uri : String,
        typeRef : Class<E>,
        page : Int,
        perPage : Int,
    ) : PagedResponse<E> {
        return listByPage(uri, typeRef, page, DEFAULT_PRE_PAGE, null)
    }

    fun <E : ResponseTarget> listByPage(
        uri : String,
        typeRef : Class<E>,
        page : Int,
        perPage : Int,
        otherQueryParams : KStrVObj?
    ) : PagedResponse<E> {
        var page = page
        var perPage = perPage
        var otherQueryParams = otherQueryParams
        if (otherQueryParams == null) {
            otherQueryParams = KStrVObj.of()
        }

        if (page < 1) {
            page = DEFAULT_START_PAGE
        }
        if (perPage < 1) {
            perPage = DEFAULT_PRE_PAGE
        }
        if (page != DEFAULT_START_PAGE) {
            otherQueryParams.put("page", page)
        }
        if (perPage != DEFAULT_PRE_PAGE) {
            otherQueryParams.put("per_page", perPage)
        }
        return getPagedResponse(uri, otherQueryParams, typeRef)
    }

    companion object {
        const val DEFAULT_PRE_PAGE : Int = 10
        const val DEFAULT_START_PAGE : Int = 1
        const val DEFAULT_TOTAL_PAGE : Int = 0
    }
}
