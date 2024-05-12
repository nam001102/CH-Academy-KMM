import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.googleservices)
    alias(libs.plugins.swiftklib)
    alias(libs.plugins.cocoapods)

}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
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
        iosTarget.compilations {
            val main by getting {
                cinterops {
                    create("KCrypto")
                }
            }
        }
    }
    
    sourceSets {
        
        androidMain.dependencies {
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)
            implementation(platform("com.google.firebase:firebase-bom:32.8.1"))
            implementation("com.google.firebase:firebase-analytics")
            implementation("com.google.firebase:firebase-auth")
            implementation("com.google.firebase:firebase-firestore")
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            implementation(libs.decompose)

            implementation(libs.kotlinx.serialization.json)

            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")

            implementation("com.soywiz.korlibs.krypto:krypto:4.0.10")

//            implementation(project.dependencies.platform("com.google.firebase:firebase-bom:33.0.0"))
//            implementation("com.google.firebase:firebase-firestore:24.10.3")
//            implementation("com.google.firebase:firebase-firestore-ktx")



            implementation("dev.gitlive:firebase-auth:1.12.0")
            implementation("dev.gitlive:firebase-database:1.12.0")
            implementation("dev.gitlive:firebase-firestore:1.12.0")
            implementation("dev.gitlive:firebase-storage:1.12.0")

            //Voyager
            implementation("cafe.adriel.voyager:voyager-navigator:1.0.0")
            implementation("cafe.adriel.voyager:voyager-tab-navigator:1.0.0")
            implementation("cafe.adriel.voyager:voyager-transitions:1.0.0")

            //MOKO
            implementation("dev.icerock.moko:mvvm-compose:0.16.1")
            implementation("dev.icerock.moko:mvvm-flow-compose:0.16.1")
            implementation("dev.icerock.moko:mvvm-livedata-compose:0.16.1")
            implementation("org.jetbrains.androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0-beta02")

        }
    }

    cocoapods {
        version = "1.0"
        summary = "CH Academy ios libs"
        homepage = "null"

        ios.deploymentTarget = "13.5"

        pod("CryptoSwift") {
            version = "1.8.2"
        }
    }

}
swiftklib {
    create("KCrypto") {
        path = file("Utils/KCrypto")
        packageName("com.chacademy.objclibs.kcrypto")

    }
}

android {
    namespace = "com.chacademy.android"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs(
        "src/commonMain/resources",
        "src/androidMain/resources"
    )
    sourceSets["main"].resources.srcDirs(
        "src/commonMain/resources"
    )

    defaultConfig {
        applicationId = "com.chacademy.android"
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
    dependencies {
        debugImplementation(libs.compose.ui.tooling)
    }
}
dependencies {
    implementation(libs.androidx.material3.android)
    implementation(libs.androidx.ui.text.google.fonts)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.firebase.firestore.ktx)
//    implementation(project(":composeApp"))
}
