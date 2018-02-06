package io.github.intellij.dlanguage.project;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import io.github.intellij.dlanguage.tools.dub.DubProcessListener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * This class is used to run 'dub describe' which outputs project info in json format. We parse the json
 * to gather up information about the projects dependencies
 */
public class DubConfigurationParser {

    private static final Logger LOG = Logger.getInstance(DubConfigurationParser.class);

    private static final Gson GSON = new Gson();
    private static final Type LIST_STRING = new TypeToken<List<String>>() {
    }.getType();
    private final Project project;
    private final String dubBinaryPath;
    private List<DubPackage> packages = new ArrayList<>();
    //    private Map<String, List<String>> targets = new HashMap<>();
    private TreeNode packageTree;


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

        if (canUseDub()) {
            parseDubConfiguration(silentMode).ifPresent(this::parseDubDescription);
        }
    }

    public Optional<DubPackage> getDubPackage() {
        return packages.stream()
            .filter(DubPackage::isRootPackage)
            .findFirst();
    }

    public boolean canUseDub() {
        final boolean dubPathValid = StringUtil.isNotEmpty(this.dubBinaryPath) && (dubBinaryPath.endsWith("dub") || dubBinaryPath.endsWith("dub.exe"));

        final VirtualFile baseDir = project.getBaseDir();
        baseDir.refresh(true, true);

        return dubPathValid && Arrays.stream(baseDir.getChildren())
            .filter(f -> !f.isDirectory())
            .anyMatch(file -> "dub.json".equalsIgnoreCase(file.getName()) || "dub.sdl".equalsIgnoreCase(file.getName()));
    }

    /**
     * @return a list of DubPackage that the root DubPackage depends on. These may be sub-packages, libs, or other dub packages
     */
    public List<DubPackage> getDubPackageDependencies() {
        return packages.stream()
            .filter(dubPackage -> !dubPackage.isRootPackage())
            .collect(Collectors.toList());
    }

//    public Map<String, List<String>> getTargets() {
//        return targets;
//    }

    @Nullable
    public TreeNode getPackageTree() {
        return packageTree;
    }

    private void parseDubDescription(final JsonObject dubProjectDescription) {
        if (dubProjectDescription == null) {
            return;
        }

        final String rootPackageName = dubProjectDescription.get("rootPackage").getAsString();
        final JsonArray packages = dubProjectDescription.get("packages").getAsJsonArray();
        this.packages = parsePackages(rootPackageName, packages);

        packageTree = getDubPackage().map(this::buildDependencyTree).orElse(null);

//        final JsonArray targetsJson = dubProjectDescription.get("targets").getAsJsonArray();
//        this.targets = parseTargets(targetsJson);
    }

    private DefaultMutableTreeNode buildDependencyTree(final DubPackage dubPackage) {
        final DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode(dubPackage, !dubPackage.getDependencies().isEmpty());

        getDubPackageDependencies().stream()
            .filter(d -> dubPackage.getDependencies().contains(d.getName()))
            .forEach(dependency -> treeNode.add(buildDependencyTree(dependency)));

        return treeNode;
    }

    private List<DubPackage> parsePackages(final String rootPackageName, final JsonArray packages) {
        final List<DubPackage> packageList = new ArrayList<>(packages.size());

        for (final JsonElement pkg : packages) {
            final JsonObject thePackage = ((JsonObject) pkg);
            final String path = thePackage.get("path").getAsString();
            final String name = thePackage.get("name").getAsString();
            final List<String> dependencies = GSON.fromJson(thePackage.get("dependencies").getAsJsonArray(), LIST_STRING);
            final String version = thePackage.get("version").getAsString();
            final JsonArray importPaths = thePackage.get("importPaths").getAsJsonArray();
            final String sourcesDir = importPaths.size() > 0 ? importPaths.get(0).getAsString() : "source";

            final List<String> stringImportPaths = GSON.fromJson(thePackage.get("stringImportPaths").getAsJsonArray(), LIST_STRING); // eg: "views"
            packageList.add(new DubPackage(name, path, dependencies, sourcesDir, stringImportPaths, version, name.equals(rootPackageName)));
        }
        return packageList;
    }

//    private Map<String, List<String>> parseTargets(final JsonArray targetsJson) {
//        final Map<String, List<String>> targets = new HashMap<>(targetsJson.size());
//        for (final JsonElement targetJson : targetsJson) {
//            final JsonObject targetObj = ((JsonObject) targetJson);
//
//            final String targetRootPackageName = targetObj.get("rootPackage").getAsString();
//            final List<String> targetPackages = GSON.fromJson(targetObj.get("packages").getAsJsonArray(), LIST_STRING);
//            final List<String> targetDependencies = GSON.fromJson(targetObj.get("dependencies").getAsJsonArray(), LIST_STRING);
//            final List<String> targetLinkDependencies = GSON.fromJson(targetObj.get("linkDependencies").getAsJsonArray(), LIST_STRING);
//
//            targets.put(targetRootPackageName, targetLinkDependencies);
//        }
//        return targets;
//    }

    private Optional<JsonObject> parseDubConfiguration() {
        return parseDubConfiguration(false);
    }

    private Optional<JsonObject> parseDubConfiguration(final boolean silentMode) {
        try {
            final GeneralCommandLine cmd = new GeneralCommandLine()
                .withWorkDirectory(project.getBaseDir().getCanonicalPath())
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
                final JsonObject jsonObject = new JsonParser()
                    .parse(listener.getStdOut())
                    .getAsJsonObject();
                return Optional.of(jsonObject);
            } else {
                errors.forEach(LOG::warn);
                LOG.warn(String.format("%s exited with %s", dubCommand, exitCode));
                if (!silentMode) {
                    SwingUtilities.invokeLater(() -> Messages.showErrorDialog(project,
                        String.format("%s exited with %s", dubCommand, exitCode), "Dub Import"));
                }
            }
        } catch (ExecutionException | JsonSyntaxException e) {
            LOG.error("Unable to parse dub configuration", e);
            e.printStackTrace();
        }
        return Optional.empty();
    }


}
