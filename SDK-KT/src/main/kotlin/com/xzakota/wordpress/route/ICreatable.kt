package com.xzakota.wordpress.route

import com.xzakota.net.ResponseTarget

@Suppress("UNCHECKED_CAST")
interface ICreatable<T : ResponseTarget> : IWPRouter<T> {
    fun create(target : T) : T? {
        return create(getRouter().uri, getRouter().resRef, target)
    }

    fun <E : ResponseTarget> create(
        uri : String,
        typeRef : Class<E>,
        target : E
    ) : E? {
        return getRouter().postCall<E>(
            typeRef,
            null,
            target.asMap()
        ).target
    }
}