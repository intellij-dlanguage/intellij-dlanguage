package io.github.intellij.dlanguage.errorreporting;

import com.intellij.diagnostic.IdeErrorsDialog;
import com.intellij.diagnostic.IdeaReportingEvent;
import com.intellij.idea.IdeaLogger;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.ErrorReportSubmitter;
import com.intellij.openapi.diagnostic.IdeaLoggingEvent;
import com.intellij.openapi.diagnostic.SubmittedReportInfo;
import com.intellij.openapi.extensions.PluginId;
import com.intellij.util.Consumer;
import com.intellij.util.ExceptionUtil;
import io.sentry.Sentry;
import io.sentry.SentryClient;
import io.sentry.context.Context;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

/**
 * Created by francis on 10/29/2017.
 */
public class DErrorReporter extends ErrorReportSubmitter {

    /**
     * @return an action text to be used in Error Reporter user interface, e.g. "Report to JetBrains".
     */
    @NotNull
    @Override
    public String getReportActionText() {
        return "Report to Plugin Developers";
    }

    /**
     * This method is called whenever an exception in a plugin code had happened and a user decided to report a problem to the plugin vendor.
     *
     * @param events          a non-empty sequence of error descriptors.
     * @param additionalInfo  additional information provided by a user.
     * @param parentComponent UI component to use as a parent in any UI activity from a submitter.
     * @param consumer        a callback to be called after sending is finished (or failed).
     * @return {@code true} if reporting was started, {@code false} if a report can't be sent at the moment.
     */
    @Override
    public boolean submit(final IdeaLoggingEvent @NotNull [] events,
                          @Nullable final String additionalInfo,
                          @NotNull final Component parentComponent,
                          final @NotNull Consumer<? super SubmittedReportInfo> consumer) {
        try {
            final SentryClient sentry = Sentry.init("https://f948f2ace2c0452a88d3ff2bd6abd4be@sentry.io/1806295", new DlangSentryClientFactory(getPluginDescriptor()));

            final Context ctx = sentry.getContext();

            for (final IdeaLoggingEvent event : events) {
                ctx.addExtra("Additional info:", additionalInfo);

                final Throwable error = IdeaReportingEvent.class.isAssignableFrom(event.getClass()) ?
                    ((IdeaReportingEvent) event).getData().getThrowable() :
                    event.getThrowable();

                try {
                    final PluginId pluginId = IdeErrorsDialog.findPluginId(event.getThrowable());

                    if(pluginId != null) {
                        ctx.addExtra("plugin id", pluginId.getIdString());
                    }

                    ctx.addExtra("Last Action", IdeaLogger.ourLastActionId);

                    ctx.addExtra("error.message", error.getMessage());
                    ctx.addExtra("error.stacktrace", ExceptionUtil.getThrowableText(error));

//                    final Map<String, String> keyValuePairs = IdeaInformationProxy.getKeyValuePairs(
//                        error,
//                        IdeaLogger.ourLastActionId,
//                        ApplicationManager.getApplication(),
//                        (ApplicationInfoEx) ApplicationInfo.getInstance(),
//                        ApplicationNamesInfo.getInstance(),
//                        super.getPluginDescriptor()
//                    );
//                    for (final String key : keyValuePairs.keySet()) {
//                        ctx.addExtra(key, keyValuePairs.get(key));
//                    }
                } catch (final Exception e) {
                    ctx.addExtra("getting plugin info failed", e);
                }

                ApplicationManager
                    .getApplication()
                    .invokeLater(() -> Sentry.capture(error));
            }
            return true;
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
