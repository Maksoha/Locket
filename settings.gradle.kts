pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Locket"
include(":app")
include(":core_ui")
include(":feature_feed")
include(":data")
include(":feature_camera_preview")
include(":navigation")
include(":feature_editor")
include(":background_worker")
include(":domain")
include(":coroutines")
include(":auth_reg")
include(":network")
