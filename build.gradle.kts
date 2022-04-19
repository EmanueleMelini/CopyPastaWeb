plugins {
    kotlin("js") version "1.6.20"
    kotlin("plugin.serialization") version "1.6.20"
}

group = "it.emanuelemelini"
version = "0.0.1"

repositories {
    mavenCentral()
}

rootProject.plugins.withType<org.jetbrains.kotlin.gradle.targets.js.yarn.YarnPlugin> {
    rootProject.the<org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension>().apply {
        lockFileDirectory = project.rootDir.resolve("kotlin-js-store")
    }
    rootProject.the<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension>().apply {
        //versions.webpackDevServer.version = "4.8.1"
        //versions.webpack.version = "5.72.0"
        //versions.webpackCli.version = "4.9.2"
        versions.karma.version = "6.3.18"
        versions.mocha.version = "9.2.2"
    }
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

    //implementation(npm("karma", "6.3.16"))
    //implementation(npm("nanoid", "3.1.31"))
    //implementation(npm("async", "3.2.2"))
    //implementation(npm("node-forge", "1.3.0"))

}


rootProject.plugins.withType<org.jetbrains.kotlin.gradle.targets.js.yarn.YarnPlugin> {
    rootProject.the<org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension>().apply {
        resolution("async", "3.2.2")
        resolution("nanoid", "3.1.31")
        resolution("node-forge", "1.3.0")
    }
}

kotlin {
    js(IR) {
        binaries.executable()
        browser {
            commonWebpackConfig {
                cssSupport.enabled = true
            }
        }
    }
}