plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
}

object LibraryVersions {
    const val KOIN = "3.1.2"
    const val RETROFIT = "2.9.0"
    const val KTX = "2.3.1"
    const val ROOM = "2.3.0"
    const val PAGING = "2.1.0"
    const val STETHO = "1.5.1"
    const val GLIDE = "4.12.0"
}

android {
    compileSdk = 30

    defaultConfig {
        applicationId = "com.michaelfotiadis.demo.reddit.android"
        minSdk = 21
        targetSdk = 30
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

    implementation("androidx.core:core-ktx:1.6.0")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.0")

    implementation("com.jakewharton.timber:timber:5.0.1")

    implementation("androidx.lifecycle:lifecycle-livedata-ktx:${LibraryVersions.KTX}")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${LibraryVersions.KTX}")

    implementation("io.insert-koin:koin-android:${LibraryVersions.KOIN}")
    implementation("io.insert-koin:koin-android-compat:${LibraryVersions.KOIN}")

    implementation("com.squareup.retrofit2:retrofit:${LibraryVersions.RETROFIT}")
    implementation("com.squareup.retrofit2:converter-gson:${LibraryVersions.RETROFIT}")
    implementation("com.squareup.okhttp3:logging-interceptor:4.7.0")
    implementation("com.squareup.okhttp3:okhttp:4.9.0")

    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.recyclerview:recyclerview:1.2.1")

    implementation("androidx.paging:paging-runtime-ktx:${LibraryVersions.PAGING}")

    implementation("androidx.room:room-runtime:${LibraryVersions.ROOM}")
    implementation("androidx.room:room-ktx:${LibraryVersions.ROOM}")
    kapt("androidx.room:room-compiler:${LibraryVersions.ROOM}")

    implementation("com.facebook.stetho:stetho:${LibraryVersions.STETHO}")
    implementation("com.facebook.stetho:stetho-okhttp3:${LibraryVersions.STETHO}")

    implementation("io.noties.markwon:core:4.6.2")

    implementation("com.github.marlonlom:timeago:4.0.3")

    implementation("com.github.bumptech.glide:glide:${LibraryVersions.GLIDE}")
    kapt("com.github.bumptech.glide:compiler:${LibraryVersions.GLIDE}")

    debugImplementation("com.facebook.flipper:flipper:0.110.0")
    debugImplementation("com.facebook.soloader:soloader:0.10.1")

    releaseImplementation("com.facebook.flipper:flipper-noop:0.110.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}