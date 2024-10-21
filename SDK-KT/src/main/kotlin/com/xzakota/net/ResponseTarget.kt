package com.xzakota.net

import com.alibaba.fastjson2.JSONObject
import com.alibaba.fastjson2.annotation.JSONField
import com.xzakota.annotation.MapIgnore
import com.xzakota.collect.KStrVObj
import com.xzakota.collect.KStrVObj.Companion.of
import com.xzakota.wordpress.model.RenderedField

open class ResponseTarget {
    @MapIgnore
    @JSONField(serialize = false, deserialize = false)
    var allFields : JSONObject? = null

    open fun asMap() : KStrVObj {
        val result = of()
        var currentClass : Class<*>? = javaClass

        while (currentClass != null && currentClass != Any::class.java) {
            val fields = currentClass.getDeclaredFields()

            for (field in fields) {
                field.setAccessible(true)

                if (field.getAnnotation(MapIgnore::class.java) != null) {
                    continue
                }

                val jsonField = field.getAnnotation(JSONField::class.java)
                val key = if (jsonField != null && !jsonField.name.isEmpty()) {
                    jsonField.name
                } else {
                    field.name
                }

                val value = field.get(this)
                if (value != null) {
                    if (value is RenderedField) {
                        result.put(key, value.rendered)
                    } else {
                        result.put(key, value)
                    }
                }
            }

            currentClass = currentClass.getSuperclass()
        }

        return result
    }

    override fun toString() : String {
        if (allFields != null) {
            return allFields.toString()
        }

        return super.toString()
    }
}
