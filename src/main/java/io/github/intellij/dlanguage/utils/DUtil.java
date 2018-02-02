package io.github.intellij.dlanguage.utils;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageClassDeclaration;
import io.github.intellij.dlanguage.psi.DLanguageIdentifierChain;
import io.github.intellij.dlanguage.psi.DLanguageIdentifierOrTemplateChain;
import io.github.intellij.dlanguage.psi.DLanguageIdentifierOrTemplateInstance;
import io.github.intellij.dlanguage.psi.DLanguageTemplateMixinExpression;
import io.github.intellij.dlanguage.psi.named.DlangFunctionDeclaration;
import io.github.intellij.dlanguage.psi.named.DlangIdentifier;
import io.github.intellij.dlanguage.psi.named.DlangInterfaceOrClass;
import io.github.intellij.dlanguage.psi.named.DlangStructDeclaration;
import io.github.intellij.dlanguage.psi.named.DlangTemplateDeclaration;
import io.github.intellij.dlanguage.psi.named.DlangUnionDeclaration;
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement;
import io.github.intellij.dlanguage.psi.interfaces.Declaration;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * General util class. Provides methods for finding named nodes in the Psi tree.
 */
public class DUtil {

//    public static Map<Boolean, PsiElement> findElementInParent(PsiElement identifier, Class className) {
//        PsiElement result = findParentOfType(identifier, className);
//        Map<Boolean, PsiElement> map = new HashMap<>();
//        map.put(result != null, result);
//        return map;
//    }

    public static Boolean elementHasParentFor(final Map<Boolean, PsiElement> result) {
        return result.containsKey(true);
    }

    public static PsiElement getElementFor(final Map<Boolean, PsiElement> result) {
        return (PsiElement) result.values().toArray()[0];
    }


    /**
     * Tells whether a named node is a definition node based on its context.
     * <p/>
     * Precondition: Element is in a DLanguage file.
     */
    public static boolean definitionNode(@NotNull final PsiNamedElement e) {
        if (e instanceof DlangIdentifier) return definitionNode((DlangIdentifier) e);
        return e instanceof Declaration;
    }

    public static boolean definitionNode(@NotNull final DlangIdentifier e) {
        return true;
    }

//    public static boolean definitionNode(@NotNull DDefinitionClass e) {
//        return true;
//    }


    /**
     * Tells whether a node is a definition node based on its context.
     */
    public static boolean definitionNode(@NotNull final ASTNode node) {
        final PsiElement element = node.getPsi();
        return element instanceof PsiNamedElement && definitionNode((PsiNamedElement) element);
    }

    public static boolean isNotNullOrEmpty(final String str) {
        return (str != null && !str.isEmpty());
    }

//    @Nullable
//    public static String getQualifiedPrefix(@NotNull PsiElement e) {
//        final PsiElement q = PsiTreeUtil.getParentOfType(e, DLanguageFunctionDeclaration.class);
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


    public static boolean isDunitTestFile(final PsiFile psiFile) {
        final Collection<DLanguageClassDeclaration> cds = PsiTreeUtil.findChildrenOfType(psiFile, DLanguageClassDeclaration.class);
        for (final DLanguageClassDeclaration cd : cds) {
            // if a class contains the UnitTest mixin assume its a valid d-unit test class
            final Collection<DLanguageTemplateMixinExpression> tmis = PsiTreeUtil.findChildrenOfType(cd, DLanguageTemplateMixinExpression.class);
            for (final DLanguageTemplateMixinExpression tmi : tmis) {
                if (tmi.getText().contains("UnitTest")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param namedElement constructor, or method contained within a class or struct
     * @return the class or struct containing this constructor/method. returns null if not found
     */
    public static DNamedElement getParentClassOrStructOrTemplateOrInterfaceOrUnion(final PsiElement namedElement) {
        return PsiTreeUtil.getParentOfType(namedElement, DlangInterfaceOrClass.class, DlangStructDeclaration.class, DlangTemplateDeclaration.class, DlangUnionDeclaration.class);
    }

    public static DlangFunctionDeclaration getParentFunction(final PsiElement namedElement) {
        return PsiTreeUtil.getParentOfType(namedElement, DlangFunctionDeclaration.class);
    }

//    public static boolean isPublic(DNamedElement symbol) {
//        //search for "public:" and "public{}"
//        final DLanguageProtectionAttribute protectionAttribute = findChildOfType(symbol, DLanguageProtectionAttribute.class);
//        try {
//            if (protectionAttribute.getText().equals("public")) {
//                return true;
//            }
//        } catch (NullPointerException ignored) {
//        }
//        return searchForPublicWrapper(symbol);
//    }
//
//    private static boolean searchForPublicWrapper(DNamedElement symbol) {
//        return searchForPublic(symbol);
//    }
//
//    private static boolean searchForPublic(PsiElement symbol) {
//        if (symbol instanceof DLanguageAttributeSpecifier)
//            if (((DLanguageAttributeSpecifier) symbol).getAttribute().getProtectionAttribute() != null && ((DLanguageAttributeSpecifier) symbol).getAttribute().getProtectionAttribute().getText().equals("public"))
//                return true;
//        if (symbol instanceof DLanguageClassDeclaration || symbol instanceof DLanguageTemplateInstance || symbol instanceof DLanguageModuleDeclaration || symbol instanceof DLanguageFunctionDeclaration || symbol instanceof DLanguageInterface || symbol instanceof DlangStructDeclaration)
//            return false;
//        if (symbol == null)
//            return false;
//        if (null != findChildOfType(symbol, DLanguageModuleDeclaration.class))
//            return false;
//        return searchForPublic(symbol.getParent());
//    }

//    public static <T extends HasVisibility> List<T> getPublicElements(final List<T> elements) {
//        final List<T> res = new ArrayList<>();
//        for (final T element : elements) {
//            if (element.isPublic()) {
//                res.add(element);
//            }
//        }
//        return res;
//    }

//    public static <T extends HasVisibility> List<T> getProtectedElements(final List<T> elements) {
//        final List<T> res = new ArrayList<>();
//        for (final T element : elements) {
//            if (element.isPublic()) {
//                res.add(element);
//            }
//        }
//        return res;
//    }
//
//    public static <T extends HasVisibility> List<T> getPrivateElements(final List<T> elements) {
//        final List<T> res = new ArrayList<>();
//        for (final T element : elements) {
//            if (element.isPublic()) {
//                res.add(element);
//            }
//        }
//        return res;
//    }

    @NotNull
    public static PsiElement getTopLevelOfRecursiveElement(final PsiElement element, final Class<? extends PsiElement> tClass) {
        if (!tClass.isInstance(element.getParent()))
            return element;
        return getTopLevelOfRecursiveElement(element.getParent(), tClass);
    }

//    @NotNull
//    public static DlangIdentifier getEndOfIdentifierList(DLanguageQualifiedIdentifierList list) {
//        return (DlangIdentifier) (list.getChildren()[list.getChildren().length - 1]);//if not identifier through
//    }
//
//    @NotNull
//    public static DlangIdentifier getEndOfIdentifierList(DLanguageModuleFullyQualifiedName list) {
//        if (list.getModuleFullyQualifiedName() == null) {
//            return list.getIdentifier();
//        }
//        return getEndOfIdentifierList(list.getModuleFullyQualifiedName());
//    }
//
//    @NotNull
//    public static DlangIdentifier getEndOfIdentifierList(DLanguageIdentifierList list) {
//        if (list.getIdentifierList() == null) {
//            return list.getIdentifier();
//        }
//        return getEndOfIdentifierList(list.getIdentifierList());
//    }

//    static List<Mixin> getMixins(PsiElement elementToSearch) {
//        List<Mixin> mixins = new ArrayList<>();
//        if (elementToSearch instanceof DLanguageMixinDeclaration) {
//            final DLanguageMixinDeclaration mixin = (DLanguageMixinDeclaration) elementToSearch;
//            mixins.add(mixin);
//        }
//        if (elementToSearch instanceof DLanguageTemplateMixin) {
//            final DLanguageTemplateMixin mixin = (DLanguageTemplateMixin) elementToSearch;
//            mixins.add(mixin);
//        }
//        if (elementToSearch instanceof DLanguageMixinExpression) {
//            final DLanguageMixinExpression mixin = (DLanguageMixinExpression) elementToSearch;
//            mixins.add(mixin);
//        }
//        if (elementToSearch instanceof DLanguageMixinStatement) {
//            final DLanguageMixinStatement mixin = (DLanguageMixinStatement) elementToSearch;
//            mixins.add(mixin);
//        }
//        return mixins;
//    }

//    public static HasVisibility.Visibility protectionToVisibilty(final DLanguageAttribute protectionAttribute) {
//        final String text = protectionAttribute.getText();
//        if (text.equals("private"))
//            return HasVisibility.Visibility.private_;
//        if (text.equals("public"))
//            return HasVisibility.Visibility.public_;
//        if (text.equals("protected"))
//            return HasVisibility.Visibility.protected_;
//        throw new IllegalArgumentException(protectionAttribute.toString() + protectionAttribute.getText());
//    }
//
//    public static HasVisibility.Visibility protectionToVisibilty(final String text) {
//        if (text.equals("private"))
//            return HasVisibility.Visibility.private_;
//        if (text.equals("public"))
//            return HasVisibility.Visibility.public_;
//        if (text.equals("protected"))
//            return HasVisibility.Visibility.protected_;
//        throw new IllegalArgumentException(text);
//
//    }

    public static DlangIdentifier getEndOfIdentifierList(final DLanguageIdentifierOrTemplateChain chain) {
        final List<DLanguageIdentifierOrTemplateInstance> list = chain.getIdentifierOrTemplateInstances();
        if (list.get(list.size() - 1).getIdentifier() != null)
            return list.get(list.size() - 1).getIdentifier();
        else
            throw new IllegalStateException();

    }


    public static ASTNode getPrevSiblingOfType(@Nullable final ASTNode child, @Nullable final IElementType type) {
        if (child == null)
            return null;
        if (child.getElementType() == type) {
            return child.getTreePrev();
        }
        return getPrevSiblingOfType(child.getTreePrev(), type);
    }

    @Nullable
    public static ASTNode getPrevSiblingOfType(@Nullable final ASTNode child, @NotNull final HashSet<IElementType> newHashSet, @NotNull final HashSet<IElementType> excluded) {
        if (child == null)
            return null;
        if (newHashSet.contains(child.getElementType())) {
            return child;
        }
        if(excluded.contains(child.getElementType())){
            return null;
        }
        return getPrevSiblingOfType(child.getTreePrev(), newHashSet,excluded);
    }

    @Nullable
    public static PsiElement findParentOfType(final PsiElement element, final Class className) {
        if (className.isInstance(element)) {
            return element;
        } else {
            try {
                return findParentOfType(element.getParent(), className);
            } catch (final Exception e) {
                return null;
            }
        }

    }

    public static DlangIdentifier getEndOfIdentifierList(final DLanguageIdentifierChain chain) {
        final List<DlangIdentifier> list = chain.getIdentifiers();
        if (list.get(list.size() - 1) != null)
            return list.get(list.size() - 1);
        else
            throw new IllegalStateException();
    }
}

