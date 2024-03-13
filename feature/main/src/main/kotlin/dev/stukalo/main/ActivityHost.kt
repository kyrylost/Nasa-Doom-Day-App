package dev.stukalo.main

import android.graphics.Color
import android.net.Uri
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.WorkManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dev.stukalo.main.databinding.ActivityHostBinding
import dev.stukalo.main.databinding.ViewSnackbarBinding
import dev.stukalo.navigation.NavigationDirection
import dev.stukalo.platform.BaseActivity
import dev.stukalo.worker.WorkerProvider
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ActivityHost : BaseActivity(R.layout.activity_host) {
    private val viewBinding: ActivityHostBinding by viewBinding(ActivityHostBinding::bind)

    private var snackbarBinding_: ViewSnackbarBinding? = null
    private val snackbarBinding get() = snackbarBinding_!!

    @Inject lateinit var workerProvider: WorkerProvider

    override fun configureUi() {
        (supportFragmentManager.findFragmentById(viewBinding.fcvNavHost.id) as? NavHostFragment)
            ?.let { navHostFragment ->
                navHostFragment.navController.let { navController ->
                    navigator.navController = navController
                }
            }
        initializeWorker()
    }

    private fun initializeWorker() {
        lifecycleScope.launch {
            WorkManager.getInstance(this@ActivityHost).enqueueUniquePeriodicWork(
                "CheckForApproachingAsteroidsWork",
                ExistingPeriodicWorkPolicy.UPDATE,
                workerProvider.createPeriodicRequest(),
            )
        }
    }

    override fun navigateTo(
        flow: NavigationDirection?,
        clearBackStackEntry: Boolean,
        deeplink: Uri?,
        arg: String,
    ) {
        navigator.navigateTo(flow, clearBackStackEntry, deeplink, arg)
    }

    override fun showLoader(isVisible: Boolean) {
        viewBinding.pbLoader.isVisible = isVisible
    }

    private fun showSnackBar(
        message: String,
        view: View,
        textColor: Int,
        cardBackgroundColor: Int,
    ) {
        Snackbar.make(view, "", 7000).apply {
            SnackbarLayoutBuilder(this)
                .setAnchorView(viewBinding.snackbarAnchorView)
                .setMessage(message)
                .setMessageTextColor(textColor)
                .setCardBackgroundColor(cardBackgroundColor)
                .build()
        }.show()
    }

    override fun operationSucceedSnackBar(
        message: String,
        view: View,
    ) {
        showSnackBar(
            message,
            view,
            ContextCompat.getColor(baseContext, dev.stukalo.ui.R.color.green_900),
            ContextCompat.getColor(baseContext, dev.stukalo.ui.R.color.green_300),
        )
    }

    override fun operationFailedSnackBar(
        message: String,
        view: View,
    ) {
        showSnackBar(
            message,
            view,
            ContextCompat.getColor(baseContext, dev.stukalo.ui.R.color.red_900),
            ContextCompat.getColor(baseContext, dev.stukalo.ui.R.color.red_300),
        )
    }

    inner class SnackbarLayoutBuilder(private val snackbar: Snackbar) {
        private var message: String = ""
        private var anchorView: View? = null
        private var messageTextColor: Int = Color.BLACK
        private var cardBackgroundColor: Int = Color.WHITE

        fun setMessage(message: String): SnackbarLayoutBuilder {
            this.message = message
            return this
        }

        fun setAnchorView(anchorView: View): SnackbarLayoutBuilder {
            this.anchorView = anchorView
            return this
        }

        fun setMessageTextColor(messageTextColor: Int): SnackbarLayoutBuilder {
            this.messageTextColor = messageTextColor
            return this
        }

        fun setCardBackgroundColor(cardBackgroundColor: Int): SnackbarLayoutBuilder {
            this.cardBackgroundColor = cardBackgroundColor
            return this
        }

        fun build() {
            with(snackbar.view as Snackbar.SnackbarLayout) {
                setPadding(0, 0, 0, 0)
                setBackgroundColor(Color.TRANSPARENT)

                val customSnackView = layoutInflater.inflate(R.layout.view_snackbar, null)
                snackbarBinding_ = ViewSnackbarBinding.bind(customSnackView)

                with(snackbarBinding) {
                    tvMessage.text = message
                    tvMessage.setTextColor(messageTextColor)
                    cvSnackbar.setCardBackgroundColor(cardBackgroundColor)
                    ibClose.setOnClickListener {
                        snackbar.dismiss()
                    }
                }

                addView(customSnackView, 0)

                snackbar.apply {
                    anchorView = this@SnackbarLayoutBuilder.anchorView
                    addCallback(
                        object : Snackbar.Callback() {
                            override fun onDismissed(
                                transientBottomBar: Snackbar?,
                                event: Int,
                            ) {
                                super.onDismissed(transientBottomBar, event)
                                snackbarBinding_ = null
                            }
                        },
                    )
                }
            }
        }
    }
}
