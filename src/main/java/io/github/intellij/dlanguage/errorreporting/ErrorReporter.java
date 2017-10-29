package io.github.intellij.dlanguage.errorreporting;

import com.intellij.diagnostic.IdeErrorsDialog;
import com.intellij.idea.IdeaLogger;
import com.intellij.openapi.application.ApplicationInfo;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.ApplicationNamesInfo;
import com.intellij.openapi.application.ex.ApplicationInfoEx;
import com.intellij.openapi.diagnostic.ErrorReportSubmitter;
import com.intellij.openapi.diagnostic.IdeaLoggingEvent;
import com.intellij.openapi.diagnostic.SubmittedReportInfo;
import com.intellij.openapi.extensions.PluginId;
import com.intellij.util.Consumer;
import de.halirutan.mathematica.errorreporting.GitHubErrorBean;
import de.halirutan.mathematica.errorreporting.IdeaInformationProxy;
import io.sentry.Sentry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.LinkedHashMap;

/**
 * Created by francis on 10/29/2017.
 */
public class ErrorReporter extends ErrorReportSubmitter {

    static {
        Sentry.init("https://f0a6a71038a645db865befe4d197def8@sentry.io/237092");
    }

    /**
     * @return an action text to be used in Error Reporter user interface, e.g. "Report to JetBrains".
     */
    @NotNull
    @Override
    public String getReportActionText() {
        return "Report to Plugin Developers";
    }

    @Override
    public boolean submit(@NotNull final IdeaLoggingEvent[] events,
                          @Nullable final String additionalInfo,
                          @NotNull final Component parentComponent,
                          @NotNull final Consumer<SubmittedReportInfo> consumer) {
        for (final IdeaLoggingEvent event : events) {
            Sentry.getContext().addExtra("Additional info:", additionalInfo);
            try {
                final PluginId pluginId = IdeErrorsDialog.findPluginId(event.getThrowable());
                Sentry.getContext().addExtra("plugin id", pluginId.getIdString());
                final GitHubErrorBean errorBean = new GitHubErrorBean(event.getThrowable(), IdeaLogger.ourLastActionId);
                final LinkedHashMap<String, String> keyValuePairs = IdeaInformationProxy.getKeyValuePairs(errorBean, ApplicationManager.getApplication(),
                    (ApplicationInfoEx) ApplicationInfo.getInstance(),
                    ApplicationNamesInfo.getInstance());
                for (final String key : keyValuePairs.keySet()) {
                    Sentry.getContext().addExtra(key, keyValuePairs.get(key));
                }

            } catch (final Exception e) {
                Sentry.getContext().addExtra("gettting plugin info failed", e);
            }

            try {
                Sentry.capture(event.getThrowable());
            } catch (Exception e) {
                return false;
            }
        }
        return true;


    }
}
