import java.io.File
import java.util.*

val keystoreProperties =
        Properties().apply {
            var file = File("androidApp/key.properties")
            if (file.exists()) load(file.reader())
        }

plugins {
    id("com.android.application")
    kotlin("android")
}


val latestGooglePlayBuildNumber = Integer.valueOf(System.getenv("LATEST_GOOGLE_PLAY_BUILD_NUMBER") ?: System.getenv("BUILD_NUMBER") ?: "0")


android {
    namespace = "io.codemagic.dtrdic17"
    compileSdk = 33
    defaultConfig {
        applicationId = "io.codemagic.dtrdic17"
        minSdk = 24
        targetSdk = 33
        versionCode = latestGooglePlayBuildNumber + 1
        versionName = "1.0.${latestGooglePlayBuildNumber + 1}"
    }
    signingConfigs {
        create("release") {
            if (System.getenv()["CI"].toBoolean()) { // CI=true is exported by Codemagic
                storeFile = file(System.getenv()["CM_KEYSTORE_PATH"])
                storePassword = System.getenv()["CM_KEYSTORE_PASSWORD"]
                keyAlias = System.getenv()["CM_KEY_ALIAS"]
                keyPassword = System.getenv()["CM_KEY_PASSWORD"]
            } else {
                storeFile = file(keystoreProperties.getProperty("storeFile"))
                storePassword = keystoreProperties.getProperty("storePassword")
                keyAlias = keystoreProperties.getProperty("keyAlias")
                keyPassword = keystoreProperties.getProperty("keyPassword")
            }
        }
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("release")
        }
    }
    
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.9"
    }
}



dependencies {
    implementation(project(":shared"))
    implementation("androidx.compose.ui:ui:1.4.3")
    implementation("androidx.compose.ui:ui-tooling:1.4.3")
    implementation("androidx.compose.ui:ui-tooling-preview:1.4.3")
    implementation("androidx.compose.foundation:foundation:1.4.3")
    implementation("androidx.compose.material:material:1.4.3")
    implementation("androidx.activity:activity-compose:1.7.1")
}