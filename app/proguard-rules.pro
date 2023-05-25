# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in H:\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

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


#===================================

#-keepattributes Exceptions, InnerClasses, Signature, Deprecated, SourceFile,LineNumberTable, Annotation, EnclosingMethod
# -dontwarn android.webkit.JavascriptInterface
# -dontwarn com.googlecode.mp4parser.**
##
# ##---------------Begin: proguard configuration common for all Android apps ----------
# -optimizationpasses 10
# -dontusemixedcaseclassnames
# -dontskipnonpubliclibraryclasses
# -dontskipnonpubliclibraryclassmembers
# -dontpreverify
# -verbose
# -dump class_files.txt
# -printseeds seeds.txt
# -printusage unused.txt
# -printmapping mapping.txt
# -optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
#
# -allowaccessmodification
# -renamesourcefileattribute SourceFile
# -keepattributes SourceFile,LineNumberTable
# -repackageclasses ''
#
# -dontnote com.android.vending.licensing.ILicensingService
##==========================
#-keep public class com.google.android.gms.common.internal.safeparcel.SafeParcelable {
#    public static final *** NULL;
#}
#
#-keepnames @com.google.android.gms.common.annotation.KeepName class *
#-keepclassmembernames class * {
#    @com.google.android.gms.common.annotation.KeepName *;
#}
#
#-keepnames class * implements android.os.Parcelable {
#    public static final ** CREATOR;
#}
#
#-keepclassmembers class com**{public *;}
#-dontwarn org.apache.lang.**
#
#-keep public class com.google.ads.** {public *;}
#
#
#-keep public class * extends android** {
#    public <init>(android.content.Context);
#}
#
#-keep public class * implements com.bumptech.glide.module.GlideModule
#-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
#    **[] $VALUES;
#    public *;
#}
#
# -keep class com** {*;}
#
#
# -keepattributes Exceptions, InnerClasses, Signature, Deprecated, SourceFile,LineNumberTable, *Annotation*, EnclosingMethod
# -dontwarn android.webkit.JavascriptInterface
# -dontwarn com.startapp.**
# -dontwarn org.jetbrains.annotations.**
#
#-dontwarn okio.**
#-dontwarn okhttp3.**
#-dontwarn com.squareup.okhttp.**
#-dontwarn com.google.appengine.**
#-dontwarn javax.servlet.**
#
## Support classes for compatibility with older API versions
#
#-dontwarn android.support.**
#-dontnote android.support.**
#-keepattributes Signature,RuntimeVisibleAnnotations,AnnotationDefault
#
#
## Needed by google-http-client-android when linking against an older platform version
#
#-dontwarn com.google.api.client.extensions.android.**
#
## Needed by google-api-client-android when linking against an older platform version
#
#-dontwarn com.google.api.client.googleapis.extensions.android.**
#
## Needed by google-play-services when linking against an older platform version
#
#-dontwarn com.google.android.gms.**
#-dontnote com.google.android.gms.**
#
## com.google.client.util.IOUtils references java.nio.file.Files when on Java 7+
#-dontnote java.nio.file.Files, java.nio.file.Path
#
## Suppress notes on LicensingServices
#-dontnote **.ILicensingService
#
## RetroFit
#-dontwarn retrofit.**
#-keep class retrofit.** { *; }
#-keepattributes Signature
#-keepattributes Exceptions
#
#-keepclasseswithmembernames class * {
##    @butterknife.* <methods>;
#    native <methods>;
#}
#-keepclassmembers class com** { <fields>; }
#-keepclassmembers class com** { <methods>; }




-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
-renamesourcefileattribute SourceFile

-dontwarn
-ignorewarnings

# RetroFit
-dontwarn retrofit.**
-keep class retrofit.** { *; }
-keepattributes Signature
-keepattributes Exceptions

-keepclasseswithmembernames class * {
    native <methods>;
    @butterknife.* <methods>;
}
-keepclassmembers class com** { <fields>; }
-keepclassmembers class com** { <methods>; }




