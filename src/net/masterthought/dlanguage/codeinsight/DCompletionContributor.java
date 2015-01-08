package net.masterthought.dlanguage.codeinsight;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Editor;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.util.Function;
import com.intellij.util.ProcessingContext;
import net.masterthought.dlanguage.DLanguage;
import net.masterthought.dlanguage.DLanguageIcons;
import net.masterthought.dlanguage.codeinsight.completions.Completion;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DCompletionContributor extends CompletionContributor {

    private final DCDCompletion dcdCompletion = new DCDCompletion();

    public DCompletionContributor() {
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement().withLanguage(DLanguage.INSTANCE),
                new CompletionProvider<CompletionParameters>() {
                    public void addCompletions(@NotNull CompletionParameters parameters,
                                               ProcessingContext context,
                                               @NotNull CompletionResultSet result) {
                        int position = parameters.getEditor().getCaretModel().getOffset();
//                        PsiElement position = parameters.getPosition();
                        PsiFile file = parameters.getOriginalFile();

                        List<Completion> completions = dcdCompletion.autoComplete(position, file);
                        for (Completion completion : completions) {
                            result.addElement(createLookupElement(completion.completionText(),"std.kings",completion.completionType()));
                        }

                    }
                }
        );
    }


    /**
     * Adjust the error message when no lookup is found.
     */
    @Nullable
    @Override
    public String handleEmptyLookup(@NotNull CompletionParameters parameters, final Editor editor) {
        return "DLanguage: no completion found.";
    }

    public static LookupElement createLookupElement(@NotNull String name, @NotNull String module, @NotNull String type) {
        return LookupElementBuilder.create(name).withIcon(DLanguageIcons.FILE)
                .withTailText(" (" + module + ')', true)
                .withTypeText(type);
    }

    public static final Function<String, LookupElement> stringToLookupElement = new Function<String, LookupElement>() {
        @Override
        public LookupElement fun(String s) {
            return LookupElementBuilder.create(s).withIcon(DLanguageIcons.FILE);
        }
    };

}
