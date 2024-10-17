package com.xzakota.wordpress.model

import com.alibaba.fastjson2.annotation.JSONField
import com.xzakota.annotation.MapIgnore
import com.xzakota.wordpress.model.links.Links

open class CustomPost : StatefulItem() {
    @JSONField(name = "date")
    var date : String? = null

    @JSONField(name = "date_gmt")
    var dateGmt : String? = null

    @JSONField(name = "guid")
    var guid : RenderedField? = null

    @JSONField(name = "modified")
    var modified : String? = null

    @JSONField(name = "modified_gmt")
    var modifiedGmt : String? = null

    @JSONField(name = "type")
    var type : String? = null

    @JSONField(name = "permalink_template")
    var permalinkTemplate : String? = null

    @JSONField(name = "generated_slug")
    var generatedSlug : String? = null

    @JSONField(name = "link")
    var link : String? = null

    @JSONField(name = "title")
    var title : RenderedField? = null

    @JSONField(name = "content")
    var content : RenderedField? = null

    @JSONField(name = "password")
    var password : String? = null

    @JSONField(name = "author")
    var author : Long? = null

    @JSONField(name = "comment_status")
    var commentStatus : String? = null

    @JSONField(name = "ping_status")
    var pingStatus : String? = null

    @JSONField(name = "template")
    var template : String? = null

    @JSONField(name = "class_list")
    var classList : List<String>? = null

    @JSONField(name = "meta")
    var meta : Any? = null

    @JSONField(name = "excerpt")
    var excerpt : RenderedField? = null

    @JSONField(name = "featured_media")
    var featuredMedia : Long? = null

    @MapIgnore
    @JSONField(name = "_links")
    var links : Links? = null

    override fun toString() : String {
        return super.toString() + ": " + title + "(" + type + ")"
    }
}
