import java.util.Properties

fun RepositoryHandler.tomtomArtifactory() {
    maven("https://repositories.tomtom.com/artifactory/maven") {
        content { includeGroupByRegex("com\\.tomtom\\..+") }
    }
}

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            val localProperties = java.util.Properties()
            val localPropertiesFile = File(settingsDir, "local.properties")
            if (localPropertiesFile.exists()) {
                localProperties.load(localPropertiesFile.inputStream())
            }

            credentials {
                username = localProperties.getProperty("tomtom.username") ?: ""
                password = localProperties.getProperty("tomtom.password") ?: ""
            }
            url = uri("https://repositories.tomtom.com/artifactory/maven")

        }
    }
}

rootProject.name = "NavigationApp"
include(":app")
