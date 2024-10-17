##### 📖 English Documentation | 📖 [中文文档](README.md)

# WordPress SDK
[![GitHub license](https://img.shields.io/github/license/xzakota/WordPressSDK?color=blue)](https://github.com/xzakota/WordPressSDK/blob/main/LICENSE)
[![GitHub release](https://img.shields.io/github/v/release/xzakota/WordPressSDK?display_name=release&logo=github&color=green)](https://github.com/xzakota/WordPressSDK/releases)

WP-SDK library built in Kotlin, only supported [Application Passwords](https://developer.wordpress.org/rest-api/using-the-rest-api/authentication/#basic-authentication-with-application-passwords) authentication.

# Dependency
`Maven`:
```xml
<dependency>
    <groupId>com.xzakota.wordpress</groupId>
    <artifactId>sdk-kt</artifactId>
    <version>${version}</version>
</dependency>
```

`Gradle`:
```groovy
dependencies {
    implementation 'com.xzakota.wordpress:sdk-kt:${version}'
}
```

# Usage
`Java`:
```java
WPClient client = new WPClient(server, new Authentication(username, appPwd));
// connect
client.connect();
// connect and test, failure to pass the test will throw an exception
// client.connectTest();

// Send request
// ...

// 断开连接
client.disconnect();
```

`Kotlin`:
```kotlin
val client = WPClient(server, Authentication(username, appPwd))
// connect
client.connect()
// connect and test, failure to pass the test will throw an exception
// client.connectTest()

// send request
// ...

// disconnect
client.disconnect()
```

## Page action
`Java`:
```java
client.request().pages(router -> {
    List<Page> pages = router.list();
    println("List of Page: ");
    println(pages);

    Page page = new Page();
    page.setTitle(new RenderedField("New page title"));
    page.setContent(new RenderedField("New page content"));

    Page createdPage = router.create(page);
    if (createdPage != null) {
        println("Created page id: " + createdPage.getId());
    }
});
```

`Kotlin`:
```kotlin
client.request().pages { router ->
    val pages = router.list()
    println("List of Page: ")
    println(pages)

    router.create(Page().apply {
        title = RenderedField("New page title")
        content = RenderedField("New page content")
    })?.let { createdPage ->
        println("Created page id: " + createdPage.id)
    }
}
```

## Post action
`Java`:
```java
client.request().posts(router -> {
    Post post = router.retrieveById(1L);
    if (post != null) {
        println("Post with ID 1: $post");

        post.setPassword("123456");
        if (post.getId() != null) {
            router.updateById(post.getId(), post);
        }
    } else {
        println("No post with ID 1.");
    }
});
```

`Kotlin`:
```kotlin
client.request().posts { router ->
    val post = router.retrieveById(1L)
    if (post != null) {
        println("Post with ID 1: $post")

        post.password = "123456"
        if (post.id != null) {
            router.updateById(post.id!!, post)
        }
    } else {
        println("No post with ID 1.")
    }
}
```

## Other action
For other uses, please check out the test unit or browse the source code:
1. [Java Test](https://github.com/xzakota/WordPressSDK/blob/master/SDK-KT/src/test/java/com/xzakota/wordpress/WPClientJTest.java)
2. [Kotlin Test](https://github.com/xzakota/WordPressSDK/blob/master/SDK-KT/src/test/kotlin/com/xzakota/wordpress/WPClientTest.kt)

# Thanks
- [Kotlin@JetBrains](https://github.com/JetBrains/kotlin)
- [FASTJSON2@alibaba](https://github.com/alibaba/fastjson2)
- [OkHttp@square](https://github.com/square/okhttp)
- [wp-api-v2-client-java@Afrozaar](https://github.com/Afrozaar/wp-api-v2-client-java)