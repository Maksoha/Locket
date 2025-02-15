package ru.chads.core_ui

import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

@Composable
fun permissionRequester(
    permission: String,
    context: Context = LocalContext.current,
    onResult: (Boolean) -> Unit,
): () -> Unit {
    var requestedPermission by remember { mutableStateOf<String?>(null) }
    var onPermissionResult by remember { mutableStateOf<((Boolean) -> Unit)?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        onPermissionResult?.invoke(isGranted)
        requestedPermission = null
        onPermissionResult = null
    }

    DisposableEffect(Unit) {
        onDispose {
            requestedPermission = null
            onPermissionResult = null
        }
    }


    return {
        val permissionStatus = ContextCompat.checkSelfPermission(context, permission)
        if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
            onResult(true)
        } else {
            requestedPermission = permission
            onPermissionResult = onResult
            launcher.launch(permission)
        }
    }
}