package com.xzakota.wordpress.route

import com.alibaba.fastjson2.JSON
import com.xzakota.collect.KStrVObj
import com.xzakota.net.ResponseTarget
import com.xzakota.wordpress.model.DeletedItem

@Suppress("UNCHECKED_CAST")
interface IDeletable<T : ResponseTarget> : IWPRouter<T> {
    fun delete(
        secondaryRoute : String,
        requestBody : KStrVObj?
    ) : DeletedItem<T>? {
        return delete(getRouter().uri, getRouter().resRef, secondaryRoute, requestBody)
    }

    fun <E : ResponseTarget> delete(
        uri : String,
        typeRef : Class<E>,
        secondaryRoute : String,
        requestBody : KStrVObj?
    ) : DeletedItem<E>? {
        val target = getRouter().deleteCall<String>(
            String::class.java,
            secondaryRoute,
            requestBody
        ).target ?: return null

        val item = DeletedItem<E>()
        val jsonObj = JSON.parseObject(target)
        val deleted = jsonObj.getBoolean("deleted")
        if (deleted == null) {
            item.deleted = false
            item.previous = JSON.parseObject<E>(target, typeRef)
        } else {
            item.deleted = deleted
            item.previous = jsonObj.getObject<E>("previous", typeRef)
        }

        return item
    }
}