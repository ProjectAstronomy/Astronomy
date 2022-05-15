plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-kapt")
}

android {
    compileSdk = Config.compileSdk

    defaultConfig {
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildFeatures {
        viewBinding = true
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }

    flavorDimensions += "TEST"
    productFlavors {
        create("FAKE") {
            dimension = "TEST"
        }
        create("REAL") {
            dimension = "TEST"
        }
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
    //Modules
    implementation(project(mapOf("path" to Modules.core)))

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

    //AndroidX
    implementation(AndroidXImpl.fragmentKTX)

    //Room
    implementation(Room.room)
    implementation(Room.roomRuntime)
    kapt(Room.roomCompiler)

    //Core
    implementation(Core.coreKTX)

    //Design
    implementation(Design.appcompat)
    implementation(Design.material)

    //Navigation
    implementation(Navigation.navigationFragmentKTX)
    implementation(Navigation.navigationUIKTX)

    //Test
    testImplementation(TestImpl.junit)
    testImplementation(TestImpl.kotlinxCoroutinesTest)
    testImplementation(TestImpl.mockk)
    testImplementation(TestImpl.coreTesting)
    testImplementation(TestImpl.robolectric)
    testImplementation(TestImpl.mockitoKotlin)

    debugImplementation(TestImpl.fragment)

    androidTestImplementation(TestImpl.navigation)
    androidTestImplementation(TestImpl.extJunit)
    androidTestImplementation(TestImpl.espresso)
}