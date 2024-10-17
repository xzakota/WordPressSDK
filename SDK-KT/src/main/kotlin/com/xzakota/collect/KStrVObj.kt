package com.xzakota.collect

import java.util.HashMap

@Suppress("unused")
class KStrVObj : HashMap<String, Any?> {
    private constructor() : super()
    private constructor(map : KStrVObj) : super(map)

    fun push(k : String, v : Any?) : KStrVObj {
        put(k, v)
        return this
    }

    fun push(map : KStrVObj) : KStrVObj {
        putAll(map)
        return this
    }

    companion object {
        @JvmStatic
        @JvmOverloads
        fun of(map : KStrVObj? = null) : KStrVObj {
            return if (map == null) {
                KStrVObj()
            } else {
                KStrVObj(map)
            }
        }
    }
}
