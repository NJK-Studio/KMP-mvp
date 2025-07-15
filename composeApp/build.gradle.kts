import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlinxSerialization)
    alias(libs.plugins.ksp)
    //alias(libs.plugins.skie)
//    alias(libs.plugins.room)
    id("com.google.osdetector") version "1.7.3"
}
kotlin {
    task("testClasses")
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    jvm("desktop")

    sourceSets {
        commonMain.dependencies {
            // local modules
            implementation(projects.shared)
            // compose
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.components.resources)
            implementation(compose.material3AdaptiveNavigationSuite)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.coroutines.core)
            // other compose & material
            implementation(libs.adaptive)
            implementation(libs.adaptive.layout)
            implementation(libs.adaptive.navigation)
            implementation(libs.material3.window.size.class1)
            implementation(libs.material.icons.extended)

            // ktor
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
//                implementation(libs.androidx.paging.common)
//                implementation(libs.androidx.room.runtime)
//                implementation(libs.sqlite.bundled)
            implementation(libs.kotlinx.atomicfu)

            implementation(libs.androidx.datastore)
            api(libs.androidx.datastore.preferences.core)
            api(libs.androidx.datastore.core.okio)
            implementation(libs.okio)

            // viewmodel
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.androidx.lifecycle.viewmodel.compose)
            // navigation
            implementation(libs.androidx.navigation.compose)
            // others
            implementation(libs.kotlinx.datetime)
        }

        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.androidx.activity.ktx)
//            implementation(libs.androidx.room.paging)
            // media3
            implementation(libs.androidx.media3.exoplayer)
            implementation(libs.androidx.media3.exoplayer.dash)
            implementation(libs.androidx.media3.ui)
            implementation(libs.androidx.media3.session)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
            //implementation(libs.skie.annotations)   // for ios code gen
        }
        val desktopMain by getting

        val os = readOsName()
        desktopMain.dependencies {
            implementation(compose.desktop.common)
            implementation(compose.desktop.currentOs)
            implementation(libs.coroutines.swing)
            implementation(libs.vlcj)

//            implementation("org.openjfx:javafx-base:19:${os}")
//            implementation("org.openjfx:javafx-graphics:19:${os}")
//            implementation("org.openjfx:javafx-controls:19:${os}")
//            implementation("org.openjfx:javafx-swing:19:${os}")
//            implementation("org.openjfx:javafx-web:19:${os}")
//            implementation("org.openjfx:javafx-media:19:${os}")
        }

//        @OptIn(ExperimentalWasmDsl::class)
//        wasmJs {
//            moduleName = "composeApp"
//            browser {
//                commonWebpackConfig {
//                    outputFileName = "composeApp.js"
//                    devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
//                        static = (static ?: mutableListOf()).apply {
//                            // Serve sources to debug inside browser
//                            add(project.projectDir.path)
//                        }
//                    }
//                }
//            }
//            binaries.executable()
//        }
    }
}

android {
    namespace = "com.kmp.mvp"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

//    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
//    sourceSets["main"].res.srcDirs("src/androidMain/res")
//    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "com.kmp.mvp"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
    dependencies {
        debugImplementation(compose.uiTooling)
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.kmp.mvp"
            packageVersion = "1.0.0"
        }
    }
}
fun readOsName(): String {
    return when (osdetector.classifier) {
        "linux-x86_64" -> "linux"
        "linux-aarch_64" -> "linux-aarch64"
        "windows-x86_64" -> "win"
        "osx-x86_64" -> "mac"
        "osx-aarch_64" -> "mac-aarch64"
        else -> throw IllegalStateException("Unknown OS: ${osdetector.classifier}")
    }
}