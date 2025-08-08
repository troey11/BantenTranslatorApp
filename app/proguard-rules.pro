# Add project specific ProGuard rules here.

-keep class com.bantentranslator.models.** { *; }
-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}