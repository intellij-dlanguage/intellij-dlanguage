package net.masterthought.dlanguage.psi.references;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.IncorrectOperationException;
import com.intellij.util.containers.ContainerUtil;
import net.masterthought.dlanguage.index.DModuleIndex;
import net.masterthought.dlanguage.psi.*;
import net.masterthought.dlanguage.psi.impl.DPsiImplUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

import static net.masterthought.dlanguage.utils.DUtil.findDefinitionNode;

/**
 * Resolves references to elements.
 */
public class DReference extends PsiReferenceBase<PsiNamedElement> implements PsiPolyVariantReference {
    public static final ResolveResult[] EMPTY_RESOLVE_RESULT = new ResolveResult[0];
    private String name;

    public DReference(@NotNull PsiNamedElement element, TextRange textRange) {
        super(element, textRange);
        name = element.getName();
    }

    /**
     * Resolves references to a set of results.
     */
    @NotNull
    @Override
    public ResolveResult[] multiResolve(boolean incompleteCode) {
//        // We should only be resolving ddfunction_definition.
//        if (!(myElement instanceof DDefinitionFunction)) {
//            return EMPTY_RESOLVE_RESULT;
//        }
//        // Make sure that we only complete the last conid in a qualified expression.
//        if (myElement instanceof HaskellConid) {
//            // Don't resolve a module import to a constructor.
//            if (PsiTreeUtil.getParentOfType(myElement, HaskellImpdecl.class) != null) { return EMPTY_RESOLVE_RESULT; }
//            HaskellQconid qconid = PsiTreeUtil.getParentOfType(myElement, HaskellQconid.class);
//            if (qconid == null) { return EMPTY_RESOLVE_RESULT; }
//            if (!myElement.equals(Iterables.getLast(qconid.getConidList()))) { return EMPTY_RESOLVE_RESULT; }
//        }
        Project project = myElement.getProject();
        final List<PsiNamedElement> namedElements = findDefinitionNode(project, name, myElement);
        // Guess 20 variants tops most of the time in any real code base.
        final Collection<PsiElement> identifiers = new HashSet<>();
        for (PsiElement namedElement : namedElements) {
            if(namedElement instanceof DLanguageFuncDeclaration){
                identifiers.add(((DLanguageFuncDeclaration) namedElement).getIdentifier());
            }
            else if(namedElement instanceof DLanguageTemplateDeclaration){
                identifiers.add(((DLanguageTemplateDeclaration) namedElement).getIdentifier());
            }
            else if(namedElement instanceof DLanguageClassDeclaration){
                if(((DLanguageClassDeclaration) namedElement).getIdentifier() != null) {
                    identifiers.add(((DLanguageClassDeclaration) namedElement).getIdentifier());
                }
                else if(((DLanguageClassDeclaration) namedElement).getClassTemplateDeclaration().getIdentifier() != null) {
                    identifiers.add(((DLanguageClassDeclaration) namedElement).getClassTemplateDeclaration().getIdentifier());
                }
            }
            else if(namedElement instanceof DLanguageUnionDeclaration){
                if(((DLanguageUnionDeclaration) namedElement).getIdentifier() != null) {
                    identifiers.add(((DLanguageUnionDeclaration) namedElement).getIdentifier());
                }
                else if(((DLanguageUnionDeclaration) namedElement).getUnionTemplateDeclaration().getIdentifier() != null) {
                    identifiers.add(((DLanguageUnionDeclaration) namedElement).getUnionTemplateDeclaration().getIdentifier());
                }
            }
            else if(namedElement instanceof DLanguageInterfaceDeclaration){
                if(((DLanguageInterfaceDeclaration) namedElement).getIdentifier() != null) {
                    identifiers.add(((DLanguageInterfaceDeclaration) namedElement).getIdentifier());
                }
                else if(((DLanguageInterfaceDeclaration) namedElement).getInterfaceTemplateDeclaration().getIdentifier() != null) {
                    identifiers.add(((DLanguageInterfaceDeclaration) namedElement).getInterfaceTemplateDeclaration().getIdentifier());
                }
            }
            else if(namedElement instanceof DLanguageStructDeclaration){
                if(((DLanguageStructDeclaration) namedElement).getIdentifier() != null) {
                    identifiers.add(((DLanguageStructDeclaration) namedElement).getIdentifier());
                }
            }
            else if(namedElement instanceof DLanguageEnumDeclaration){
                identifiers.add(((DLanguageEnumDeclaration) namedElement).getIdentifier());
            }
            else if(namedElement instanceof DLanguageAutoDeclarationY){
                identifiers.add(((DLanguageAutoDeclarationY) namedElement).getIdentifier());
            }
            else if(namedElement instanceof DLanguageAliasDeclaration){
                if(((DLanguageAliasDeclaration) namedElement).getIdentifier() != null) {
                    identifiers.add(((DLanguageAliasDeclaration) namedElement).getIdentifier());
                }
                else if(((DLanguageAliasDeclaration) namedElement).getAliasDeclarationX() != null) {
                    identifiers.add(((DLanguageAliasDeclaration) namedElement).getAliasDeclarationX().getAliasDeclarationY().getIdentifier());
                }
                else if(((DLanguageAliasDeclaration) namedElement).getAliasDeclarationX() != null) {
                    identifiers.add(((DLanguageAliasDeclaration) namedElement).getAliasDeclarationX().getAliasDeclarationY().getIdentifier());
                }
            }
            else if(namedElement instanceof DLanguageDeclaratorInitializer){
                if(((DLanguageDeclaratorInitializer) namedElement).getAltDeclarator() != null &&
                    ((DLanguageDeclaratorInitializer) namedElement).getAltDeclarator().getIdentifier() != null){
                    identifiers.add(((DLanguageDeclaratorInitializer) namedElement).getAltDeclarator().getIdentifier());
                }
                else if(((DLanguageDeclaratorInitializer) namedElement).getVarDeclarator() != null){
                    identifiers.add(((DLanguageDeclaratorInitializer) namedElement).getVarDeclarator().getIdentifier());
                }
            }
            else {
                identifiers.add(namedElement);
            }
        }

        identifiers.remove(myElement);

        List<ResolveResult> results = new ArrayList<ResolveResult>(20);
        for (PsiElement property : identifiers) {
            //noinspection ObjectAllocationInLoop
            results.add(new PsiElementResolveResult(property));
        }
        return results.toArray(new ResolveResult[results.size()]);
    }

    /**
     * Resolves references to a single result, or fails.
     */
    @Nullable
    @Override
    public PsiElement resolve() {
        ResolveResult[] resolveResults = multiResolve(false);
        return resolveResults.length == 1 ? resolveResults[0].getElement() : null;
    }

    /**
     * Controls what names that get added to the autocompletion popup available
     * on ctrl-space.
     */
    @NotNull
    @Override
    public Object[] getVariants() {
//        // If we are not in an expression, don't provide reference completion.
//        if (PsiTreeUtil.getParentOfType(myElement, HaskellExp.class) == null) {
//            return new Object[]{};
//        }
//        // If we are in a qualified name, don't provide reference completion.
//        final PsiElement qId = PsiTreeUtil.getParentOfType(myElement, HaskellQconid.class, HaskellQvarid.class);
//        if (qId != null && qId.textContains('.')) {
//            return new Object[]{};
//        }

//        final PsiFile containingFile = myElement.getContainingFile();
//               if (!(containingFile instanceof DLanguageFile)) {
//                   return new Object[]{};
//               }
//        int offset = myElement.getTextOffset();
//
//        DCDCompletion dcdCompletion = new DCDCompletion();
//        return dcdCompletion.autoComplete(offset,containingFile).toArray();

//        final PsiFile containingFile = myElement.getContainingFile();
//        if (!(containingFile instanceof DLanguageFile)) {
//            return new Object[]{};
//        }
//        List<PsiNamedElement> namedNodes = DUtil.findDefinitionNodes((DLanguageFile)containingFile);
//        List<String> variants = new ArrayList<String>(20);
//        for (final PsiNamedElement namedElement : namedNodes) {
//            variants.add(namedElement.getName());
//        }
//        return variants.toArray();
        PsiNamedElement e = getElement();
        Project project = e.getProject();
        final Set<String> potentialModules =
            DPsiUtil.parseImports(e.getContainingFile());

        List<PsiNamedElement> declarations = ContainerUtil.newArrayList();
        final PsiFile psiFile = e.getContainingFile().getOriginalFile();
        // find definition in current file
        if (psiFile instanceof DLanguageFile) {
            findDefinitionNode((DLanguageFile) psiFile, null, e, declarations);
            declarations.addAll(PsiTreeUtil.findChildrenOfType(psiFile, DLanguageIdentifier.class));
        }
        // find definition in imported files
        for (String potentialModule : potentialModules) {
            List<DLanguageFile> files = DModuleIndex.getFilesByModuleName(project, potentialModule, GlobalSearchScope.allScope(project));
            for (DLanguageFile f : files) {
                findDefinitionNode(f, null, e, declarations);
            }
        }
        ArrayList<String> result = new ArrayList<>();
        int i = 0;
        for (PsiNamedElement declaration : declarations) {
            result.add(declaration.getName());
        }
        result.add("abstract");
        result.add("alias");
        result.add("align");
        result.add("asm");
        result.add("assert");
        result.add("auto");
        result.add("body");
        result.add("bool");
        result.add("break");
        result.add("byte");
        result.add("case");
        result.add("cast");
        result.add("catch");
        result.add("cdouble");
        result.add("cent");
        result.add("cfloat");
        result.add("char");
        result.add("class");
        result.add("const");
        result.add("continue");
        result.add("creal");
        result.add("dchar");
        result.add("debug");
        result.add("default");
        result.add("delegate");
        result.add("delete");
        result.add("deprecated");
        result.add("do");
        result.add("double");
        result.add("else");
        result.add("enum");
        result.add("export");
        result.add("extern");
        result.add("false");
        result.add("final");
        result.add("finally");
        result.add("float");
        result.add("for");
        result.add("foreach");
        result.add("foreach_reverse");
        result.add("function");
        result.add("goto");
        result.add("idouble");
        result.add("if");
        result.add("ifloat");
        result.add("immutable");
        result.add("import");
        result.add("in");
        result.add("inout");
        result.add("int");
        result.add("interface");
        result.add("invariant");
        result.add("ireal");
        result.add("is");
        result.add("lazy");
        result.add("long");
        result.add("mixin");
        result.add("module");
        result.add("new");
        result.add("nothrow");
        result.add("null");
        result.add("out");
        result.add("override");
        result.add("package");
        result.add("pragma");
        result.add("private");
        result.add("protected");
        result.add("public");
        result.add("pure");
        result.add("real");
        result.add("ref");
        result.add("return");
        result.add("scope");
        result.add("shared");
        result.add("short");
        result.add("static");
        result.add("struct");
        result.add("super");
        result.add("switch");
        result.add("synchronized");
        result.add("template");
        result.add("this");
        result.add("throw");
        result.add("true");
        result.add("try");
        result.add("typedef");
        result.add("typeid");
        result.add("typeof");
        result.add("ubyte");
        result.add("ucent");
        result.add("uint");
        result.add("ulong");
        result.add("union");
        result.add("unittest");
        result.add("ushort");
        result.add("version");
        result.add("void");
        result.add("volatile");
        result.add("wchar");
        result.add("while");
        result.add("with");
        result.add("__FILE__");
        result.add("__FILE_FULL_PATH__");
        result.add("__MODULE__");
        result.add("__LINE__");
        result.add("__FUNCTION__");
        result.add("__PRETTY_FUNCTION__");
        result.add("__gshared");
        result.add("__traits");
        result.add("__vector");
        result.add("__parameters");
        return result.toArray();
    }

    @Override
    public TextRange getRangeInElement() {
        return new TextRange(0, this.getElement().getNode().getTextLength());
    }

    /**
     * Called when renaming refactoring tries to rename the Psi tree.
     */
    @Override
    public PsiElement handleElementRename(final String newName)  throws IncorrectOperationException {
        PsiElement element;
        if (myElement instanceof DLanguageIdentifier) {
            element = DPsiImplUtil.setName((DLanguageIdentifier) myElement, newName);
            if (element != null) return element;
            throw new IncorrectOperationException("Cannot rename " + name + " to " + newName);
        }
        if (myElement instanceof DLanguageFuncDeclaration) {
            element = DPsiImplUtil.setName((DLanguageIdentifier) myElement, newName);
            if (element != null) return element;
            throw new IncorrectOperationException("Cannot rename " + name + " to " + newName);
        }
        return super.handleElementRename(newName);
    }
}

