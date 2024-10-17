package com.xzakota.wordpress.model.links

import com.alibaba.fastjson2.annotation.JSONField

open class Embeddable : Href() {
    @JSONField(name = "embeddable")
    var embeddable : Boolean? = null
}
