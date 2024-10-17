package com.xzakota.model

import java.util.Locale

open class SlugName @JvmOverloads constructor(
    var name : String,
    var slug : String = name.lowercase(Locale.getDefault())
) {
    override fun toString() : String {
        return "$name($slug)"
    }

    companion object {
        @JvmStatic
        fun parseFormString(s : String) : SlugName {
            val name = s.substring(0, s.lastIndexOf('('))
            val slug = s.substring(s.lastIndexOf('(') + 1, s.lastIndexOf(')'))

            return SlugName(name, slug)
        }
    }
}
