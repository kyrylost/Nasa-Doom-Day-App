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

rootProject.name = "Stukalo Kyrylo"
include(":app")
include(":feature")
include(":feature:splash")
include(":feature:main")
include(":feature:onboard")
include(":feature:init")
include(":feature:asteroids")
include(":feature:asteroiddetails")
include(":feature:favoriteasteroids")
include(":feature:settings")
include(":feature:compare")
include(":core")
include(":core:ui")
include(":core:platform")
include(":core:navigation")
include(":data")
include(":data:network")
include(":data:datastore")
include(":data:database")
include(":domain")
