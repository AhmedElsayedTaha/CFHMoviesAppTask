plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.hilt)
    kotlin("kapt")
}

android {
    namespace = "com.cfh.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {

    api(project(":domain"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    //Hilt
    implementation(libs.hilt)
    kapt(libs.hiltCompiler)
    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.okhttp)
    implementation(libs.gson)
    implementation(libs.loggingInterceptor)
    implementation(libs.converterGson)
    //coroutine
    implementation(libs.coroutinesAndroid)
    //Room
    implementation(libs.roomRuntime)
    kapt(libs.roomCompiler)
    implementation(libs.roomKtx)
    testImplementation(libs.roomTesting)
    implementation(libs.roomPaging)
    //Pagination 3
    implementation(libs.pagingRuntime)



}

kapt {
    correctErrorTypes = true
}