package net.masterthought.dlanguage.utils;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.index.DModuleIndex;
import net.masterthought.dlanguage.psi.*;
import net.masterthought.dlanguage.psi.references.DReference;
import org.jetbrains.annotations.NotNull;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;

import static com.intellij.psi.util.PsiTreeUtil.*;

/**
 * Created by franc on 1/18/2017.
 */
public class DResolveUtil {
    public static Set<PsiNamedElement> findDefinitionNodes(@NotNull DNamedElement element){
        if(!(element instanceof DLanguageIdentifier))
            return Collections.EMPTY_SET;//prevent resolving definitions
        Set<PsiNamedElement> definitionNodes = new HashSet<>();
        //first try to find the definition in the file, since most definitions like variables will be in the same file
        Set<PsiNamedElement> currentFileResult = findDefinitionNodes((DLanguageFile) element.getContainingFile(), element);
        if(currentFileResult.size() != 0)
            return currentFileResult;
        //not found in current file, proceed to search all files
        Project project = element.getProject();
        Set<String> importedModules = DPsiUtil.parseImports(element.getContainingFile());
        Set<DLanguageFile> filesToSearch = fromModulesToFiles(project,importedModules);
        for (DLanguageFile dLanguageFile : filesToSearch) {
            definitionNodes.addAll(findDefinitionNodes(dLanguageFile,element));
        }
        Set<PsiNamedElement> identifiersToReturn = new HashSet<>();
        for (PsiNamedElement definitionNode : definitionNodes) {
            //there needs to be a better way to do this. implement common interface todo
            if(definitionNode instanceof DLanguageFuncDeclaration){
                identifiersToReturn.add(((DLanguageFuncDeclaration) definitionNode).getIdentifier());
            }
            else if(definitionNode instanceof DLanguageClassDeclaration){
                identifiersToReturn.add(((DLanguageClassDeclaration) definitionNode).getIdentifier());
            }
            else if(definitionNode instanceof DLanguageStructDeclaration){
                identifiersToReturn.add(((DLanguageStructDeclaration) definitionNode).getIdentifier());
            }
            else if(definitionNode instanceof DLanguageTemplateDeclaration){
                identifiersToReturn.add(((DLanguageTemplateDeclaration) definitionNode).getIdentifier());
            }
            else if(definitionNode instanceof DLanguageAliasDeclaration){
                identifiersToReturn.add(((DLanguageAliasDeclaration) definitionNode).getIdentifier());
            }
            else if(definitionNode instanceof DLanguageConstructor){
                identifiersToReturn.add(definitionNode);
            }
            else if(definitionNode instanceof DLanguageDestructor){
                identifiersToReturn.add(definitionNode);
            }
            else
                identifiersToReturn.add(definitionNode);
        }
        return identifiersToReturn;

    }

    private static Set<PsiNamedElement> findDefinitionNodes(DLanguageFile file, PsiNamedElement element){
        final DLanguageNewExpression newExpression = getParentOfType(element, DLanguageNewExpression.class);
        final DLanguageNewExpressionWithArgs newExpressionWithArgs = getParentOfType(element, DLanguageNewExpressionWithArgs.class);
        final DLanguageDeleteExpression deleteExpression = getParentOfType(element, DLanguageDeleteExpression.class);
        final DLanguageExpressionStatement expressionStatement = getParentOfType(element, DLanguageExpressionStatement.class);//an expression statement is some statement terminated by a semicolon
        final DLanguagePrimaryExpression functionUsageParent = getParentOfType(element, DLanguagePrimaryExpression.class);//functions are primary expressions
        if(newExpression != null || newExpressionWithArgs != null){
            if(newExpression != null)
                return findConstructorDefinitionNodes(file,newExpression.getType());
            return findConstructorDefinitionNodes(file,newExpressionWithArgs.getType());
        }
        if(functionUsageParent != null){
            return findFunctionDefinitionNodes(file,functionUsageParent);
        }
        if(expressionStatement != null){
            if(deleteExpression != null){
                return findDestructorDefinitionNodes(file,deleteExpression);
            }
            throw new IllegalStateException();
        }
        else{
            final DLanguageVarDeclarations varDeclarations = getParentOfType(element, DLanguageVarDeclarations.class);// any expression which declares a variable, or initializes a variable. The bnf grammar does not destinguish between x = 0; and int x = 0 ;
            //some kind of variable declaration like x = 1; Class class = new Class(); etc.
            if(deleteExpression != null){
                return findDestructorDefinitionNodes(file,deleteExpression);
            }
            DLanguageTemplateInstance templateInstance = getChildOfType(element.getParent(), DLanguageTemplateInstance.class);
            if(templateInstance != null){
                return findTemplateDefinitionNode(file,templateInstance);
            }
            final DLanguageBasicType type = getParentOfType(element, DLanguageBasicType.class);
            if(type == null)
                return findVariableDefinitionNodes(file,varDeclarations);
            Set<PsiNamedElement> classStructAliasDefinitionNodes = findClassAndStructDefinitionNodes(file, type);
            classStructAliasDefinitionNodes.addAll(findAliasDefinitionNodes(file, type));
            return classStructAliasDefinitionNodes;
        }
    }

//    private static Set<PsiNamedElement> findDefinitionNodes(DLanguageFile fileToSearch,DNamedElement element){
//        PsiElement parent = element;
//        if (element.getParent() instanceof DLanguageIdentifierList){
//            if(((DLanguageIdentifierList)element.getParent()).getTemplateInstance() != null)
//                return findTemplateDefinitionNode(fileToSearch,element);
//        }
//        while (parent != null){
//            if(parent instanceof DLanguageNewExpression || parent instanceof DLanguageNewExpressionWithArgs){
//                //resolving constructor
//                //should resolve to this(){} from new Class()
//                return findConstructorDefinitionNodes(fileToSearch,element);
//            }
//            else if(parent instanceof DLanguageDeleteExpression){
//                //resolving destructor aka should resolve to ~this(){} from delete object;
//                return findDestructorDefinitionNodes(fileToSearch,element);
//            }
//            else if (parent instanceof DLanguageVarDeclarator || parent instanceof DLanguageVarDeclarations || parent instanceof DLanguageAutoDeclarationX || parent instanceof DLanguageAutoDeclarationY){
//                //resolving variable: like such: x= 2; , resolving x
//                return findVariableDefinitionNodes(fileToSearch,element);
//            }
//            if (parent instanceof DLanguagePrimaryExpression){
//                return findFunctionDefinitionNodes(fileToSearch,element);
//            }
//            else if (parent instanceof DLanguageBasicType && parent.getParent() instanceof DLanguageVarDeclarations){
//                //resolving type name aka Cat cat = new Cat(), resolving the Cat class
//                Set<PsiNamedElement> returnValue = new HashSet<>();
//                returnValue.addAll(findClassAndStructDefinitionNodes(fileToSearch,element));
//                returnValue.addAll(findAliasDefinitionNodes(fileToSearch,element));
//                return returnValue;
//            }
//            parent = parent.getParent();
//        }
//        return null;
////        return findByName(fileToSearch,element);
//
//    }

    private static Set<PsiNamedElement> findFunctionDefinitionNodes(DLanguageFile file, DLanguagePrimaryExpression element) {
        Set<PsiNamedElement> returnValue = new HashSet<>();
        Collection<DLanguageFuncDeclaration> funcDeclarations = PsiTreeUtil.findChildrenOfType(file, DLanguageFuncDeclaration.class);
        String elementName = null;
        try {
            elementName = findChildOfType(element, DLanguageIdentifier.class).getName();
        }catch (NullPointerException e){
            throw new IllegalStateException();
        }
        for (DLanguageFuncDeclaration funcDeclaration : funcDeclarations) {
            if(funcDeclaration.getName().equals(elementName))
                returnValue.add(funcDeclaration);
        }
        //todo search arguments of parent functions etc.
        return returnValue;
    }

    private static Set<PsiNamedElement> findByName(DLanguageFile file, DNamedElement element) {
        Set<PsiNamedElement> returnValue = new HashSet<>();
        for (DLanguageIdentifier identifier : PsiTreeUtil.findChildrenOfType(file, DLanguageIdentifier.class)) {
            if(identifier.getName().equals(element.getName())){
                returnValue.add(identifier);
            }
        }
        return returnValue;
    }

    private static Set<PsiNamedElement> findAliasDefinitionNodes(DLanguageFile file, DLanguageBasicType element) {
        Set<PsiNamedElement> results = new HashSet<>();
        String elementName = element.getIdentifierList().getIdentifier().getName();
        Collection<DLanguageAliasDeclaration> aliases = PsiTreeUtil.findChildrenOfType(file, DLanguageAliasDeclaration.class);
        for (DLanguageAliasDeclaration alias : aliases) {
            if(alias.getName().equals(elementName))
                results.add(alias);
        }
        return results;
    }

    private static Set<PsiNamedElement> findClassAndStructDefinitionNodes(DLanguageFile file, DLanguageBasicType type) {
        Set<PsiNamedElement> results = new HashSet<>();
        String elementName = type.getIdentifierList().getIdentifier().getName();
        Collection<DNamedElement> declarations = findChildrenOfAnyType(file, DLanguageClassDeclaration.class,DLanguageStructDeclaration.class);
        for (DNamedElement declaration : declarations) {
            if(declaration.getName().equals(elementName))
                results.add(declaration);
        }
        return results;
    }

    private static Set<PsiNamedElement> findVariableDefinitionNodes(DLanguageFile file, DLanguageVarDeclarations element) {
        //first check for local vars
        Set<PsiNamedElement> returnValue = new HashSet<>();
        //not found in current function so try the whole file:
        Collection<DLanguageVarDeclarations> potentialVarDeclarations = PsiTreeUtil.findChildrenOfType(file, DLanguageVarDeclarations.class);
        Set<DLanguageVarDeclarations> varDeclarations = removeNonInitializer(potentialVarDeclarations);
        //since VarDeclarations doesn't inherit DNamedElement we need to get the identifiers
        for (DLanguageVarDeclarations varDeclaration : varDeclarations) {
            PsiNamedElement identifier = PsiTreeUtil.findChildOfType(varDeclaration, DLanguageIdentifier.class);
            returnValue.add(identifier);
        }
        return returnValue;
    }

    private static Set<DLanguageVarDeclarations> removeNonInitializer(Collection<DLanguageVarDeclarations> possibleDeclarations){
        Set<DLanguageVarDeclarations> returnVal = new HashSet<>();
        for (DLanguageVarDeclarations possibleDeclaration : possibleDeclarations) {
            if(possibleDeclaration.getStorageClasses() != null || possibleDeclaration.getBasicType() != null)
                returnVal.add(possibleDeclaration);
        }
        return returnVal;
    }

    private static Set<PsiNamedElement> findTemplateDefinitionNode(DLanguageFile file, DLanguageTemplateInstance element) {
        Collection<DLanguageTemplateDeclaration> td = PsiTreeUtil.findChildrenOfType(file, DLanguageTemplateDeclaration.class);
        HashSet<PsiNamedElement> psiNamedElements = new HashSet<>();
        for (DLanguageTemplateDeclaration template : td) {
            String elementName = null;
            try {
                elementName = element.getIdentifier().getName();
            } catch (NullPointerException e) {
                elementName = ((DLanguageIdentifierList)element.getParent()).getIdentifier().getName();
            }
            String resolvedName = template.getName();
            if(resolvedName.equals(elementName))
                psiNamedElements.add(template);
        }
        return psiNamedElements;
    }

    private static Set<PsiNamedElement> findDestructorDefinitionNodes(DLanguageFile file, DLanguageDeleteExpression element) {

        Collection<DLanguageDestructor> dd = PsiTreeUtil.findChildrenOfType(file, DLanguageDestructor.class);
        HashSet<PsiNamedElement> psiNamedElements = new HashSet<>();
        String variableToDeleteName = ((DLanguageIdentifier)findChildOfType(element,DLanguageIdentifier.class)).getName();

        String elementName = element.getText();
        for (DLanguageDestructor deallocator : dd) {
            DNamedElement constructorClassOrStructDefinition = DUtil.getParentClassOrStruct(deallocator);
            String resolvedName = constructorClassOrStructDefinition.getName();
            if(resolvedName.equals(elementName))
                psiNamedElements.add(deallocator);
        }
        throw new NotImplementedException();
//        return psiNamedElements;
    }

    private static Set<PsiNamedElement> findConstructorDefinitionNodes(DLanguageFile file, DLanguageType element){
        Collection<DLanguageConstructor> cd = PsiTreeUtil.findChildrenOfType(file, DLanguageConstructor.class);
        HashSet<PsiNamedElement> psiNamedElements = new HashSet<>();
        for (DLanguageConstructor constructor : cd) {
            DLanguageIdentifier identifier = findChildOfType(element, DLanguageIdentifier.class);
            String elementName = identifier.getName();
            DNamedElement constructorClassOrStructDefinition = DUtil.getParentClassOrStruct(constructor);
            String resolvedName = constructorClassOrStructDefinition.getName();
            if(resolvedName.equals(elementName))
                psiNamedElements.add(constructor);
        }
        return psiNamedElements;
    }

    private static Set<DLanguageFile> fromModulesToFiles(Project project,Set<String> modules){
        Set<DLanguageFile> filesFound = new HashSet<>();
        for (String module : modules) {
            List<DLanguageFile> files = DModuleIndex.getFilesByModuleName(project, module, GlobalSearchScope.allScope(project));
            filesFound.addAll(files);
        }
        return filesFound;
    }
}
