package io.github.intellij.dlanguage.codeinsight.dcd;

import com.google.common.collect.Maps;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.configurations.ParametersList;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.psi.PsiFile;
import io.github.intellij.dlanguage.codeinsight.dcd.completions.TextCompletion;
import io.github.intellij.dlanguage.settings.ToolKey;
import io.github.intellij.dlanguage.utils.DUtil;
import io.github.intellij.dlanguage.utils.ExecUtil;
import io.github.intellij.dlanguage.codeinsight.dcd.completions.Completion;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class DCDCompletionClient {

    private final Map<String, String> completionTypeMap = getCompletionTypeMap();
    private final List<Completion> completions = new ArrayList<>();
    @Nullable
    private Process process;
    @Nullable
    private BufferedWriter output;

    public List<Completion> autoComplete(final int position, final PsiFile file) throws DCDCompletionServer.DCDError {
        final Module module = ModuleUtilCore.findModuleForPsiElement(file);

        completions.clear();
        if (module != null) {
            final String path = lookupPath();
            if (path != null) {
                final DCDCompletionServer dcdCompletionServer = module.getComponent(DCDCompletionServer.class);
                try {
                    dcdCompletionServer.exec();
                } catch (final DCDCompletionServer.DCDError dcdError) {
                    dcdError.printStackTrace();
                }
//                System.out.println("position: " + String.valueOf(position));
                final String workingDirectory = file.getProject().getBasePath();

                final GeneralCommandLine commandLine = new GeneralCommandLine();
                commandLine.setWorkDirectory(workingDirectory);
                commandLine.setExePath(path);
                final ParametersList parametersList = commandLine.getParametersList();
                parametersList.addParametersString("-c");
                parametersList.addParametersString(String.valueOf(position));

                final String flags = ToolKey.DCD_CLIENT_KEY.getFlags();

                if (DUtil.isNotNullOrEmpty(flags)) {
                    final String[] importList = flags.split(",");
                    for (int i = 0; i < importList.length; i++) {
                        parametersList.addParametersString("-I");
                        parametersList.addParametersString(importList[i]);
                    }
                }

                try {
                    if(process == null) {
                        process = commandLine.createProcess();
                        output = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
                        output.write(file.getText());
                    }
                } catch (final Exception e) {
                    e.printStackTrace();
                }

                try {
                    final String result = Objects.requireNonNull(ExecUtil.readCommandLine(commandLine, file.getText()))
                        .get(3, TimeUnit.SECONDS);

                    if (result != null && !result.isEmpty()) {
                        final String[] tokens = result.split("\\n");
                        final String firstLine = tokens[0];
                        if (firstLine.contains("identifiers")) {
                            for (int i = 0; i < tokens.length; i++) {
                                final String token = tokens[i];

                                if (!token.contains("identifiers")) {
                                    final String[] parts = token.split("\\s");
                                    final String completionType = getCompletionType(parts);
                                    final String completionText = getCompletionText(parts);
                                    final Completion completion = new TextCompletion(completionType, completionText);
                                    completions.add(completion);
                                }
                            }
                        } else if (firstLine.contains("calltips")) {
                            //TODO - this goes in a Parameter Info handler (ctrl+p) instead of here - see: ShowParameterInfoHandler.register
                            System.out.println(tokens);
                        }
                    }
                } catch (InterruptedException | TimeoutException | ExecutionException e) {
                    e.printStackTrace();
                }

//                kill();
            }
        }

        return completions;
    }

    @Nullable
    private String lookupPath() {
        return ToolKey.DCD_CLIENT_KEY.getPath();
    }

    private String getType(final String[] parts) {
        final String type = parts[parts.length - 1];
        return type.isEmpty() ? "U" : type.trim();
    }

    private String getCompletionType(final String[] parts) {
        final String mapping = completionTypeMap.get(getType(parts));
        return mapping == null ? "Unknown" : mapping;
    }

    private String getCompletionText(final String[] parts) {
        final String text = parts[0];
        final String result = text.isEmpty() ? "" : text.trim();
        final String type = getType(parts);
        if (type.equals("f")) {
            return result + "()";
        }
        return result;
    }

    private Map<String, String> getCompletionTypeMap() {
        final Map<String, String> map = Maps.newTreeMap();
        map.put("c", "Class");
        map.put("i", "Interface");
        map.put("s", "Struct");
        map.put("u", "Union");
        map.put("v", "Variable");
        map.put("m", "Variable");
        map.put("k", "Keyword");
        map.put("f", "Function");
        map.put("g", "Enum");
        map.put("e", "Enum");
        map.put("P", "Package");
        map.put("M", "Module");
        map.put("a", "Array");
        map.put("A", "Map");
        map.put("l", "Alias");
        map.put("t", "Template");
        map.put("T", "Mixin");
        map.put("U", "Unknown");
        return map;
    }

    /**
     * Kills the existing process and closes input and output if they exist.
     */
//    private synchronized void kill() {
//        if (process != null) process.destroy();
//        process = null;
//        try {
//            if (output != null) output.close();
//        } catch (final IOException e) { /* Ignored */ }
//        output = null;
//    }

}
