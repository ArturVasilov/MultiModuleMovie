-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable
-repackageclasses ''
-verbose
-dontusemixedcaseclassnames
-printseeds
-allowaccessmodification
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers

-printconfiguration build/proguard-configuration.txt

-keepclassmembers enum * {
     public static **[] values();
     public static ** valueOf(java.lang.String);
}

-keepattributes Signature,*Annotation*,EnclosingMethod

# Gson specific classes
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }

-dontwarn com.google.**

# Okio
-keep class sun.misc.Unsafe { *; }
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn org.conscrypt.**
# A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

# Retain service method parameters when optimizing.
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}

# Ignore annotation used for build tooling.
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement

# Ignore JSR 305 annotations for embedding nullability information.
-dontwarn javax.annotation.**

-keepnames class * extends android.content.ContentProvider
-keepnames public abstract class * extends android.app.Activity

-optimizations method/inlining/*
-optimizations code/removal/*
-optimizations code/simplification/*
-optimizations !code/allocation/variable
-optimizations class/merging/vertical
-optimizations class/marking/final
-optimizations class/unboxing/enum
-optimizations field/marking/private
-optimizations field/removal/writeonly
-optimizations method/marking/final
-optimizations method/marking/private
-optimizations method/marking/static
-optimizations method/removal/parameter
-optimizations method/propagation/parameter
-optimizations !class/merging/horizontal
-optimizations !field/propagation/value
-optimizations method/propagation/returnvalue
-optimizations code/merging