##### [📖 English Documentation](README_EN.md) | 📖 中文文档

# WordPress SDK
[![GitHub license](https://img.shields.io/github/license/xzakota/WordPressSDK?color=blue)](https://github.com/xzakota/WordPressSDK/blob/main/LICENSE)
[![GitHub release](https://img.shields.io/github/v/release/xzakota/WordPressSDK?display_name=release&logo=github&color=green)](https://github.com/xzakota/WordPressSDK/releases)

使用 kotlin 编写构建的 WP-SDK 库，暂时只支持 [Application Passwords](https://developer.wordpress.org/rest-api/using-the-rest-api/authentication/#basic-authentication-with-application-passwords) 验证。

# 依赖
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

# 使用
`Java`:
```java
WPClient client = new WPClient(server, new Authentication(username, appPwd));
// 正常连接
client.connect();
// 连接并测试，未通过测试将抛出异常
// client.connectTest();

// 发送请求
// ...

// 断开连接
client.disconnect();
```

`Kotlin`:
```kotlin
val client = WPClient(server, Authentication(username, appPwd))
// 正常连接
client.connect()
// 连接并测试，未通过测试将抛出异常
// client.connectTest()

// 发送请求
// ...

// 断开连接
client.disconnect()
```

## 页面操作
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

## 文章操作
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

## 其他操作
其他使用请查看测试单元或浏览源代码
1. [Java Test](https://github.com/xzakota/WordPressSDK/blob/master/SDK-KT/src/test/java/com/xzakota/wordpress/WPClientJTest.java)
2. [Kotlin Test](https://github.com/xzakota/WordPressSDK/blob/master/SDK-KT/src/test/kotlin/com/xzakota/wordpress/WPClientTest.kt)

# 其他衍生项目
- File2Server(f2s): 计划开发，目前进度 20%。将本地 markdown、json 等文件解析上传至服务器，包括但不限于文章、页面、链接，字段丰富，管理简单。WordPress 平台则使用本项目(WP-SDK)。

# 鸣谢
- [Kotlin@JetBrains](https://github.com/JetBrains/kotlin)
- [FASTJSON2@alibaba](https://github.com/alibaba/fastjson2)
- [OkHttp@square](https://github.com/square/okhttp)
- [wp-api-v2-client-java@Afrozaar](https://github.com/Afrozaar/wp-api-v2-client-java)