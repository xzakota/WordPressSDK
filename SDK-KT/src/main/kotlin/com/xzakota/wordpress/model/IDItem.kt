package com.xzakota.wordpress.model

import com.alibaba.fastjson2.annotation.JSONField
import com.xzakota.net.ResponseTarget

open class IDItem : ResponseTarget() {
    @JSONField(name = "id")
    var id : Long? = null

    @JSONField(name = "slug")
    var slug : String? = null

    override fun toString() : String {
        return if (id != null) {
            "ID($id)"
        } else if (slug != null) {
            "SLUG($slug)"
        } else {
            super.toString()
        }
    }
}
