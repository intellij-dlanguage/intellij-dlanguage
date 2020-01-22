package io.github.intellij.dlanguage.errorreporting

import com.intellij.ide.plugins.PluginManager
import com.intellij.idea.IdeaLogger
import com.intellij.openapi.application.ApplicationInfo
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.application.ApplicationNamesInfo
import com.intellij.openapi.application.ex.ApplicationInfoEx
import com.intellij.openapi.diagnostic.ErrorReportSubmitter
import com.intellij.openapi.diagnostic.IdeaLoggingEvent
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.diagnostic.SubmittedReportInfo
import com.intellij.openapi.extensions.PluginDescriptor
import com.intellij.openapi.util.SystemInfo
import com.intellij.util.Consumer
import de.halirutan.mathematica.errorreporting.IdeaInformationProxy
import io.sentry.DefaultSentryClientFactory
import io.sentry.Sentry
import io.sentry.dsn.Dsn
import java.awt.Component

/**
 * A re-implementation of the original DErrorReporter that was written by francis (pirocks) on 10/29/2017
 *
 * This version uses a custom SentryClientFactory which helps simplify this class
 *
 * @author Samael (singingbush)
 */
class SentryErrorHandler : ErrorReportSubmitter() {

    private val log: Logger = Logger.getInstance(javaClass)

    init {
        Sentry.init("https://f948f2ace2c0452a88d3ff2bd6abd4be@sentry.io/1806295", DlangSentryClientFactory(pluginDescriptor))
    }

    override fun getReportActionText(): String = "Report to the D Language developers"

    override fun getPrivacyNoticeText(): String = "All data is anonymised prior to being transferred to <a href=\"https://sentry.io\">sentry.io</a> for use by the dev team."

    override fun submit(events: Array<out IdeaLoggingEvent>, additionalInfo: String?, parentComponent: Component, consumer: Consumer<SubmittedReportInfo>): Boolean {
        events.forEach { e ->
            IdeaInformationProxy.getKeyValuePairs(
                e.throwable?.cause ?: e.throwable,
                IdeaLogger.ourLastActionId,
                ApplicationManager.getApplication(),
                ApplicationInfo.getInstance() as ApplicationInfoEx,
                ApplicationNamesInfo.getInstance(),
                super.getPluginDescriptor()
            ).forEach {
                Sentry.getContext().addExtra(it.key, it.value)
            }
            ApplicationManager.getApplication().invokeLater { Sentry.capture(e.throwable) }
            log.info("The error has been submitted to Sentry.io")
        }

        return true // return true to indicate that a process has begun to send data async
    }
}

/**
 * By creating our own SentryClientFactory we can ensure that all requests to sentry.io contain the same basic info.
 * Also, it means we can get the sentry client to use the proxy settings of the IDE (in case user is behind a proxy).
 */
class DlangSentryClientFactory(pluginDescriptor: PluginDescriptor?) : DefaultSentryClientFactory() {

    private val plugin = PluginManager.getPlugin(pluginDescriptor?.pluginId)
    private val version = plugin?.version ?: ""
    private val appInfo = ApplicationInfo.getInstance() as ApplicationInfoEx
    private val namesInfo = ApplicationNamesInfo.getInstance()

    override fun getRelease(dsn: Dsn?): String = version

    override fun getEnvironment(dsn: Dsn?): String = namesInfo.productName

    override fun getServerName(dsn: Dsn?): String = "" // override to anonymise the data

    //override fun getDist(dsn: Dsn?): String = ""

    override fun getTags(dsn: Dsn?): MutableMap<String, String> = mutableMapOf(
        "Version" to version,
        "OS Name" to SystemInfo.OS_NAME,
        "Java version" to SystemInfo.JAVA_VERSION,
        "Java vendor" to SystemInfo.JAVA_VENDOR,
        "IDE Name" to namesInfo.productName,
        "IDE Full Name" to namesInfo.fullProductNameWithEdition,
        "IDE Version" to appInfo.fullVersion,
        "IDE Build" to appInfo.build.asString(),
        "Is EAP" to appInfo.isEAP.toString()
    )

    override fun getInAppFrames(dsn: Dsn?): MutableCollection<String> = mutableSetOf("io.github.intellij.dlanguage")

    // todo: Get Intellij System settings for proxy and use them if configured
//    override fun getProxyHost(dsn: Dsn?): String {
//        return super.getProxyHost(dsn)
//    }
//
//    override fun getProxyPort(dsn: Dsn?): Int {
//        return super.getProxyPort(dsn)
//    }
//
//    override fun getProxyUser(dsn: Dsn?): String {
//        return super.getProxyUser(dsn)
//    }
//
//    override fun getProxyPass(dsn: Dsn?): String {
//        return super.getProxyPass(dsn)
//    }

}
