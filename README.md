# Compose-Weather-App-

An educational Android weather app using Jetpack Compose and WeatherAPI.

## Run locally

1. Open the repository in Android Studio. Use JDK 11 as the Gradle JDK and the checked-in Gradle 7.4 wrapper (Android Gradle Plugin 7.3.1).
2. Install Android SDK Platform 32 and Android SDK Build-Tools 30.0.3 (the plugin default). The app runs on Android 5.0 / API 21 or newer.
3. Get your own key from [WeatherAPI](https://www.weatherapi.com/). This app calls `https://api.weatherapi.com/v1/forecast.json`.
4. Add the setting from [gradle.properties.example](gradle.properties.example) to your **user** Gradle properties file: `~/.gradle/gradle.properties` on macOS/Linux or `%USERPROFILE%\.gradle\gradle.properties` on Windows. Create the file if needed; preserve any existing settings. Replace `YOUR_WEATHERAPI_KEY` with your own value. Do not copy credentials into the tracked project `gradle.properties`.
5. Sync Gradle, select the `app` configuration, and run on an emulator or device with internet access.

Build from the repository root:

```sh
sh ./gradlew :app:assembleDebug
```

On Windows use `gradlew.bat :app:assembleDebug`. The APK is generated at `app/build/outputs/apk/debug/app-debug.apk`.

## Configuration placeholders

| Setting | Purpose | Without a real value |
| --- | --- | --- |
| `WEATHER_API_KEY` | Key for WeatherAPI forecast requests | The default is empty; live weather cannot load. |
| `sdk.dir` in local `local.properties` | Installed Android SDK directory | Android Studio can create this file; CLI builds need an SDK path. |

`WEATHER_API_KEY` can also be supplied as an environment variable before starting Gradle/Android Studio. A Gradle property takes precedence over the environment variable. Rebuild after changing the key because it is copied into `BuildConfig.WEATHER_API_KEY` at build time.

The example value is a placeholder, not a working shared key. The initial city is `Ivanovo` in `MainActivity.kt`. A blank forecast can indicate a missing/invalid key, a request limit, or a network error; the existing app logs request errors under `MyLog` in Logcat.

## Build notes

This project keeps its original dependency versions. Use the wrapper and select the documented JDK before attempting dependency upgrades. See the [AGP 7.3 compatibility table](https://developer.android.com/build/releases/agp-7-3-0-release-notes#compatibility).

Build verification on 2026-10-14: `:app:assembleDebug` completed with JDK 17, SDK Platform 32, and the checked-in wrapper. The APK was built with an empty API key; generation of the configuration was also checked with a dummy value. Real WeatherAPI responses and device UI behavior have not been verified.
