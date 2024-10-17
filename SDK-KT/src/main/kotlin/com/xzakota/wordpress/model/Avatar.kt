package com.xzakota.wordpress.model

import com.alibaba.fastjson2.annotation.JSONField

class Avatar {
    @JSONField(name = "24")
    var size24 : String? = null

    @JSONField(name = "48")
    var size48 : String? = null

    @JSONField(name = "96")
    var size96 : String? = null
}
