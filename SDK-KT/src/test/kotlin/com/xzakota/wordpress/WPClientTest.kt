package com.xzakota.wordpress

import com.xzakota.collect.KStrVObj
import com.xzakota.model.Authentication
import com.xzakota.net.ResponseTarget
import com.xzakota.wordpress.model.Medium
import com.xzakota.wordpress.model.RenderedField
import com.xzakota.wordpress.model.status.ItemStatus
import io.github.cdimascio.dotenv.Dotenv
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import java.io.File
import kotlin.test.Test

class WPClientTest {
    companion object {
        lateinit var client : WPClient

        @BeforeAll
        @JvmStatic
        fun setup() {
            val env = Dotenv.load()
            val server = env["SERVER_HTTPS_URL"]
            val username = env["AUTH_USERNAME"]
            val password = env["AUTH_APPLICATION_PASSWORD"]

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
    fun userTest() {
        val user = client.request().users().retrieveById(1L)
        if (user != null) {
            println("The first user of the website: ")
            println(user.asMap())
        }
        divide()
    }

    @Test
    fun mediaTest() {
        client.request().media { mediaRouter ->
            val media = mediaRouter.list()
            println("List of Medium: ")
            println(media)
            divide()

            val file = File("/path/test.png")
            if (file.exists() && file.isFile()) {
                val newMedium = Medium()
                newMedium.resource = file
                newMedium.title = RenderedField("test")
                val uploadedMedium = mediaRouter.create(newMedium)
                println("Uploaded new file: $uploadedMedium")
            }
        }
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
