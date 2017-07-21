package net.masterthought.dlanguage.utils;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.stubs.StubIndex;
import com.intellij.util.containers.ContainerUtil;
import net.masterthought.dlanguage.index.DModuleIndex;
import net.masterthought.dlanguage.psi.*;
import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
import net.masterthought.dlanguage.psi.interfaces.Declaration;
import net.masterthought.dlanguage.stubs.index.DAllNameIndex;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * Created by francis on 5/12/17.
 */
public class DResolveUtil {
    /**
     * Finds name definition across all DLanguage files in the project. All
     * definitions are found when name is null.
     */
    @NotNull
    public static List<PsiNamedElement> findDefinitionNode(@NotNull Project project, @Nullable String name, @Nullable PsiNamedElement e) {
        // Guess where the name could be defined by lookup up potential modules.
        final Set<String> potentialModules =
            e == null ? Collections.EMPTY_SET
                : DPsiUtil.parseImports(e.getContainingFile());

        List<PsiNamedElement> result = ContainerUtil.newArrayList();
        final PsiFile psiFile = e == null ? null : e.getContainingFile().getOriginalFile();
        // find definition in current file
        if (psiFile instanceof DLanguageFile) {
            findDefinitionNode((DLanguageFile) psiFile, name, e, result);
        }
        // find definition in imported files
        for (String potentialModule : potentialModules) {
            List<DLanguageFile> files = DModuleIndex.getFilesByModuleName(project, potentialModule, GlobalSearchScope.allScope(project));
            for (DLanguageFile f : files) {
                final boolean returnAllReferences = name == null;
                final boolean inLocalModule = f != null && f.equals(psiFile);
                final boolean inImportedModule = f != null && potentialModules.contains(f.getModuleName());
                if (returnAllReferences || inLocalModule || inImportedModule) {
                    findDefinitionNode(f, name, e, result);
                }
            }
        }
        return result;
    }

    /**
     * finds definition(s) of functions/class/template
     * todo this method could be made more efficient and effective. Use a stub tree?
     * @param file the file to search for definitions in
     * @param name the name of the function/class/template to resolve
     * @param e the usage of the defined function/class/template etc.
     * @param result the results are added to the is arraylist
     */
    public static void findDefinitionNode(@Nullable DLanguageFile file, @Nullable String name, @Nullable PsiNamedElement e, @NotNull List<PsiNamedElement> result) {
        if (file == null) return;
        // start with empty list of potential named elements
        Collection<DNamedElement> declarationElements = new ArrayList<>();

        if (e instanceof DLanguageIdentifier) {

            List<Declaration> declarations = new ArrayList<>();
            final Collection<DNamedElement> elements = StubIndex.getElements(DAllNameIndex.KEY, e.getName(), e.getProject(), GlobalSearchScope.fileScope(file), DNamedElement.class);
            for (DNamedElement element : elements) {
                if (element instanceof Declaration) {
                    declarations.add((Declaration) element);
                }
            }

            for (DNamedElement candidateDeclaration : declarations) {
                if(candidateDeclaration instanceof DLanguageAutoDeclarationY){
                    if(((DLanguageAutoDeclarationY) candidateDeclaration).actuallyIsDeclaration()){
                        declarationElements.add(candidateDeclaration);
                    }
                    continue;
                }
                declarationElements.add(candidateDeclaration);
            }
        }

        boolean resolvingConstructor = false;

        PsiElement parent = e.getParent();
        while (true) {
            if(parent == null)
                break;
            if(parent instanceof DLanguageNewExpression || parent instanceof DLanguageNewExpressionWithArgs)
                resolvingConstructor = true;
            parent = parent.getParent();
        }

        // check the list of potential named elements for a match on name
        for (DNamedElement namedElement : declarationElements) {
            //non void initializer
            if(resolvingConstructor) {
                if (namedElement instanceof DLanguageConstructor) {
                    DLanguageConstructor constructor = (DLanguageConstructor) namedElement;
                    result.add(constructor);
                }
            } else if (name == null || (name.equals(namedElement.getName()) && !(e.equals(namedElement)) && !(namedElement instanceof DLanguageConstructor))) {
                result.add(namedElement);
            }
        }
    }

    /**
     * Finds a name definition inside a D file. All definitions are found when name
     * is null.
     */
    @NotNull
    public static List<PsiNamedElement> findDefinitionNodes(@Nullable DLanguageFile dLanguageFile, @Nullable String name) {
        List<PsiNamedElement> ret = ContainerUtil.newArrayList();
        findDefinitionNode(dLanguageFile, name, null, ret);
        return ret;
    }

    /**
     * Finds name definition across all D files in the project. All
     * definitions are found when name is null.
     */
    @NotNull
    public static List<PsiNamedElement> findDefinitionNodes(@NotNull Project project) {
        return findDefinitionNode(project, null, null);
    }

    /**
     * Finds name definitions that are within the scope of a file, including imports (to some degree).
     */
    @NotNull
    public static List<PsiNamedElement> findDefinitionNodes(@NotNull DLanguageFile psiFile) {
        List<PsiNamedElement> result = findDefinitionNodes(psiFile, null);
        result.addAll(findDefinitionNode(psiFile.getProject(), null, null));
        return result;
    }
}
