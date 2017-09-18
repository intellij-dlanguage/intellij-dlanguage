package net.masterthought.dlanguage.codeinsight;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.editor.Editor;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiFile;
import com.intellij.util.Function;
import com.intellij.util.ProcessingContext;
import net.masterthought.dlanguage.DLanguage;
import net.masterthought.dlanguage.icons.DlangIcons;
import net.masterthought.dlanguage.codeinsight.dcd.DCDCompletionClient;
import net.masterthought.dlanguage.codeinsight.dcd.DCDCompletionServer;
import net.masterthought.dlanguage.codeinsight.dcd.completions.Completion;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DCompletionContributor extends CompletionContributor {

    private final DCDCompletionClient dcdCompletionClient = new DCDCompletionClient();

    public DCompletionContributor() {
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement().withLanguage(DLanguage.INSTANCE),
                new CompletionProvider<CompletionParameters>() {
                    public void addCompletions(@NotNull final CompletionParameters parameters,
                                               final ProcessingContext context,
                                               @NotNull final CompletionResultSet result) {
                        final int position = parameters.getEditor().getCaretModel().getOffset();
//                        PsiElement position = parameters.getPosition();
                        final PsiFile file = parameters.getOriginalFile();

                        List<Completion> completions = null;
                        try {
                            completions = dcdCompletionClient.autoComplete(position, file);

                            for (final Completion completion : completions) {
                                result.addElement(createLookupElement(completion.completionText(),"",completion.completionType()));
                            }
                        } catch (final DCDCompletionServer.DCDError dcdError) {
                            dcdError.printStackTrace();
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
