@file:Suppress("UnstableApiUsage")

pluginManagement {
    repositories {
        maven(uri("https://maven.aliyun.com/repository/gradle-plugin"))
        maven(uri("https://maven.aliyun.com/repository/public/"))

        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven(uri("https://maven.aliyun.com/repository/public/"))

        mavenLocal()
        google()
        mavenCentral()
    }
}

rootProject.name = "WordPressSDK"
include("SDK-KT")