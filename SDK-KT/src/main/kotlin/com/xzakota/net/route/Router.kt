package com.xzakota.net.route

import com.alibaba.fastjson2.JSON
import com.alibaba.fastjson2.JSONArray
import com.alibaba.fastjson2.JSONObject
import com.xzakota.collect.KStrVObj
import com.xzakota.collect.KStrVObj.Companion.of
import com.xzakota.model.Authentication
import com.xzakota.model.media.MediaTypePool
import com.xzakota.net.BadRequestException
import com.xzakota.net.Client
import com.xzakota.net.HttpMethod
import com.xzakota.net.ResponseEntity
import com.xzakota.net.ResponseTarget
import okhttp3.Credentials.basic
import okhttp3.Headers
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.MultipartBody
import okhttp3.Request
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.lang.RuntimeException
import java.net.URLDecoder
import java.util.function.BiFunction

abstract class Router<T : ResponseTarget>(
    protected val client : Client,
    mainRoute : String,
    val resRef : Class<T>
) {
    val uri = client.server + mainRoute

    @JvmOverloads
    fun <E> getCall(
        typeRef : Class<E>,
        secondaryRoute : String? = null,
        queryParams : KStrVObj? = null
    ) : ResponseEntity<E> {
        return doExchange<E>(HttpMethod.GET, uri, secondaryRoute, typeRef, queryParams, null)
    }

    @JvmOverloads
    fun <E> postCall(
        typeRef : Class<E>,
        secondaryRoute : String? = null,
        requestBody : KStrVObj? = null
    ) : ResponseEntity<E> {
        return doExchange<E>(HttpMethod.POST, uri, secondaryRoute, typeRef, null, requestBody)
    }

    @JvmOverloads
    fun <E> deleteCall(
        typeRef : Class<E>,
        secondaryRoute : String? = null,
        requestBody : KStrVObj? = null
    ) : ResponseEntity<E> {
        return doExchange<E>(HttpMethod.DELETE, uri, secondaryRoute, typeRef, null, requestBody)
    }

    @JvmOverloads
    fun <E> doExchange(
        method : HttpMethod = HttpMethod.GET,
        uri : String,
        secondaryRoute : String? = null,
        typeRef : Class<E>,
        queryParams : KStrVObj? = null,
        requestBody : KStrVObj? = null
    ) : ResponseEntity<E> {
        val internalClient = client.internalClient
            ?: throw RuntimeException("Please first call client.connect()")

        if (uri.isEmpty()) {
            throw RuntimeException("Url is Empty.")
        }

        var uri = uri + (secondaryRoute ?: "")
        val oldUrl = uri.toHttpUrlOrNull()
            ?: throw RuntimeException("$uri is a invalid url.")

        val urlBuilder = oldUrl.newBuilder()
        if (!queryParams.isNullOrEmpty()) {
            queryParams.forEach { k, v ->
                urlBuilder.addQueryParameter(k, v.toString())
            }
        }

        val newUrl = urlBuilder.build()
        val headers = buildHeaders()
        if (client.debug) {
            println(method.value + ": " + URLDecoder.decode(newUrl.toString(), Charsets.UTF_8))
        }

        val entity = ResponseEntity<E>()
        internalClient.newCall(
            Request.Builder()
                .url(newUrl)
                .headers(headers)
                .method(
                    method.value,
                    if (method != HttpMethod.GET && !requestBody.isNullOrEmpty()) {
                        val file = requestBody.get("file")
                        if (file is File) {
                            val body = MultipartBody.Builder().setType(MultipartBody.FORM)
                            requestBody.forEach { k, v ->
                                body.addFormDataPart(k, v.toString())
                            }
                            body.addFormDataPart("file", file.name, file.asRequestBody(MediaTypePool.OCTET_STREAM))
                            body.build()
                        } else {
                            JSON.toJSONString(requestBody).toRequestBody(MediaTypePool.JSON)
                        }
                    } else {
                        null
                    }
                ).build()
        ).execute().use { response ->
            entity.code = response.code
            entity.headers = response.headers
            entity.body = response.body?.string()
                ?: throw BadRequestException("Response body is null.")

            if (entity.code < 200 || entity.code > 299) {
                throw JSON.parseObject(entity.body, BadRequestException::class.java)
            } else {
                val initFields = BiFunction { obj : ResponseTarget, map : JSONObject ->
                    if (obj.allFields == null) {
                        obj.allFields = of()
                    }
                    obj.allFields?.putAll(map)
                }

                val parseObj = JSON.parse(entity.body)
                var e : E? = null
                if (parseObj is JSONArray) {
                    e = parseObj.to(typeRef)

                    if (e is Array<*>) {
                        e.forEachIndexed { i, o ->
                            if (o is ResponseTarget) {
                                initFields.apply(o, parseObj.getJSONObject(i))
                            }
                        }
                    }
                } else if (parseObj is JSONObject) {
                    e = parseObj.to(typeRef)
                    if (e is ResponseTarget) {
                        initFields.apply(e, JSON.parseObject(entity.body))
                    }
                }

                entity.target = e
            }
        }

        return entity
    }

    private fun buildHeaders() : Headers {
        val builder = Headers.Builder()
            .add("User-Agent", client.userAgent)
            .add("Accept", "*/*")

        if (client.auth.type == Authentication.AuthType.APPLICATION) {
            val username = client.auth.username
            val password = client.auth.password
            if (username != null && password != null) {
                val credential = basic(username, password)
                builder.add("Authorization", credential)
            }
        }

        return builder.build()
    }
}
