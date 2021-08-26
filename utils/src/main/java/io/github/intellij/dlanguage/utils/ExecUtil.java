package io.github.intellij.dlanguage.utils;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.CapturingProcessHandler;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.project.ProjectUtil;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Helper class to perform execution related tasks, including locating programs.
 *
 * much of this class has been around since 2015 and is in need of an overhaul. some methods are now
 * deprecated as ideally parts of the code that need to use command line processes need to make use of
 * {@link com.intellij.openapi.application.ReadAction} and {@link com.intellij.openapi.application.WriteAction} or
 * simply running on a pooled thread depending on the context of what needs to be done, so this general purpose
 * utility class is causing more problems these days than it solves.
 * See: https://github.com/intellij-dlanguage/intellij-dlanguage/issues/550
 * and https://github.com/intellij-dlanguage/intellij-dlanguage/issues/591
 */
@Deprecated
public class ExecUtil {

    // Messages go to the log available in Help -> Show log in finder.
    private final static Logger LOG = Logger.getInstance(ExecUtil.class);

    /**
     * Execute a command using the default shell.
     * @param command the command to be run
     * @return the output of the command to be run or null
     * @deprecated only used by GuiUtil calling to locateExecutableByGuessing() so no need fo all these layers of
     * abstraction. Can probably push everything up to the class that actually needs to locate D Tool binaries
     */
    @Nullable
    public static String exec(@NotNull final String command) {
        // Find some valid working directory, doesn't matter which one.
        final ProjectManager pm = ProjectManager.getInstance();
        final Project[] projects = pm == null ? null : pm.getOpenProjects();
        final String defaultWorkDir = ".";
        final String workDir;

        // Set the working directory if there is an open project.
        if (pm == null || projects.length == 0) {
            LOG.info("No open projects so cannot find a valid path. Using '.'.");
            workDir = defaultWorkDir;
        } else {
            @Nullable final VirtualFile projectDir = ProjectUtil.guessProjectDir(projects[0]);

            if (projectDir == null) {
                workDir = defaultWorkDir;//getBaseDir returns null for default project
            } else {
                workDir = projectDir.getCanonicalPath();
            }
        }
        return exec(workDir == null ? defaultWorkDir : workDir, command);
    }

    /**
     * Execute a command using the default shell in a given work directory.
     */
    @Nullable
    public static String exec(@NotNull final String workDir, @NotNull final String command) {
        // Setup shell and the GeneralCommandLine.
        final GeneralCommandLine cmd = new GeneralCommandLine()
            .withWorkDirectory(workDir)
            .withParentEnvironmentType(GeneralCommandLine.ParentEnvironmentType.CONSOLE);

        if (SystemInfo.isWindows) {
            cmd.setExePath("cmd");
            cmd.addParameter("/c");
        } else {
            // Default to UNIX if not Windows.
            cmd.setExePath("/bin/sh");
            cmd.addParameter("-c");
        }
        cmd.addParameter(command);

        return readCommandLine(cmd);
    }

    /**
     * This should not be here long term. It's mostly here just so that it's clear that calls will to whichever
     * command line tool are being made have not been done in a way that makes use of the various Intellij platforms
     * methods of dealing with external tool.
     *
     * Calls to external command line calls should be making use of actions such as:
     *     ReadAction.compute()
     *     ReadAction.nonBlocking()
     *     WriteAction.compute()
     *     WriteAction.computeAndWait()
     * or creating a background task with
     *     Task.Backgroundable task = new Task.Backgroundable()
     *
     * Further refactoring will be needed to make this possible. It's probably worth having this done on a
     * case by case basis in the various classes that need to run a commandline process and remove ExecUtil alltogether.
     *
     * This method is called in various places such as running 'dub --version' etc.
     * @param commandLine The command to be run
     * @param input potential args that are required
     * @return the output of the command as a string or null
     */
    @Deprecated
    @Nullable
    public static String blocking(@NotNull final GeneralCommandLine commandLine, @Nullable final String input) {
        try {
            final Process process = commandLine.createProcess();

            if (StringUtil.isNotEmpty(input)) {
                try (final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()))) {
                    writer.write(input);
                    writer.flush();
                }
            }

            final Future<String> future = ApplicationManager
                .getApplication()
                .executeOnPooledThread(() -> new CapturingProcessHandler(process, Charset.defaultCharset(), commandLine.getCommandLineString())
                    .runProcess()
                    .getStdout());

            return future.get(2L, TimeUnit.SECONDS);
        } catch (final IOException | ExecutionException | InterruptedException | java.util.concurrent.ExecutionException | TimeoutException e) {
            LOG.error(e);
        }
        return null;
    }

    /**
     * Tries to get the absolute path for a command in the PATH.
     */
    @Nullable
    public static String locateExecutable(@NotNull final String command) {
        final String whereCmd = (SystemInfo.isWindows ? "where" : "which") + ' ' + command;
        String res = exec(whereCmd);
        if (res != null && SystemInfo.isWindows && res.contains("C:\\")) {
            final String[] split = res.split("(?=C:\\\\)");
            res = split[0];//if there are multiple results defualt to first one.
        }
        if (res != null && res.isEmpty()) {
            LOG.info("Could not find " + command);
        }
        return res;
    }

    /**
     * Tries to get the absolute path for a command by defaulting to first trying to locate the
     * command in path, and falling back to trying likely directories.
     */
    @Nullable
    public static String locateExecutableByGuessing(@NotNull final String command) {
        final String located = locateExecutable(command);
        if (located != null && !located.isEmpty()) {
            // Found it!
            return StringUtil.trim(located);
        }

        final char sep = File.separatorChar;
        final List<String> paths = new ArrayList<>();
        if (SystemInfo.isWindows) {
            paths.add(sep + "D" + sep + "dmd2" + sep + "windows" + sep + "bin");
        } else {
            final String homeDir = System.getProperty("user.home");
            paths.add(sep + "usr" + sep + "local" + sep + "bin");
            paths.add(sep + "usr" + sep + "bin");
            paths.add(homeDir + sep + "bin");
            paths.add(sep + "snap" + sep + command + sep + "current"); // #575 support snaps
        }
        for (final String path : paths) {
            LOG.info(String.format("Looking for %s in %s", command, path));
            final String cmd = StringUtil.trim(path + sep + command);
            //noinspection ObjectAllocationInLoop
            if (new File(cmd).canExecute()) {
                return cmd;
            }
        }
        return null;
    }

    @NotNull
    public static String guessWorkDir(@NotNull final Project project,
        @NotNull final VirtualFile file) {
        final Module module = ModuleUtilCore.findModuleForFile(file, project);
        return module == null ? project.getBasePath() : guessWorkDir(module);
    }

    @NotNull
    public static String guessWorkDir(@NotNull final PsiFile file) {
        return guessWorkDir(file.getProject(), file.getVirtualFile());
    }

    @NotNull
    public static String guessWorkDir(@NotNull final Module module) {
        final VirtualFile moduleFile = module.getModuleFile();
        final VirtualFile moduleDir = moduleFile == null ? null : moduleFile.getParent();
        return moduleDir == null ? module.getProject().getBasePath() : moduleDir.getPath();
    }

    /**
     * Executes commandLine, optionally piping input to stdin, and return stdout.
     */
    @Nullable
    public static Future<String> readCommandLine(@NotNull final GeneralCommandLine commandLine, @Nullable final String input) {
        return ApplicationManager.getApplication().executeOnPooledThread(() -> {
            final Process process = commandLine.createProcess();

            if (input != null) {
                try (final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()))) {
                    writer.write(input);
                    writer.flush();
                }
            }

            return new CapturingProcessHandler(process,
                Charset.defaultCharset(),
                commandLine.getCommandLineString()
            ).runProcess().getStdout();
        });
    }

    @Nullable
    public static String readCommandLine(@NotNull final GeneralCommandLine commandLine) {
        try {
            return Objects.requireNonNull(readCommandLine(commandLine, null)).get();
        } catch (InterruptedException | java.util.concurrent.ExecutionException e) {
            LOG.error("Could not exec command", e);
        }
        return null;
    }

    @Nullable
    public static String readCommandLine(@Nullable final String workingDirectory,
                                         @NotNull final String command,
                                         @NotNull final String[] params,
                                         @Nullable final String input) {
        final GeneralCommandLine commandLine = new GeneralCommandLine(command);
        if (workingDirectory != null) {
            commandLine.setWorkDirectory(workingDirectory);
        }
        commandLine.addParameters(params);

        try {
            return Objects.requireNonNull(readCommandLine(commandLine, input)).get();
        } catch (InterruptedException | java.util.concurrent.ExecutionException e) {
            LOG.error("Could not exec command", e);
        }
        return null;
    }

    @Nullable
    public static String readCommandLine(@Nullable final String workingDirectory,
        @NotNull final String command, @NotNull final String... params) {
        return readCommandLine(workingDirectory, command, params, null);
    }

    /**
     * Heuristically finds the version number. Current implementation is the identity function since
     * cabal plays nice.
     */
    public static String getVersion(final String cmd) {
        final @Nullable String versionOutput = readCommandLine(null, cmd, "--version");

        if (StringUtil.isNotEmpty(versionOutput)) {
            final String version = versionOutput.split("\n")[0].trim();
            LOG.debug(String.format("%s [%s]", cmd, version));
            return version;
        }
        return "";
    }
}
