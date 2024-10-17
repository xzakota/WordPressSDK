package com.xzakota.wordpress.model.links

import com.alibaba.fastjson2.annotation.JSONField

class VersionHistory : Href() {
    @JSONField(name = "count")
    var count : Int? = null
}
