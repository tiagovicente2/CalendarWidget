# Material Expressive Calendar Widget - Agentic Implementation Plan

> **For agentic workers:** This is the master implementation plan. Execute tasks sequentially, commit after each task, verify with specified commands.

**Goal:** Build a Material 3 floating overlay calendar widget for Android with Google Calendar and iCal URL support, offline caching, and smart sync

**Architecture:** Kotlin Android app with Room database, WorkManager for background sync, Retrofit for network, Material3 UI with floating WindowManager overlay. Follows MVVM pattern with Repository pattern for data layer.

**Tech Stack:** Kotlin 1.9, Gradle 8.x, Android API 26-34, Material3 1.11.x, Room 2.6.x, WorkManager 2.9.x, Retrofit 2.9.x, Hilt 2.50.x, Google Calendar API, iCal4j 3.2.14

**Design Reference:** `/home/tiago/dev/personal/CalendarWidget/docs/DESIGN.md`

**Project Location:** `/home/tiago/dev/personal/CalendarWidget/`

---

## File Structure Overview

```
CalendarWidget/
├── app/
│   ├── src/main/java/com/calendar/widget/
│   │   ├── data/
│   │   │   ├── local/
│   │   │   │   ├── database/
│   │   │   │   │   ├── AppDatabase.kt
│   │   │   │   │   ├── EventDao.kt
│   │   │   │   │   └── Converters.kt
│   │   │   │   └── entity/
│   │   │   │       └── EventEntity.kt
│   │   │   ├── remote/
│   │   │   │   ├── api/
│   │   │   │   │   ├── GoogleCalendarApi.kt
│   │   │   │   │   └── IcalService.kt
│   │   │   │   └── dto/
│   │   │   │       ├── GoogleEventDto.kt
│   │   │   │       └── IcalEventDto.kt
│   │   │   ├── repository/
│   │   │   │   └── EventRepository.kt
│   │   │   └── model/
│   │   │       └── Event.kt
│   │   ├── di/
│   │   │   ├── AppModule.kt
│   │   │   └── NetworkModule.kt
│   │   ├── service/
│   │   │   ├── FloatingCalendarService.kt
│   │   │   └── SyncWorker.kt
│   │   ├── ui/
│   │   │   ├── overlay/
│   │   │   │   ├── CalendarOverlayView.kt
│   │   │   │   ├── DayHeaderView.kt
│   │   │   │   ├── EventItemView.kt
│   │   │   │   ├── EventAdapter.kt
│   │   │   │   └── OverlayDragHelper.kt
│   │   │   └── config/
│   │   │       ├── ConfigActivity.kt
│   │   │       └── WelcomeFragment.kt
│   │   ├── sync/
│   │   │   ├── SyncManager.kt
│   │   │   ├── GoogleCalendarSync.kt
│   │   │   └── IcalSync.kt
│   │   ├── parser/
│   │   │   └── IcalParser.kt
│   │   └── CalendarWidgetApplication.kt
│   ├── src/main/res/
│   │   ├── layout/
│   │   ├── values/
│   │   ├── values-night/
│   │   ├── drawable/
│   │   └── xml/
│   ├── src/test/
│   └── build.gradle.kts
├── gradle/
└── settings.gradle.kts
```

---

## Phase 1: Foundation - Project Setup & Core Data Layer

### Task 1: Create Android Project Build Files

**Files to Create:**
- `/home/tiago/dev/personal/CalendarWidget/settings.gradle.kts`
- `/home/tiago/dev/personal/CalendarWidget/gradle/libs.versions.toml`
- `/home/tiago/dev/personal/CalendarWidget/app/build.gradle.kts`

- [ ] **Step 1: Create settings.gradle.kts**

```kotlin
// /home/tiago/dev/personal/CalendarWidget/settings.gradle.kts
pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "CalendarWidget"
include(":app")
```

- [ ] **Step 2: Create version catalog (gradle/libs.versions.toml)**

```toml
# /home/tiago/dev/personal/CalendarWidget/gradle/libs.versions.toml
[versions]
agp = "8.2.0"
kotlin = "1.9.20"
coreKtx = "1.12.0"
junit = "4.13.2"
junitVersion = "1.1.5"
espressoCore = "3.5.1"
lifecycleRuntimeKtx = "2.6.2"
material3 = "1.11.0"
room = "2.6.1"
workManager = "2.9.0"
retrofit = "2.9.0"
okhttp = "4.12.0"
gson = "2.10.1"
hilt = "2.50"
coroutines = "1.7.3"
googlePlayServicesAuth = "20.7.0"
googleApiClient = "2.2.0"
googleCalendarApi = "v3-rev20230707-2.0.0"
ical4j = "3.2.14"
appcompat = "1.6.1"
constraintlayout = "2.1.4"
recyclerview = "1.3.2"
cardview = "1.0.0"
swiperefreshlayout = "1.1.0"

[libraries]
androidx-core-ktx = { group = "androidx.core", name = "core-ktx", version.ref = "coreKtx" }
junit = { group = "junit", name = "junit", version.ref = "junit" }
androidx-junit = { group = "androidx.test.ext", name = "junit", version.ref = "junitVersion" }
androidx-espresso-core = { group = "androidx.test.espresso", name = "espresso-core", version.ref = "espressoCore" }
androidx-lifecycle-runtime-ktx = { group = "androidx.lifecycle", name = "lifecycle-runtime-ktx", version.ref = "lifecycleRuntimeKtx" }
androidx-appcompat = { group = "androidx.appcompat", name = "appcompat", version.ref = "appcompat" }
material = { group = "com.google.android.material", name = "material", version.ref = "material3" }
androidx-constraintlayout = { group = "androidx.constraintlayout", name = "constraintlayout", version.ref = "constraintlayout" }
androidx-recyclerview = { group = "androidx.recyclerview", name = "recyclerview", version.ref = "recyclerview" }
androidx-cardview = { group = "androidx.cardview", name = "cardview", version.ref = "cardview" }
androidx-swiperefreshlayout = { group = "androidx.swiperefreshlayout", name = "swiperefreshlayout", version.ref = "swiperefreshlayout" }

# Room
androidx-room-runtime = { group = "androidx.room", name = "room-runtime", version.ref = "room" }
androidx-room-ktx = { group = "androidx.room", name = "room-ktx", version.ref = "room" }
androidx-room-compiler = { group = "androidx.room", name = "room-compiler", version.ref = "room" }

# WorkManager
androidx-work-runtime-ktx = { group = "androidx.work", name = "work-runtime-ktx", version.ref = "workManager" }
androidx-hilt-work = { group = "androidx.hilt", name = "hilt-work", version = "1.1.0" }

# Network
retrofit = { group = "com.squareup.retrofit2", name = "retrofit", version.ref = "retrofit" }
retrofit-gson = { group = "com.squareup.retrofit2", name = "converter-gson", version.ref = "retrofit" }
okhttp = { group = "com.squareup.okhttp3", name = "okhttp", version.ref = "okhttp" }
okhttp-logging = { group = "com.squareup.okhttp3", name = "logging-interceptor", version.ref = "okhttp" }
gson = { group = "com.google.code.gson", name = "gson", version.ref = "gson" }

# Hilt
hilt-android = { group = "com.google.dagger", name = "hilt-android", version.ref = "hilt" }
hilt-compiler = { group = "com.google.dagger", name = "hilt-compiler", version.ref = "hilt" }

# Coroutines
kotlinx-coroutines-android = { group = "org.jetbrains.kotlinx", name = "kotlinx-coroutines-android", version.ref = "coroutines" }

# Google Calendar API
google-play-services-auth = { group = "com.google.android.gms", name = "play-services-auth", version.ref = "googlePlayServicesAuth" }
google-api-client-android = { group = "com.google.api-client", name = "google-api-client-android", version.ref = "googleApiClient" }
google-api-services-calendar = { group = "com.google.apis", name = "google-api-services-calendar", version.ref = "googleCalendarApi" }

# iCal
ical4j = { group = "org.mnode.ical4j", name = "ical4j", version.ref = "ical4j" }
slf4j-simple = { group = "org.slf4j", name = "slf4j-simple", version = "2.0.9" }

[plugins]
androidApplication = { id = "com.android.application", version.ref = "agp" }
jetbrainsKotlinAndroid = { id = "org.jetbrains.kotlin.android", version.ref = "kotlin" }
hilt = { id = "com.google.dagger.hilt.android", version.ref = "hilt" }
kapt = { id = "org.jetbrains.kotlin.kapt", version.ref = "kotlin" }
```

- [ ] **Step 3: Create app/build.gradle.kts**

```kotlin
// /home/tiago/dev/personal/CalendarWidget/app/build.gradle.kts
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kapt)
}

android {
    namespace = "com.calendar.widget"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.calendar.widget"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.cardview)
    implementation(libs.androidx.swiperefreshlayout)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    kapt(libs.androidx.room.compiler)

    // WorkManager
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.androidx.hilt.work)

    // Network
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)
    implementation(libs.gson)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // Coroutines
    implementation(libs.kotlinx.coroutines.android)

    // Google Calendar API
    implementation(libs.google.play.services.auth)
    implementation(libs.google.api.client.android)
    implementation(libs.google.api.services.calendar)

    // iCal
    implementation(libs.ical4j)
    implementation(libs.slf4j.simple)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
```

- [ ] **Step 4: Create gradle wrapper files**

```bash
cd /home/tiago/dev/personal/CalendarWidget && gradle wrapper --gradle-version 8.2
```

- [ ] **Step 5: Initial commit**

```bash
cd /home/tiago/dev/personal/CalendarWidget
git init
git add -A
git commit -m "chore: setup Android project structure with Material3, Room, WorkManager, Hilt"
```

---

## Next Tasks

Continue with:
- Task 2: Create EventEntity.kt (Room database entity)
- Task 3: Create EventDao.kt (database access object)
- Task 4: Create AppDatabase.kt and Converters.kt
- Task 5: Create domain Event model and EventRepository
- Task 6: Setup Hilt DI with Application class
- Task 7-14: See full plan in original specification

**Execute tasks sequentially. Each task has exact file paths, code, verification commands, and commit instructions.**

---

## Quick Reference

**Build Project:**
```bash
cd /home/tiago/dev/personal/CalendarWidget && ./gradlew build
```

**Run Tests:**
```bash
cd /home/tiago/dev/personal/CalendarWidget && ./gradlew test
```

**Verify Compilation:**
```bash
cd /home/tiago/dev/personal/CalendarWidget && ./gradlew :app:compileDebugKotlin
```

**Project Location:** `/home/tiago/dev/personal/CalendarWidget/`
