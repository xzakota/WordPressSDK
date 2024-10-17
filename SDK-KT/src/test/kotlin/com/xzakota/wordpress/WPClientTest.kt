package com.xzakota.wordpress

import com.xzakota.collect.KStrVObj
import com.xzakota.model.Authentication
import com.xzakota.net.ResponseTarget
import com.xzakota.wordpress.model.status.ItemStatus
import io.github.cdimascio.dotenv.Dotenv
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import kotlin.test.Test

class WPClientTest {
    companion object {
        lateinit var client : WPClient

        @BeforeAll
        @JvmStatic
        fun setup() {
            val env = Dotenv.load()
            val server = env["SERVER_HTTPS_URL"]
            val username = env["ADMIN_USERNAME"]
            val password = env["ADMIN_APPLICATION_PASSWORD"]

            if (server == null || username == null || password == null) {
                error("Please configure .env file!")
                return
            }

            client = WPClient(server, Authentication(username, password))
            client.userAgent = "Test ${client.userAgent}"
            client.debug = true
            client.connectTest()
            divide()
        }

        @AfterAll
        @JvmStatic
        fun release() {
            client.disconnect()
        }

        private fun divide() {
            println("-".repeat(80))
        }
    }

    @Test
    fun mediaTest() {
        val media = client.request().media().list()
        println("List of Medium: ")
        println(media)
        divide()
    }

    @Test
    fun pageTest() {
        val status = arrayOf(ItemStatus.PRIVATE, ItemStatus.PUBLISH)
        val pages = client.request().pages().listByStatus(*status)
        println("List of Page" + status.contentToString() + ": ")
        println(pages)
        divide()
    }

    @Test
    fun postTest() {
        val post = client.request().posts().retrieveById(1L)
        if (post != null) {
            println("Post with ID 1: $post")
        } else {
            println("No post with ID 1.")
        }

        divide()
    }

    @Test
    fun themeTest() {
        client.request {
            router("/themes", ResponseTarget::class.java) { router ->
                var themes = router.list(KStrVObj.of())
                val themeStylesheet = themes.map { obj : ResponseTarget ->
                    obj.allFields?.get("stylesheet")
                }.toList()
                println(themeStylesheet)
            }
        }

        divide()
    }
}
