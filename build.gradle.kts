plugins {
    kotlin("js") version "1.6.20"
    kotlin("plugin.serialization") version "1.6.20"
}

group = "it.emanuelemelini"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react:18.0.0-pre.329-kotlin-1.6.20")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom:18.0.0-pre.329-kotlin-1.6.20")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react-css:18.0.0-pre.329-kotlin-1.6.20")
    implementation(npm("react", "18.0.0"))
    implementation(npm("react-dom", "18.0.0"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")
}

kotlin {
    js(LEGACY) {
        binaries.executable()
        browser {
            commonWebpackConfig {
                cssSupport.enabled = true
            }
        }
    }
}