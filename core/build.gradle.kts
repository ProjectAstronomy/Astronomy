plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    compileSdk = Config.compileSdk

    defaultConfig {
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk

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
    //Koin
    implementation(Koin.koinCore)
    implementation(Koin.koinAndroid)
    implementation(Koin.koinAndroidCompat)

    //Lifecycle
    implementation(Lifecycle.lifecycleViewModelKTX)
    implementation(Lifecycle.lifecycleLiveDataKTX)
    implementation(Lifecycle.lifecycleViewModelSavedState)

    //Retrofit2
    implementation(Retrofit.retrofit)
    implementation(Retrofit.converterGson)
    implementation(Retrofit.loggingInterceptor)
    implementation(Retrofit.retrofitKotlinCoroutinesAdapter)

    //Coroutines
    implementation(Coroutines.kotlinxCoroutinesCore)
    implementation(Coroutines.kotlinxCoroutinesAndroid)

    //Core
    implementation(Core.coreKTX)

    //Design
    implementation(Design.appcompat)
    implementation(Design.material)

    //Test
    testImplementation(TestImpl.junit)
    androidTestImplementation(TestImpl.extJunit)
    androidTestImplementation(TestImpl.espresso)
}