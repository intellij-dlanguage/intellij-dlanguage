package io.github.intellij.dlanguage.codeinsight.dcd;

import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.configurations.ParametersList;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.psi.PsiFile;
import io.github.intellij.dlanguage.codeinsight.dcd.completions.DCDModel;
import io.github.intellij.dlanguage.settings.ToolKey;
import io.github.intellij.dlanguage.utils.DUtil;
import io.github.intellij.dlanguage.utils.ExecUtil;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class DCDCompletionClient {
    private final List<DCDModel> completions = new ArrayList<>();
    @Nullable
    private Process process;
    @Nullable
    private BufferedWriter output;

    public List<DCDModel> autoComplete(final int position, final PsiFile file) throws DCDCompletionServer.DCDError {
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
                final String workingDirectory = file.getProject().getBasePath();

                final GeneralCommandLine commandLine = new GeneralCommandLine();
                commandLine.setWorkDirectory(workingDirectory);
                commandLine.setExePath(path);
                final ParametersList parametersList = commandLine.getParametersList();
                parametersList.addParametersString("-c");
                parametersList.addParametersString(String.valueOf(position));
                parametersList.addParametersString("--extended");

                final String flags = ToolKey.DCD_CLIENT_KEY.getFlags();

                if (DUtil.isNotNullOrEmpty(flags)) {
                    final String[] importList = flags.split(",");
                    for (int i = 0; i < importList.length; i++) {
                        parametersList.addParametersString("-I");
                        parametersList.addParametersString(importList[i]);
                    }
                }

                try {
                    if (process == null) {
                        process = commandLine.createProcess();
                        output = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
                        output.write(file.getText());
                    }
                } catch (final Exception e) {
                    e.printStackTrace();
                }

                final String result = ExecUtil.readCommandLine(commandLine, file.getText());

                if (result != null && !result.isEmpty()) {
                    System.out.println("Result: \n" + result);

                    if (result.startsWith("identifiers")) {
                        final String[] tokens = result.split("\\n");

                        // first pass: add user code first!
                        for (int i = 0; i < tokens.length; i++) {
                            String token = tokens[i];
                            String[] parts = token.split("\\t");

                            if (parts.length > 2) {
                                completions.add(new DCDModel(parts[0], parts[1], parts[2].split("\\s")[0]));
                            }
                        }

                        // second pass: add the rest
                        for (int i = 0; i < tokens.length; i++) {
                            String token = tokens[i];
                            String[] parts = token.split("\\t");

                            if (parts.length == 2) {
                                completions.add(new DCDModel(parts[0], parts[1]));
                            }
                        }

                    } else if (result.startsWith("calltips")) {
                        //TODO: handle calltips
                    }
                }
            }
        }

        return completions;
    }

    @Nullable
    private String lookupPath() {
        return ToolKey.DCD_CLIENT_KEY.getPath();
    }

}
