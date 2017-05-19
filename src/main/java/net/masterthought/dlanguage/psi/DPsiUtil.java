package net.masterthought.dlanguage.psi;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.Set;


public class DPsiUtil {
    @NotNull
    public static <T extends PsiElement> String[] getTexts(@NotNull List<T> psiElements) {
        final int size = psiElements.size();
        String[] result = new String[size];
        for (int i = 0; i < size; ++i) {
            result[i] = psiElements.get(i).getText();
        }
        return result;
    }

    @Deprecated
    private static List<DLanguageDeclDefs> getDeclDefs(PsiElement defs, List<DLanguageDeclDefs> declDefsList) {
        DLanguageDeclDefs declDefs = PsiTreeUtil.getChildOfType(defs, DLanguageDeclDefs.class);
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
        Set<String> imports = Sets.newHashSet();
        List<DLanguageDeclDef> declDefList = Lists.newArrayList();
        declDefList.addAll(PsiTreeUtil.findChildrenOfType(file, DLanguageDeclDef.class));
        for (DLanguageDeclDef declDef : declDefList) {
            Collection<DLanguageImportDeclaration> importDecls = PsiTreeUtil.findChildrenOfType(declDef, DLanguageImportDeclaration.class);
            for (DLanguageImportDeclaration importDecl : importDecls) {
                imports.add(importDecl.getImportList().getText());
            }
        }
        return imports;
    }

}

