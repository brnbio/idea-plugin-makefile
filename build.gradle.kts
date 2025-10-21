plugins {
    id("org.jetbrains.intellij.platform") version "2.1.0"
    kotlin("jvm") version "2.0.21"
}

repositories {
    mavenCentral()
    intellijPlatform { defaultRepositories() }
}

dependencies {
    intellijPlatform {
        create("IC", "2024.1")
        bundledPlugin("org.jetbrains.plugins.terminal")
        instrumentationTools()
    }
}

kotlin {
    jvmToolchain(17)
}

intellijPlatform {
    pluginConfiguration {
        name = "Make Targets"
        version = "1.1.0"
        ideaVersion {
            sinceBuild = "241"
            untilBuild = provider { null }
        }
    }
    buildSearchableOptions = false
    publishing {
        token = providers.environmentVariable("PUBLISH_TOKEN")
        // Channels: default, beta, alpha, eap
        // channels = listOf("default")
    }
    signing {
        certificateChain = providers.environmentVariable("CERTIFICATE_CHAIN")
        privateKey = providers.environmentVariable("PRIVATE_KEY")
        password = providers.environmentVariable("PRIVATE_KEY_PASSWORD")
    }
}