package com.xzakota.wordpress.model

import com.alibaba.fastjson2.annotation.JSONField
import com.xzakota.net.ResponseTarget

class ApplicationPassword : ResponseTarget() {
    @JSONField(name = "uuid")
    var uuid : String? = null

    @JSONField(name = "app_id")
    var appID : String? = null

    @JSONField(name = "name")
    var name : String? = null

    @JSONField(name = "password")
    var password : String? = null

    @JSONField(name = "created")
    var created : String? = null

    @JSONField(name = "last_used")
    var lastUsed : String? = null

    @JSONField(name = "last_ip")
    var lastIP : String? = null

    override fun toString() : String {
        return "$name($uuid) created $created lastUsed $lastUsed"
    }
}
