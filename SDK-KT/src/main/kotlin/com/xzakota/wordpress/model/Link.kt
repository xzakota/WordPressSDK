package com.xzakota.wordpress.model

import com.alibaba.fastjson2.annotation.JSONField
import com.xzakota.wordpress.model.links.Links

class Link : StatefulItem() {
    @JSONField(name = "url")
    var url : String? = null

    @JSONField(name = "image")
    var image : String? = null

    @JSONField(name = "target")
    var target : String? = null

    @JSONField(name = "description")
    var description : String? = null

    @JSONField(name = "author")
    var author : String? = null

    @JSONField(name = "rating")
    var rating : String? = null

    @JSONField(name = "updated")
    var updated : Any? = null

    @JSONField(name = "visible")
    var visible : String? = null

    @JSONField(name = "notes")
    var notes : String? = null

    @JSONField(name = "rel")
    var rel : String? = null

    @JSONField(name = "rss")
    var rss : String? = null

    @JSONField(name = "categories")
    var categoryIds : List<Long>? = null

    @JSONField(name = "_links")
    var links : Links? = null

    override fun toString() : String {
        return super.toString() + ": " + url
    }
}
