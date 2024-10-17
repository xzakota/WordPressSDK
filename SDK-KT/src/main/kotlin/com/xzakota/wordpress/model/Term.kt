package com.xzakota.wordpress.model

import com.alibaba.fastjson2.annotation.JSONField
import com.xzakota.wordpress.model.links.Links

open class Term : IDItem() {
    @JSONField(name = "count")
    var count : Long? = null

    @JSONField(name = "description")
    var description : String? = null

    @JSONField(name = "link")
    var link : String? = null

    @JSONField(name = "name")
    var name : String? = null

    @JSONField(name = "taxonomy")
    var taxonomy : String? = null

    @JSONField(name = "parent")
    var parent : Long? = null

    @JSONField(name = "_links")
    var links : Links? = null

    override fun toString() : String {
        return super.toString() + ": " + name + "(" + slug + ")"
    }
}
