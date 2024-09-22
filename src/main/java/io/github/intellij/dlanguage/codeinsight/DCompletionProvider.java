package io.github.intellij.dlanguage.codeinsight;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.PrioritizedLookupElement;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.ex.ApplicationUtil;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.util.Computable;
import com.intellij.psi.PsiFile;
import com.intellij.util.ProcessingContext;
import io.github.intellij.dlanguage.codeinsight.dcd.DCDCompletionClient;
import io.github.intellij.dlanguage.codeinsight.dcd.DCDCompletionServer;
import io.github.intellij.dlanguage.codeinsight.dcd.completions.Completion;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.*;

import static io.github.intellij.dlanguage.codeinsight.DCompletionContributor.createLookupElement;

final class DCompletionProvider extends CompletionProvider<CompletionParameters> {

    private static final Logger log = Logger.getInstance(DCompletionProvider.class);

    private final Executor executor = Executors.newSingleThreadExecutor();

    @Override
    protected void addCompletions(@NotNull CompletionParameters parameters, @NotNull ProcessingContext context, @NotNull CompletionResultSet result) {
        final PsiFile file = parameters.getOriginalFile();
        final String fileText = file.getText();

        final CompletableFuture<List<Completion>> completionsFuture = CompletableFuture.runAsync(() -> {
            final DCDCompletionServer dcdCompletionServer = ApplicationManager.getApplication().runReadAction((Computable<DCDCompletionServer>)() -> {
                final Module module = ModuleUtilCore.findModuleForPsiElement(file);
                assert module != null;
                return module.getService(DCDCompletionServer.class);
            });

            try {
                dcdCompletionServer.exec();
            } catch (final Exception e) {
                log.warn("There was a problem starting dcd server", e);
                throw new RuntimeException(e);
            }
        }, executor).thenApplyAsync(aVoid -> {
            final int position = ApplicationManager.getApplication()
                .runReadAction((Computable<Integer>) () -> parameters.getEditor().getCaretModel().getOffset());

            try {
                return DCDCompletionClient.autoComplete(position, file, fileText);
            } catch (DCDCompletionClient.DCDError e) {
                log.warn(String.format("There was a problem using dcd-client on file %s at position %s",
                    file.getVirtualFile().getName(),
                    position), e);
                throw new RuntimeException(e);
            }
        });

        final Future<Void> completionsHandlerFuture = completionsFuture.thenAccept(completions -> {
            for (final Completion completion : completions) {
                result.addElement(PrioritizedLookupElement
                    .withPriority(
                        createLookupElement(completion.completionText(), "", completion.completionType()),
                        prioritise(completion)
                    )
                );
            }
        });

        try {
            ApplicationUtil.runWithCheckCanceled(completionsHandlerFuture, ProgressManager.getInstance().getProgressIndicator());
        } catch (ExecutionException e) {
            log.warn("D completions failed : " + e.getMessage(), e);
        } catch (final Exception e) {
            //e.printStackTrace();
        }
    }

    private double prioritise(@NotNull final Completion completion) {
        return switch (completion.completionType()) {
            case "Function" -> 100;
            case "Variable" -> completion.completionText().startsWith("__") ? 60 : 80;
            case "Keyword" -> completion.completionText().endsWith("of") ? 40 : 20;
            default -> 0;
        };
    }

}
