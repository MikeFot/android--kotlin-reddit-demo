pluginManagement {
    @Suppress("UnstableApiUsage")
    plugins {
        id("org.jetbrains.kotlin.jvm") version "1.5.21"
    }
    repositories {
        google()
        mavenCentral()
        maven(url = "https://plugins.gradle.org/m2/")
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "RedditDemo"
include(":app")
