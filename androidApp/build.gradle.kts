plugins {
    id("com.android.application")
    kotlin("android")
}

// // used only for local testing
val keystoreProperties = Properties()
val keystorePropertiesFile = rootProject.file("key.properties")

if (keystorePropertiesFile.exists()) {
    keystoreProperties.load(keystorePropertiesFile.inputStream())
}


// // get version code from the specified property argument `-PversionCode` during the build call
// def getMyVersionCode = { ->
//     return project.hasProperty('versionCode') ? versionCode.toInteger() : -1
// }

// // get version name from the specified property argument `-PversionName` during the build call
// def getMyVersionName = { ->
//     return project.hasProperty('versionName') ? versionName : "1.0"
// }

android {
    namespace = "io.codemagic.dtrdic17"
    compileSdk = 33
    defaultConfig {
        applicationId = "io.codemagic.dtrdic17"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }
      signingConfigs {
         create("release") {
                storeFile = file(System.getenv()["CM_KEYSTORE_PATH"])
                storePassword = System.getenv()["CM_KEYSTORE_PASSWORD"]
                keyAlias = System.getenv()["CM_KEY_ALIAS"]
                keyPassword = System.getenv()["CM_KEY_PASSWORD"]

            //   if (System.getenv()["CI"]) { // CI=true is exported by Codemagic
            //   } else {
            //       keyAlias keystoreProperties['keyAlias']
            //       keyPassword keystoreProperties['keyPassword']
            //       storeFile keystoreProperties['storeFile'] ? file(keystoreProperties['storeFile']) : null
            //       storePassword keystoreProperties['storePassword']
            //   }
          }
      }
    buildTypes {
     release {
              signingConfig = signingConfigs.getByName("release")
          }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.7"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
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