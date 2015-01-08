package net.masterthought.dlanguage.codeinsight;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.configurations.ParametersList;
import com.intellij.psi.PsiFile;
import net.masterthought.dlanguage.codeinsight.completions.Completion;
import net.masterthought.dlanguage.codeinsight.completions.TextCompletion;
import net.masterthought.dlanguage.utils.ExecUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DCDCompletion {

    private Map<String,String> completionTypeMap = getCompletionTypeMap();

    private String DCD_PATH = "/Users/kings/development/projects/intellij-dev/DCD/bin/dcd-client";

    public List<Completion> autoComplete(int position, PsiFile file) {
        System.out.println("position: " + String.valueOf(position));
        final String filePath = file.getOriginalFile().getVirtualFile().getCanonicalPath();
        System.out.println(filePath);
        final String workingDirectory = file.getProject().getBasePath();

        final GeneralCommandLine commandLine = new GeneralCommandLine();
        commandLine.setWorkDirectory(workingDirectory);
        commandLine.setExePath(DCD_PATH);
        ParametersList parametersList = commandLine.getParametersList();
        parametersList.addParametersString("-c");
        parametersList.addParametersString(String.valueOf(position));
        parametersList.addParametersString(filePath);

        final List<Completion> completions = Lists.newArrayList();
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

            }
        }
        return completions;
    }

   private String getType(List<String> parts){
       String type = parts.get(parts.size()-1);
       return type.isEmpty() ? "U" : type.trim();
   }

   private String getCompletionType(List<String> parts){
       String mapping = completionTypeMap.get(getType(parts));
       return mapping == null ? "Unknown" : mapping;
   }

   private String getCompletionText(List<String> parts){
       String text = parts.get(0);
       String result = text.isEmpty() ? "" : text.trim();
       String type = getType(parts);
       if(type.equals("f")){
         return result + "()";
       }
       return result;
   }

    private Map<String,String> getCompletionTypeMap(){
        Map<String,String> map = Maps.newTreeMap();
        map.put("c","Class");
        map.put("i","Interface");
        map.put("s","Struct");
        map.put("u","Union");
        map.put("v","Variable");
        map.put("m","Variable");
        map.put("k","Keyword");
        map.put("f","Function");
        map.put("g","Enum");
        map.put("e","Enum");
        map.put("P","Package");
        map.put("M","Module");
        map.put("a","Array");
        map.put("A","Map");
        map.put("l","Alias");
        map.put("t","Template");
        map.put("T","Mixin");
        map.put("U","Unknown");
        return map;
    }

}
