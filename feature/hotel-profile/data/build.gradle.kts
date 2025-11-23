plugins {
    alias(libs.plugins.nowinandroid.android.library)
    alias(libs.plugins.nowinandroid.hilt)
    alias(libs.plugins.nowinandroid.android.feature)
//    id("kotlinx-serialization")
}

android {
    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
    namespace = "com.karuhun.feature.hotelprofile.data"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.database)
    api(projects.core.datastore)
    implementation(libs.retrofit.core)
    implementation(projects.core.network)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.converter.gson)
}