package com.xzakota.net

import okhttp3.Headers

class ResponseEntity<T> {
    var code : Int? = null
    var headers : Headers? = null
    var body : String? = null
    var target : T? = null
}
