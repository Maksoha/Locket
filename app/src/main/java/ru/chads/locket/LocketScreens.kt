package ru.chads.locket

interface LocketScreen {
    val route: String get() = this::class.java.simpleName
}

sealed interface LocketScreens {
    object LockedFeed: LocketScreen
}