package com.xzakota.wordpress.model

import com.alibaba.fastjson2.annotation.JSONField
import com.xzakota.annotation.MapIgnore
import com.xzakota.wordpress.model.links.Links

class User : IDItem() {
    @JSONField(name = "name")
    var name : String? = null

    @JSONField(name = "username")
    var username : String? = null

    @JSONField(name = "first_name")
    var firstName : String? = null

    @JSONField(name = "last_name")
    var lastName : String? = null

    @JSONField(name = "email")
    var email : String? = null

    @JSONField(name = "url")
    var url : String? = null

    @JSONField(name = "description")
    var description : String? = null

    @JSONField(name = "link")
    var link : String? = null

    @JSONField(name = "locale")
    var locale : String? = null

    @JSONField(name = "nickname")
    var nickname : String? = null

    @JSONField(name = "registered_date")
    var registeredDate : String? = null

    @JSONField(name = "roles")
    var roles : List<String>? = null

    @JSONField(name = "password")
    var password : String? = null

    @JSONField(name = "capabilities")
    var capabilities : Map<String, Any>? = null

    @JSONField(name = "extra_capabilities")
    var extraCapabilities : Map<String, Any>? = null

    @JSONField(name = "avatar_urls")
    var avatarUrls : Avatar? = null

    @MapIgnore
    @JSONField(name = "_links")
    var links : Links? = null

    override fun toString() : String {
        return super.toString() + ": " + username + "(" + email + ")"
    }
}
