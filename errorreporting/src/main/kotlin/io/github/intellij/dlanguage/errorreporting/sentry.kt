package io.github.intellij.dlanguage.errorreporting

import com.intellij.ide.DataManager
import com.intellij.ide.plugins.PluginManagerCore
import com.intellij.idea.IdeaLogger
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.application.ApplicationInfo
import com.intellij.openapi.application.ApplicationNamesInfo
import com.intellij.openapi.application.ex.ApplicationInfoEx
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.diagnostic.ErrorReportSubmitter
import com.intellij.openapi.diagnostic.IdeaLoggingEvent
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.diagnostic.SubmittedReportInfo
import com.intellij.openapi.util.SystemInfo
import com.intellij.openapi.util.text.StringUtil
import com.intellij.platform.ide.progress.withBackgroundProgress
import com.intellij.platform.util.coroutines.childScope
import com.intellij.util.Consumer
import io.github.intellij.dlanguage.DlangBundle
import io.sentry.Sentry
import io.sentry.SentryEvent
import io.sentry.protocol.SentryId
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.awt.Component

class SentryErrorHandler : ErrorReportSubmitter() {

    private companion object {
        const val SENTRY_DSN = "https://f948f2ace2c0452a88d3ff2bd6abd4be@sentry.io/1806295"
        private val log: Logger = Logger.getInstance(SentryErrorHandler::class.java)
    }

    /*
     * @return text that is used on the Error Reporters submit button, e.g. "Report to JetBrains".
     */
    override fun getReportActionText(): String = DlangBundle.message("dlang.errorreporting.reportaction")

    /*
     * @return the text to display in the UI in T&C of privacy policy (under the stack trace)
     */
    override fun getPrivacyNoticeText(): String = DlangBundle.message("dlang.errorreporting.privacynotice")

    override fun submit(
        events: Array<out IdeaLoggingEvent>,
        additionalInfo: String?,
        parentComponent: Component,
        consumer: Consumer<in SubmittedReportInfo>
    ): Boolean {
        Sentry.init { options ->
            options.dsn = SENTRY_DSN
            // options.isEnableMetrics = true // todo: configure some metrics in Sentry
            options.isAttachStacktrace = true
            options.isAttachServerName = false

            options.setTag("OS Name", SystemInfo.OS_NAME)
            options.setTag("Java version", SystemInfo.JAVA_VERSION)
            options.setTag("Java vendor", SystemInfo.JAVA_VENDOR)

            ApplicationNamesInfo.getInstance().let {
                options.environment = it.productName // the default environment is 'production' which isn't useful
                options.setTag("IDE Name", it.productName)
                options.setTag("IDE Full Name", it.fullProductNameWithEdition)
            }

            ApplicationInfo.getInstance().let {
                options.setTag("IDE Version", it.fullVersion)
                options.setTag("IDE Build", it.build.asString())
                options.setTag("Is EAP", "${(it as ApplicationInfoEx).isEAP}")
            }

            super.getPluginDescriptor()?.let {
                PluginManagerCore.getPlugin(it.pluginId)?.let { plugin ->
                    options.setTag("Plugin", plugin.name)
                    options.setTag("Version", plugin.version)
                    options.release = plugin.version // specifying the release is important to make the most of Sentry
                }
            }

            // todo: Consider setting transport factory by creating implementation of io.sentry.transport.ITransport
            // which may be needed if cannot submit errors when on a proxy
        }

        val project = CommonDataKeys.PROJECT.getData(DataManager.getInstance().getDataContext(parentComponent))

        events.forEach { e ->
            val sentryEvent = SentryEvent(e.throwable)

            sentryEvent.setExtra("Message", e.message)

            additionalInfo?.let {
                sentryEvent.setExtra("User Comments", it)
            }

            if (e.attachments.isNotEmpty()) {
                sentryEvent.setExtra("Attachments", e.attachments)
            }

            if (StringUtil.isNotEmpty(IdeaLogger.ourLastActionId)) {
                sentryEvent.setExtra("Last Action", IdeaLogger.ourLastActionId)
            }

            service<SentryCoroutineScopeHolder>().coroutineScope.launch {
                try {
                    val sentryEventId: SentryId = if (project != null) {
                        withBackgroundProgress(project, "Submitting error report") {
                            Sentry.captureEvent(sentryEvent)
                        }
                    } else {
                        Sentry.captureEvent(sentryEvent)
                    }
                    log.info("An error report has been submitted to Sentry.io for the D language plugin. Ref: $sentryEventId")

                    // the error reporting modal will display the link text prefixed with "Submitted as"
                    consumer.consume(
                        SubmittedReportInfo(
                            "https://singingbush.sentry.io/issues?query=${sentryEventId}",
                            sentryEventId.toString(),
                            SubmittedReportInfo.SubmissionStatus.NEW_ISSUE
                        )
                    )
                } catch (e: Throwable) {
                    log.warn("Unable to report error to sentry.io", e)
                    consumer.consume(
                        SubmittedReportInfo(
                            null,
                            e.message,
                            SubmittedReportInfo.SubmissionStatus.FAILED
                        )
                    )
                }
            }
        }
        return true
    }

}

@Service
internal class SentryCoroutineScopeHolder(coroutineScope: CoroutineScope) {

    @JvmField
    val dispatcher: CoroutineDispatcher = Dispatchers.IO

    @JvmField
    internal val coroutineScope = coroutineScope.childScope("Sentry call", dispatcher)
}
