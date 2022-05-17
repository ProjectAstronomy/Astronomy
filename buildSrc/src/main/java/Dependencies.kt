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
    const val core = ":core"
}

object Versions {
    //Navigation
    const val navigationFragmentKTXVersion = "2.4.2"

    //Koin
    const val koinVersion = "3.1.6"

    //Coil
    const val coilVersion = "1.4.0"

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
    const val circleImageviewVersion = "3.1.0"

    //AndroidX
    const val activityKTXVersion = "1.4.0"
    const val fragmentKTXVersion = "1.4.1"
    const val preferencesVersion = "1.2.0"

    //Room
    const val roomVersion = "2.4.2"

    //Test
    const val junitVersion = "4.13.2"
    const val extJunitVersion = "1.1.3"
    const val fragmentVersion = "1.4.0"
    const val composetVersion = "1.0.4"

    const val mockkVersion = "1.12.3"
    const val mockitoVersion = "2.19.0"
    const val mockitoinlineVersion = "2.8.9"
    const val mockitoKotlinVersion = "3.2.0"

    const val espressoVersion = "3.3.0"
    const val espressoContriblinVersion = "3.3.0"

    const val nav_version = "2.4.2"
    const val koinTestsversion = "2.1.6"
    const val koinTestversion = "2.1.6"
    const val coreTestingTestversion = "2.1.0"

    const val coreTestingVersion = "2.1.0"
    const val robolectricVersion = "4.8.1"
    const val startupRuntimeVersion = "1.1.1"
}

object StartUp {
    const val startUpRuntime = "androidx.startup:startup-runtime:${Versions.startupRuntimeVersion}"
}

object Navigation {
    const val navigationFragmentKTX =
        "androidx.navigation:navigation-fragment-ktx:${Versions.navigationFragmentKTXVersion}"
    const val navigationUIKTX =
        "androidx.navigation:navigation-ui-ktx:${Versions.navigationFragmentKTXVersion}"
    const val navigationRuntime =
        "androidx.navigation:navigation-runtime-ktx:${Versions.navigationFragmentKTXVersion}"
}

object Koin {
    const val koinCore = "io.insert-koin:koin-core:${Versions.koinVersion}"
    const val koinAndroid = "io.insert-koin:koin-android:${Versions.koinVersion}"
    const val koinAndroidCompat = "io.insert-koin:koin-android-compat:${Versions.koinVersion}"
}

object Coil {
    const val coil = "io.coil-kt:coil:${Versions.coilVersion}"
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

object Room {
    const val room = "androidx.room:room-ktx:${Versions.roomVersion}"
    const val roomRuntime = "androidx.room:room-runtime:${Versions.roomVersion}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.roomVersion}"
}

object Core {
    const val coreKTX = "androidx.core:core-ktx:${Versions.coreKTXVersion}"
}

object Design {
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompatVersion}"
    const val material = "com.google.android.material:material:${Versions.materialVersion}"
    const val circleImageView = "de.hdodenhof:circleimageview:${Versions.circleImageviewVersion}"

}

object AndroidXImpl {
    const val activityKTX = "androidx.activity:activity-ktx:${Versions.activityKTXVersion}"
    const val fragmentKTX = "androidx.fragment:fragment-ktx:${Versions.fragmentKTXVersion}"
    const val preferences = "androidx.preference:preference-ktx:${Versions.preferencesVersion}"
}

object TestImpl {
    const val junit = "junit:junit:${Versions.junitVersion}"
    const val extJunit = "androidx.test.ext:junit:${Versions.extJunitVersion}"
    const val composeJunit = "androidx.compose.ui:ui-test-junit4:${Versions.composetVersion}"
    const val fragment = "androidx.fragment:fragment-testing:${Versions.fragmentVersion}"

    const val mockk = "io.mockk:mockk:${Versions.mockkVersion}"
    const val mockito = "org.mockito:mockito-core:${Versions.mockitoVersion}"
    const val mockitoInline = "org.mockito:mockito-core:${Versions.mockitoinlineVersion}"
    const val mockitoKotlin = "org.mockito.kotlin:mockito-kotlin:${Versions.mockitoKotlinVersion}"

    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espressoVersion}"
    const val espressoContrib = "androidx.test.espresso:espresso-contrib:${Versions.espressoContriblinVersion}"

    const val navigation = "androidx.navigation:navigation-testing:${Versions.nav_version}"
    const val koinTests = "org.koin:koin-test:${Versions.koinTestsversion}"
    const val koinTest = "org.koin:koin-test:${Versions.koinTestversion}"
    const val coreTesting = "androidx.arch.core:core-testing:${Versions.coreTestingTestversion}"

    const val kotlinxCoroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutinesVersion}"
    const val robolectric = "org.robolectric:robolectric:${Versions.robolectricVersion}"
}