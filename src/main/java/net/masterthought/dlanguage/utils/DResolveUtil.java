package net.masterthought.dlanguage.utils;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.search.GlobalSearchScope;
import net.masterthought.dlanguage.psi.*;
import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
import net.masterthought.dlanguage.psi.interfaces.containers.*;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static com.intellij.psi.util.PsiTreeUtil.findChildOfType;
import static com.intellij.psi.util.PsiTreeUtil.getParentOfType;
import static java.util.Collections.EMPTY_SET;
import static net.masterthought.dlanguage.index.DModuleIndex.getFilesByModuleName;
import static net.masterthought.dlanguage.psi.DPsiUtil.parseImports;
import static net.masterthought.dlanguage.utils.DUtil.getEndOfIdentifierList;
import static net.masterthought.dlanguage.utils.DUtil.getTopLevelOfRecursiveElement;

/**
 * Created by franc on 1/18/2017.
 */
public class DResolveUtil {
    static Logger log = Logger.getInstance(DResolveUtil.class);
    public static Set<PsiNamedElement> findDefinitionNodes(@NotNull DNamedElement element) {
        if (!(element instanceof DLanguageIdentifier))
            return EMPTY_SET;//prevent resolving definitions
        Set<PsiNamedElement> definitionNodes = new HashSet<>();
        //not found in current file, proceed to search all files
        Project project = element.getProject();
        //todo:limited scope imports will not be parsed correctly
        Set<String> importedModules = parseImports(element.getContainingFile());
        Set<DLanguageFile> filesToSearch = fromModulesToFiles(project, importedModules);
        filesToSearch.add((DLanguageFile) element.getContainingFile());
        for (DLanguageFile dLanguageFile : filesToSearch) {
            definitionNodes.addAll(findDefinitionNodes(dLanguageFile, (DLanguageIdentifier) element));
        }
        return definitionNodes;

    }

    private static List<DNamedElement> findDefinitionNodes(DLanguageFile dLanguageFile, DLanguageIdentifier element) {
        //five major possibilities:
        //1. identifier is part of import statement, and should resolve to a file
        //2. identifier is a normal function/class name/etc..
        //  2.a.overloaded functions, specifically resolving the correct overloaded functions
        //3. identifier is a constructor/destructor usage
        //  3.a.overloaded constructors, specifically resolving the correct overloaded functions
        //4. resolving something referred to with its full name eg. foo.bar.something();
        //5. resolving a member function/var of a class/struct/template etc...
        final PsiElement parent = element.getParent();
        //identifiers within templates break this approach
        if (parent.getText().contains(".") || parent instanceof DLanguageModuleFullyQualifiedName) {
            //#1,4, or 5 are true
            if (getParentOfType(parent, DLanguageImportDeclaration.class) != null) {
                //#1 is true
                log.info("#1:" + parent.getText());//todo remove later
                return findModuleDefinitionNodes(dLanguageFile, getParentOfType(element, DLanguageModuleFullyQualifiedName.class));
            } else {
                //#4 or #5 are true
                log.info("#4/5:" + parent.getText());
                //to distinguish between #4 and #5 resolve the rightmost identifier. eg. in foo.bar.gh(), find foo and resolve it
                DLanguageIdentifier topLevelIdentifier = null;
                if (parent instanceof DLanguagePrimaryExpression) {
                    //find topLevel expression/statement
                    PsiElement current = parent;
                    while (true) {
                        if (current instanceof DLanguageStatement)
                            break;
                        if (current instanceof DLanguageAssignExpression)
                            break;
                        if (current == null)
                            throw new IllegalStateException();
                        current = current.getParent();
                    }
                    topLevelIdentifier = findChildOfType(current, DLanguageIdentifier.class);//depth first search, so this will get the first identifier
                }
                if (parent instanceof DLanguageIdentifierList) {
                    final DLanguageIdentifierList topLevel = (DLanguageIdentifierList) getTopLevelOfRecursiveElement(parent, parent.getClass());
                    topLevelIdentifier = topLevel.getIdentifier();

                } else if (parent instanceof DLanguageSymbolTail) {
                    final DLanguageSymbolTail topLevel = (DLanguageSymbolTail) getTopLevelOfRecursiveElement(parent, parent.getClass());
                    topLevelIdentifier = topLevel.getIdentifier();

                } else if (parent instanceof DLanguageQualifiedIdentifierList) {
                    final DLanguageQualifiedIdentifierList topLevel = (DLanguageQualifiedIdentifierList) getTopLevelOfRecursiveElement(parent, parent.getClass());
                    topLevelIdentifier = topLevel.getIdentifier();
                }

                //the identifier is within one of the following:
                // IdentifierList
                // UnaryExpression
                // PostfixExpression
                // PrimaryExpression
                // DotIdentifier
                // Symbol
                // SymbolTail
                // MixinTemplateName
                // QualifiedIdentifierList
            }

        } else {
            //#2 or 3
            PsiElement current = parent;
            while (true) {
                if (current instanceof DLanguageNewExpression || current instanceof DLanguageNewExpressionWithArgs) {
                    //#3 is true
                    log.info("#3:" + parent.getText());
                }
                if (current instanceof DLanguageCastExpression) {
                    //#2 is true
                    break;
                    // specifically a class,alias or struct
                }
                if (current instanceof DLanguageUnaryExpression) {
                    //#2 is true
                    break;
                }
                if (current instanceof DLanguageTypeidExpression) {
                    //#2 is true
                    break;
                }
                if (current instanceof DLanguageIsExpression) {
                    //#2 is true
                    break;
                }
                if (current instanceof DLanguageTraitsArgument) {
                    //#2 is true
                    break;
                }
                if (current == null)
                    break;
                current = current.getParent();
            }
            //default to #2:
            log.info("#2:" + parent.getText());
            return findDefinitionNodesStandard(dLanguageFile, element, element.getName());

        }
        return Collections.emptyList();

    }

    /**
     * for resolving anything that isn't a constructor and doesn't have a long name like foo.bar.gh()
     *
     * @param dLanguageFile
     * @param element
     * @param name
     * @return
     */
    private static List<DNamedElement> findDefinitionNodesStandard(DLanguageFile dLanguageFile, DLanguageIdentifier element, String name) {
        //first check in current file.
        ArrayList<DNamedElement> elements = new ArrayList<>();
        ArrayList<DNamedElement> res = new ArrayList<>();
        elements.addAll(dLanguageFile.getClassDeclarations(true, false, false));
        elements.addAll(dLanguageFile.getStructDeclarations(true, false, false));
        elements.addAll(dLanguageFile.getTemplateDeclarations(true, false, false));
        elements.addAll(dLanguageFile.getGlobalVariableDeclarations(true, false, false));
        elements.addAll(dLanguageFile.getFunctionDeclarations(true, false, false));
        for (DNamedElement dNamedElement : elements) {
            if (dNamedElement.getName().equals(name))
                res.add(dNamedElement);
        }
        //check in element parents
        boolean includeFromInheritance = false;
        PsiElement current = element.getParent();
        while (true) {
            if (current instanceof ClassContainer) {
                ((ClassContainer) current).getClassDeclarations(true, true, true);
            }
            if (current instanceof StructContainer) {
                ((StructContainer) current).getStructDeclarations(true, true, true);
            }
            if (current instanceof TemplateContainer) {
                ((TemplateContainer) current).getTemplateDeclarations(true, true, true);
            }
            if (current instanceof GlobalVariableContainer) {
                ((GlobalVariableContainer) current).getGlobalVariableDeclarations(true, true, true);
            }
            if (current instanceof LocalVarContainer) {
                ((LocalVarContainer) current).getLocalVariableDeclarations(true, true, true);
            }
            if (current instanceof FunctionContainer) {
                ((FunctionContainer) current).getFunctionDeclarations(true, true, true);
            }
            if (current instanceof DLanguageFile)
                break;
            current = current.getParent();
        }
        return res;
    }

    private static List<DNamedElement> findModuleDefinitionNodes(DLanguageFile dLanguageFile, DLanguageModuleFullyQualifiedName module) {
        final String fileName = dLanguageFile.getName();
        final String moduleName = fileName.substring(0, fileName.length() - 2);
        final String name = getEndOfIdentifierList(module).getName();
        if (name.equals(moduleName))
            return Collections.singletonList(dLanguageFile);
        return Collections.emptyList();
    }

    public static Set<DLanguageFile> fromModulesToFiles(Project project, Set<String> modules) {
        Set<DLanguageFile> filesFound = new HashSet<>();
        for (String module : modules) {
            List<DLanguageFile> files = getFilesByModuleName(project, module, GlobalSearchScope.allScope(project));
            filesFound.addAll(files);
        }
        return filesFound;
    }
}
