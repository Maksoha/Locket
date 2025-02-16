package ru.chads.navigation

import android.net.Uri
import ru.chads.navigation.NavCommand.RouterCommand.ToLocketCreator.toEncodeUri

sealed interface NavCommand {

    sealed class RouterCommand(val route: String) : NavCommand {
        data object ToLocketCreator : RouterCommand(LocketDestinations.LocketCreator.route)
        data class ToLocketEditor(val imageUri: Uri) :
            RouterCommand(
                "${LocketDestinations.LocketEditor.route}/${imageUri.toEncodeUri()}"
            )

        internal fun Uri.toEncodeUri(): String = Uri.encode(this.toString())
    }

    data object GoBack : NavCommand
}