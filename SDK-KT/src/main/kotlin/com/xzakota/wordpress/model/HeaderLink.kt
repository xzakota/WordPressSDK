package com.xzakota.wordpress.model

class HeaderLink(val href : String, val rel : String) {
    override fun toString() : String {
        return "$rel: $href"
    }
}
