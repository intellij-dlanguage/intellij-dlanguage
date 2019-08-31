package io.github.intellij.dlanguage.codeinsight;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.editor.Editor;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiFile;
import com.intellij.util.Function;
import com.intellij.util.ProcessingContext;
import io.github.intellij.dlanguage.DLanguage;
import io.github.intellij.dlanguage.icons.DlangIcons;
import io.github.intellij.dlanguage.codeinsight.dcd.DCDCompletionClient;
import io.github.intellij.dlanguage.codeinsight.dcd.DCDCompletionServer;
import io.github.intellij.dlanguage.codeinsight.dcd.completions.Completion;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class DCompletionContributor extends CompletionContributor {
    public DCompletionContributor() {
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement().withLanguage(DLanguage.INSTANCE),
                new DCompletionProvider()
        );
    }

    /**
     * Adjust the error message when no lookup is found.
     */
    @Nullable
    @Override
    public String handleEmptyLookup(@NotNull final CompletionParameters parameters, final Editor editor) {
        return "DLanguage: no completion found.";
    }

    public static LookupElement createLookupElement(@NotNull final String name, @NotNull final String module, @NotNull final String type) {
        return LookupElementBuilder.create(name).withIcon(DlangIcons.FILE)
//                .withTailText(" (" + module + ')', true)
                .withTypeText(type);
    }

    public static final Function<String, LookupElement> stringToLookupElement = s -> LookupElementBuilder.create(s).withIcon(DlangIcons.FILE);

}
