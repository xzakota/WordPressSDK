package com.xzakota.wordpress.route

import com.xzakota.collect.KStrVObj
import com.xzakota.net.ResponseTarget

@Suppress("UNCHECKED_CAST")
interface IUpdatable<T : ResponseTarget> : IWPRouter<T> {
    fun update(secondaryRoute : String?, target : T) : T? {
        return update(secondaryRoute, target, null)
    }

    fun update(
        secondaryRoute : String?,
        target : T,
        otherRequestBody : KStrVObj? = null
    ) : T? {
        return update(getRouter().uri, getRouter().resRef, secondaryRoute, target, otherRequestBody)
    }

    fun <E : ResponseTarget> update(
        uri : String,
        typeRef : Class<E>,
        secondaryRoute : String?,
        target : E
    ) : E? {
        return update(uri, typeRef, secondaryRoute, target, null)
    }

    fun <E : ResponseTarget> update(
        uri : String,
        typeRef : Class<E>,
        secondaryRoute : String?,
        target : E,
        otherRequestBody : KStrVObj? = null
    ) : E? {
        return getRouter().postCall<E>(
            typeRef,
            secondaryRoute,
            target.asMap().push(otherRequestBody)
        ).target
    }
}