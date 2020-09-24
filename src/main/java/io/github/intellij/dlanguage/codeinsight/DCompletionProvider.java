package io.github.intellij.dlanguage.codeinsight;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.openapi.application.ex.ApplicationUtil;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.psi.PsiFile;
import com.intellij.util.ProcessingContext;
import io.github.intellij.dlanguage.codeinsight.dcd.DCDCompletionClient;
import io.github.intellij.dlanguage.codeinsight.dcd.DCDCompletionServer;
import io.github.intellij.dlanguage.codeinsight.dcd.completions.Completion;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static io.github.intellij.dlanguage.codeinsight.DCompletionContributor.createLookupElement;

final class DCompletionProvider extends CompletionProvider<CompletionParameters> {

    private static final Logger log = Logger.getInstance(DCompletionProvider.class);

    private final Executor executor = Executors.newSingleThreadExecutor();

    @Override
    protected void addCompletions(@NotNull CompletionParameters parameters, @NotNull ProcessingContext context, @NotNull CompletionResultSet result) {
        final int position = parameters.getEditor().getCaretModel().getOffset();
        final PsiFile file = parameters.getOriginalFile();

        final String fileContent = file.getText();

        final CompletableFuture<List<Completion>> completionsFuture = CompletableFuture.runAsync(() -> {
            final Module module = ModuleUtilCore.findModuleForPsiElement(file);
            final DCDCompletionServer dcdCompletionServer = module.getComponent(DCDCompletionServer.class);
            try {
                dcdCompletionServer.exec();
            } catch (DCDCompletionServer.DCDError e) {
                log.warn("There was a problem starting dcd server", e);
                throw new RuntimeException(e);
            }
        }, executor).thenApplyAsync(aVoid -> {
            try {
                return new DCDCompletionClient().autoComplete(position, file, fileContent);
            } catch (DCDCompletionClient.DCDError e) {
                log.warn("There was a problem using dcd client", e);
                throw new RuntimeException(e);
            }
        });

        final Future<Void> completionsHandlerFuture = completionsFuture.thenAccept(completions -> {
            for (final Completion completion : completions) {
                result.addElement(createLookupElement(completion.completionText(), "", completion.completionType()));
            }
        });

        try {
            ApplicationUtil.runWithCheckCanceled(completionsHandlerFuture, ProgressManager.getInstance().getProgressIndicator());
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
}
