plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.navigation.safeargs)
    kotlin("kapt")
}

android {
    namespace = "com.cfh.cfhmoviesapptask"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.cfh.cfhmoviesapptask"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    api(project(":domain"))
    api(project(":data"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.hilt)
    kapt(libs.hiltCompiler)
    //coroutine
    implementation(libs.coroutinesAndroid)
    //Pagination 3
    implementation(libs.pagingRuntime)
    //Navigation
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    //glide
    implementation(libs.glide)
    implementation(libs.converterGson)
    testImplementation ("junit:junit:4.13.2")
    testImplementation ("org.mockito:mockito-core:4.6.1")
    testImplementation ("org.mockito:mockito-inline:4.6.1")
    testImplementation ("androidx.arch.core:core-testing:2.1.0")
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.0")
    testImplementation ("org.mockito.kotlin:mockito-kotlin:4.1.0")

}

kapt {
    correctErrorTypes = true
}