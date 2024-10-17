package com.xzakota.wordpress.model

import com.alibaba.fastjson2.annotation.JSONField

class Post : CustomPost() {
    @JSONField(name = "sticky")
    var sticky : Boolean? = null

    @JSONField(name = "format")
    var format : String? = null

    @JSONField(name = "categories")
    var categoryIds : List<Long>? = null

    @JSONField(name = "tags")
    var tagIds : List<Long>? = null
}
