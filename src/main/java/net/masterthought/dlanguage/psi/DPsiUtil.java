package net.masterthought.dlanguage.psi;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.resolve.ParameterCountRange;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.Set;


public class DPsiUtil {
    @NotNull
    public static <T extends PsiElement> String[] getTexts(@NotNull final List<T> psiElements) {
        final int size = psiElements.size();
        final String[] result = new String[size];
        for (int i = 0; i < size; ++i) {
            result[i] = psiElements.get(i).getText();
        }
        return result;
    }

    @Deprecated
    private static List<DLanguageDeclaration> getDeclDefs(final PsiElement defs, final List<DLanguageDeclaration> declDefsList) {
        final DLanguageDeclaration declDefs = PsiTreeUtil.getChildOfType(defs, DLanguageDeclaration.class);
        if (declDefs != null) {
            declDefsList.add(declDefs);
            getDeclDefs(declDefs, declDefsList);

        }
        return declDefsList;
    }

    /**
     * Returns a map of module -> alias for each imported module.  If a module is imported but not qualified, alias
     * will be null.
     */

    @NotNull
    public static Set<String> parseImports(@NotNull final PsiFile file) {
        final Set<String> imports = Sets.newHashSet();
        final List<DLanguageDeclaration> declDefList = Lists.newArrayList();
        declDefList.addAll(PsiTreeUtil.findChildrenOfType(file, DLanguageDeclaration.class));
        for (final DLanguageDeclaration declDef : declDefList) {
            final Collection<DLanguageSingleImport> importDecls = PsiTreeUtil.findChildrenOfType(declDef, DLanguageSingleImport.class);
            for (final DLanguageSingleImport importDecl : importDecls) {
                imports.add(importDecl.getIdentifierChain().getText());
            }
        }
        return imports;
    }

    public static PsiElement getParent(final @NotNull PsiElement element, final @NotNull Set<IElementType> targetType, final @NotNull Set<IElementType> excludedType) {
        if (element.getParent() == null || element instanceof DLanguageFile) {
            return null;
        }

        if (targetType.contains(element.getNode().getElementType())) {
            return element;
        }

        if (excludedType.contains(element.getNode().getElementType())) {
            return null;
        }

        return getParent(element.getParent(), targetType, excludedType);

    }

    public static ParameterCountRange getNumParameters(@NotNull DLanguageParameters parameters) {
        int min = 0;
        int max = 0;
        for (DLanguageParameter parameter : parameters.getParameters()) {
            if (parameter.getOP_EQ() != null) {
                max++;
                continue;
            }
            min++;
            max++;
        }
        if (parameters.getOP_TRIPLEDOT() != null) {
            max = Integer.MAX_VALUE;
        }
        return new ParameterCountRange(min, max);

    }
}

