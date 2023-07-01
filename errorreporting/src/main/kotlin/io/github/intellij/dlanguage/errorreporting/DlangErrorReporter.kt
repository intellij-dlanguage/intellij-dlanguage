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
import com.intellij.openapi.diagnostic.SubmittedReportInfo
import com.intellij.openapi.util.SystemInfo
import com.intellij.util.Consumer
import io.sentry.Sentry
import io.sentry.SentryEvent
import io.sentry.SentryOptions

import java.awt.*

/**
 * Created by francis on 10/29/2017.
 */
class DErrorReporter : ErrorReportSubmitter() {
    private companion object {
        const val DSN = "https://f948f2ace2c0452a88d3ff2bd6abd4be@sentry.io/1806295"
    }

    /**
     * @return text that is used on the Error Reporter's submit button, e.g. "Report to JetBrains".
     */
    override fun getReportActionText(): String = "Report to D Language Plugin Development Team"

    /**
     * @return the text to display in the UI in T&C of privacy policy (under the stack trace)
     */
    override fun getPrivacyNoticeText(): String = "Please provide a brief description to explain how the error occurred. By submitting this bug report you are agreeing for the displayed stacktrace to be shared with the developers via <a href=\"https://sentry.io\">sentry.io</a>. Please also consider raising a bug directly on our <a href=\"https://github.com/intellij-dlanguage/intellij-dlanguage\">Github</a>."

    /**
     * This method is called whenever an exception in a plugin code had happened and a user decided to report a problem to the plugin vendor.
     *
     * @param events          a non-empty sequence of error descriptors.
     * @param additionalInfo  additional information provided by a user.
     * @param parentComponent UI component to use as a parent in any UI activity from a submitter.
     * @param consumer        a callback to be called after sending is finished (or failed).
     * @return {@code true} if reporting was started, {@code false} if a report can't be sent at the moment.
     */
    override fun submit(
        events: Array<out IdeaLoggingEvent>,
        additionalInfo: String?,
        parentComponent: Component,
        consumer: Consumer<in SubmittedReportInfo>
    ): Boolean {
        try {
            Sentry.init(this::sentryOptions, false)

            events.forEach { event ->
                val error: Throwable = if (IdeaReportingEvent::class.java.isAssignableFrom(event.javaClass)) {
                    (event as IdeaReportingEvent).data.throwable
                } else {
                    event.throwable
                }

                val sentryEvent = SentryEvent(error)

                additionalInfo?.let {
                    sentryEvent.setExtra("User Comments", it)
                }

                IdeaLogger.ourLastActionId?.let {
                    sentryEvent.setExtra("Last Action", it)
                }

                ApplicationManager
                    .getApplication()
                    .invokeLater { Sentry.captureEvent(sentryEvent) }
            }
            return true
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    private fun sentryOptions(options: SentryOptions) {
        options.dsn = DSN
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

        val isEarlyAccess = (appInfo as ApplicationInfoEx).isEAP
        options.setTag("Is EAP", isEarlyAccess.toString()) // perhaps remove

        super.getPluginDescriptor()?.let {
            PluginManagerCore.getPlugin(it.pluginId)?.let { plugin ->
                options.setTag("Plugin", plugin.name)
                options.setTag("Version", plugin.version)
            }
        }
    }
}
