import com.android.build.gradle.BaseExtension
import com.lagradost.cloudstream3.gradle.CloudstreamExtension

buildscript {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.1.4")
        classpath("com.github.recloudstream:gradle:master-SNAPSHOT")
        // ÇÖZÜM: CloudStream'in güncel versiyonuyla eşleşmesi için Kotlin 2.0.0'a yükseltildi
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:2.0.0")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
}

fun Project.cloudstream(configuration: CloudstreamExtension.() -> Unit) =
    extensions.configure("cloudstream", configuration)

fun Project.android(configuration: BaseExtension.() -> Unit) =
    extensions.configure("android", configuration)

subprojects {
    apply(plugin = "com.android.library")
    apply(plugin = "kotlin-android")
    apply(plugin = "com.lagradost.cloudstream3.gradle")

    cloudstream {
        setRepo(System.getenv("GITHUB_REPOSITORY") ?: "https://github.com/user/repo")
    }

    android {
        compileSdkVersion(34)
        defaultConfig {
            // Uyarıları gidermek için minSdk ve targetSdk olarak güncellendi
            minSdk = 21
            targetSdk = 34
        }
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }
    }

    dependencies {
        val compileOnly by configurations
        compileOnly("com.github.recloudstream:cloudstream:master-SNAPSHOT")
        compileOnly("org.jsoup:jsoup:1.17.2")
    }
}
