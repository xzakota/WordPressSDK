package com.xzakota.util

import java.lang.StringBuilder

object ArrayUtils {
    @JvmStatic
    fun toString(array : Array<*>) : String {
        val r = StringBuilder()
        for (o in array) {
            r.append(o).append(",")
        }

        return r.deleteCharAt(r.length - 1).toString()
    }
}
