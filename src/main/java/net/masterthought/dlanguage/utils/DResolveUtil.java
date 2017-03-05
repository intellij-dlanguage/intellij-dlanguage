package net.masterthought.dlanguage.utils;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.index.DModuleIndex;
import net.masterthought.dlanguage.psi.*;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.intellij.psi.util.PsiTreeUtil.findChildrenOfType;
import static com.intellij.psi.util.PsiTreeUtil.getParentOfType;
import static java.util.Collections.EMPTY_SET;

/**
 * Created by franc on 1/18/2017.
 */
public class DResolveUtil {
    private static final Set<WhatAreWeResolving> allOfTheAbove = new HashSet<WhatAreWeResolving>() {{
        for (WhatAreWeResolving w : WhatAreWeResolving.values()) {
            add(w);
        }
    }};
    private static final Set<WhatAreWeResolving> aType = new HashSet<WhatAreWeResolving>() {{
        add(WhatAreWeResolving.Alias);
        add(WhatAreWeResolving.Template);
        add(WhatAreWeResolving.ClassAndStruct);
    }};
    private static final HashSet<WhatAreWeResolving> classMembers = new HashSet<WhatAreWeResolving>() {{
        add(WhatAreWeResolving.Function);
        add(WhatAreWeResolving.MemberVariable);
    }};

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

    private static Set<PsiNamedElement> findDefinitionNodes(DLanguageFile file, DLanguageIdentifier element) {
        Collection<DLanguageIdentifier> identifiersInFile = PsiTreeUtil.findChildrenOfType(file, DLanguageIdentifier.class);//this is the one expensive operation in resolve process. It seems to be far more efficient/workable to search for all identifiers and then search through these.

        Set<PsiNamedElement> results = new HashSet<PsiNamedElement>();
        for (WhatAreWeResolving whatAreWeResolving : determineWhatToResolved(element)) {
            switch (whatAreWeResolving) {
                case Function:
                    results.addAll(findFunctionDefinitionNodes(element,identifiersInFile));
                    break;
                case Template:
                    results.addAll(findTemplateDefinitionNode(element,identifiersInFile));
                    break;
                case Alias:
                    results.addAll(findAliasDefinitionNodes(element,identifiersInFile));
                    break;
                case Label:
                    results.addAll(findLabelDefinitionNodes(element,identifiersInFile));
                    break;
                case Module:
                    results.addAll(findModuleDefinitionNodes(element,identifiersInFile));
                    break;
                case Variable:
                    results.addAll(findVariableDefinitionNodes(element,identifiersInFile));
                    break;
                case ClassAndStruct:
                    results.addAll(findClassAndStructDefinitionNodes(element,identifiersInFile));
                    break;
                case Constructor:
                    results.addAll(findClassAndStructDefinitionNodes(element,identifiersInFile));
                    break;
                case MemberVariable:
                    results.addAll(findMemberVariableDefinitionNodes(element,identifiersInFile));
                    break;
            }
        }
        return results;
    }

    private static Set<PsiNamedElement> findMemberVariableDefinitionNodes(DLanguageIdentifier element, Collection<DLanguageIdentifier> identifiers) {
        Set<PsiNamedElement> result = new HashSet<PsiNamedElement>();
        for (PsiNamedElement psiNamedElement : findVariableDefinitionNodes(element, identifiers)) {
            final boolean inClass = null != getParentOfType(element, DLanguageClassDeclaration.class);
            final boolean inFunction = null != getParentOfType(element, DLanguageFuncDeclaration.class);
            if((!inFunction) && inClass)
                result.add(psiNamedElement);
        }
        return result;
    }

    private static Set<? extends PsiNamedElement> findLabelDefinitionNodes(DLanguageIdentifier element, Collection<DLanguageIdentifier> identifiersInFile) {
        final String elementName = element.getName();
        Set<PsiNamedElement> result = new HashSet<>();
        for (DLanguageIdentifier identifier : identifiersInFile) {
            if(identifier.getName().equals(elementName) && identifier.getParent() instanceof DLanguageLabeledStatement)
                result.add(identifier);
        }
        return result;
    }

    private static Set<WhatAreWeResolving> determineWhatToResolved(DLanguageIdentifier element) {
        //this method was created by lookking at every usage of the identifier and identifierlist keyword in the bnf, and then determining what should be resolved. This makes for a complicated method, but robust and also should be garumteed bug free.
        final PsiElement parent = element.getParent();
        if (parent instanceof DLanguageIdentifierList) {
            if (parent.getParent() instanceof DLanguageBasicType) {
                if(parent.getParent().getParent().getParent() instanceof DLanguageTemplateArgument)
                    return allOfTheAbove;
                return aType;
            } else if (parent.getParent() instanceof DLanguageLinkageAttribute) {
                return EMPTY_SET;
            } else if (parent.getParent() instanceof DLanguageProtectionAttribute) {
                return EMPTY_SET;
            }else if(parent.getParent() instanceof DLanguageIdentifierList){
                return EMPTY_SET;
            }
        }
        if (parent instanceof DLanguageDotIdentifier) {
            if (parent.getParent() instanceof DLanguageAsmPrimaryExp) {
                return EMPTY_SET;
            }
        }
        if (parent instanceof DLanguageModuleFullyQualifiedName) {
            return EMPTY_SET;
        } else if (parent instanceof DLanguageImport) {
            return new HashSet<WhatAreWeResolving>() {{
                add(WhatAreWeResolving.Module);
            }};
        } else if (parent instanceof DLanguageImportBind) {
            return allOfTheAbove;
        } else if (parent instanceof DLanguageUnaryExpression) {
            return classMembers;
        } else if (parent instanceof DLanguagePostfixExpression) {
            return classMembers;
        } else if (parent instanceof DLanguagePrimaryExpression) {
            return new HashSet<WhatAreWeResolving>() {{
                add(WhatAreWeResolving.MemberVariable);
                add(WhatAreWeResolving.Function);
                add(WhatAreWeResolving.Variable);
            }};
        } else if (parent instanceof DLanguageLambda) {
            return allOfTheAbove;
        } else if (parent instanceof DLanguageIsExpression) {
            return new HashSet<WhatAreWeResolving>() {{
                add(WhatAreWeResolving.Variable);
                add(WhatAreWeResolving.MemberVariable);
                add(WhatAreWeResolving.Alias);
            }};
        } else if (parent instanceof DLanguageLabeledStatement) {
            return EMPTY_SET;
        } else if (parent instanceof DLanguageIfCondition) {
            return EMPTY_SET;
        } else if (parent instanceof DLanguageForeachType) {
            return EMPTY_SET;
        } else if (parent instanceof DLanguageContinueStatement) {
            return new HashSet<WhatAreWeResolving>(){{
                add(WhatAreWeResolving.Label);
            }};
        } else if (parent instanceof DLanguageBreakStatement) {
            return new HashSet<WhatAreWeResolving>(){{
                add(WhatAreWeResolving.Label);
            }};
        } else if (parent instanceof DLanguageGotoStatement) {
            return new HashSet<WhatAreWeResolving>(){{
                add(WhatAreWeResolving.Label);
            }};
        } else if (parent instanceof DLanguageCatchParameter) {
            return EMPTY_SET;
        } else if (parent instanceof DLanguageAsmInstruction) {
            return allOfTheAbove;
        } else if (parent instanceof DLanguageIntegerExpression) {
            return new HashSet<WhatAreWeResolving>(){{
                add(WhatAreWeResolving.Variable);
                add(WhatAreWeResolving.MemberVariable);
            }};
        } else if (parent instanceof DLanguageAliasDeclaration) {
            return EMPTY_SET;
        } else if (parent instanceof DLanguageAliasDeclarationY) {
            return EMPTY_SET;
        } else if (parent instanceof DLanguageAutoDeclarationY){
            final DLanguageAutoDeclaration autoDeclaration = (DLanguageAutoDeclaration)parent.getParent().getParent();
            final DLanguageDeclarationStatement declarationStatement = (DLanguageDeclarationStatement)autoDeclaration.getParent().getParent().getParent();
            if(declarationStatement.getStorageClasses() != null)
                return EMPTY_SET;
            return new HashSet<WhatAreWeResolving>(){{
                add(WhatAreWeResolving.Variable);
                add(WhatAreWeResolving.MemberVariable);
                add(WhatAreWeResolving.Function);
            }};
        } else if (parent instanceof DLanguageVarDeclaratorIdentifier) {
            return new HashSet<WhatAreWeResolving>(){{
                add(WhatAreWeResolving.Variable);
            }};
        } else if (parent instanceof DLanguageAltDeclaratorIdentifier) {
            return new HashSet<WhatAreWeResolving>(){{
                add(WhatAreWeResolving.Variable);
            }};
        } else if (parent instanceof DLanguageVarDeclarator) {
            return new HashSet<WhatAreWeResolving>(){{
                add(WhatAreWeResolving.Variable);
            }};
        } else if (parent instanceof DLanguageAltDeclarator) {
            return new HashSet<WhatAreWeResolving>(){{
                add(WhatAreWeResolving.Variable);
            }};
        } else if (parent instanceof DLanguageAltDeclaratorX) {
            return new HashSet<WhatAreWeResolving>(){{
                add(WhatAreWeResolving.Variable);
            }};
        } else if (parent instanceof DLanguageFuncDeclaration) {
            return new HashSet<WhatAreWeResolving>(){{
                add(WhatAreWeResolving.Function);
            }};
        } else if (parent instanceof DLanguageClassDeclaration) {
            return EMPTY_SET;
        } else if (parent instanceof DLanguageClassTemplateDeclaration) {
            return EMPTY_SET;
        } else if (parent instanceof DLanguageInterfaceDeclaration) {
            return EMPTY_SET;
        } else if (parent instanceof DLanguageInterfaceTemplateDeclaration) {
            return EMPTY_SET;
        } else if (parent instanceof DLanguageStructDeclaration) {
            return EMPTY_SET;
        } else if (parent instanceof DLanguageUnionDeclaration) {
            return EMPTY_SET;
        } else if (parent instanceof DLanguageUnionTemplateDeclaration) {
            return EMPTY_SET;
        } else if (parent instanceof DLanguageAliasThis) {
            return new HashSet<WhatAreWeResolving>(){{
                add(WhatAreWeResolving.Variable);
                add(WhatAreWeResolving.MemberVariable);
            }};
        } else if (parent instanceof DLanguageEnumDeclaration) {
            return EMPTY_SET;
        } else if (parent instanceof DLanguageEnumMember) {
            return EMPTY_SET;
        } else if (parent instanceof DLanguageTemplateDeclaration) {
            return EMPTY_SET;
        } else if (parent instanceof DLanguageTemplateInstance) {
            return new HashSet<WhatAreWeResolving>(){{
                add(WhatAreWeResolving.Template);
            }};
        } else if (parent instanceof DLanguageSymbolTail) {
            return allOfTheAbove;
        } else if (parent instanceof DLanguageTemplateSingleArgument) {
            return allOfTheAbove;
        } else if (parent instanceof DLanguageTemplateTypeParameter) {
            return allOfTheAbove;
        } else if (parent instanceof DLanguageTemplateAliasParameter) {
            return allOfTheAbove;
        } else if (parent instanceof DLanguageTemplateMixinDeclaration) {
            return EMPTY_SET;
        } else if (parent instanceof DLanguageTemplateMixin) {
            return EMPTY_SET;
        } else if (parent instanceof DLanguageQualifiedIdentifierList) {
            return new HashSet<WhatAreWeResolving>(){{add(WhatAreWeResolving.Template);}};
        } else if (parent instanceof DLanguageLinkageAttribute) {
            return EMPTY_SET;
        } else if (parent instanceof DLanguageLinkageType) {
            return EMPTY_SET;
        } else if (parent instanceof DLanguageUserDefinedAttribute) {
            return allOfTheAbove;
        } else if (parent instanceof DLanguagePragma) {
            return allOfTheAbove;
        } else if (parent instanceof DLanguageVersionCondition) {
            return EMPTY_SET;
        } else if (parent instanceof DLanguageDebugCondition) {
            return EMPTY_SET;
        } else if (parent instanceof DLanguageVersionSpecification) {
            return EMPTY_SET;
        } else if (parent instanceof DLanguageDebugSpecification) {
            return EMPTY_SET;
        }
        throw new IllegalStateException("this really shouldn't happen");
    }

    private static Set<PsiNamedElement> findModuleDefinitionNodes(DLanguageIdentifier element, Collection<DLanguageIdentifier> identifiers) {
        Set<PsiNamedElement> result = new HashSet<>();
        final String elementName = element.getName();
        for (DLanguageIdentifier identifier : identifiers) {
            if (identifier.getName().equals(elementName) && identifier.getParent().getParent() instanceof DLanguageGlobalDeclaration)
                result.add(identifier);
        }
        return result;

    }

    private static Set<PsiNamedElement> findFunctionDefinitionNodes(DLanguageIdentifier element, Collection<DLanguageIdentifier> identifiers) {
        Set<PsiNamedElement> returnValue = new HashSet<>();
        String elementName = element.getName();
        for (DLanguageIdentifier identifier : identifiers) {
            if (elementName.equals(identifier.getName()) && identifier.getParent() instanceof DLanguageFuncDeclaration) {
                returnValue.add(identifier);
                //todo check the arguments and match
            }
        }
        return returnValue;
    }


/*
    private static Set<PsiNamedElement> findDefinitionNodes(DLanguageFile file,DLanguageIdentifier element){
        Collection<DLanguageIdentifier> identifiersInFile = PsiTreeUtil.findChildrenOfType(file, DLanguageIdentifier.class);//this is the one expensive operation in resolve process. It seems to be far more efficient/workable to search for all identifiers and then search through these.
        //since we want to return identifiers this is more practical that searching for declarations and then getting there identifiers.
        //searching for identifiers instead of declarations is also more robust in the event of bad parsing(the majority of the standard library is not parsing correctly).

        final DLanguageDeclDef declDef = getParentOfType(element, DLanguageDeclDef.class);
        final DLanguageStatement statement = getParentOfType(element, DLanguageStatement.class);
        final DLanguageNewExpression newExpression = getParentOfType(element, DLanguageNewExpression.class);
        final DLanguageNewExpressionWithArgs newExpressionWithArgs = getParentOfType(element, DLanguageNewExpressionWithArgs.class);
        final DLanguageDeleteExpression deleteExpression = getParentOfType(element, DLanguageDeleteExpression.class);
        final DLanguageExpressionStatement expressionStatement = getParentOfType(element, DLanguageExpressionStatement.class);//an expression statement is some statement terminated by a semicolon
        final DLanguagePrimaryExpression functionUsageParent = getParentOfType(element, DLanguagePrimaryExpression.class);//functions are primary expressions
        final DLanguageVarDeclarations varDeclarations = getParentOfType(element, DLanguageVarDeclarations.class);// any expression which declares a variable, or initializes a variable. The bnf grammar does not destinguish between x = 0; and int x = 0 ;
        final DLanguageTemplateInstance templateInstance = getChildOfType(element.getParent(), DLanguageTemplateInstance.class);
        final boolean inNewExpression = newExpression != null;
        final boolean inNewExpressionWithArgs = newExpressionWithArgs != null;
        final boolean inDeleteExpression = deleteExpression != null;
        final boolean inExpressionStatement = expressionStatement != null;
        final boolean inFunctionUsageParent = functionUsageParent != null;
        final boolean inVarDeclarations = varDeclarations != null;
        final boolean inTemplateInstance = templateInstance != null;
        final boolean inStatment = statement != null;
        final boolean inDeclDef = declDef != null;
        if(inStatment && inDeclDef){
            //determine which
        }
        else if(inStatment){
            //case statement
                //resolving the constant/label
            //no case statement
                //label statement. aka for gotos
                //DeclarationStatement:
                    //resolving the type in the storage class
                    //enum declaration
                        //resolving definition right hand side initialization of enum member
                    //function declaration
                        //inside the body
                        //inside a lambda
                    //alias declaration
                        //resolving member of right hand side
                    //aggregate declaration
                        //
                    //import declaration
                        //resolving within
                    //template declaration//contains a DeclDef body
                        //Template parameters within
                        //within constraint
                        //within main body
                    //var declaration
                        //resolving the identifier in the Declaration
                        //resolving the right hand side
                //expression statement
                    //expression with ;
                        //resolving the lhs or rhs.
                //if statement
                    //either within or resolving the condition
                //while statement, same as if
                //do statement
                    //resolving do while statement condition
                //for statement. resolving a variable within the for statement or for statement condition
                //foreach statement. resolving the array or something that creates an array. foreach contains an expression for the array so no need to you use this
                //switch statement. probably resolving the expression within switch\(\)
                //final switch and switch are same
                //continue statement has optional identifier. That identifier must be a goto label
                //break statement is same as goto
                //return statement contains optional expression. Will be resolving within that expression
                //goto statement
                    //contains a identifier, which will point to a label
                    // could contain the following goto case expression. where expression will evaluate to a switch branch
                //with statement
                    //either in the body or:
                    //when within a parentheses could be an expression or template instance.
                //synchronized statement
                    //in body
                    // parenthes may contain an expression whicvh will evualuate to a class
                //try statement. must be resolving witth in body
                //catch: catchParameter contains variable declaration.

        }
        else if(inDeclDef){

        }
        else {
            //rsolving module?
        }
    }
*/

/*
    private static Set<PsiNamedElement> findDefinitionNodes(DLanguageFile file, PsiNamedElement element) {
        Collection<DLanguageIdentifier> identifiersInFile = PsiTreeUtil.findChildrenOfType(file, DLanguageIdentifier.class);//this is the one expensive operation in resolve process. It seems to be far more efficient/workable to search for all identifiers and then search through these.
        //since we want to return identifiers this is more practical that searching for declarations and then getting there identifiers.
        //searching for identifiers instead of declarations is also more robust in the event of bad parsing(the majority of the standard library is not parsing correctly).

        final DLanguageNewExpression newExpression = getParentOfType(element, DLanguageNewExpression.class);
        final DLanguageNewExpressionWithArgs newExpressionWithArgs = getParentOfType(element, DLanguageNewExpressionWithArgs.class);
        final DLanguageDeleteExpression deleteExpression = getParentOfType(element, DLanguageDeleteExpression.class);
        final DLanguageExpressionStatement expressionStatement = getParentOfType(element, DLanguageExpressionStatement.class);//an expression statement is some statement terminated by a semicolon
        final DLanguagePrimaryExpression functionUsageParent = getParentOfType(element, DLanguagePrimaryExpression.class);//functions are primary expressions
        final DLanguageVarDeclarations varDeclarations = getParentOfType(element, DLanguageVarDeclarations.class);// any expression which declares a variable, or initializes a variable. The bnf grammar does not destinguish between x = 0; and int x = 0 ;
        final DLanguageTemplateInstance templateInstance = getChildOfType(element.getParent(), DLanguageTemplateInstance.class);
        final boolean inNewExpression = newExpression != null;
        final boolean inNewExpressionWithArgs = newExpressionWithArgs != null;
        final boolean inDeleteExpression = deleteExpression != null;
        final boolean inExpressionStatement = expressionStatement != null;
        final boolean inFunctionUsageParent = functionUsageParent != null;
        final boolean inVarDeclarations = varDeclarations != null;
        final boolean inTemplateInstance = templateInstance != null;
        if (inDeleteExpression) {
            return findVariableDefinitionNodes(findChildOfType(deleteExpression, DLanguageIdentifier.class), identifiersInFile);
        }
        if (inNewExpression || inNewExpressionWithArgs) {
            if (inNewExpression)
                return findConstructorDefinitionNodes(newExpression.getType(), identifiersInFile);
            return findConstructorDefinitionNodes(newExpressionWithArgs.getType(), identifiersInFile);
        }
        if (element.getParent() instanceof DLanguageTemplateInstance)
            return findTemplateDefinitionNode((DLanguageTemplateInstance) element.getParent(), identifiersInFile);
        if (inFunctionUsageParent) {
            return findFunctionDefinitionNodes(functionUsageParent, identifiersInFile);
        }
        if (inExpressionStatement) {
            throw new IllegalStateException();
        } else {
            //some kind of variable declaration like x = 1; Class class = new Class(); etc.
            if (inTemplateInstance) {
                return findTemplateDefinitionNode(templateInstance, identifiersInFile);
            }
            final DLanguageBasicType type = getParentOfType(element, DLanguageBasicType.class);
            final boolean inType = type != null;
            if (inType) {
                if (inVarDeclarations) {
                    // if we are in here this means that we are likely on the wrong side of an alias declaration. aka alias Classp = <cursor>Class *;, but we are trying to resolve Classp;
                    return new HashSet<PsiNamedElement>() {{
                        add(element);
                    }};
                }
                return findVariableDefinitionNodes(findChildOfType(varDeclarations, DLanguageIdentifier.class), identifiersInFile);
            }
            Set<PsiNamedElement> classStructAliasDefinitionNodes = findClassAndStructDefinitionNodes(type, identifiersInFile);
            classStructAliasDefinitionNodes.addAll(findAliasDefinitionNodes(type, identifiersInFile));
            return classStructAliasDefinitionNodes;
        }
    }
*/

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

    private static Set<PsiNamedElement> findAliasDefinitionNodes(DLanguageIdentifier element, Collection<DLanguageIdentifier> identifiers) {
        Set<PsiNamedElement> results = new HashSet<>();
        String elementName = element.getName();
        for (DLanguageIdentifier identifier : identifiers) {
            if (identifier.getName().equals(elementName) && identifier.getParent() instanceof DLanguageAliasDeclaration)
                results.add(identifier);
        }
        return results;
    }

    private static Set<PsiNamedElement> findClassAndStructDefinitionNodes(@NotNull DLanguageIdentifier element, Collection<DLanguageIdentifier> identifiers) {
        Set<PsiNamedElement> results = new HashSet<>();
        String elementName = element.getName();
        for (DLanguageIdentifier identifier : identifiers) {
            if (identifier.getName().equals(elementName) && (identifier.getParent() instanceof DLanguageClassDeclaration || identifier.getParent() instanceof DLanguageStructDeclaration))
                results.add(identifier);
        }
        return results;
    }

    private static Set<PsiNamedElement> findVariableDefinitionNodes(DLanguageIdentifier element, Collection<DLanguageIdentifier> identifiers) {
        //first check for local vars
        Set<PsiNamedElement> returnValue = new HashSet<>();
        String elementName = element.getName();
        for (DLanguageIdentifier identifier : identifiers) {
            if (elementName.equals(identifier.getName())) {
                boolean inVarDeclarator = null != getParentOfType(identifier, DLanguageVarDeclarator.class);
                boolean inVarDeclarations = null != getParentOfType(identifier,DLanguageVarDeclarations.class);
//                todo args
                if (inVarDeclarator)
                    returnValue.add(identifier);
            }
        }
        return returnValue;
    }

    private static Set<PsiNamedElement> findTemplateDefinitionNode(DLanguageIdentifier element, Collection<DLanguageIdentifier> identifiers) {
        HashSet<PsiNamedElement> psiNamedElements = new HashSet<>();
        String elementName = element.getName();
        for (DLanguageIdentifier identifier : identifiers) {
            if (identifier.getName().equals(elementName)) {
                if (identifier.getParent() instanceof DLanguageTemplateDeclaration) {
                    psiNamedElements.add(identifier);
                }
            }
        }
        return psiNamedElements;
    }

    private static Set<PsiNamedElement> findConstructorDefinitionNodes(DLanguageIdentifier element, Collection<DLanguageIdentifier> identifiersInFile) {
        HashSet<PsiNamedElement> psiNamedElements = new HashSet<>();
        String elementName = element.getName();
        Set<DLanguageIdentifier> potentialDeclarations = getMatchingName(identifiersInFile, elementName);
        for (DLanguageIdentifier potentialDeclaration : potentialDeclarations) {
            if (potentialDeclaration.getParent() instanceof DLanguageClassDeclaration || potentialDeclaration.getParent() instanceof DLanguageStructDeclaration) {
                Collection<DLanguageConstructor> constructors = findChildrenOfType(potentialDeclaration.getParent(), DLanguageConstructor.class);
                psiNamedElements.addAll(constructors);
            }
        }
        return psiNamedElements;
    }

    private static Set<DLanguageIdentifier> getMatchingName(Collection<DLanguageIdentifier> identifiersInFile, String name) {
        Set<DLanguageIdentifier> res = new HashSet<DLanguageIdentifier>();
        for (DLanguageIdentifier identifier : identifiersInFile) {
            if (identifier.getName().equals(name))
                res.add(identifier);
        }
        return res;
    }

    public static Set<DLanguageFile> fromModulesToFiles(Project project, Set<String> modules) {
        Set<DLanguageFile> filesFound = new HashSet<>();
        for (String module : modules) {
            List<DLanguageFile> files = DModuleIndex.getFilesByModuleName(project, module, GlobalSearchScope.allScope(project));
            filesFound.addAll(files);
        }
        return filesFound;
    }

    private enum WhatAreWeResolving {
        Function,
        Template,
        Alias,
        Label,
        Module,
        Variable,
        ClassAndStruct,
        Constructor,
        MemberVariable//it is impossible to distinguish between a member variable and a function because of ucfs and @property functions. for this reason searchs for member vars must always include searchs for member functions
    }
}
