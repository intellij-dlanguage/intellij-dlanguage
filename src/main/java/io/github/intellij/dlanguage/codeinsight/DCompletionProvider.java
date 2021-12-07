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

// should be renamed to DCDCompletionProvider
final class DCompletionProvider extends CompletionProvider<CompletionParameters> {

    private static final Logger log = Logger.getInstance(DCompletionProvider.class);

    private final Executor executor = Executors.newSingleThreadExecutor();

    public DCompletionProvider() {
        log.info("Creating DCD completion provider");
    }

    @Override
    protected void addCompletions(@NotNull CompletionParameters parameters, @NotNull ProcessingContext context, @NotNull CompletionResultSet result) {
        final PsiFile file = parameters.getOriginalFile();

        final CompletableFuture<Void> completionsFuture = CompletableFuture.runAsync(() -> {
            final Module module = ModuleUtilCore.findModuleForPsiElement(file);
            final DCDCompletionServer dcdCompletionServer = module.getComponent(DCDCompletionServer.class);
            try {
                dcdCompletionServer.exec();
            } catch (final Exception e) {
                log.warn("There was a problem starting dcd server", e);
                throw new RuntimeException(e);
            }
        }, executor)
        .thenRunAsync(() -> {
            final int position = ApplicationManager.getApplication()
                .runReadAction((Computable<Integer>) () -> parameters.getEditor().getCaretModel().getOffset());

            DCDCompletionClient.autoComplete(position, file, results -> {
                results.forEach(c -> result.addElement(PrioritizedLookupElement
                    .withPriority( //todo: consider using withGrouping instead
                        createLookupElement(c.completionText(), "", c.completionType()),
                        prioritise(c)
                    )
                ));
            });
        });

        try {
            ApplicationUtil.runWithCheckCanceled(completionsFuture, ProgressManager.getInstance().getProgressIndicator());
        } catch (ExecutionException e) {
            log.warn("D completions failed : " + e.getMessage(), e);
        } catch (final Exception e) {
            //e.printStackTrace();
        }
    }

    private double prioritise(@NotNull final Completion completion) {
        switch (completion.completionType()) {
            case "Function":
                return 100;
            case "Variable":
                return completion.completionText().startsWith("__") ? 60 : 80;
            case "Keyword":
                return completion.completionText().endsWith("of") ? 40 : 20;
        }
        return 0;
    }

}
