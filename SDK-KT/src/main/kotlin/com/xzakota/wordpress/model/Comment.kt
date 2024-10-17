package com.xzakota.wordpress.model

import com.alibaba.fastjson2.annotation.JSONField

class Comment : CustomPost() {
    @JSONField(name = "author_email")
    var authorEmail : String? = null

    @JSONField(name = "author_ip")
    var authorIP : String? = null

    @JSONField(name = "author_name")
    var authorName : String? = null

    @JSONField(name = "author_url")
    var authorUrl : String? = null

    @JSONField(name = "author_user_agent")
    var authorUserAgent : String? = null

    @JSONField(name = "parent")
    var parent : Long? = null

    @JSONField(name = "author_avatar_urls")
    var authorAvatarUrls : Avatar? = null

    override fun toString() : String {
        return "ID($id): $authorName($authorEmail)"
    }
}
