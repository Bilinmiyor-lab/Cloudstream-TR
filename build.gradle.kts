import com.android.build.gradle.BaseExtension
import com.lagradost.cloudstream3.gradle.CloudstreamExtension

buildscript {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.2.2")
        classpath("com.github.recloudstream:gradle:master-SNAPSHOT")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.22")
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
            minSdkVersion(21)
            targetSdkVersion(34)
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }
    }

    dependencies {
        val implementation by configurations
        
        // CloudStream ana kütüphanesi (MainAPI, TvType vb. için zorunlu)
        implementation("com.github.recloudstream.cloudstream:library:-SNAPSHOT")
        
        // HTML Parser ve HTTP Kütüphaneleri
        implementation("org.jsoup:jsoup:1.18.1")
        implementation("com.github.Blatzar:NiceHttp:0.4.11")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.16.0")
    }
}
