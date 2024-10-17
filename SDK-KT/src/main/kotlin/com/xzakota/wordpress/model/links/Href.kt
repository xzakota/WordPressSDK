package com.xzakota.wordpress.model.links

import com.alibaba.fastjson2.annotation.JSONField

open class Href {
    @JSONField(name = "href")
    var href : String? = null
}
