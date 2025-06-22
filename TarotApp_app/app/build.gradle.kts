plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android) // Kotlin Android 플러그인 추가
}

android {
    namespace = "kr.ac.kopo.tarotapp"
    compileSdk = 35

    aaptOptions {
        noCompress += "bin" // 로컬 LLM 시도 Legacy code
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}" // 로컬 LLM 시도 Legacy code
        }
    }

    defaultConfig {
        applicationId = "kr.ac.kopo.tarotapp"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation("com.google.mediapipe:tasks-genai:latest.release") // 로컬 LLM 시도 Legacy code
    implementation("androidx.compose.runtime:runtime:1.6.0") // 로컬 LLM 시도 Legacy code
    implementation ("com.squareup.okhttp3:okhttp:4.11.0") // 로컬 LLM server를 따로 두기로 함
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "11"
    }
}