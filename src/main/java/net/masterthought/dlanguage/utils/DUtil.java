package net.masterthought.dlanguage.utils;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.containers.ContainerUtil;
import net.masterthought.dlanguage.index.DModuleIndex;
import net.masterthought.dlanguage.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.util.*;

import static net.masterthought.dlanguage.psi.impl.DPsiImplUtil.findParentOfType;

/**
 * General util class. Provides methods for finding named nodes in the Psi tree.
 */
public class DUtil {
    /**
     * Finds name definition across all Haskell files in the project. All
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

    public static Map<Boolean, PsiElement> findElementInParent(PsiElement identifier, Class className) {
        PsiElement result = findParentOfType(identifier, className);
        Map<Boolean, PsiElement> map = new HashMap<>();
        map.put(result != null, result);
        return map;
    }

    public static Boolean elementHasParentFor(Map<Boolean, PsiElement> result) {
        return result.containsKey(true);
    }

    public static PsiElement getElementFor(Map<Boolean, PsiElement> result) {
        return (PsiElement) result.values().toArray()[0];
    }


    /**
     * finds definition(s) of functions/class/template
     * todo this method could be made more efficient and effective. For starters is long and finds a lot of definitions that are never used later
     * @param file the file to search for definitions in
     * @param name the name of the function/class/template to resolve
     * @param e the usage of the defined function/class/template etc.
     * @param result the results are added to the is arraylist
     */
    public static void findDefinitionNode(@Nullable DLanguageFile file, @Nullable String name, @Nullable PsiNamedElement e, @NotNull List<PsiNamedElement> result) {
        if (file == null) return;
        // start with empty list of potential named elements
        Collection<PsiNamedElement> namedElements = Collections.EMPTY_LIST;

        if (e instanceof DLanguageIdentifier) {
            if (e.getParent() instanceof DLanguagePrimaryExpression) {

                Collection<DLanguageFuncDeclaration> fd = PsiTreeUtil.findChildrenOfType(file, DLanguageFuncDeclaration.class);
                Collection<DLanguageClassDeclaration> cd = PsiTreeUtil.findChildrenOfType(file, DLanguageClassDeclaration.class);
                Collection<DLanguageVarDeclarator> vd = PsiTreeUtil.findChildrenOfType(file, DLanguageVarDeclarator.class);
                Collection<DLanguageAutoDeclarationY> ady = PsiTreeUtil.findChildrenOfType(file, DLanguageAutoDeclarationY.class);

                Collection<DLanguageTemplateDeclaration> td = PsiTreeUtil.findChildrenOfType(file, DLanguageTemplateDeclaration.class);

                Collection<DLanguageConstructor> constructors = PsiTreeUtil.findChildrenOfType(file, DLanguageConstructor.class);

                List<PsiNamedElement> ne = new ArrayList<>();

                // add func decls
                for (DLanguageFuncDeclaration d : fd) {
                    ne.add(d.getIdentifier());
                }

                // add class decls
                for (DLanguageClassDeclaration d : cd) {
                    if (d.getIdentifier() != null) {
                        ne.add(d.getIdentifier());
                    }
                }

                // add var decl
                for (DLanguageVarDeclarator d : vd) {
                    ne.add(d.getIdentifier());
                }

                // add auto decl y
                for (DLanguageAutoDeclarationY d : ady) {
                    ne.add(d.getIdentifier());
                }

                for (DLanguageTemplateDeclaration d : td) {
                    ne.add(d.getIdentifier());
                }

                for (DLanguageConstructor constructor : constructors) {
                    ne.add(constructor);
                }

                namedElements = ne;

            } else if (e.getParent() instanceof DLanguageIdentifierList) {
                List<PsiNamedElement> ne = new ArrayList<>();

                // add func decls
                Collection<DLanguageAutoDeclarationY> ady = PsiTreeUtil.findChildrenOfType(file, DLanguageAutoDeclarationY.class);
                Collection<DLanguageTemplateDeclaration> td = PsiTreeUtil.findChildrenOfType(file, DLanguageTemplateDeclaration.class);
                Collection<DLanguageConstructor> constructors = PsiTreeUtil.findChildrenOfType(file, DLanguageConstructor.class);
                Collection<DLanguageFuncDeclaration> fd = PsiTreeUtil.findChildrenOfType(file, DLanguageFuncDeclaration.class);
                Collection<DLanguageVarDeclarator> vd = PsiTreeUtil.findChildrenOfType(file, DLanguageVarDeclarator.class);
                Collection<DLanguageClassDeclaration> cd = PsiTreeUtil.findChildrenOfType(file, DLanguageClassDeclaration.class);

                for (DLanguageFuncDeclaration d : fd) {
                    ne.add(d.getIdentifier());
                }

                // add var declarator
                for (DLanguageVarDeclarator d : vd) {
                    ne.add(d.getIdentifier());
                }

                // add class decl
                for (DLanguageClassDeclaration d : cd) {
                    if (d.getIdentifier() != null) {
                        ne.add(d.getIdentifier());
                    }
                }

                // add auto decl y
                for (DLanguageAutoDeclarationY d : ady) {
                    ne.add(d.getIdentifier());
                }

                for (DLanguageTemplateDeclaration d : td) {
                    ne.add(d.getIdentifier());
                }

                for (DLanguageConstructor constructor : constructors) {
                    ne.add(constructor);
                }

                namedElements = ne;
            } else if (e.getParent() instanceof DLanguageTemplateInstance) {
                List<PsiNamedElement> ne = new ArrayList<>();

                Collection<DLanguageTemplateDeclaration> td = PsiTreeUtil.findChildrenOfType(file, DLanguageTemplateDeclaration.class);

                for (DLanguageTemplateDeclaration d : td) {
                    ne.add(d.getIdentifier());
                }

                namedElements = ne;

            }
//        } else {
//            namedElements = PsiTreeUtil.findChildrenOfType(file, PsiNamedElement.class);
        }

        // check the list of potential named elements for a match on name
        for (PsiNamedElement namedElement : namedElements) {
            //non void initializer
            boolean resolvingConstructor = false;

            PsiElement parent = e.getParent();
            try {
                while (!(parent instanceof DLanguageNewExpression)) {
                    parent = parent.getParent();
                }
                resolvingConstructor = true;
            } catch (NullPointerException ignored) {}

            if(resolvingConstructor) {
                if (namedElement instanceof DLanguageConstructor) {
                    DLanguageConstructor constructor = (DLanguageConstructor) namedElement;
                    //todo check that the constructor arguments match and only use those who match
                    result.add(constructor);
                }
            }
            else if ((name == null || name.equals(namedElement.getName())) && definitionNode(namedElement)) {
                result.add(namedElement);
            }
        }
    }

    /**
     * Finds a name definition inside a Haskell file. All definitions are found when name
     * is null.
     */
    @NotNull
    public static List<PsiNamedElement> findDefinitionNodes(@Nullable DLanguageFile dLanguageFile, @Nullable String name) {
        List<PsiNamedElement> ret = ContainerUtil.newArrayList();
        findDefinitionNode(dLanguageFile, name, null, ret);
        return ret;
    }

    /**
     * Finds name definition across all Haskell files in the project. All
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

    /**
     * Tells whether a named node is a definition node based on its context.
     * <p/>
     * Precondition: Element is in a Haskell file.
     */
    public static boolean definitionNode(@NotNull PsiNamedElement e) {
        if (e instanceof DLanguageIdentifier) return definitionNode((DLanguageIdentifier) e);
//        if (e instanceof DDefinitionClass) return definitionNode((DDefinitionClass) e);
        return false;
    }

    public static boolean definitionNode(@NotNull DLanguageIdentifier e) {
        return true;
    }

//    public static boolean definitionNode(@NotNull DDefinitionClass e) {
//        return true;
//    }


    /**
     * Tells whether a node is a definition node based on its context.
     */
    public static boolean definitionNode(@NotNull ASTNode node) {
        final PsiElement element = node.getPsi();
        return element instanceof PsiNamedElement && definitionNode((PsiNamedElement) element);
    }

    public static boolean isNotNullOrEmpty(String str) {
        return (str != null && !str.isEmpty());
    }

//    @Nullable
//    public static String getQualifiedPrefix(@NotNull PsiElement e) {
//        final PsiElement q = PsiTreeUtil.getParentOfType(e, DLanguageFuncDeclaration.class);
//        if (q == null) {
//            return null;
//        }
//        final String qText = q.getText();
//        final int lastDotPos = qText.lastIndexOf('.');
//        if (lastDotPos == -1) {
//            return null;
//        }
//        return qText.substring(0, lastDotPos);
//    }

//    @NotNull
//    public static Set<String> getPotentialDefinitionModuleNames(@NotNull PsiElement e, @NotNull List<String> imports) {
//        final String qPrefix = getQualifiedPrefix(e);
//        if (qPrefix == null) { return DPsiUtil.getImportModuleNames(imports); }
//        Set<String> result = new HashSet<String>();
//        for (DPsiUtil.Import anImport : imports) {
//            if (qPrefix.equals(anImport.module) || qPrefix.equals(anImport.alias)) {
//                result.add(anImport.module);
//            }
//        }
//        return result;
//    }


    public static boolean isDunitTestFile(PsiFile psiFile) {
        Collection<DLanguageClassDeclaration> cds = PsiTreeUtil.findChildrenOfType(psiFile, DLanguageClassDeclaration.class);
        for (DLanguageClassDeclaration cd : cds) {
            // if a class contains the UnitTest mixin assume its a valid d-unit test class
            Collection<DLanguageTemplateMixin> tmis = PsiTreeUtil.findChildrenOfType(cd, DLanguageTemplateMixin.class);
            for (DLanguageTemplateMixin tmi : tmis) {
                if (tmi.getText().contains("UnitTest")) {
                    return true;
                }
            }
        }
        return false;
    }
}

