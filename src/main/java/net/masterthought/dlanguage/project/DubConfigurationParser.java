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
import java.util.*;
import java.util.stream.Collectors;

public class DubConfigurationParser {

    private static final Logger LOG = Logger.getInstance(DubConfigurationParser.class);

    private List<DubPackage> packages = new ArrayList<>();
    private final Project project;
    private final String dubBinaryPath;

    public DubConfigurationParser(Project project, String dubBinaryPath) {
        this.project = project;
        this.dubBinaryPath = dubBinaryPath;
        parseDubConfiguration()
            .ifPresent(this::parseDubDescription);
    }

    // todo: make this return Optional<DubPackage>
    public Optional<DubPackage> getDubPackage() {
        return packages.stream()
            .filter(DubPackage::isRootPackage)
            .findFirst();
    }

    /**
     *
     * @return a list of DubPackage that the root DubPackage depends on. These may be sub-packages, libs, or other dub packages
     */
    public List<DubPackage> getDubPackageDependencies() {
        return packages.stream()
            .filter(dubPackage -> !dubPackage.isRootPackage())
            .collect(Collectors.toList());
    }

    private void parseDubDescription(final JsonObject dubProjectDescription) {
        if(dubProjectDescription == null) {
            return;
        }

        final JsonArray packages = dubProjectDescription.get("packages").getAsJsonArray();
        final String rootPackage = dubProjectDescription.get("rootPackage").getAsString();

        final Gson gson = new Gson();
        final Type listString = new TypeToken<List<String>>() {}.getType();

        final List<DubPackage> packageList = new ArrayList<>(packages.size());
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
        this.packages = packageList;
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
