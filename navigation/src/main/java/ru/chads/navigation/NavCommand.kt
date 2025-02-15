package ru.chads.navigation

sealed class NavCommand(val route: String) {
    data object ToLocketCreator : NavCommand(LocketDestinations.LocketCreator.route)
    data object ToLocketEditor: NavCommand(LocketDestinations.LocketEditor.route)
}