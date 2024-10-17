package com.xzakota.wordpress.model.links

import com.alibaba.fastjson2.annotation.JSONField

class Links {
    @JSONField(name = "self")
    var self : List<Href>? = null

    @JSONField(name = "collection")
    var collection : List<Href>? = null

    @JSONField(name = "about")
    var about : List<Href>? = null

    @JSONField(name = "author")
    var author : List<Embeddable>? = null

    @JSONField(name = "replies")
    var replies : List<Embeddable>? = null

    @JSONField(name = "version-history")
    var versionHistory : List<VersionHistory>? = null

    @JSONField(name = "wp:attachment")
    var wpAttachment : List<Href>? = null

    @JSONField(name = "wp:term")
    var wpTerm : List<WPTerm>? = null

    @JSONField(name = "curies")
    var curies : List<Curies>? = null
}
