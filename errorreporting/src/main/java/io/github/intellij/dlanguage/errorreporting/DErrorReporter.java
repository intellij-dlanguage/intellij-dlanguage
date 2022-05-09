package io.github.intellij.dlanguage.errorreporting;

import com.intellij.diagnostic.IdeaReportingEvent;
import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.CapturingProcessHandler;
import com.intellij.ide.plugins.IdeaPluginDescriptor;
import com.intellij.ide.plugins.PluginManagerCore;
import com.intellij.idea.IdeaLogger;
import com.intellij.openapi.application.ApplicationInfo;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.ApplicationNamesInfo;
import com.intellij.openapi.application.ex.ApplicationInfoEx;
import com.intellij.openapi.diagnostic.ErrorReportSubmitter;
import com.intellij.openapi.diagnostic.IdeaLoggingEvent;
import com.intellij.openapi.diagnostic.SubmittedReportInfo;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.util.Consumer;
import io.github.intellij.dlanguage.settings.ToolKey;
import io.sentry.Sentry;
import io.sentry.SentryEvent;
import io.sentry.SentryOptions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * Created by francis on 10/29/2017.
 */
public class DErrorReporter extends ErrorReportSubmitter {

    /**
     * @return text that is used on the Error Reporter's submit button, e.g. "Report to JetBrains".
     */
    @NotNull
    @Override
    public String getReportActionText() {
        return "Report to D Language Plugin Development Team";
    }

    /**
     * @return the text to display in the UI in T&C of privacy policy (under the stack trace)
     */
    @Override
    public String getPrivacyNoticeText() {
        return "Please provide a brief description to explain how the error occurred. By submitting this bug report you are agreeing for the displayed stacktrace to be shared with the developers via <a href=\"https://sentry.io\">sentry.io</a>. Please also consider raising a bug directly on our <a href=\"https://github.com/intellij-dlanguage/intellij-dlanguage\">Github</a>.";
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
            Sentry.init(this::sentryOptions, false);

            for (final IdeaLoggingEvent event : events) {
                final Throwable error = IdeaReportingEvent.class.isAssignableFrom(event.getClass()) ?
                    ((IdeaReportingEvent) event).getData().getThrowable() :
                    event.getThrowable();

                final SentryEvent sentryEvent = new SentryEvent(error);

                if(StringUtil.isNotEmpty(additionalInfo)) {
                    sentryEvent.setExtra("User Comments", additionalInfo);
                }

                Optional.ofNullable(IdeaLogger.ourLastActionId)
                    .ifPresent(actionId -> sentryEvent.setExtra("Last Action", actionId));

                ApplicationManager
                    .getApplication()
                    .invokeLater(() -> {
                        addBinaryVersion(ToolKey.DUB_KEY, sentryEvent);
                        addBinaryVersion(ToolKey.DCD_CLIENT_KEY, sentryEvent);
                        addBinaryVersion(ToolKey.DCD_CLIENT_KEY, sentryEvent);
                        addBinaryVersion(ToolKey.DSCANNER_KEY, sentryEvent);
                        addBinaryVersion(ToolKey.DFIX_KEY, sentryEvent);
                        addBinaryVersion(ToolKey.DFORMAT_KEY, sentryEvent);
                        addBinaryVersion(ToolKey.GDB_KEY, sentryEvent);
                        Sentry.captureEvent(sentryEvent);
                    });
            }
            return true;
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void sentryOptions(@NotNull SentryOptions options) {
        options.setDsn("https://f948f2ace2c0452a88d3ff2bd6abd4be@sentry.io/1806295");
        options.setAttachStacktrace(true);
        options.setAttachServerName(false);

        options.setTag("OS Name", SystemInfo.OS_NAME);
        options.setTag("Java version", SystemInfo.JAVA_VERSION);
        options.setTag("Java vendor", SystemInfo.JAVA_VENDOR);

        options.setTag("IDE Name", ApplicationNamesInfo.getInstance().getProductName());
        options.setTag("IDE Full Name", ApplicationNamesInfo.getInstance().getFullProductNameWithEdition());
        options.setTag("IDE Version", ApplicationInfo.getInstance().getFullVersion());
        options.setTag("IDE Build", ApplicationInfo.getInstance().getBuild().asString());
        options.setTag("Is EAP", Boolean.toString( ((ApplicationInfoEx) ApplicationInfo.getInstance()).isEAP() )); // perhaps remove

        if(super.getPluginDescriptor() != null) {
            final IdeaPluginDescriptor plugin = PluginManagerCore.getPlugin(super.getPluginDescriptor().getPluginId());
            if (plugin != null) {
                options.setTag("Plugin", plugin.getName());
                options.setTag("Version", plugin.getVersion());
            }
        }

//        this.addBinaryVersion(ToolKey.DUB_KEY, options);
//
//        this.addBinaryVersion(ToolKey.DCD_SERVER_KEY, options);
//
//        this.addBinaryVersion(ToolKey.DCD_CLIENT_KEY, options);
//
//        this.addBinaryVersion(ToolKey.DSCANNER_KEY, options);
//
//        this.addBinaryVersion(ToolKey.DFORMAT_KEY, options);
//
//        this.addBinaryVersion(ToolKey.DFIX_KEY, options);
//
//        this.addBinaryVersion(ToolKey.GDB_KEY, options);
    }

    private void addBinaryVersion(@NotNull final ToolKey toolKey, @NotNull final SentryOptions options) {
        @Nullable final String toolPath = toolKey.getPath();

        if(!StringUtil.isEmptyOrSpaces(toolPath)) {
            final GeneralCommandLine cmd = new GeneralCommandLine(toolPath, "--version")
                .withCharset(StandardCharsets.US_ASCII)
                .withRedirectErrorStream(true);

            ApplicationManager
                .getApplication()
                .executeOnPooledThread(() -> {
                    try {
                        final String stdout = new CapturingProcessHandler(
                            cmd.createProcess(),
                            cmd.getCharset(),
                            cmd.getCommandLineString()
                        ).runProcess().getStdout();

                        if (StringUtil.isNotEmpty(stdout)) {
                            final String version = stdout.split("\n")[0].trim();
                            options.setTag(toolKey.getName(), version);
                        }
                    } catch (ExecutionException e) {
                        options.setTag(toolKey.getName(), "ERROR");
                    }
                });
        }
    }

    private void addBinaryVersion(@NotNull final ToolKey toolKey, @NotNull final SentryEvent event) {
        @Nullable final String toolPath = toolKey.getPath();

        if(!StringUtil.isEmptyOrSpaces(toolPath)) {
            final GeneralCommandLine cmd = new GeneralCommandLine(toolPath, "--version")
                .withCharset(StandardCharsets.US_ASCII)
                .withRedirectErrorStream(true);

            try {
                final String stdout = new CapturingProcessHandler(
                    cmd.createProcess(),
                    cmd.getCharset(),
                    cmd.getCommandLineString()
                ).runProcess().getStdout();

                if (StringUtil.isNotEmpty(stdout)) {
                    final String version = stdout.split("\n")[0].trim();
                    event.setExtra(toolKey.getName(), version);
                    //event.setTag(toolKey.getName(), version);
                }
            } catch (ExecutionException e) {
                event.setExtra(toolKey.getName(), "ERROR");
//                        event.setTag(toolKey.getName(), "ERROR");
            }

//            ApplicationManager
//                .getApplication()
//                .executeOnPooledThread(() -> {
//                    try {
//                        final String stdout = new CapturingProcessHandler(
//                            cmd.createProcess(),
//                            cmd.getCharset(),
//                            cmd.getCommandLineString()
//                        ).runProcess().getStdout();
//
//                        if (StringUtil.isNotEmpty(stdout)) {
//                            final String version = stdout.split("\n")[0].trim();
//                            event.setExtra(toolKey.getName(), version);
//                            //event.setTag(toolKey.getName(), version);
//                        }
//                    } catch (ExecutionException e) {
//                        event.setExtra(toolKey.getName(), "ERROR");
////                        event.setTag(toolKey.getName(), "ERROR");
//                    }
//                });
        }
    }
}
