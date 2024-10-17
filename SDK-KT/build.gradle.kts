import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
    kotlin("jvm")
    alias(libs.plugins.vanniktech.maven.publish)
}

group = "com.xzakota.wordpress"
version = "1.0"

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
    coordinates(project.group.toString(), "sdk-kt", project.version.toString())

    pom {
        name = rootProject.name
        description = "WP-SDK library built in Kotlin."
        url = "https://github.com/xzakota/WordPressSDK"
        licenses {
            license {
                name = "The Apache Software License, Version 2.0"
                url = "https://www.apache.org/licenses/LICENSE-2.0.txt"
            }
        }
        developers {
            developer {
                id = "xzakota"
                name = "xzakota"
                email = "xzakota@qq.com"
            }
        }

        scm {
            connection = "scm:git:https://github.com/xzakota/WordPressSDK.git"
            developerConnection = "scm:git:https://github.com/xzakota/WordPressSDK.git"
            url = "https://github.com/xzakota/WordPressSDK"
        }
    }

    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL, automaticRelease = true)
    signAllPublications()
}