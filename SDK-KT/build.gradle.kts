import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
    kotlin("jvm")
    alias(libs.plugins.vanniktech.maven.publish)
}

group = "com.xzakota.wordpress"
version = "1.1"
val artifactID = "sdk-kt"
val gitUrl = "https://github.com/xzakota/WordPressSDK"
val author = "xzakota"

dependencies {
    // implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(libs.fastjson2)
    implementation(libs.okhttp)

    testImplementation(kotlin("test"))
    testImplementation(libs.dotenv) {
        exclude(group = "org.jetbrains.kotlin")
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    jvmToolchain(17)
    compilerOptions {
        apiVersion = KotlinVersion.KOTLIN_2_0
        languageVersion = KotlinVersion.KOTLIN_2_0
    }
}

tasks {
    jar {
        manifest {
            attributes(
                mapOf(
                    "Implementation-Title" to project.name,
                    "Implementation-Version" to project.version
                )
            )
        }
    }

    javadoc {
        options.encoding = "UTF-8"
    }

    test {
        useJUnitPlatform()
    }
}

mavenPublishing {
    coordinates(group.toString(), artifactID, version.toString())

    pom {
        name = rootProject.name
        description = "WP-SDK library built in Kotlin."
        url = gitUrl
        licenses {
            license {
                name = "The Apache Software License, Version 2.0"
                url = "https://www.apache.org/licenses/LICENSE-2.0.txt"
            }
        }
        developers {
            developer {
                id = author
                name = author
                email = "xzakota@qq.com"
            }
        }

        scm {
            connection = "scm:git:${gitUrl}.git"
            developerConnection = connection
            url = gitUrl
        }
    }

    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL, automaticRelease = true)
    signAllPublications()
}