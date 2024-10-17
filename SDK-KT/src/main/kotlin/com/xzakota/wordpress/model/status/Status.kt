package com.xzakota.wordpress.model.status

import com.xzakota.model.SlugName

class Status(name: String) : SlugName(name) {
    override fun toString() : String {
        return name
    }
}