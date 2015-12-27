package net.masterthought.dlanguage.codeinsight.dcd;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.configurations.ParametersList;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.psi.PsiFile;
import net.masterthought.dlanguage.codeinsight.dcd.completions.Completion;
import net.masterthought.dlanguage.codeinsight.dcd.completions.TextCompletion;
import net.masterthought.dlanguage.settings.ToolKey;
import net.masterthought.dlanguage.utils.ExecUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DCDCompletionClient {

    private Map<String, String> completionTypeMap = getCompletionTypeMap();

    private
    @Nullable
    Process process;
    private
    @Nullable
    BufferedWriter output;

    public List<Completion> autoComplete(int position, PsiFile file) throws DCDCompletionServer.DCDError {
        final List<Completion> completions = Lists.newArrayList();
        final Module module = ModuleUtilCore.findModuleForPsiElement(file);
        if (module != null) {
            String path = lookupPath(module);
            if (path != null) {
                DCDCompletionServer dcdCompletionServer = module.getComponent(DCDCompletionServer.class);
                dcdCompletionServer.exec();
//                System.out.println("position: " + String.valueOf(position));
                final String workingDirectory = file.getProject().getBasePath();

                final GeneralCommandLine commandLine = new GeneralCommandLine();
                commandLine.setWorkDirectory(workingDirectory);
                commandLine.setExePath(path);
                ParametersList parametersList = commandLine.getParametersList();
                parametersList.addParametersString("-c");
                parametersList.addParametersString(String.valueOf(position));

                String flags = ToolKey.DCD_CLIENT_KEY.getFlags(module.getProject());
                List<String> importList = Arrays.asList(flags.split(","));
                for(String item : importList){
//                  parametersList.addParametersString("-I");
                    parametersList.addParametersString("-I"+item);
                }
                
                try {
                    process = commandLine.createProcess();
                    output = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
                    output.write(file.getText());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String result = ExecUtil.readCommandLine(commandLine, file.getText());

                if (result != null && !result.isEmpty()) {
                    List<String> tokens = Arrays.asList(result.split("\\n"));
                    String firstLine = tokens.get(0);
                    if (firstLine.contains("identifiers")) {
                        for (String token : tokens) {
                            if (!token.contains("identifiers")) {
                                List<String> parts = Arrays.asList(token.split("\\s"));
                                String completionType = getCompletionType(parts);
                                String completionText = getCompletionText(parts);
                                Completion completion = new TextCompletion(completionType, completionText);
                                completions.add(completion);
                            }
                        }
                    } else if (firstLine.contains("calltips")) {
                        //TODO - this goes in a Parameter Info handler (ctrl+p) instead of here - see: ShowParameterInfoHandler.register
                        System.out.println(tokens);
                    }
                }
                kill();
            }
        }
        return completions;
    }

    @Nullable
    private String lookupPath(Module module) {
        return ToolKey.DCD_CLIENT_KEY.getPath(module.getProject());
    }

    private String getType(List<String> parts) {
        String type = parts.get(parts.size() - 1);
        return type.isEmpty() ? "U" : type.trim();
    }

    private String getCompletionType(List<String> parts) {
        String mapping = completionTypeMap.get(getType(parts));
        return mapping == null ? "Unknown" : mapping;
    }

    private String getCompletionText(List<String> parts) {
        String text = parts.get(0);
        String result = text.isEmpty() ? "" : text.trim();
        String type = getType(parts);
        if (type.equals("f")) {
            return result + "()";
        }
        return result;
    }

    private Map<String, String> getCompletionTypeMap() {
        Map<String, String> map = Maps.newTreeMap();
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
    private synchronized void kill() {
        if (process != null) process.destroy();
        process = null;
        try {
            if (output != null) output.close();
        } catch (IOException e) { /* Ignored */ }
        output = null;
    }

}
