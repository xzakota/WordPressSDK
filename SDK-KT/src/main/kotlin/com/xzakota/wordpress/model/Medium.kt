package com.xzakota.wordpress.model

import com.alibaba.fastjson2.annotation.JSONField

class Medium : CustomPost() {
    @JSONField(name = "alt_text")
    var altText : String? = null

    @JSONField(name = "caption")
    var caption : String? = null

    @JSONField(name = "description")
    var description : RenderedField? = null

    @JSONField(name = "media_type")
    var mediaType : String? = null

    @JSONField(name = "mime_type")
    var mimeType : String? = null

    @JSONField(name = "media_details")
    var mediaDetails : Map<String, Any>? = null

    @JSONField(name = "post")
    var post : Long? = null

    @JSONField(name = "source_url")
    var sourceUrl : String? = null

    @JSONField(name = "missing_image_sizes")
    var missingImageSizes : List<Any>? = null
}
