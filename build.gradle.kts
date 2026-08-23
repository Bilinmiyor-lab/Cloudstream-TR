import com.android.build.gradle.BaseExtension
import com.lagradost.cloudstream3.gradle.CloudstreamExtension

buildscript {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.1.1")
        classpath("com.github.recloudstream:gradle:master-SNAPSHOT")
        // Kotlin sürümünü R8/D8 hatasını önlemek için sabitliyoruz:
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.24")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
}

// Alt projelerdeki blokların tanınması için gerekli fonksiyonlar:
fun Project.cloudstream(configuration: CloudstreamExtension.() -> Unit) = extensions.getByName("cloudstream").configuration()
fun Project.android(configuration: BaseExtension.() -> Unit) = extensions.getByName("android").configuration()

subprojects {
    apply(plugin = "com.android.library")
    apply(plugin = "kotlin-android")
    apply(plugin = "com.lagradost.cloudstream3.gradle")

    cloudstream {
        setRepo(System.getenv("GITHUB_REPOSITORY") ?: "https://github.com/Cloudstream-TR/Cloudstream-TR")
    }

    android {
        compileSdkVersion(33)
        defaultConfig {
            minSdk = 21
            targetSdk = 33
        }
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }
    }
}
