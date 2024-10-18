package com.xzakota.wordpress.model

import com.alibaba.fastjson2.annotation.JSONField

class RenderedField {
    @JSONField(name = "raw")
    var raw : String? = null

    @JSONField(name = "rendered")
    var rendered : String

    @JSONField(name = "protected")
    var protectedValue : Boolean? = null

    constructor(rendered : String) {
        this.rendered = rendered
    }

    override fun toString() : String {
        return rendered
    }
}