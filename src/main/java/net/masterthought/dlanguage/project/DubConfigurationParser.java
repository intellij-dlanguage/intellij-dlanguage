package net.masterthought.dlanguage.project;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.configurations.ParametersList;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.process.ProcessAdapter;
import com.intellij.execution.process.ProcessEvent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.Key;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class DubConfigurationParser {

    private static final Logger LOG = Logger.getInstance(DubConfigurationParser.class);

    private JsonObject dubConfiguration;
    private final Project project;
    private final String dubBinaryPath;

    public DubConfigurationParser(Project project, String dubBinaryPath) {
        this.project = project;
        this.dubBinaryPath = dubBinaryPath;
        parseDubConfiguration()
            .ifPresent(jsonObject -> dubConfiguration = jsonObject);
    }

    public class DubPackage {
        public String name;
        public String path;
        public List<String> dependencies;
        public String sourcesDir; // todo: this should prob be String[]
        public List<String> resources;
        public String version;
        public boolean isRootPackage;

        public DubPackage(String name, String path, List<String> dependencies, String sourcesDir, List<String> resources, String version, boolean isRootPackage) {
            this.name = name;
            this.path = path;
            this.dependencies = dependencies;
            this.sourcesDir = sourcesDir;
            this.resources = resources;
            this.version = version;
            this.isRootPackage = isRootPackage;
        }
    }

    // todo: make this return Optional<DubPackage>
    public DubPackage getDubPackage() {
        for (DubPackage dubPackage : getAllPackages()) {
            if (dubPackage.isRootPackage) {
                return dubPackage;
            }
        }
        return null;
    }

    /**
     *
     * @return a list of DubPackage that the root DubPackage depends on. These may be sub-packages, libs, or other dub packages
     */
    // todo: make this return Optional<List<DubPackage>>
    public List<DubPackage> getDubPackageDependencies() {
        List<DubPackage> dependencies = new ArrayList<>();
        for (DubPackage dubPackage : getAllPackages()) {
            if (!dubPackage.isRootPackage) {
                dependencies.add(dubPackage);
            }
        }
        return dependencies;
    }

    private List<DubPackage> getAllPackages() {
        if(dubConfiguration == null) {
            return Collections.EMPTY_LIST;
        }

        final JsonArray packages = dubConfiguration.get("packages").getAsJsonArray();
        final String rootPackage = dubConfiguration.get("rootPackage").getAsString();

        final Gson gson = new Gson();
        final Type listString = new TypeToken<List<String>>() {}.getType();

        List<DubPackage> packageList = new ArrayList<>(packages.size());
        for (JsonElement pkg : packages) {
            JsonObject thePackage = ((JsonObject) pkg);
            String path = thePackage.get("path").getAsString();
            String name = thePackage.get("name").getAsString();
            final List<String> dependencies = gson.fromJson(thePackage.get("dependencies").getAsJsonArray(), listString);
            String version = thePackage.get("version").getAsString();
            String sourcesDir = "source";
            JsonArray importPaths = thePackage.get("importPaths").getAsJsonArray();
            if (importPaths.size() > 0) {
                sourcesDir = importPaths.get(0).getAsString();
            }
            final List<String> stringImportPaths = gson.fromJson(thePackage.get("stringImportPaths").getAsJsonArray(), listString); // eg: "views"
            packageList.add(new DubPackage(name, path, dependencies, sourcesDir, stringImportPaths, version, name.equals(rootPackage)));
        }
        return packageList;
    }

    private Optional<JsonObject> parseDubConfiguration() {
        try {
            final String baseDir = project.getBaseDir().getCanonicalPath();
            final GeneralCommandLine commandLine = new GeneralCommandLine();
            commandLine.setWorkDirectory(new File(baseDir));
            commandLine.setExePath(dubBinaryPath);
            final ParametersList parametersList = commandLine.getParametersList();
            parametersList.addParametersString("describe");

            final String dubCommand = commandLine.getCommandLineString();
            final OSProcessHandler process = new OSProcessHandler(commandLine.createProcess(), dubCommand);

            final StringBuilder builder = new StringBuilder();
            final List<String> errors = new ArrayList<>();

            process.addProcessListener(new ProcessAdapter() {
                @Override
                public void onTextAvailable(ProcessEvent event, Key outputType) {
                    switch (outputType.toString()) {
                        case "stdout":
                            builder.append(event.getText());
                            break;
                        case "stderr":
                            errors.add(event.getText());
                            break;
                    }
                }
            });

            process.startNotify();
            process.waitFor();

            final Integer exitCode = process.getExitCode();

            if (exitCode == 0) {
                if (errors.isEmpty()) {
                    LOG.info(String.format("%s exited without errors", dubCommand));
                    //Messages.showInfoMessage(this.project, "Dub project imported", "Dub Import");
                } else {
                    LOG.warn(String.format("%s exited with %s errors", dubCommand, errors.size()));
                    // potential error messages are things like:
                    //   "No valid root package found - aborting."
                    //   "Package vibe-d declared a sub-package, definition file is missing: /path/to/package"
                    //   "Non-optional dependency vibe-d:core of vibe-d not found in dependency tree!?."
                    // todo: do something useful with the errors
                    Messages.showWarningDialog(this.project, String.format("%s exited with %s errors", dubCommand, errors.size()), "Dub Import");
                }
                final JsonObject jsonObject = new JsonParser()
                    .parse(builder.toString())
                    .getAsJsonObject();
                return Optional.of(jsonObject);
            } else {
                LOG.error(String.format("%s exited with %s", dubCommand, exitCode));
                // todo: do something useful with the errors
                Messages.showErrorDialog(this.project, String.format("%s exited with %s", dubCommand, exitCode), "Dub Import");
            }
        } catch (ExecutionException | JsonSyntaxException e) {
            LOG.error("Unable to parse dub configuration", e);
            e.printStackTrace();
        }
        return Optional.empty();
    }


}
