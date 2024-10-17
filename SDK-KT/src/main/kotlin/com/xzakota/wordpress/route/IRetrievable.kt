package com.xzakota.wordpress.route

import com.xzakota.collect.KStrVObj
import com.xzakota.net.ResponseTarget

@Suppress("UNCHECKED_CAST")
interface IRetrievable<T : ResponseTarget> : IWPRouter<T> {
    fun retrieve(secondaryRoute : String, queryParams : KStrVObj?) : T? {
        return retrieve(getRouter().uri, getRouter().resRef, secondaryRoute, queryParams)
    }

    fun <E : ResponseTarget> retrieve(
        uri : String,
        typeRef : Class<E>,
        secondaryRoute : String,
        queryParams : KStrVObj?
    ) : E? {
        return getRouter().getCall<E>(
            typeRef,
            secondaryRoute,
            queryParams
        ).target
    }
}