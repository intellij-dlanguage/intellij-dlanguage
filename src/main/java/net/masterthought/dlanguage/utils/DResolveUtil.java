package net.masterthought.dlanguage.utils;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiNamedElement;
import net.masterthought.dlanguage.psi.DLanguageFile;
import net.masterthought.dlanguage.psi.DLanguageIdentifier;
import net.masterthought.dlanguage.psi.DPsiUtil;
import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

import static java.util.Collections.EMPTY_SET;

/**
 * Created by franc on 1/18/2017.
 */
public class DResolveUtil {
    public static Set<PsiNamedElement> findDefinitionNodes(@NotNull DNamedElement element) {
        if (!(element instanceof DLanguageIdentifier))
            return EMPTY_SET;//prevent resolving definitions
        Set<PsiNamedElement> definitionNodes = new HashSet<>();
        //not found in current file, proceed to search all files
        Project project = element.getProject();
        Set<String> importedModules = DPsiUtil.parseImports(element.getContainingFile());
        Set<DLanguageFile> filesToSearch = fromModulesToFiles(project, importedModules);
        filesToSearch.add((DLanguageFile) element.getContainingFile());
        for (DLanguageFile dLanguageFile : filesToSearch) {
            definitionNodes.addAll(findDefinitionNodes(dLanguageFile, (DLanguageIdentifier) element));
        }
        return definitionNodes;

    }


}
