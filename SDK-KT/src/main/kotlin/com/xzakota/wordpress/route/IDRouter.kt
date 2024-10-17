package com.xzakota.wordpress.route

import com.xzakota.collect.KStrVObj
import com.xzakota.wordpress.WPClient
import com.xzakota.wordpress.model.DeletedItem
import com.xzakota.wordpress.model.IDItem

@Suppress("unused")
open class IDRouter<T : IDItem>(
    client : WPClient, mainRoute : String, typeRef : Class<T>
) : WPV2Router<T>(client, mainRoute, typeRef) {
    fun list() : List<T> {
        return list(null)
    }

    fun retrieveById(id : Long) : T? {
        return retrieveById(id.toString())
    }

    @JvmOverloads
    fun retrieveById(identifier : String, queryParams : KStrVObj? = null) : T? {
        return retrieve(checkRoute(identifier), queryParams)
    }

    fun updateById(id : Long, target : T) : T? {
        return updateById(id.toString(), target)
    }

    fun updateById(identifier : String, target : T) : T? {
        return update(checkRoute(identifier), target)
    }

    @JvmOverloads
    fun deleteById(id : Long, force : Boolean = false) : DeletedItem<T>? {
        return deleteById(id.toString(), KStrVObj.of().push("force", force))
    }

    fun deleteById(identifier : String, requestBody : KStrVObj?) : DeletedItem<T>? {
        return delete(checkRoute(identifier), requestBody)
    }
}
