package com.xzakota.wordpress.model

import com.alibaba.fastjson2.annotation.JSONField

class Page : CustomPost() {
    @JSONField(name = "menu_order")
    var menuOrder : Long? = null

    @JSONField(name = "parent")
    var parent : Long? = null

    override fun toString() : String {
        return super.toString() + "(" + slug + ")"
    }
}
