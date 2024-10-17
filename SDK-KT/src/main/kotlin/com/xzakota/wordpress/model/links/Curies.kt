package com.xzakota.wordpress.model.links

import com.alibaba.fastjson2.annotation.JSONField

class Curies : Href() {
    @JSONField(name = "name")
    var name : String? = null

    @JSONField(name = "templated")
    var templated : Boolean? = null
}
