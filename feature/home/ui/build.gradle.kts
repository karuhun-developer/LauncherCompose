plugins {
    alias(libs.plugins.nowinandroid.android.feature)
    alias(libs.plugins.nowinandroid.android.library.compose)
}

android {
    namespace = "com.karuhun.feature.home.ui"
}

dependencies {
    implementation(projects.core.domain)
    implementation(project(":core:common"))
    implementation("com.google.zxing:core:3.5.3")
}
