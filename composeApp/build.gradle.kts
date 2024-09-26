import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlinxSerialization)
    alias(libs.plugins.skie)
    alias(libs.plugins.ksp)
//    alias(libs.plugins.room)
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

    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.material3.android)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.androidx.activity.ktx)
//            implementation(libs.androidx.room.paging)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
        val commonMain by getting {
//            kotlin.srcDir("build/generated/ksp/commonMain/kotlin")
            dependencies {
                // compose
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.components.resources)
                implementation(compose.components.uiToolingPreview)
                implementation(libs.coroutines.core)
                // ktor
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.ktor.serialization.kotlinx.json)
                implementation(libs.skie.annotations)   // for ios code gen
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
                // local projects
                implementation(projects.shared)
            }
        }
        jvm("desktop")
        val desktopMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation(libs.coroutines.swing)
            }
        }
        // disable: Web, desktop
//    @OptIn(ExperimentalWasmDsl::class)
//    wasmJs {
//        moduleName = "composeApp"
//        browser {
//            commonWebpackConfig {
//                outputFileName = "composeApp.js"
//                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
//                    static = (static ?: mutableListOf()).apply {
//                        // Serve sources to debug inside browser
//                        add(project.projectDir.path)
//                    }
//                }
//            }
//        }
//        binaries.executable()
//    }
    }
}

android {
    namespace = "com.kmp.mvp"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

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
dependencies {
//    add("kspAndroid", libs.androidx.room.compiler)
//    add("kspIosSimulatorArm64", libs.androidx.room.compiler)
//    add("kspIosX64", libs.androidx.room.compiler)
//    add("kspIosArm64", libs.androidx.room.compiler)
//    add("kspCommonMainMetadata", projects.libProcessor)
//    implementation(projects.libProcessor)
//    ksp(projects.libProcessor)
//    add("kspJvm", projects.libProcessor)
//    add("kspJvmTest", projects.libProcessor)
//    add("kspJs", projects.libProcessor)
//    add("kspJsTest", projects.libProcessor)
//    add("kspAndroidNativeX64", projects.libProcessor)
//    add("kspAndroidNativeX64Test", projects.libProcessor)
//    add("kspAndroidNativeArm64", projects.libProcessor)
//    add("kspAndroidNativeArm64Test", projects.libProcessor)
//    add("kspLinuxX64", projects.libProcessor)
//    add("kspLinuxX64Test", projects.libProcessor)
//    add("kspMingwX64", projects.libProcessor)
//    add("kspMingwX64Test", projects.libProcessor)
//    ksp(projects.libProcessor)
}
//kotlin.sourceSets.commonMain {
//    kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
//}
//tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().all {
//    if (name != "kspCommonMainKotlinMetadata") {
//        dependsOn("kspCommonMainKotlinMetadata")
//    }
//}
//ksp {
//    arg("measureDuration", "true")
//}

//room {
//    schemaDirectory("$projectDir/schemas")
//}
