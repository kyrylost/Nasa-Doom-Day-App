package dev.stukalo.datastore

object Constants {
    enum class Velocity(val value: String) {
        KILOMETERS_PER_SECOND("km/sec"),
        KILOMETERS_PER_HOUR("km/h"),
        MILES_PER_HOUR("mph"),
    }

    enum class Distance(val value: String) {
        ASTRONOMICAL("au"),
        LUNAR("lu"),
        KILOMETERS("km"),
        MILES("mi"),
    }

    enum class Diameter(val value: String) {
        KILOMETERS("km"),
        METERS("m"),
        MILES("mi"),
        FEET("ft"),
    }

    enum class SynchronizationTime(val value: String) {
        M30("30M"),
        H1("1H"),
        H2("2H"),
        H6("6H"),
    }

    enum class MinimumInterval(val value: String) {
        H6("6H"),
        H12("12H"),
        H24("24H"),
        H48("48H"),
    }
}
