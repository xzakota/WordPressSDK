package com.xzakota.net

import okhttp3.Headers

class ResponseEntity<T> {
    var code = 200
    var headers : Headers? = null
    var body : String? = null
    var target : T? = null
}
