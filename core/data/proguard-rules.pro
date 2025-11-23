# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Keep ChangeListVersions and property references for sync operations
-keep class com.karuhun.core.model.ChangeListVersions { *; }
-keepclassmembers class com.karuhun.core.model.ChangeListVersions {
    *;
}

# Keep Kotlin property references used in sync operations
-keep class kotlin.reflect.** { *; }
-keep class kotlin.jvm.internal.** { *; }
-keepclassmembers class kotlin.Metadata {
    *;
}

# Keep lambda functions and function types used in sync
-keepclassmembers class ** {
    kotlin.jvm.functions.Function* *;
    kotlin.reflect.KProperty* *;
}

# Keep sync utilities and synchronizer interface
-keep class com.karuhun.core.data.** { *; }
-keepclassmembers class com.karuhun.core.data.** {
    *;
}

# Keep network change list models
-keep class com.karuhun.core.model.NetworkChangeList { *; }
-keepclassmembers class com.karuhun.core.model.NetworkChangeList {
    *;
}

# Keep extension functions on ChangeListVersions
-keepclassmembers class com.karuhun.core.model.ChangeListVersions {
    *** copy(...);
}

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile