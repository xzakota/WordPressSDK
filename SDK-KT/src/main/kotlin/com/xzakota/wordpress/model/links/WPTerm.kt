package com.xzakota.wordpress.model.links

import com.alibaba.fastjson2.annotation.JSONField

class WPTerm : Embeddable() {
    @JSONField(name = "taxonomy")
    var taxonomy : String? = null
}
