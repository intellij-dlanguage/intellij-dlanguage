package io.github.intellij.dlanguage.run;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.ExecutionResult;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.CommandLineState;
import com.intellij.execution.configurations.CommandLineTokenizer;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.filters.TextConsoleBuilder;
import com.intellij.execution.filters.TextConsoleBuilderImpl;
import com.intellij.execution.process.ColoredProcessHandler;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.runners.ProgramRunner;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.roots.CompilerModuleExtension;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VfsUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import io.github.intellij.dlanguage.DlangSdkType;
import io.github.intellij.dlanguage.run.exception.ModuleNotFoundException;
import io.github.intellij.dlanguage.run.exception.NoValidDlangSdkFound;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.LinkedList;
import java.util.List;


public class DlangRunAppState extends CommandLineState {
    private final DlangRunAppConfiguration config;
    private Executor executor;

    protected DlangRunAppState(@NotNull final ExecutionEnvironment environment, @NotNull final DlangRunAppConfiguration config) {
        super(environment);
        this.config = config;
    }

    @NotNull
    @Override
    public ExecutionResult execute(@NotNull final Executor executor, @NotNull final ProgramRunner runner) throws ExecutionException {
        final TextConsoleBuilder consoleBuilder = new TextConsoleBuilderImpl(config.getProject());
        setConsoleBuilder(consoleBuilder);
        this.executor = executor;
        return super.execute(executor, runner);
    }

    @NotNull
    @Override
    public ProcessHandler startProcess() throws ExecutionException {
        try {
            final GeneralCommandLine appCommandLine = getExecutableCommandLine(config);
            return new ColoredProcessHandler(appCommandLine.createProcess(), appCommandLine.getCommandLineString());
        } catch (final NoValidDlangSdkFound e) {
            throw new ExecutionException("No valid DMD SDK found!");
        } catch (final ModuleNotFoundException e) {
            throw new ExecutionException("Run configuration has no module selected.");
        } catch (final ExecutionException e) {
            final String message = e.getMessage();
            final boolean isEmpty = message.equals("Executable is not specified");
            final boolean notCorrect = message.startsWith("Cannot run program");

            if (isEmpty || notCorrect) {
                Notifications.Bus.notify(
                    new Notification("D App run configuration", "D App settings",
                        "D application executable path is " + (isEmpty ? "empty" : "not specified correctly") +
                            "<br/><a href='configure'>Configure</a> output folder",
                        NotificationType.ERROR), config.getProject());
            }
            throw e;
        }
    }

    /* Build command line to start compiled executable
     **/
    public GeneralCommandLine getExecutableCommandLine(final DlangRunAppConfiguration config)
        throws ModuleNotFoundException, NoValidDlangSdkFound {
        final Module module = config.getConfigurationModule().getModule();
        if (module == null) {
            throw new ModuleNotFoundException();
        }

        final ModuleRootManager moduleRootManager = ModuleRootManager.getInstance(module);
        final Sdk sdk = moduleRootManager.getSdk();
        if (sdk == null || !(sdk.getSdkType() instanceof DlangSdkType)) {
            throw new NoValidDlangSdkFound();
        }

        final VirtualFile sourcesRoot = getSourceRoot(module);
        final GeneralCommandLine cmd = new GeneralCommandLine();
        cmd.setExePath(getOutputFilePath(module));

        if (StringUtil.isNotEmpty(config.getWorkDir())) {
            cmd.withWorkDirectory(config.getWorkDir());
        } else {
            cmd.withWorkDirectory(sourcesRoot.getPath());
        }

        if (StringUtil.isNotEmpty(config.getAdditionalParams())) {
            cmd.addParameters(splitArguments(config.getAdditionalParams()));
        }

        return cmd;
    }

    @NotNull
    private String[] splitArguments(@Nullable final String arguments) {
        if(arguments != null) {
            final List<String> argsLst = new LinkedList<>();
            final CommandLineTokenizer tokenizer = new CommandLineTokenizer(arguments);
            while (tokenizer.hasMoreTokens()) {
                argsLst.add(tokenizer.nextToken());
            }
            if (argsLst.size() > 0) {
                return (String[]) argsLst.toArray();
            }
        }
        return new String[0];
    }

    @Nullable
    private VirtualFile getSourceRoot(@Nullable final Module module) {
        if (module != null) {
            final VirtualFile[] sourcesRoots = ModuleRootManager.getInstance(module).getSourceRoots();
            if (sourcesRoots.length >= 1) {
                return sourcesRoots[0];
            }
        }
        return null;
    }

    @NotNull
    private String getOutputFilePath(final Module module) {
        String filename = module.getName();
        if (SystemInfo.isWindows) {
            filename += ".exe";
        }
        final String outputDirUrl = getOutputDir(module);
        final File outputFile = new File(VfsUtilCore.urlToPath(outputDirUrl), filename);
        return outputFile.getPath();
    }

    @Nullable
    private String getOutputDir(@NotNull final Module module) {
        return ModuleRootManager.getInstance(module)
                        .getModuleExtension(CompilerModuleExtension.class)
                        .getCompilerOutputUrl();
    }

    public DlangRunAppConfiguration getConfig() {
        return config;
    }
}
