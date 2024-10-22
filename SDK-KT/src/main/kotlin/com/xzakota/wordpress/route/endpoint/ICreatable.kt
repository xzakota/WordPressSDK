package com.xzakota.wordpress.route.endpoint

import com.xzakota.collect.KStrVObj
import com.xzakota.net.ResponseTarget
import com.xzakota.wordpress.route.IWPRouter

@Suppress("UNCHECKED_CAST")
interface ICreatable<T : ResponseTarget> : IWPRouter<T> {
    fun create(target : T) : T? {
        return create(target, null)
    }

    fun create(target : T, otherRequestBody : KStrVObj?) : T? {
        return create(getRouter().uri, getRouter().resRef, target, otherRequestBody)
    }

    fun <E : ResponseTarget> create(
        uri : String,
        typeRef : Class<E>,
        target : E
    ) : E? {
        return create(uri, typeRef, target, null)
    }

    fun <E : ResponseTarget> create(
        uri : String,
        typeRef : Class<E>,
        target : E,
        otherRequestBody : KStrVObj?
    ) : E? {
        return getRouter().postCall<E>(
            typeRef,
            null,
            target.asMap().push(otherRequestBody)
        ).target
    }
}