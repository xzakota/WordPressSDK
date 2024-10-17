package com.xzakota.wordpress.model

import com.alibaba.fastjson2.annotation.JSONField

class DeletedItem<T> {
    @JSONField(name = "deleted")
    var deleted : Boolean? = null

    @JSONField(name = "previous")
    var previous : T? = null

    override fun toString() : String {
        return previous.toString()
    }
}
