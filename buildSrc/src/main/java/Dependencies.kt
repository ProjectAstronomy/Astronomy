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
}


object Versions {
    //Core
    const val coreKTXVersion = "1.7.0"

    //Design
    const val appcompatVersion = "1.4.1"
    const val materialVersion = "1.5.0"

    //Test
    const val junitVersion = "4.13.2"
    const val extJunitVersion = "1.1.3"
    const val espressoVersion = "3.4.0"
}

object Core {
    const val coreKTX = "androidx.core:core-ktx:${Versions.coreKTXVersion}"
}

object Design {
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompatVersion}"
    const val material = "com.google.android.material:material:${Versions.materialVersion}"
}

object TestImpl {
    const val junit = "junit:junit:${Versions.junitVersion}"
    const val extJunit = "androidx.test.ext:junit:${Versions.extJunitVersion}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espressoVersion}"
}