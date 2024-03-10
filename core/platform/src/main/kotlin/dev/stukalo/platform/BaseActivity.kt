package dev.stukalo.platform

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dev.stukalo.navigation.Navigable
import dev.stukalo.navigation.NavigationDirection
import dev.stukalo.navigation.Navigator
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

abstract class BaseActivity(
    @LayoutRes layout: Int,
) : AppCompatActivity(layout), Navigable {
    private var isPermissionGranted = Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU
    val navigator: Navigator = Navigator()

    private val pushNotificationPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission(),
        ) { granted ->
            Toast.makeText(this, "Permissions granted: $granted", Toast.LENGTH_SHORT).show()
            isPermissionGranted = granted
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        runBlocking {
            installSplashScreen()
            delay(3000)
            this@BaseActivity.setTheme(dev.stukalo.ui.R.style.Theme_StukaloKyrylo)
        }
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            isPermissionGranted = checkSelfPermission(
                Manifest.permission.POST_NOTIFICATIONS,
            ) == PackageManager.PERMISSION_GRANTED

            if (!isPermissionGranted) {
                pushNotificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }

        configureUi()
    }

    protected open fun configureUi() = Unit

    override fun navigateTo(
        flow: NavigationDirection?,
        clearBackStackEntry: Boolean,
        deeplink: Uri?,
        arg: String,
    ) {
        try {
            navigator.navigateTo(flow, clearBackStackEntry, deeplink, arg)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    abstract fun showLoader(isVisible: Boolean)

    abstract fun operationSucceedSnackBar(
        message: String,
        view: View,
    )

    abstract fun operationFailedSnackBar(
        message: String,
        view: View,
    )
}
