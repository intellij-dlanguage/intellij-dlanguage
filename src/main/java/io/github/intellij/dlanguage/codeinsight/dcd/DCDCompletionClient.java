package io.github.intellij.dlanguage.codeinsight.dcd;

import com.google.common.collect.Maps;
import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.CapturingProcessHandler;
import com.intellij.execution.process.ProcessAdapter;
import com.intellij.execution.process.ProcessEvent;
import com.intellij.execution.process.ProcessOutputType;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.ProjectUtil;
import com.intellij.openapi.util.Computable;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.util.PathUtil;
import io.github.intellij.dlanguage.codeinsight.dcd.completions.TextCompletion;
import io.github.intellij.dlanguage.settings.ToolKey;
import io.github.intellij.dlanguage.utils.DUtil;
import io.github.intellij.dlanguage.codeinsight.dcd.completions.Completion;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public final class DCDCompletionClient {

    private final static Logger LOG = Logger.getInstance(DCDCompletionClient.class);
    private static final Map<String, String> completionTypeMap = getCompletionTypeMap();

    public static synchronized void autoComplete(final int position, @NotNull final PsiFile file, @NotNull final DCDCompletionResult callback) {
        try {
            callback.onResults(autoComplete(position, file));
        } catch (final DCDClientException e) {
            e.printStackTrace();
        }
    }

    public static synchronized List<Completion> autoComplete(final int position, @NotNull final PsiFile file) throws DCDClientException {
        try {
            return autoComplete(position, file, file.getText());
        } catch (DCDError e) {
            throw new DCDClientException(e);
        }
    }

    @Deprecated
    public static synchronized List<Completion> autoComplete(final int position, final PsiFile file, final String fileContent) throws DCDError {
        if (StringUtil.isEmptyOrSpaces(fileContent)) {
            LOG.warn("Attempted auto completion via DCD but file content was blank");
            return Collections.emptyList();
        }

        @Nullable final String dcdPath = ToolKey.DCD_CLIENT_KEY.getPath();
        if (StringUtil.isEmptyOrSpaces(dcdPath)) {
            LOG.warn("Attempted auto completion via DCD but path was blank");
            return Collections.emptyList();
        }

        final File dcdBinary = Paths.get(dcdPath).toFile();
        if(!dcdBinary.canExecute()) {
            LOG.warn(String.format("Attempted auto completion via DCD but path '%s' not executable", dcdPath));
            return Collections.emptyList();
        }

        final GeneralCommandLine dcdClientCommand = buildDcdCommand(dcdPath, position, file);
        // Protect reading the file's text with the read lock
        final String fileText = ApplicationManager.getApplication().runReadAction((Computable<String>) () -> file.getText());

        try {
            return runCommandLine(dcdClientCommand, fileText).get(2L, TimeUnit.SECONDS);
        } catch (InterruptedException | java.util.concurrent.ExecutionException | TimeoutException e) {
            throw new DCDError(e);
        }
    }

    // is package private for unit testing
    static GeneralCommandLine buildDcdCommand(final String dcdClientPath, int position, PsiFile file) {
        @Nullable final VirtualFile projectRoot = ProjectUtil.guessProjectDir(file.getProject());

        @Nullable final String workingDirectory = projectRoot != null && projectRoot.exists() ?
            projectRoot.getCanonicalPath() :
            file.getProject().getBasePath();

        final GeneralCommandLine commandLine = new GeneralCommandLine()
            .withCharset(StandardCharsets.UTF_8)
            .withWorkDirectory(PathUtil.toSystemDependentName(workingDirectory))
            .withExePath(dcdClientPath)
            .withParameters("-c", String.valueOf(position));

        final String flags = ToolKey.DCD_CLIENT_KEY.getFlags();

        // todo: consider ditching DCD Client flags. It typically causes problems for users anyway and are probably
        // best left empty
        if (DUtil.isNotNullOrEmpty(flags)) {
            Arrays.stream(flags.split(","))
                .forEach(flag -> commandLine.addParameters("-I", flag));
        }

        return commandLine;
    }

    /**
     * Executes dcd-client and then parses the output from stdout into a collection of completions
     */
    private static Future<List<Completion>> runCommandLine(@NotNull final GeneralCommandLine commandLine, @Nullable final String input) {
        if(StringUtil.isEmptyOrSpaces(input)) {
            return CompletableFuture.completedFuture(Collections.emptyList());
        }
        //ApplicationManager.getApplication().runReadAction();
        return ApplicationManager.getApplication().executeOnPooledThread(() -> {
            try {
                final String output = new DCDClientProcessHandler(commandLine, input)
                    .runProcess(1_000, true)
                    .getStdout();

                if(StringUtil.isEmptyOrSpaces(output)) {
                    LOG.debug("DCD output was blank");
                    return Collections.emptyList();
                } else {
                    return processDcdOutput(output);
                }
            } catch (final ExecutionException | IOException e) {
                LOG.warn("There was a problem running dcd-client", e);
                return Collections.emptyList(); //throw new DCDError(e);
            }
        });
    }

    // package private for unit testing
    static List<Completion> processDcdOutput(@NotNull final String output) {
        if (!output.isEmpty()) {
            final String[] lines = output.split("\\n");

            if (lines.length > 1 && lines[0].contains("identifiers")) {
                final List<Completion> completions = new ArrayList<>();
                for (int i = 1; i < lines.length; i++) {
                    final String line = lines[i];

                    if(StringUtil.isNotEmpty(line)) {
                        final String[] tokens = line.split("\\s");
                        final String completionType = getCompletionType(tokens);
                        final String completionText = getCompletionText(tokens);
                        completions.add(new TextCompletion(completionType, completionText));
                    }
                }
                return completions;
            }
//            else if (lines[0].contains("calltips")) {
//                //TODO - this goes in a Parameter Info handler (ctrl+p) instead of here - see: ShowParameterInfoHandler.register
//                System.out.println(tokens);
//            }
        }

        return Collections.emptyList();
    }

    private static String getType(final String[] parts) {
        final String type = parts[parts.length - 1];
        return type.isEmpty() ? "U" : type.trim();
    }

    private static String getCompletionType(final String[] parts) {
        final String mapping = completionTypeMap.get(getType(parts));
        return mapping == null ? "Unknown" : mapping;
    }

    private static String getCompletionText(final String[] parts) {
        final String text = parts[0];
        final String result = text.isEmpty() ? "" : text.trim();
        final String type = getType(parts);
        if (type.equals("f")) {
            return result + "()";
        }
        return result;
    }

    private static Map<String, String> getCompletionTypeMap() {
        final Map<String, String> map = Maps.newTreeMap();
        map.put("c", "Class");
        map.put("i", "Interface");
        map.put("s", "Struct");
        map.put("u", "Union");
        map.put("v", "Variable");
        map.put("m", "Variable"); // Member Variable
        map.put("k", "Keyword");
        map.put("f", "Function");
        map.put("g", "Enum"); // enum name
        map.put("e", "Enum"); // enum member
        map.put("P", "Package");
        map.put("M", "Module");
        map.put("a", "Array");
        map.put("A", "Map"); // associative array
        map.put("l", "Alias");
        map.put("t", "Template");
        map.put("T", "Mixin");
        map.put("h", "Type Param"); // template type parameter (when no colon constraint)
        map.put("p", "Variadic Param"); // template variadic parameter
        map.put("U", "Unknown");
        return map;
    }

    /*
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


    public static interface DCDCompletionResult {
        void onResults(final List<Completion>completions);
    }

    public static class DCDClientException extends Exception {
        DCDClientException(Throwable throwable) {
            super(throwable);
        }
    }

    /**
     * This process handler is used to make sure that we log the output from the dcd-client as well
     * as simply capturing it
     */
    private static class DCDClientProcessHandler extends CapturingProcessHandler {

        public DCDClientProcessHandler(GeneralCommandLine commandLine, String fileContent) throws ExecutionException, IOException {
            this(commandLine.createProcess());

//            this.getProcessInput().write(fileContent.getBytes(StandardCharsets.UTF_8));
            try (final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(myProcess.getOutputStream()))) {
                writer.write(fileContent);
                writer.flush();
            }
        }

        public DCDClientProcessHandler(@NotNull final Process process) {
            super(process, StandardCharsets.UTF_8, process.toString());

            this.addProcessListener(new ProcessAdapter() {
                @Override
                public void onTextAvailable(@NotNull ProcessEvent event, @NotNull Key outputType) {
                    if(ProcessOutputType.isStdout(outputType)) {
                        LOG.info(event.getText());
                    } else if(ProcessOutputType.isStderr(outputType)) {
                        LOG.warn(event.getText());
                    }
                }
            });
        }
    }

    @Deprecated
    public static class DCDError extends Exception {
        DCDError(Throwable throwable) {
            super(throwable);
        }
    }
}
