package net.masterthought.dlanguage.codeinsight;

import com.google.common.collect.Lists;
import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.configurations.ParametersList;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.process.ProcessAdapter;
import com.intellij.execution.process.ProcessEvent;
import com.intellij.openapi.util.Key;
import com.intellij.psi.PsiFile;

import java.util.List;

public class DCDCompletion {

  private String DCD_PATH = "/Users/kings/development/projects/intellij-dev/DCD/bin/dcd-client";

  public List<String> autoComplete(int position, PsiFile file){
      System.out.println("position: " + String.valueOf(position));
      final String filePath = file.getOriginalFile().getVirtualFile().getCanonicalPath();
         final String workingDirectory = file.getProject().getBasePath();

         final GeneralCommandLine commandLine = new GeneralCommandLine();
         commandLine.setWorkDirectory(workingDirectory);
         commandLine.setExePath(DCD_PATH);
         ParametersList parametersList = commandLine.getParametersList();
//         parametersList.addParametersString("-d");
         parametersList.addParametersString("-c");
         parametersList.addParametersString(String.valueOf(position));
         parametersList.addParametersString(filePath);

         final List<String> completions = Lists.newArrayList();

         try {
             OSProcessHandler process = new OSProcessHandler(commandLine.createProcess());
             process.addProcessListener(new ProcessAdapter() {
                 @Override
                 public void onTextAvailable(ProcessEvent event, Key outputType) {
                     completions.add(event.getText());
                 }
             });

             process.startNotify();
             process.waitFor();

         } catch (ExecutionException e) {
             e.printStackTrace();
         }
         return completions;
  }

}
