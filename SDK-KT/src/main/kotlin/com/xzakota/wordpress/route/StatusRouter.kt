package com.xzakota.wordpress.route

import com.xzakota.collect.KStrVObj
import com.xzakota.util.ArrayUtils
import com.xzakota.wordpress.WPClient
import com.xzakota.wordpress.model.IDItem
import com.xzakota.wordpress.model.status.Status
import com.xzakota.wordpress.response.PagedResponse

open class StatusRouter<T : IDItem>(
    client : WPClient, mainRoute : String, typeRef : Class<T>
) : IDRouter<T>(client, mainRoute, typeRef) {
    fun listByStatus(vararg status : String?) : List<T> {
        return list(argOf(status))
    }

    fun listByStatus(vararg status : Status?) : List<T> {
        return list(argOf(status))
    }

    fun listByStatusPage(
        page : Int,
        perPage : Int = IListable.DEFAULT_PRE_PAGE,
        vararg status : Status?
    ) : PagedResponse<T> {
        return listByPage(page, perPage, argOf(status))
    }

    private fun argOf(status : Array<*>) : KStrVObj {
        return KStrVObj.of().apply {
            if (status.isNotEmpty()) {
                put("status", ArrayUtils.toString(status))
            }
        }
    }
}
