package io.github.intellij.dlanguage.project;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectUtil;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import io.github.intellij.dlanguage.tools.dub.DescribeParser;
import io.github.intellij.dlanguage.tools.dub.DescribeParserException;
import io.github.intellij.dlanguage.tools.dub.DescribeParserImpl;
import io.github.intellij.dlanguage.tools.dub.DubProcessListener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import java.util.*;

/**
 * This class is used to run 'dub describe' which outputs project info in json format. We parse the json
 * to gather up information about the projects dependencies
 */
public class DubConfigurationParser {

    private static final Logger LOG = Logger.getInstance(DubConfigurationParser.class);

    private final DescribeParser parser;
    private final Project project;
    private final String dubBinaryPath;

    private DubProject dubProject;


    /**
     * DO NOT REMOVE - This is required for backward compatibility
     * @param project A valid D project
     * @param dubBinaryPath The location of the dub binary
     */
    public DubConfigurationParser(@NotNull final Project project, final String dubBinaryPath) {
        this(project, dubBinaryPath, false);
    }

    /**
     * @param project A valid D project
     * @param dubBinaryPath The location of the dub binary
     * @param silentMode When set to true notifications will not show in the UI
     * @since v1.16
     */
    public DubConfigurationParser(@NotNull final Project project, final String dubBinaryPath, final boolean silentMode) {
        this.project = project;
        this.dubBinaryPath = dubBinaryPath;
        this.parser = new DescribeParserImpl();

        if (canUseDub()) {
            parseDubConfiguration(silentMode)
                .ifPresent(p -> this.dubProject = p);
        }
    }

    /**
     *
     * @return a {@link io.github.intellij.dlanguage.project.DubProject} is a data class containing lots of useful details returned from 'dub describe'
     *
     * @since v1.16.2
     */
    public Optional<DubProject> getDubProject() {
        return this.dubProject != null? Optional.of(this.dubProject) : Optional.empty();
    }

    /**
     * This method is now Deprecated as it makes more sense to users of this class to use getDubProject()
     * @return the root DubPackage for this project
     */
    @Deprecated
    public Optional<DubPackage> getDubPackage() {
        return this.dubProject != null? Optional.of(this.dubProject.getRootPackage()) : Optional.empty();
    }

    /**
     * @return true if a path to a dub binary is configured and the project has a dub.sdl or dub.json
     */
    public boolean canUseDub() {
        // For wsl, dub command can have any file name not only dub/dub.exe
        final boolean dubPathValid = StringUtil.isNotEmpty(this.dubBinaryPath);

        @Nullable final VirtualFile baseDir = ProjectUtil.guessProjectDir(project);

        if(baseDir == null) {
            return false; // can't use dub without knowing base path
        }

        baseDir.refresh(true, true);

        return dubPathValid && Arrays.stream(baseDir.getChildren())
            .filter(f -> !f.isDirectory())
            .anyMatch(file -> "dub.json".equalsIgnoreCase(file.getName()) || "dub.sdl".equalsIgnoreCase(file.getName()));
    }

    /**
     * This method is now Deprecated as it makes more sense to users of this class to use getDubProject()
     * @return a list of DubPackage that the root DubPackage depends on. These may be sub-packages, libs, or other dub packages
     */
    @Deprecated
    public List<DubPackage> getDubPackageDependencies() {
        return this.dubProject != null? dubProject.getPackages() : Collections.EMPTY_LIST;
    }

    /**
     * lazilly evaluated. This isn't actually used yet so is open for changes. Plan is to use it from DUB plugin
     * @return A Tree for use in Swing UI components
     */
    @Nullable
    public TreeNode getPackageTree() {
        return this.dubProject != null ? buildDependencyTree(dubProject.getRootPackage()) : null;
    }

    private DefaultMutableTreeNode buildDependencyTree(final DubPackage dubPackage) {
        final DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode(dubPackage, !dubPackage.getDependencies().isEmpty());

        getDubPackageDependencies().stream()
            .filter(d -> dubPackage.getDependencies().contains(d.getName()))
            .forEach(dependency -> treeNode.add(buildDependencyTree(dependency)));

        return treeNode;
    }

    private Optional<DubProject> parseDubConfiguration(final boolean silentMode) {
        @Nullable final VirtualFile projectDir = ProjectUtil.guessProjectDir(project);

        if(projectDir == null) {
            return Optional.empty();
        }

        try {
            final GeneralCommandLine cmd = new GeneralCommandLine()
                .withWorkDirectory(projectDir.getCanonicalPath())
                .withExePath(dubBinaryPath)
                .withParameters("describe");

            final String dubCommand = cmd.getCommandLineString();
            final DubProcessListener listener = new DubProcessListener();

            final OSProcessHandler process = new OSProcessHandler(cmd.createProcess(), dubCommand);
            process.addProcessListener(listener);
            process.startNotify();
            process.waitFor();

            final Integer exitCode = process.getExitCode();

            final List<String> errors = listener.getErrors();

            if (exitCode != null && exitCode == 0) {
                if (errors.isEmpty()) {
                    LOG.info(String.format("%s exited without errors", dubCommand));
                    if (LOG.isDebugEnabled()) {
                        Notifications.Bus.notify(new Notification(
                                "DubNotification",
                                "DUB Import",
                                "dub project imported without errors",
                                NotificationType.INFORMATION
                            ),
                            this.project);
                    }
                } else {
                    if (!silentMode) {
                        LOG.warn(
                            String.format("%s exited with %s errors", dubCommand, errors.size()));
                    } else {
                        LOG.info(
                            String.format("%s exited with %s errors", dubCommand, errors.size()));
                    }
                    // potential error messages are things like:
                    //   "No valid root package found - aborting."
                    //   "Package vibe-d declared a sub-package, definition file is missing: /path/to/package"
                    //   "Non-optional dependency vibe-d:core of vibe-d not found in dependency tree!?."
                    if (!silentMode) {
                        errors.forEach(errorMessage -> Notifications.Bus.notify(
                            new Notification(
                                "DubNotification",
                                "DUB Import Error",
                                errorMessage,
                                NotificationType.WARNING
                            ),
                            this.project)
                        );
                    }
                }
                return Optional.of(parser.parse(listener.getStdOut()));
            } else {
                errors.forEach(LOG::warn);
                LOG.warn(String.format("%s exited with %s", dubCommand, exitCode));
                if (!silentMode) {
                    SwingUtilities.invokeLater(() -> Messages.showErrorDialog(project,
                        String.format("%s exited with %s", dubCommand, exitCode), "Dub Import"));
                }
            }
        } catch (ExecutionException | DescribeParserException e) {
            LOG.error("Unable to parse dub configuration", e);
            e.printStackTrace();
        }
        return Optional.empty();
    }


}
