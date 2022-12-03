[![](https://jitpack.io/v/sachinkumar53/CraterSdk.svg)](https://jitpack.io/#sachinkumar53/CraterSdk)
# Crater SDK

An android library to integrate Crater app features inside your app.


## Prerequisite
- Kotlin version 1.7.20
- targetSdk & compileSdk  33
- Compose UI version 1.3.1 and Compose Compiler version 1.3.2
- Dagger hilt setup
- Kotlinx serialization


## How to use?

Step 1. Add the JitPack maven repository

```groovy
maven { url "https://jitpack.io"  }
```

Step 2. Add Dagger hilt and Kotlinx serialization. You can Skip this step if it is already setup.

Add these in your project level `build.gradle` file
```groovy
dependencies{
    classpath "com.google.dagger:hilt-android-gradle-plugin:2.43.2"
    classpath "org.jetbrains.kotlin:kotlin-serialization:1.7.20"
}
```

Add these in your app level `build.gradle` file
```groovy
plugins {
    id 'dagger.hilt.android.plugin'
    id 'kotlin-kapt'
}

dependencies{
    implementation "com.google.dagger:hilt-android:2.43.2"
    kapt "com.google.dagger:hilt-compiler:2.43.2"
}
```

Step 3. Add crater sdk to your app level `build.gradle` file
```groovy
dependencies {
    implementation "com.github.sachinkumar53:CraterSdk:0.1.4"
}
```

Step 4. Sync the project

Step 5. Initialize Crater SDK in your application

```kotlin
@HiltAndroidApp
class YourApp : Application() {
    
    override fun onCreate() {
        super.onCreate()
        Crater.initialize()
    }
}
```
Step 6. Now the SDK is ready to use. Start the CraterActivity from your app.
```kotlin
startActivity(Intent(context, CraterActivity::class.java))}
```
