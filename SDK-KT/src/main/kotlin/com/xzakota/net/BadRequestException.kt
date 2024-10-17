package com.xzakota.net

import com.alibaba.fastjson2.annotation.JSONField

class BadRequestException() : RuntimeException() {
    @JSONField(name = "code")
    var code = "bad_request"

    @JSONField(name = "message")
    override var message : String? = null

    @JSONField(name = "data")
    var data = Data()

    constructor(message : String) : this() {
        this.message = message
    }

    override fun toString() : String {
        return "错误的请求($code[${data.status}]): $message"
    }

    class Data @JvmOverloads constructor(
        @JSONField(name = "status")
        var status : Int = 400
    )
}
