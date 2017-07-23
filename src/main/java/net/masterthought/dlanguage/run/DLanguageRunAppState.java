package net.masterthought.dlanguage.run;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.ExecutionResult;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.CommandLineState;
import com.intellij.execution.configurations.CommandLineTokenizer;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.filters.TextConsoleBuilder;
import com.intellij.execution.filters.TextConsoleBuilderImpl;
import com.intellij.execution.process.OSProcessHandler;
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
import net.masterthought.dlanguage.DLanguageSdkType;
import net.masterthought.dlanguage.run.exception.ModuleNotFoundException;
import net.masterthought.dlanguage.run.exception.NoValidDLanguageSdkFound;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.LinkedList;
import java.util.List;


public class DLanguageRunAppState extends CommandLineState {
    private final DLanguageRunAppConfiguration config;
    private Executor executor;

    protected DLanguageRunAppState(@NotNull ExecutionEnvironment environment, @NotNull DLanguageRunAppConfiguration config) {
        super(environment);
        this.config = config;
    }

    @NotNull
    @Override
    public ExecutionResult execute(@NotNull Executor executor, @NotNull ProgramRunner runner) throws ExecutionException {
        TextConsoleBuilder consoleBuilder = new TextConsoleBuilderImpl(config.getProject());
        setConsoleBuilder(consoleBuilder);
        this.executor = executor;
        return super.execute(executor, runner);
    }

    @NotNull
    @Override
    protected ProcessHandler startProcess() throws ExecutionException {
        try {
            GeneralCommandLine appCommandLine = getExecutableCommandLine(config);
            return new OSProcessHandler(appCommandLine.createProcess(), appCommandLine.getCommandLineString());
        } catch (NoValidDLanguageSdkFound e) {
            throw new ExecutionException("No valid DMD SDK found!");
        } catch (ModuleNotFoundException e) {
            throw new ExecutionException("Run configuration has no module selected.");
        } catch (ExecutionException e) {
            String message = e.getMessage();
            boolean isEmpty = message.equals("Executable is not specified");
            boolean notCorrect = message.startsWith("Cannot run program");
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
    private GeneralCommandLine getExecutableCommandLine(DLanguageRunAppConfiguration config)
        throws ModuleNotFoundException, NoValidDLanguageSdkFound {
        Module module = config.getConfigurationModule().getModule();
        if (module == null) {
            throw new ModuleNotFoundException();
        }

        ModuleRootManager moduleRootManager = ModuleRootManager.getInstance(module);
        Sdk sdk = moduleRootManager.getSdk();
        if (sdk == null || !(sdk.getSdkType() instanceof DLanguageSdkType)) {
            throw new NoValidDLanguageSdkFound();
        }

        VirtualFile sourcesRoot = getSourceRoot(module);
        GeneralCommandLine commandLine = new GeneralCommandLine();
        commandLine.setExePath(getOutputFilePath(module));

        if (StringUtil.isEmptyOrSpaces(config.getWorkDir())) {
            commandLine.withWorkDirectory(config.getWorkDir());
        } else {
            commandLine.withWorkDirectory(sourcesRoot.getPath());
        }

        if (StringUtil.isEmptyOrSpaces(config.getAdditionalParams())) {
            commandLine.addParameters(splitArguments(config.getAdditionalParams()));
        }

        return commandLine;
    }

    private String[] splitArguments(String arguments) {
        List<String> argsLst = new LinkedList<String>();
        CommandLineTokenizer tokenizer = new CommandLineTokenizer(arguments);
        while (tokenizer.hasMoreTokens()) {
            argsLst.add(tokenizer.nextToken());
        }
        if (argsLst.size() > 0) {
            return (String[]) argsLst.toArray();
        } else {
            return new String[0];
        }
    }

    private VirtualFile getSourceRoot(Module module) {
        if (module != null) {
            VirtualFile[] sourcesRoots = ModuleRootManager.getInstance(module).getSourceRoots();
            if (sourcesRoots.length >= 1) {
                return sourcesRoots[0];
            }
        }
        return null;
    }

    @NotNull
    private String getOutputFilePath(Module module) {
        String filename = module.getName();
        if (SystemInfo.isWindows) {
            filename += ".exe";
        }
        String outputDirUrl = getOutputDir(module);
        File outputFile = new File(VfsUtilCore.urlToPath(outputDirUrl), filename);
        return outputFile.getPath();
    }

    private String getOutputDir(Module module) {
        return ModuleRootManager.getInstance(module).getModuleExtension(CompilerModuleExtension.class).getCompilerOutputUrl();
    }
}
