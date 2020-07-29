package io.github.intellij.dlanguage.utils;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.*;
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement;
import io.github.intellij.dlanguage.psi.interfaces.Declaration;
import io.github.intellij.dlanguage.psi.named.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

/**
 * General util class. Provides methods for finding named nodes in the Psi tree.
 */
public class DUtil {

    /**
     * Tells whether a named node is a definition node based on its context.
     * <p/>
     * Precondition: Element is in a DLanguage file.
     */
    public static boolean definitionNode(@NotNull final PsiNamedElement e) {
        if (e instanceof DlangIdentifier) return true;
        return e instanceof Declaration;
    }

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

    @Nullable
    public static ASTNode getPrevSiblingOfType(@Nullable final ASTNode child,
                                               @Nullable final IElementType type) {
        if (child == null)
            return null;
        if (child.getElementType() == type) {
            return child.getTreePrev();
        }
        return getPrevSiblingOfType(child.getTreePrev(), type);
    }

    @Nullable
    public static ASTNode getPrevSiblingOfType(@Nullable final ASTNode child,
                                               @NotNull final IElementType type,
                                               @Nullable final HashSet<IElementType> excluded) {
        if (child == null)
            return null;
        if (child.getElementType() == type) {
            return child;
        }
        if(excluded != null && excluded.contains(child.getElementType())) {
            return null;
        }
        return getPrevSiblingOfType(child.getTreePrev(), type, excluded);
    }

    @Nullable
    public static ASTNode getPrevSiblingOfTypes(@Nullable final ASTNode child,
                                               @NotNull final HashSet<IElementType> newHashSet,
                                               @Nullable final HashSet<IElementType> excluded) {
        if (child == null)
            return null;
        if (newHashSet.contains(child.getElementType())) {
            return child;
        }
        if(excluded != null && excluded.contains(child.getElementType())) {
            return null;
        }
        return getPrevSiblingOfTypes(child.getTreePrev(), newHashSet, excluded);
    }

    @SuppressWarnings("unchecked")
    @Nullable
    public static <T extends PsiElement> T findParentOfType(@NotNull final PsiElement element, @NotNull final Class<T> className) {
        if (className.isInstance(element)) {
            return (T) element;
        }

        return Optional.ofNullable(element.getParent())
                .map(parent -> findParentOfType(parent, className))
                .orElse(null);
    }

    @Nullable
    public static DlangIdentifier getEndOfIdentifierList(final @NotNull DLanguageIdentifierChain chain) {
        final List<DlangIdentifier> list = chain.getIdentifiers();
        if (list.isEmpty()) {
            return null;
        }

        return list.get(list.size() - 1);
    }

    @Nullable
    public static DlangIdentifier getEndOfIdentifierList(@Nullable final DLanguageIdentifierOrTemplateChain chain) {
        if(chain == null) return null;

        @NotNull final List<DLanguageIdentifierOrTemplateInstance> list = chain.getIdentifierOrTemplateInstances();

        return list.size() > 0 ? list.get(list.size() - 1).getIdentifier() : null;
    }

    /**
     * D file names should be composed of the ASCII characters lower case letters, digits or _ and should also not be a Keyword.
     * @param name a filename to check
     * @return true if filename
     */
    public static boolean isValidDlangFileName(@NotNull final String name) {
        return name.matches("[a-zA-Z_0-9]+(\\.di|\\.d)?");
    }
}

