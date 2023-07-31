package io.github.intellij.dlanguage.errorreporting

import com.intellij.diagnostic.IdeaReportingEvent
import com.intellij.ide.plugins.PluginManagerCore
import com.intellij.idea.IdeaLogger
import com.intellij.openapi.application.ApplicationInfo
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.application.ApplicationNamesInfo
import com.intellij.openapi.application.ex.ApplicationInfoEx
import com.intellij.openapi.diagnostic.ErrorReportSubmitter
import com.intellij.openapi.diagnostic.IdeaLoggingEvent
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.diagnostic.SubmittedReportInfo
import com.intellij.openapi.util.SystemInfo
import com.intellij.openapi.util.text.StringUtil
import com.intellij.util.Consumer
import io.sentry.*
import java.awt.Component

class SentryErrorHandler : ErrorReportSubmitter() {

    private companion object {
        const val SENTRY_DSN = "https://f948f2ace2c0452a88d3ff2bd6abd4be@sentry.io/1806295"
        private val log: Logger = Logger.getInstance(SentryErrorHandler::class.java)
    }

    /*
     * @return text that is used on the Error Reporters submit button, e.g. "Report to JetBrains".
     */
    override fun getReportActionText(): String = "Report to D Language Plugin Development Team"

    /*
     * @return the text to display in the UI in T&C of privacy policy (under the stack trace)
     */
    override fun getPrivacyNoticeText(): String = "Please provide a brief description to explain how the error occurred. By submitting this bug report you are agreeing for the displayed stacktrace to be shared with the developers via <a href=\"https://sentry.io\">sentry.io</a>. Please also consider raising a bug directly on our <a href=\"https://github.com/intellij-dlanguage/intellij-dlanguage\">Github</a>."

    override fun submit(
        events: Array<out IdeaLoggingEvent>,
        additionalInfo: String?,
        parentComponent: Component,
        consumer: Consumer<in SubmittedReportInfo>
    ): Boolean {
        Sentry.init { options ->
            options.dsn = SENTRY_DSN
            options.isAttachStacktrace = true
            options.isAttachServerName = false

            options.setTag("OS Name", SystemInfo.OS_NAME)
            options.setTag("Java version", SystemInfo.JAVA_VERSION)
            options.setTag("Java vendor", SystemInfo.JAVA_VENDOR)

            val appNames = ApplicationNamesInfo.getInstance()
            options.setTag("IDE Name", appNames.productName)
            options.setTag("IDE Full Name", appNames.fullProductNameWithEdition)

            val appInfo = ApplicationInfo.getInstance()
            options.setTag("IDE Version", appInfo.fullVersion)
            options.setTag("IDE Build", appInfo.build.asString())
            options.setTag("Is EAP", "${(appInfo as ApplicationInfoEx).isEAP}")

            super.getPluginDescriptor()?.let {
                PluginManagerCore.getPlugin(it.pluginId)?.let { plugin ->
                    options.setTag("Plugin", plugin.name)
                    options.setTag("Version", plugin.version)
                }
            }

            // todo: Consider setting transport factory by creating implementation of io.sentry.transport.ITransport
            // which may be needed if cannot submit errors when on a proxy
        }

        events.forEach { e ->
            val error = if (IdeaReportingEvent::class.java.isAssignableFrom(e.javaClass)) (e as IdeaReportingEvent).data.throwable else e.throwable

            val sentryEvent = SentryEvent(error)

            additionalInfo?.let {
                sentryEvent.setExtra("User Comments", it)
            }

            if(StringUtil.isNotEmpty(IdeaLogger.ourLastActionId)) {
                sentryEvent.setExtra("Last Action", IdeaLogger.ourLastActionId)
            }

            ApplicationManager
                .getApplication()
                .invokeLater { Sentry.captureEvent(sentryEvent) }

            log.debug("An error report has been submitted to Sentry.io for the D language plugin")
        }

        return true // return true to indicate that a process has begun to send data async
    }

}
