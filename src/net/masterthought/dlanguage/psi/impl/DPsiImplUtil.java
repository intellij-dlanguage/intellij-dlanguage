package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.navigation.ItemPresentation;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiReference;
import net.masterthought.dlanguage.DLanguageIcons;
import net.masterthought.dlanguage.psi.*;
import net.masterthought.dlanguage.psi.references.DReference;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;


/**
 * Source of the methods pointed out in Haskell.bnf.
 */
public class DPsiImplUtil {

//    // ------------- Symbol  ------------------ //
//    @NotNull
//    public static String getName(@NotNull DLanguageSymbol o) {
//        return o.getText();
//    }
//
//    @Nullable
//    public static PsiElement getNameIdentifier(@NotNull DLanguageSymbol o) {
//        ASTNode keyNode = o.getNode();
//        return keyNode != null ? keyNode.getPsi() : null;
//    }
//
//    @Nullable
//    public static PsiElement setName(@NotNull DLanguageSymbol o, @NotNull String newName) {
//        PsiElement e = DElementFactory.createDLanguageSymbolFromText(o.getProject(), newName);
//        if (e == null) return null;
//        o.replace(e);
//        return o;
//    }
//
//    @NotNull
//    public static PsiReference getReference(@NotNull DLanguageSymbol o) {
//        return new DReference(o, TextRange.from(0, getName(o).length()));
//    }
//
//    @NotNull
//    public static ItemPresentation getPresentation(final DLanguageSymbol o) {
//        return new ItemPresentation() {
//            @Nullable
//            @Override
//            public String getPresentableText() {
//                return o.getName();
//            }
//
//            /**
//             * This is needed to decipher between files when resolving multiple references.
//             */
//            @Nullable
//            @Override
//            public String getLocationString() {
//                final PsiFile psiFile = o.getContainingFile();
//                return psiFile instanceof DLanguageFile ? ((DLanguageFile) psiFile).getModuleOrFileName() : null;
//            }
//
//            @Nullable
//            @Override
//            public Icon getIcon(boolean unused) {
//                return DLanguageIcons.FILE;
//            }
//        };
//    }
//    // ------------- Symbol ------------------ //


    // ------------- Ref Identifier ------------------ //
    @NotNull
    public static String getName(@NotNull DLanguageIdentifier o) {
        return o.getText();
    }

    @Nullable
    public static PsiElement getNameIdentifier(@NotNull DLanguageIdentifier o) {
        ASTNode keyNode = o.getNode();
        return keyNode != null ? keyNode.getPsi() : null;
    }

    @Nullable
    public static PsiElement setName(@NotNull DLanguageIdentifier o, @NotNull String newName) {
        PsiElement e = DElementFactory.createDLanguageIdentifierFromText(o.getProject(), newName);
        if (e == null) return null;
        o.replace(e);
        return o;
    }

    @NotNull
    public static PsiReference getReference(@NotNull DLanguageIdentifier o) {
        return new DReference(o, TextRange.from(0, getName(o).length()));
    }

    @NotNull
    public static ItemPresentation getPresentation(final DLanguageIdentifier o) {
        return new ItemPresentation() {
            @Nullable
            @Override
            public String getPresentableText() {
                return o.getName();
            }

            /**
             * This is needed to decipher between files when resolving multiple references.
             */
            @Nullable
            @Override
            public String getLocationString() {
                final PsiFile psiFile = o.getContainingFile();
                return psiFile instanceof DLanguageFile ? ((DLanguageFile) psiFile).getModuleOrFileName() : null;
            }

            @Nullable
            @Override
            public Icon getIcon(boolean unused) {
                return DLanguageIcons.FILE;
            }
        };
    }
    // ------------- Ref Identifier ------------------ //


    // ------------- Definition function ------------------ //
    @NotNull
    public static String getName(@NotNull DLanguageFuncDeclaration o) {
//        DLanguageFuncDeclarationStub stub = o.getStub();
//        if (stub != null) return StringUtil.notNullize(stub.getName());
        
        DLanguageFuncDeclarator fd = o.getFuncDeclarator();
        if(fd != null){
          return fd.getIdentifier().getText();  
        } else {
            return StringUtil.notNullize(o.getText());
        }
    }

    @Nullable
    public static PsiElement getNameIdentifier(@NotNull DLanguageFuncDeclaration o) {
        ASTNode keyNode = o.getNode();
        return keyNode != null ? keyNode.getPsi() : null;
    }

    @Nullable
    public static PsiElement setName(@NotNull DLanguageFuncDeclaration o, @NotNull String newName) {
        PsiElement e = DElementFactory.createDLanguageFuncDeclarationFromText(o.getProject(), newName);
        if (e == null) return null;
        o.replace(e);
        return o;
    }

    @NotNull
    public static PsiReference getReference(@NotNull DLanguageFuncDeclaration o) {
        return new DReference(o, TextRange.from(0, getName(o).length()));
    }

    @NotNull
    public static ItemPresentation getPresentation(final DLanguageFuncDeclaration o) {
        return new ItemPresentation() {
            @Nullable
            @Override
            public String getPresentableText() {
                return o.getName();
            }

            /**
             * This is needed to decipher between files when resolving multiple references.
             */
            @Nullable
            @Override
            public String getLocationString() {
                final PsiFile psiFile = o.getContainingFile();
                return psiFile instanceof DLanguageFile ? ((DLanguageFile) psiFile).getModuleOrFileName() : null;
            }

            @Nullable
            @Override
            public Icon getIcon(boolean unused) {
                return DLanguageIcons.FILE;
            }
        };
    }
    // ------------- Definition function ------------------ //

    // ------------- Definition class ------------------ //
//    @NotNull
//    public static String getName(@NotNull DDefinitionClass o) {
//        DDefinitionClassStub stub = o.getStub();
//        if (stub != null) return StringUtil.notNullize(stub.getName());
//        return o.getSymbol().getText();
//    }
//
//    @Nullable
//    public static PsiElement getNameIdentifier(@NotNull DDefinitionClass o) {
//        ASTNode keyNode = o.getNode();
//        return keyNode != null ? keyNode.getPsi() : null;
//    }
//
//    @Nullable
//    public static PsiElement setName(@NotNull DDefinitionClass o, @NotNull String newName) {
//        PsiElement e = DElementFactory.createDDefinitionFunctionFromText(o.getProject(), newName);
//        if (e == null) return null;
//        o.replace(e);
//        return o;
//    }
//
//    @NotNull
//    public static PsiReference getReference(@NotNull DDefinitionClass o) {
//        return new DReference(o, TextRange.from(0, getName(o).length()));
//    }
//
//    @NotNull
//    public static ItemPresentation getPresentation(final DDefinitionClass o) {
//        return new ItemPresentation() {
//            @Nullable
//            @Override
//            public String getPresentableText() {
//                return o.getName();
//            }
//
//            /**
//             * This is needed to decipher between files when resolving multiple references.
//             */
//            @Nullable
//            @Override
//            public String getLocationString() {
//                final PsiFile psiFile = o.getContainingFile();
//                return psiFile instanceof DLanguageFile ? ((DLanguageFile) psiFile).getModuleOrFileName() : null;
//            }
//
//            @Nullable
//            @Override
//            public Icon getIcon(boolean unused) {
//                return DLanguageIcons.FILE;
//            }
//        };
//    }
    // ------------- Definition class ------------------ //
}

