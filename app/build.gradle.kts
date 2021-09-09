plugins {
    id("com.android.application")
    id("kotlin-android")
}

object DependencyVersions {
    const val KOIN = "3.1.2"
    const val RETROFIT = "2.9.0"
    const val KTX = "2.3.1"
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

    implementation("androidx.lifecycle:lifecycle-livedata-ktx:${DependencyVersions.KTX}")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${DependencyVersions.KTX}")

    implementation("io.insert-koin:koin-android:${DependencyVersions.KOIN}")
    implementation("io.insert-koin:koin-android-compat:${DependencyVersions.KOIN}")

    implementation("com.squareup.retrofit2:retrofit:${DependencyVersions.RETROFIT}")
    implementation("com.squareup.retrofit2:converter-gson:${DependencyVersions.RETROFIT}")
    implementation("com.squareup.okhttp3:logging-interceptor:4.7.0")
    implementation("com.squareup.okhttp3:okhttp:4.9.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}