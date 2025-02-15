package ru.chads.navigation

sealed interface LocketDestination {
    val route: String get() = this::class.java.simpleName
}

sealed interface LocketDestinations: LocketDestination {
    data object LockedFeed: LocketDestinations
    data object LocketCreator: LocketDestinations
    data object LocketEditor: LocketDestinations
}