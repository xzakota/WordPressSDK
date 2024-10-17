package com.xzakota.wordpress.model

import com.alibaba.fastjson2.annotation.JSONField

abstract class StatefulItem : IDItem() {
    @JSONField(name = "status")
    var status : String? = null
}
