package com.xzakota.wordpress.route

import com.xzakota.net.ResponseTarget

@Suppress("UNCHECKED_CAST")
interface IUpdatable<T : ResponseTarget> : IWPRouter<T> {
    fun update(secondaryRoute : String?, target : T) : T? {
        return update(getRouter().uri, getRouter().resRef, secondaryRoute, target)
    }

    fun <E : ResponseTarget> update(
        uri : String,
        typeRef : Class<E>,
        secondaryRoute : String?,
        target : E
    ) : E? {
        return getRouter().postCall<E>(
            typeRef,
            secondaryRoute,
            target.asMap()
        ).target
    }
}