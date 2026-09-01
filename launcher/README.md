# Growtopia Launcher

A minimal, standalone Android app with a single "Launch" button that opens the
official, already-installed Growtopia app (package `com.rtsoft.growtopia`).

It does not bundle, modify, or redistribute any Growtopia game files — it
only asks the OS to start the app if it's installed, and otherwise sends the
user to the Play Store listing.

## Project layout

```
launcher/
  app/
    src/main/java/com/example/growtopialauncher/MainActivity.kt
    src/main/res/...
    src/main/AndroidManifest.xml
  build.gradle.kts
  settings.gradle.kts
```

## Building

This project needs the Android SDK / Android Studio, which isn't available
in this sandbox, so it hasn't been compiled here. To build it:

1. Open the `launcher/` folder in Android Studio (Giraffe or newer), or
2. From a machine with the Android SDK installed:
   ```
   cd launcher
   ./gradlew assembleDebug
   ```
   (generate the Gradle wrapper first with `gradle wrapper` if `gradlew` is
   missing).

The resulting APK will have a "Launch" button that starts Growtopia if it's
installed, or opens its Play Store page if it isn't.
