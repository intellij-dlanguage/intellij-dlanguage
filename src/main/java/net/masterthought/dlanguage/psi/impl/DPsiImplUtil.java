package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.navigation.ItemPresentation;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.PsiReference;
import net.masterthought.dlanguage.icons.DLanguageIcons;
import net.masterthought.dlanguage.psi.*;
import net.masterthought.dlanguage.psi.references.DReference;
import net.masterthought.dlanguage.stubs.*;
import net.masterthought.dlanguage.utils.DResolveUtil;
import net.masterthought.dlanguage.utils.DUtil;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.*;

import static com.intellij.psi.util.PsiTreeUtil.*;
import static net.masterthought.dlanguage.utils.DResolveUtil.fromModulesToFiles;


/**
 * Source of the methods pointed out in DLanguage.bnf.
 */
public class DPsiImplUtil {

    // ------------- Identifier ------------------ //
    @NotNull
    public static String getName(@NotNull DLanguageIdentifier o) {
        DLanguageIdentifierStub stub = o.getStub();
        if (stub != null) return StringUtil.notNullize(stub.getName());
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


    public static PsiElement findParentOfType(PsiElement element, Class className) {
        if (className.isInstance(element)) {
            return element;
        } else {
            try {
                return findParentOfType(element.getParent(), className);
            } catch(Exception e){
                return null;
            }
        }

    }

    private static String getParentDeclarationDescription(DLanguageIdentifier o) {
        PsiNamedElement funcDecl = (PsiNamedElement) findParentOfType(o, DLanguageFuncDeclaration.class);
        PsiNamedElement classDecl = (PsiNamedElement) findParentOfType(o, DLanguageClassDeclaration.class);
        String description = "";
        if(funcDecl != null){
            description = " [Function] (" + funcDecl.getName() + ")";
        }
        if(classDecl != null){
            description = " [Class] (" + classDecl.getName() + ")";
        }
        return description;
    }

    @NotNull
    public static ItemPresentation getPresentation(final DLanguageIdentifier o) {
        return new ItemPresentation() {
            @Nullable
            @Override
            public String getPresentableText() {
                return o.getName() + getParentDeclarationDescription(o);
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
    // ------------- Identifier ------------------ //

    // ------------- Function Definition ------------------ //
    @NotNull
    public static String getName(@NotNull DLanguageFuncDeclaration o) {
        DLanguageFuncDeclarationStub stub = o.getStub();
        if (stub != null) return StringUtil.notNullize(stub.getName());
        return o.getIdentifier().getText();
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

    public static List<DLanguageParameter> getArguments(DLanguageFuncDeclaration o) {
        return Arrays.asList(getChildrenOfType(o.getFuncDeclaratorSuffix().getParameters(), DLanguageParameter.class));
    }

    public static List<DLanguageTemplateParameter> getTemplateArguments(DLanguageFuncDeclaration o) {
        if (o.getFuncDeclaratorSuffix().getTemplateParameters() != null)
            return Arrays.asList(getChildrenOfType(o.getFuncDeclaratorSuffix().getTemplateParameters(), DLanguageTemplateParameter.class));
        return new ArrayList<>();
    }

    public static List<DLanguageProtectionAttribute> getProtection(DLanguageFuncDeclaration o) {
        return Collections.singletonList(getChildOfType(o, DLanguageProtectionAttribute.class));
    }

    // ------------- Function Definition ------------------ //

    // ------------- Class Definition ------------------ //
    @NotNull
    public static String getName(@NotNull DLanguageClassDeclaration o) {
        DLanguageClassDeclarationStub stub = o.getStub();
        if (stub != null) return StringUtil.notNullize(stub.getName());

        if (o.getIdentifier() != null) {
            return o.getIdentifier().getText();
        } else {
            return "not found";
        }
    }

    @Nullable
    public static PsiElement getNameIdentifier(@NotNull DLanguageClassDeclaration o) {
        ASTNode keyNode = o.getNode();
        return keyNode != null ? keyNode.getPsi() : null;
    }

    @Nullable
    public static PsiElement setName(@NotNull DLanguageClassDeclaration o, @NotNull String newName) {
        PsiElement e = DElementFactory.createDLanguageClassDeclarationFromText(o.getProject(), newName);
        if (e == null) return null;
        o.replace(e);
        return o;
    }

    @NotNull
    public static PsiReference getReference(@NotNull DLanguageClassDeclaration o) {
        return new DReference(o, TextRange.from(0, getName(o).length()));
    }

    @NotNull
    public static ItemPresentation getPresentation(final DLanguageClassDeclaration o) {
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


    public static DLanguageProtectionAttribute getProtection(DLanguageClassDeclaration o) {
        return getChildOfType(o, DLanguageProtectionAttribute.class);
    }

//    private static <T extends PsiElement> List<T> getXOfType(DLanguageStructDeclaration o,Class<? extends T> type,boolean inheritance,boolean mixin){
//
//    }

    public static List<DLanguageFuncDeclaration> getMethods(DLanguageClassDeclaration o, boolean includeFromInheritance, boolean includeFromMixins) {
        final DLanguageFuncDeclaration[] methods = getChildrenOfType(o, DLanguageFuncDeclaration.class);
        if (methods != null)
            return Arrays.asList(methods);
        return new ArrayList<>();
    }

    public static List<DLanguageVarDeclarations> getVariables(DLanguageClassDeclaration o, boolean includeFromInheritance, boolean includeFromMixins) {
        final DLanguageVarDeclarations[] methods = getChildrenOfType(o, DLanguageVarDeclarations.class);
        if (methods != null)
            return Arrays.asList(methods);
        return new ArrayList<>();
    }

    public static List<DLanguageFuncDeclaration> getPropertyMethods(DLanguageClassDeclaration o, boolean includeFromInheritance, boolean includeFromMixins) {
        final DLanguageFuncDeclaration[] methods = getChildrenOfType(o, DLanguageFuncDeclaration.class);
        if (methods == null)
            return new ArrayList<>();
        ArrayList<DLanguageFuncDeclaration> toReturn = new ArrayList<>();
        for (DLanguageFuncDeclaration method : methods) {
            if (getChildOfType(method, DLanguagePropertyIdentifier.class).getText().equals("property"))
                toReturn.add(method);
        }
        return toReturn;
    }

    public static List<DLanguageTemplateParameter> getTemplateArguments(DLanguageClassDeclaration o, boolean includeFromInheritance, boolean includeFromMixins) {
        final DLanguageTemplateParameter[] methods = getChildrenOfType(o, DLanguageTemplateParameter.class);
        if (methods != null)
            return Arrays.asList(methods);
        return new ArrayList<>();
    }
//
//    public static List<DNamedElement> getPublicSymbols(DLanguageClassDeclaration o,boolean includeFromInheritance, boolean includeFromMixins){
//
//    }
//    public static List<DLanguageFuncDeclaration> getPublicMethods(DLanguageClassDeclaration o,boolean includeFromInheritance, boolean includeFromMixins){
//
//    }
//    public static List<DLanguageVarDeclarations> getPublicVariables(DLanguageClassDeclaration o,boolean includeFromInheritance, boolean includeFromMixins){
//
//    }
//    public static List<DNamedElement> getAllSymbols(DLanguageClassDeclaration o,boolean includeFromInheritance, boolean includeFromMixins){
//
//    }
//    public static List<DLanguageFuncDeclaration> getPublicPropertyMethods(DLanguageClassDeclaration o,boolean includeFromInheritance, boolean includeFromMixins){
//
//    }
//    public static List<DLanguageFuncDeclaration> getProtectedMethods(DLanguageClassDeclaration o,boolean includeFromInheritance, boolean includeFromMixins){
//
//    }
//    public static List<> getProtectedVariables(DLanguageClassDeclaration o,boolean includeFromInheritance, boolean includeFromMixins){
//
//    }
//    public static List<DLanguageFuncDeclaration> getProtectedPropertyMethods(DLanguageClassDeclaration o,boolean includeFromInheritance, boolean includeFromMixins){
//
//    }
//    public static String getBaseClassName(DLanguageClassDeclaration o,boolean includeFromInheritance, boolean includeFromMixins){
//
//    }
//    public static List<String> getInterfacesNames(DLanguageClassDeclaration o,boolean includeFromInheritance, boolean includeFromMixins){
//
//    }
//    public static List<DLanguageFuncDeclaration> getOverideMethods(DLanguageClassDeclaration o,boolean includeFromInheritance, boolean includeFromMixins){
//
//    }
//    public static List<DLanguageFuncDeclaration> getOverridePropertyMethods(DLanguageClassDeclaration o,boolean includeFromInheritance, boolean includeFromMixins){
//
//    }
//    public static List<> getDestructors(DLanguageClassDeclaration o,boolean includeFromInheritance, boolean includeFromMixins){
//
//    }
//    public static List<DLanguageConstructor> getPublicContructors(DLanguageClassDeclaration o,boolean includeFromInheritance, boolean includeFromMixins){
//
//    }
//    public static List<DLanguageConstructor> getProtectedConstructors(DLanguageClassDeclaration o,boolean includeFromInheritance, boolean includeFromMixins){
//
//    }
//    public static List<DLanguageConstructor> getPrivateConstructors(DLanguageClassDeclaration o,boolean includeFromInheritance, boolean includeFromMixins){
//
//    }
//    public static List<DLanguageDestructor> getPublicDestructors(DLanguageClassDeclaration o,boolean includeFromInheritance, boolean includeFromMixins){
//
//    }
//    public static List<DLanguageDestructor> getPrivateDestructors(DLanguageClassDeclaration o,boolean includeFromInheritance, boolean includeFromMixins){
//
//    }
//    public static List<DLanguageDestructor> getProtectedDestructors(DLanguageClassDeclaration o,boolean includeFromInheritance, boolean includeFromMixins){
//
//    }
//    public static List<DLanguageFuncDeclaration> getPrivateMethods(DLanguageClassDeclaration o,boolean includeFromInheritance, boolean includeFromMixins){
//
//    }
//    public static List<DLanguageVarDeclarations> getPrivateVariables(DLanguageClassDeclaration o,boolean includeFromInheritance, boolean includeFromMixins){
//
//    }
//    public static List<DLanguageFuncDeclaration> getPrivatePropertyMethod(DLanguageClassDeclaration o,boolean includeFromInheritance, boolean includeFromMixins){
//
//    }
//    public static List<DLanguageTemplateMixin> getMixins(DLanguageClassDeclaration o, boolean includeFromInheritance, boolean includeFromMixins){
//
//    }

    // ------------- Class Definition ------------------ //

    // ------------- Struct Definition ------------------ //
    @NotNull
    public static String getName(@NotNull DLanguageStructDeclaration o) {
        DLanguageStructDeclarationStub stub = o.getStub();
        if (stub != null) return StringUtil.notNullize(stub.getName());

        if (o.getIdentifier() != null) {
            return o.getIdentifier().getText();
        } else {
            return "not found";
        }
    }

    @Nullable
    public static PsiElement getNameIdentifier(@NotNull DLanguageStructDeclaration o) {
        ASTNode keyNode = o.getNode();
        return keyNode != null ? keyNode.getPsi() : null;
    }

    @Nullable
    public static PsiElement setName(@NotNull DLanguageStructDeclaration o, @NotNull String newName) {
        PsiElement e = DElementFactory.createDLanguageStructDeclarationFromText(o.getProject(), newName);
        if (e == null) return null;
        o.replace(e);
        return o;
    }

    @NotNull
    public static PsiReference getReference(@NotNull DLanguageStructDeclaration o) {
        return new DReference(o, TextRange.from(0, getName(o).length()));
    }

    @NotNull
    public static ItemPresentation getPresentation(final DLanguageStructDeclaration o) {
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

    public static DLanguageProtectionAttribute getProtection(DLanguageStructDeclaration o) {
        return getChildOfType(o, DLanguageProtectionAttribute.class);
    }

//    private static <T extends PsiElement> List<T> getXOfType(DLanguageStructDeclaration o,Class<? extends T> type,boolean inheritance,boolean mixin){
//
//    }

    public static List<DLanguageFuncDeclaration> getMethods(DLanguageStructDeclaration o, boolean includeFromInheritance, boolean includeFromMixins) {
        final DLanguageFuncDeclaration[] methods = getChildrenOfType(o, DLanguageFuncDeclaration.class);
        if (methods != null)
            return Arrays.asList(methods);
        return new ArrayList<>();
    }

    public static List<DLanguageVarDeclarations> getVariables(DLanguageStructDeclaration o, boolean includeFromInheritance, boolean includeFromMixins) {
        final DLanguageVarDeclarations[] methods = getChildrenOfType(o, DLanguageVarDeclarations.class);
        if (methods != null)
            return Arrays.asList(methods);
        return new ArrayList<>();
    }

    public static List<DLanguageFuncDeclaration> getPropertyMethods(DLanguageStructDeclaration o, boolean includeFromInheritance, boolean includeFromMixins) {
        final DLanguageFuncDeclaration[] methods = getChildrenOfType(o, DLanguageFuncDeclaration.class);
        if (methods == null)
            return new ArrayList<>();
        ArrayList<DLanguageFuncDeclaration> toReturn = new ArrayList<>();
        for (DLanguageFuncDeclaration method : methods) {
            if (getChildOfType(method, DLanguagePropertyIdentifier.class).getText().equals("property"))
                toReturn.add(method);
        }
        return toReturn;
    }

    public static List<DLanguageTemplateParameter> getTemplateArguments(DLanguageStructDeclaration o, boolean includeFromInheritance, boolean includeFromMixins) {
        final DLanguageTemplateParameter[] methods = getChildrenOfType(o, DLanguageTemplateParameter.class);
        if (methods != null)
            return Arrays.asList(methods);
        return new ArrayList<>();
    }

//    public static List<DNamedElement> getPublicSymbols(DLanguageStructDeclaration o,boolean includeFromInheritance, boolean includeFromMixins){
//
//    }
//    public static List<DLanguageFuncDeclaration> getPublicMethods(DLanguageStructDeclaration o,boolean includeFromInheritance, boolean includeFromMixins){
//
//    }
//    public static List<DLanguageVarDeclarations> getPublicVariables(DLanguageStructDeclaration o,boolean includeFromInheritance, boolean includeFromMixins){
//
//    }
//    public static List<DNamedElement> getAllSymbols(DLanguageStructDeclaration o,boolean includeFromInheritance, boolean includeFromMixins){
//
//    }
//    public static List<DLanguageFuncDeclaration> getPublicPropertyMethods(DLanguageStructDeclaration o,boolean includeFromInheritance, boolean includeFromMixins){
//
//    }
//    public static List<DLanguageFuncDeclaration> getProtectedMethods(DLanguageStructDeclaration o,boolean includeFromInheritance, boolean includeFromMixins){
//
//    }
//    public static List<> getProtectedVariables(DLanguageStructDeclaration o,boolean includeFromInheritance, boolean includeFromMixins){
//
//    }
//    public static List<DLanguageFuncDeclaration> getProtectedPropertyMethods(DLanguageStructDeclaration o,boolean includeFromInheritance, boolean includeFromMixins){
//
//    }
//    public static String getBaseClassName(DLanguageStructDeclaration o,boolean includeFromInheritance, boolean includeFromMixins){
//
//    }
//    public static List<String> getInterfacesNames(DLanguageStructDeclaration o,boolean includeFromInheritance, boolean includeFromMixins){
//
//    }
//    public static List<DLanguageFuncDeclaration> getOverideMethods(DLanguageStructDeclaration o,boolean includeFromInheritance, boolean includeFromMixins){
//
//    }
//    public static List<DLanguageFuncDeclaration> getOverridePropertyMethods(DLanguageStructDeclaration o,boolean includeFromInheritance, boolean includeFromMixins){
//
//    }
//    public static List<> getDestructors(DLanguageStructDeclaration o,boolean includeFromInheritance, boolean includeFromMixins){
//
//    }
//    public static List<DLanguageConstructor> getPublicContructors(DLanguageStructDeclaration o,boolean includeFromInheritance, boolean includeFromMixins){
//
//    }
//    public static List<DLanguageConstructor> getProtectedConstructors(DLanguageStructDeclaration o,boolean includeFromInheritance, boolean includeFromMixins){
//
//    }
//    public static List<DLanguageConstructor> getPrivateConstructors(DLanguageStructDeclaration o,boolean includeFromInheritance, boolean includeFromMixins){
//
//    }
//    public static List<DLanguageDestructor> getPublicDestructors(DLanguageStructDeclaration o,boolean includeFromInheritance, boolean includeFromMixins){
//
//    }
//    public static List<DLanguageDestructor> getPrivateDestructors(DLanguageStructDeclaration o,boolean includeFromInheritance, boolean includeFromMixins){
//
//    }
//    public static List<DLanguageDestructor> getProtectedDestructors(DLanguageStructDeclaration o,boolean includeFromInheritance, boolean includeFromMixins){
//
//    }
//    public static List<DLanguageFuncDeclaration> getPrivateMethods(DLanguageStructDeclaration o,boolean includeFromInheritance, boolean includeFromMixins){
//
//    }
//    public static List<DLanguageVarDeclarations> getPrivateVariables(DLanguageStructDeclaration o,boolean includeFromInheritance, boolean includeFromMixins){
//
//    }
//    public static List<DLanguageFuncDeclaration> getPrivatePropertyMethod(DLanguageStructDeclaration o,boolean includeFromInheritance, boolean includeFromMixins){
//
//    }
//    public static List<DLanguageTemplateMixin> getMixins(DLanguageStructDeclaration o, boolean includeFromInheritance, boolean includeFromMixins){
//
//    }


    // ------------- Struct Definition ------------------ //

    // ------------- Template Definition ------------------ //
    @NotNull
    public static String getName(@NotNull DLanguageTemplateDeclaration o) {
        DLanguageTemplateDeclarationStub stub = o.getStub();
        if (stub != null) return StringUtil.notNullize(stub.getName());

        if (o.getIdentifier() != null) {
            return o.getIdentifier().getText();
        } else {
            return "not found";
        }
    }

    @Nullable
    public static PsiElement getNameIdentifier(@NotNull DLanguageTemplateDeclaration o) {
        ASTNode keyNode = o.getNode();
        return keyNode != null ? keyNode.getPsi() : null;
    }

    @Nullable
    public static PsiElement setName(@NotNull DLanguageTemplateDeclaration o, @NotNull String newName) {
        PsiElement e = DElementFactory.createDLanguageTemplateDeclarationFromText(o.getProject(), newName);
        if (e == null) return null;
        o.replace(e);
        return o;
    }

    @NotNull
    public static PsiReference getReference(@NotNull DLanguageTemplateDeclaration o) {
        return new DReference(o, TextRange.from(0, getName(o).length()));
    }

    @NotNull
    public static ItemPresentation getPresentation(final DLanguageTemplateDeclaration o) {
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
    // ------------- Template Definition ------------------ //

    // ------------- Constructor ------------------ //
    @NotNull
    public static String getName(@NotNull DLanguageConstructor o) {
        return "this";
//        DLanguageConstructorStub stub = o.getStub();
//        if (stub != null) return StringUtil.notNullize(stub.getName());
//
//        PsiElement parent = o.getParent();
//
//
//        while (!(parent instanceof DLanguageClassDeclaration)) {
//            parent = parent.getParent();
//        }

//        return ((DLanguageClassDeclaration)parent).getName() + "constructor";
//        if (o.getIdentifier() != null) {
//            return o.getIdentifier().getText();
//        } else {
//            return "not found";
//        }
    }

    @Nullable
    public static PsiElement getNameIdentifier(@NotNull DLanguageConstructor o) {
        ASTNode keyNode = o.getNode();
        return keyNode != null ? keyNode.getPsi() : null;
    }

    @Nullable
    public static PsiElement setName(@NotNull DLanguageConstructor o, @NotNull String newName) {
        PsiElement e = DElementFactory.createDLanguageConstructorFromText(o.getProject(), newName);
        if (e == null) return null;
        o.replace(e);
        return o;
    }

    @NotNull
    public static PsiReference getReference(@NotNull DLanguageConstructor o) {
        return new DReference(o, TextRange.from(0, getName(o).length()));
    }

    @NotNull
    public static ItemPresentation getPresentation(final DLanguageConstructor o) {
        return new ItemPresentation() {
            @Nullable
            @Override
            public String getPresentableText() {
                String string = "";
                for (PsiElement psiElement : o.getChildren()) {
                    if(psiElement instanceof DLanguageParametersImpl)
                        string += psiElement.getText();
                }
                return o.getName() + string;
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
    // ------------- Constructor ------------------ //

    // ------------- Destructor ------------------ //
    @NotNull
    public static String getName(@NotNull DLanguageDestructor o) {
        return "~this";
//        DLanguageDestructorStub stub = o.getStub();
//        if (stub != null) return StringUtil.notNullize(stub.getName());
//
//        PsiElement parent = o.getParent();
//
//
//        while (!(parent instanceof DLanguageClassDeclaration)) {
//            parent = parent.getParent();
//        }

//        return ((DLanguageClassDeclaration)parent).getName() + "constructor";
//        if (o.getIdentifier() != null) {
//            return o.getIdentifier().getText();
//        } else {
//            return "not found";
//        }
    }

    @Nullable
    public static PsiElement getNameIdentifier(@NotNull DLanguageDestructor o) {
        ASTNode keyNode = o.getNode();
        return keyNode != null ? keyNode.getPsi() : null;
    }

    @Nullable
    public static PsiElement setName(@NotNull DLanguageDestructor o, @NotNull String newName) {
        PsiElement e = DElementFactory.createDLanguageDestructorFromText(o.getProject(), newName);
        if (e == null) return null;
        o.replace(e);
        return o;
    }

    @NotNull
    public static PsiReference getReference(@NotNull DLanguageDestructor o) {
        return new DReference(o, TextRange.from(0, getName(o).length()));
    }

    @NotNull
    public static ItemPresentation getPresentation(final DLanguageDestructor o) {
        return new ItemPresentation() {
            @Nullable
            @Override
            public String getPresentableText() {
                String string = "";
                for (PsiElement psiElement : o.getChildren()) {
                    if(psiElement instanceof DLanguageParametersImpl)
                        string += psiElement.getText();
                }
                return o.getName() + string;
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
    // ------------- Destructor ------------------ //

    // ------------- Alias Definition ------------------ //
    @NotNull
    public static String getName(@NotNull DLanguageAliasDeclaration o) {
        DLanguageAliasDeclarationStub stub = o.getStub();
        if (stub != null) return StringUtil.notNullize(stub.getName());

        if (o.getIdentifier() != null) {
            return o.getIdentifier().getText();
        } else {
            return "not found";
        }
    }

    @Nullable
    public static PsiElement getNameIdentifier(@NotNull DLanguageAliasDeclaration o) {
        ASTNode keyNode = o.getNode();
        return keyNode != null ? keyNode.getPsi() : null;
    }

    @Nullable
    public static PsiElement setName(@NotNull DLanguageAliasDeclaration o, @NotNull String newName) {
        PsiElement e = DElementFactory.createDLanguageAliasDeclarationFromText(o.getProject(), newName);
        if (e == null) return null;
        o.replace(e);
        return o;
    }

    @NotNull
    public static PsiReference getReference(@NotNull DLanguageAliasDeclaration o) {
        return new DReference(o, TextRange.from(0, getName(o).length()));
    }

    @NotNull
    public static ItemPresentation getPresentation(final DLanguageAliasDeclaration o) {
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

    // ------------- Alias Definition ------------------ //

    // ------------ Module Declaration ----------------- //

    @NotNull
    public static String getName(@NotNull DLanguageModuleGlobalDeclaration o) {
        DLanguageModuleDeclarationStub stub = o.getStub();
        if (stub != null) return StringUtil.notNullize(stub.getName());

        if (o.getModuleFullyQualifiedName().getText() != null) {
            return o.getModuleFullyQualifiedName().getText();
        } else {
            return "not found";
        }
    }

    @Nullable
    public static PsiElement getNameIdentifier(@NotNull DLanguageModuleGlobalDeclaration o) {
        ASTNode keyNode = o.getNode();
        return keyNode != null ? keyNode.getPsi() : null;
    }

    @Nullable
    public static PsiElement setName(@NotNull DLanguageModuleGlobalDeclaration o, @NotNull String newName) {
        PsiElement e = DElementFactory.createDLanguageModuleFromText(o.getProject(), newName);
        if (e == null) return null;
        o.replace(e);
        return o;
    }

    @NotNull
    public static PsiReference getReference(@NotNull DLanguageModuleGlobalDeclaration o) {
        return new DReference(o, TextRange.from(0, getName(o).length()));
    }

    @NotNull
    public static ItemPresentation getPresentation(final DLanguageModuleGlobalDeclaration o) {
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

    @Contract("null -> null")
    public static DLanguageProtectionAttribute getProtection(DLanguageModuleGlobalDeclaration o) {
        return getChildOfType(o, DLanguageProtectionAttribute.class);
    }


    //todo include mixin contents in these results

    private static DLanguageIdentifier getLastIdentifier(DLanguageQualifiedIdentifierList t) {
        if (t.getQualifiedIdentifierList() != null)
            return getLastIdentifier(t.getQualifiedIdentifierList());
        return t.getIdentifier();
    }

    private static <T extends PsiElement> List<T> getXofType(DLanguageModuleGlobalDeclaration o, Class<? extends T> aClass, boolean includeMixins) {
        final Set<DLanguageFile> dLanguageFiles = getdLanguageFiles(o);
        List<T> declarations = new ArrayList<>();
        if (includeMixins) {
            final List<DLanguageTemplateMixin> templateMixins = getTemplateMixins(o, true);
            for (DLanguageTemplateMixin templateMixin : templateMixins) {
                final Set<PsiNamedElement> definitionNodes = DResolveUtil.findDefinitionNodes(getLastIdentifier(templateMixin.getMixinTemplateName().getQualifiedIdentifierList()));
                if (definitionNodes.size() > 1) {
                    throw new IllegalStateException("this shouldn't be happening");
                }
                for (PsiNamedElement definitionNode : definitionNodes) {
                    if (definitionNode instanceof DNamedElement)
                        final Collection<T> childrenOfType = findChildrenOfType(definitionNode, aClass);
                    declarations.addAll(childrenOfType);
                }
            }
        }
        for (DLanguageFile dLanguageFile : dLanguageFiles) {
            final Collection<T> childrenOfType = findChildrenOfType(dLanguageFile, aClass);
            declarations.addAll(childrenOfType);
        }
        return declarations;
    }

    public static List<DLanguageClassDeclaration> getClassDeclarations(DLanguageModuleGlobalDeclaration o, boolean includeFromMixins) {
        return getXofType(o, DLanguageClassDeclaration.class, includeFromMixins);
    }

    public static List<DLanguageTemplateDeclaration> getTemplateDeclarations(DLanguageModuleGlobalDeclaration o, boolean includeFromMixins) {
        return getXofType(o, DLanguageTemplateDeclaration.class, includeFromMixins);
    }

    public static List<DLanguageStructDeclaration> getStructDeclarations(DLanguageModuleGlobalDeclaration o, boolean includeFromMixins) {
        return getXofType(o, DLanguageStructDeclaration.class, includeFromMixins);
    }

    public static List<DLanguageFuncDeclaration> getFunctionDeclarations(DLanguageModuleGlobalDeclaration o, boolean includeFromMixins) {
        return getXofType(o, DLanguageFuncDeclaration.class, includeFromMixins);
    }

    public static List<DLanguageVarDeclarations> getVarDeclarations(DLanguageModuleGlobalDeclaration o, boolean includeFromMixins) {
        return getXofType(o, DLanguageVarDeclarations.class, includeFromMixins);
    }

    public static List<DLanguageTemplateMixin> getTemplateMixins(DLanguageModuleGlobalDeclaration o, boolean includeFromMixins) {
        return getXofType(o, DLanguageTemplateMixin.class, includeFromMixins);
    }

    private static <T extends PsiElement> List<T> getTopLevelXofType(DLanguageModuleGlobalDeclaration o, Class<? extends T> aClass, boolean includeMixins) {
        final Set<DLanguageFile> dLanguageFiles = getdLanguageFiles(o);
        List<T> declarations = new ArrayList<>();
        if (includeMixins) {
            final List<DLanguageTemplateMixin> templateMixins = getTopLevelTemplateMixins(o, true);
            for (DLanguageTemplateMixin templateMixin : templateMixins) {
                final Set<PsiNamedElement> definitionNodes = DResolveUtil.findDefinitionNodes(getLastIdentifier(templateMixin.getMixinTemplateName().getQualifiedIdentifierList()));
                if (definitionNodes.size() > 1) {
                    throw new IllegalStateException("this shouldn't be happening");
                }
                for (PsiNamedElement definitionNode : definitionNodes) {
                    final Collection<T> childrenOfType = findChildrenOfType(definitionNode, aClass);
                    for (T childOfType : childrenOfType) {
                        if (null != getParentOfType(childOfType, DLanguageClassDeclaration.class))
                            continue;
                        if (null != getParentOfType(childOfType, DLanguageFuncDeclaration.class))
                            continue;
                        if (null != getParentOfType(childOfType, DLanguageStructDeclaration.class))
                            continue;
                        if (null != getParentOfType(childOfType, DLanguageTemplateDeclaration.class))
                            continue;
                        if (null != getParentOfType(childOfType, DLanguageVarDeclarations.class))
                            continue;
                        declarations.add(childOfType);
                    }
                }
            }
        }
        for (DLanguageFile dLanguageFile : dLanguageFiles) {
            final Collection<T> childrenOfType = findChildrenOfType(dLanguageFile, aClass);
            for (T childOfType : childrenOfType) {
                if (null != getParentOfType(childOfType, DLanguageClassDeclaration.class))
                    continue;
                if (null != getParentOfType(childOfType, DLanguageFuncDeclaration.class))
                    continue;
                if (null != getParentOfType(childOfType, DLanguageStructDeclaration.class))
                    continue;
                if (null != getParentOfType(childOfType, DLanguageTemplateDeclaration.class))
                    continue;
                if (null != getParentOfType(childOfType, DLanguageVarDeclarations.class))
                    continue;
                declarations.add(childOfType);
            }
        }
        return declarations;
    }

    public static List<DLanguageClassDeclaration> getTopLevelClassDeclarations(DLanguageModuleGlobalDeclaration o, boolean includeFromMixins) {
        return getTopLevelXofType(o, DLanguageClassDeclaration.class, includeFromMixins);
    }

    public static List<DLanguageTemplateDeclaration> getTopLevelTemplateDeclarations(DLanguageModuleGlobalDeclaration o, boolean includeFromMixins) {
        return getTopLevelXofType(o, DLanguageTemplateDeclaration.class, includeFromMixins);
    }

    public static List<DLanguageStructDeclaration> getTopLevelStructDeclarations(DLanguageModuleGlobalDeclaration o, boolean includeFromMixins) {
        return getTopLevelXofType(o, DLanguageStructDeclaration.class, includeFromMixins);
    }

    public static List<DLanguageVarDeclarations> getTopLevelVarDeclarations(DLanguageModuleGlobalDeclaration o, boolean includeFromMixins) {
        return getTopLevelXofType(o, DLanguageVarDeclarations.class, includeFromMixins);
    }

    public static List<DLanguageFuncDeclaration> getTopLevelFunctionDeclarations(DLanguageModuleGlobalDeclaration o, boolean includeFromMixins) {
        return getTopLevelXofType(o, DLanguageFuncDeclaration.class, includeFromMixins);
    }

    private static Set<DLanguageFile> getdLanguageFiles(DLanguageModuleGlobalDeclaration o) {
        Set<String> module = new HashSet<>();
        module.add(o.getModuleFullyQualifiedName().getText());
        return fromModulesToFiles(o.getProject(), module);
    }

    public static Set<DNamedElement> getPubliclyAccessibleSymbols(DLanguageModuleGlobalDeclaration o, boolean includeFromMixins) {
        Set<DNamedElement> symbols = new HashSet<>();
        Set<DNamedElement> publicSymbols = new HashSet<>();
        symbols.addAll(getTopLevelClassDeclarations(o, includeFromMixins));
        symbols.addAll(getTopLevelTemplateDeclarations(o, includeFromMixins));
        symbols.addAll(getTopLevelStructDeclarations(o, includeFromMixins));
        symbols.addAll(getTopLevelFunctionDeclarations(o, includeFromMixins));
        symbols.addAll(getTopLevelVarDeclarations(o, includeFromMixins));
        for (DNamedElement symbol : symbols) {
            if (DUtil.isPublic(symbol))
                publicSymbols.add(symbol);
        }
        return publicSymbols;
    }

    public static Set<DNamedElement> getAllSymbols(DLanguageModuleGlobalDeclaration o, boolean includeFromMixins) {
        Set<DNamedElement> symbols = new HashSet<>();
        symbols.addAll(getClassDeclarations(o, includeFromMixins));
        symbols.addAll(getTemplateDeclarations(o, includeFromMixins));
        symbols.addAll(getStructDeclarations(o, includeFromMixins));
        symbols.addAll(getFunctionDeclarations(o, includeFromMixins));
        symbols.addAll(getVarDeclarations(o, includeFromMixins));
        return symbols;
    }

    public static List<DLanguageTemplateMixin> getTopLevelTemplateMixins(DLanguageModuleGlobalDeclaration o, boolean includeFromMixins) {
        return getTopLevelXofType(o, DLanguageTemplateMixin.class, includeFromMixins);
    }

    // ------------ Module Declaration ----------------- //

    // ------------- Interface Definition ------------------ //
    @NotNull
    public static String getName(@NotNull DLanguageInterfaceDeclaration o) {
        DLanguageInterfaceDeclarationStub stub = o.getStub();
        if (stub != null) return StringUtil.notNullize(stub.getName());
        return o.getIdentifier().getText();
    }

    @Nullable
    public static PsiElement getNameIdentifier(@NotNull DLanguageInterfaceDeclaration o) {
        ASTNode keyNode = o.getNode();
        return keyNode != null ? keyNode.getPsi() : null;
    }

    @Nullable
    public static PsiElement setName(@NotNull DLanguageInterfaceDeclaration o, @NotNull String newName) {
        PsiElement e = DElementFactory.createDLanguageInterfaceDeclarationFromText(o.getProject(), newName);
        if (e == null) return null;
        o.replace(e);
        return o;
    }

    @NotNull
    public static PsiReference getReference(@NotNull DLanguageInterfaceDeclaration o) {
        return new DReference(o, TextRange.from(0, getName(o).length()));
    }

    @NotNull
    public static ItemPresentation getPresentation(final DLanguageInterfaceDeclaration o) {
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
    // ------------- Interface Definition ------------------ //

    // ------------- Var Declaration ------------------ //
    @NotNull
    public static String getName(@NotNull DLanguageVarDeclarations o) {
        DLanguageVarDeclaratorStub stub = o.getStub();
        if (stub != null) return StringUtil.notNullize(stub.getName());
        if (o.getAutoDeclaration() != null)
            return o.getAutoDeclaration().getAutoDeclarationX().getAutoDeclarationY().getIdentifier().getText();
        return o.getDeclarators().getText();//doesn't have any one name??
    }

    @Nullable
    public static PsiElement getNameIdentifier(@NotNull DLanguageVarDeclarations o) {
        ASTNode keyNode = o.getNode();
        return keyNode != null ? keyNode.getPsi() : null;
    }

    @Nullable
    public static PsiElement setName(@NotNull DLanguageVarDeclarations o, @NotNull String newName) {
        PsiElement e = DElementFactory.createDLanguageVarDeclarationFromText(o.getProject(), newName);
        if (e == null) return null;
        o.replace(e);
        return o;
    }

    @NotNull
    public static PsiReference getReference(@NotNull DLanguageVarDeclarations o) {
        return new DReference(o, TextRange.from(0, getName(o).length()));
    }

    @NotNull
    public static ItemPresentation getPresentation(final DLanguageVarDeclarations o) {
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
    // ------------- Var Declaration ------------------ //

    // ------------- Labeled Statement ------------------ //
    @NotNull
    public static String getName(@NotNull DLanguageLabeledStatement o) {
        DLanguageLabeledStatementStub stub = o.getStub();
        if (stub != null) return StringUtil.notNullize(stub.getName());
        return o.getIdentifier().getText();
    }

    @Nullable
    public static PsiElement getNameIdentifier(@NotNull DLanguageLabeledStatement o) {
        ASTNode keyNode = o.getNode();
        return keyNode != null ? keyNode.getPsi() : null;
    }

    @Nullable
    public static PsiElement setName(@NotNull DLanguageLabeledStatement o, @NotNull String newName) {
        PsiElement e = DElementFactory.createDLanguageLabeledStatementFromText(o.getProject(), newName);
        if (e == null) return null;
        o.replace(e);
        return o;
    }

    @NotNull
    public static PsiReference getReference(@NotNull DLanguageLabeledStatement o) {
        return new DReference(o, TextRange.from(0, getName(o).length()));
    }

    @NotNull
    public static ItemPresentation getPresentation(final DLanguageLabeledStatement o) {
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
    // ------------- Labeled Statement ------------------ //

    // ------------- Mixin Declaration ------------------ //
    @NotNull
    public static String getName(@NotNull DLanguageTemplateMixinDeclaration o) {
        DLanguageTemplateMixinDeclarationStub stub = o.getStub();
        if (stub != null) return StringUtil.notNullize(stub.getName());
        return o.getIdentifier().getText();//doesn't have any one name??
    }

    @Nullable
    public static PsiElement getNameIdentifier(@NotNull DLanguageTemplateMixinDeclaration o) {
        ASTNode keyNode = o.getNode();
        return keyNode != null ? keyNode.getPsi() : null;
    }

    @Nullable
    public static PsiElement setName(@NotNull DLanguageTemplateMixinDeclaration o, @NotNull String newName) {
        PsiElement e = DElementFactory.createDLanguageVarDeclarationFromText(o.getProject(), newName);
        if (e == null) return null;
        o.replace(e);
        return o;
    }

    @NotNull
    public static PsiReference getReference(@NotNull DLanguageTemplateMixinDeclaration o) {
        return new DReference(o, TextRange.from(0, getName(o).length()));
    }

    @NotNull
    public static ItemPresentation getPresentation(final DLanguageTemplateMixinDeclaration o) {
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

    // ------------- Mixin Declaration ------------------ //

    // -------------- Mixin Statement ------------------- //
    public DLanguageTemplateMixinDeclaration getTemplate() {

    }

    // -------------- Mixin Statement ------------------- //


}

