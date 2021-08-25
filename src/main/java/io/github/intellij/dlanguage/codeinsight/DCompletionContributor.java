package io.github.intellij.dlanguage.codeinsight;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.*;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.editor.Editor;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.util.Function;
import io.github.intellij.dlanguage.DLanguage;
import io.github.intellij.dlanguage.icons.DlangIcons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.Icon;

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

    public static LookupElement createLookupElement(@NotNull final String name,
                                                    @Nullable final String module,
                                                    @NotNull final String type) {
        final Icon icon = "Function".equals(type) ? AllIcons.Nodes.Function // should perhaps use DlangIcons.NODE_FUNCTION
                            : "Variable".equals(type) ? AllIcons.Nodes.Variable :DlangIcons.FILE;

        return LookupElementBuilder.create(name)
            .withItemTextItalic("Keyword".equals(type))
            //.withItemTextForeground(JBColor.ORANGE)
            .withIcon(icon)
            .withTypeText(type, true)
            //.withTailText(" (" + module + ')', true)
            //.withRenderer()
            //.withAutoCompletionPolicy(AutoCompletionPolicy.SETTINGS_DEPENDENT)
            ;
    }

    public static final Function<String, LookupElement> stringToLookupElement = s -> LookupElementBuilder
        .create(s)
        .withIcon(DlangIcons.FILE);

}
