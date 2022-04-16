object Config {
    const val applicationId = "com.project.astronomy"
    const val compileSdk = 31
    const val targetSdk = 31
    const val minSdk = 23
    const val jvmTarget = "1.8"
}

object Releases {
    const val versionCode = 1
    const val versionName = "1.0"
}

object Modules {
    const val app = ":app"
    const val apod = ":apod"
    const val donki = ":donki"
    const val epic = ":epic"
    const val mrp = ":mrp"
    const val utils = ":utils"
    const val core = ":core"
}

object Versions {
    //Navigation
    const val navigationFragmentKTXVersion = "2.4.2"

    //Koin
    const val koinVersion = "3.1.6"

    //Lifecycle
    const val lifecycleVersion = "2.4.1"

    //Retrofit2
    const val retrofitVersion = "2.9.0"
    const val loggingInterceptorVersion = "5.0.0-alpha.3"
    const val retrofitKotlinCoroutinesAdapterVersion = "0.9.2"

    //Coroutines
    const val coroutinesVersion = "1.5.0"

    //Core
    const val coreKTXVersion = "1.7.0"

    //Design
    const val appcompatVersion = "1.4.1"
    const val materialVersion = "1.5.0"

    //AndroidX
    const val activityKTXVersion = "1.4.0"
    const val fragmentKTXVersion = "1.4.1"

    //Test
    const val junitVersion = "4.13.2"
    const val extJunitVersion = "1.1.3"
    const val espressoVersion = "3.4.0"
}

object Navigation {
    const val navigationFragmentKTX =
        "androidx.navigation:navigation-fragment-ktx:${Versions.navigationFragmentKTXVersion}"
    const val navigationUIKTX =
        "androidx.navigation:navigation-ui-ktx:${Versions.navigationFragmentKTXVersion}"
}

object Koin {
    const val koinCore = "io.insert-koin:koin-core:${Versions.koinVersion}"
    const val koinAndroid = "io.insert-koin:koin-android:${Versions.koinVersion}"
    const val koinAndroidCompat = "io.insert-koin:koin-android-compat:${Versions.koinVersion}"
}

object Lifecycle {
    const val lifecycleViewModelKTX =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycleVersion}"
    const val lifecycleLiveDataKTX =
        "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycleVersion}"
    const val lifecycleViewModelSavedState =
        "androidx.lifecycle:lifecycle-viewmodel-savedstate:${Versions.lifecycleVersion}"
}

object Retrofit {
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofitVersion}"
    const val converterGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofitVersion}"
    const val loggingInterceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.loggingInterceptorVersion}"
    const val retrofitKotlinCoroutinesAdapter =
        "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${Versions.retrofitKotlinCoroutinesAdapterVersion}"
}

object Coroutines {
    const val kotlinxCoroutinesCore =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutinesVersion}"
    const val kotlinxCoroutinesAndroid =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesVersion}"
}

object Core {
    const val coreKTX = "androidx.core:core-ktx:${Versions.coreKTXVersion}"
}

object Design {
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompatVersion}"
    const val material = "com.google.android.material:material:${Versions.materialVersion}"
}

object AndroidXImpl {
    const val activityKTX = "androidx.activity:activity-ktx:${Versions.activityKTXVersion}"
    const val fragmentKTX = "androidx.fragment:fragment-ktx:${Versions.fragmentKTXVersion}"
}

object TestImpl {
    const val junit = "junit:junit:${Versions.junitVersion}"
    const val extJunit = "androidx.test.ext:junit:${Versions.extJunitVersion}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espressoVersion}"
}