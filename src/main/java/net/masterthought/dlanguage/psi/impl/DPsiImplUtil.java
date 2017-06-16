package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.navigation.ItemPresentation;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.*;
import com.intellij.psi.scope.PsiScopeProcessor;
import net.masterthought.dlanguage.icons.DLanguageIcons;
import net.masterthought.dlanguage.psi.*;
import net.masterthought.dlanguage.psi.interfaces.*;
import net.masterthought.dlanguage.psi.interfaces.containers.*;
import net.masterthought.dlanguage.psi.references.DReference;
import net.masterthought.dlanguage.resolve.DResolveUtil;
import net.masterthought.dlanguage.resolve.ScopeProcessorImpl;
import net.masterthought.dlanguage.stubs.*;
import net.masterthought.dlanguage.utils.DUtil;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.*;

import static com.intellij.psi.util.PsiTreeUtil.*;
import static net.masterthought.dlanguage.psi.interfaces.HasVisibility.Visibility;
import static net.masterthought.dlanguage.utils.DUtil.getEndOfIdentifierList;
import static net.masterthought.dlanguage.utils.DUtil.protectionToVisibilty;


/**
 * Source of the methods pointed out in DLanguage.bnf.
 * todo: this file is rather long and not getting shorter. Split into multiple files at a later date.
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

    @NotNull
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

    @Nullable
    public static PsiElement findParentOfType(PsiElement element, Class className) {
        if (className.isInstance(element)) {
            return element;
        } else {
            try {
                return findParentOfType(element.getParent(), className);
            } catch (Exception e) {
                return null;
            }
        }

    }

    private static String getParentDeclarationDescription(DLanguageIdentifier o) {
        PsiNamedElement funcDecl = (PsiNamedElement) findParentOfType(o, DLanguageFuncDeclaration.class);
        PsiNamedElement classDecl = (PsiNamedElement) findParentOfType(o, DLanguageClassDeclaration.class);
        String description = "";
        if (funcDecl != null) {
            description = " [Function] (" + funcDecl.getName() + ")";
        }
        if (classDecl != null) {
            description = " [Class] (" + classDecl.getName() + ")";
        }
        return description;
    }

    @NotNull
    public static ItemPresentation getPresentation(final DLanguageIdentifier o) {
        return new ItemPresentation() {
            @NotNull
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

    public static void delete(DLanguageIdentifier identifier) {
        final List<PsiNamedElement> definitionNode = DResolveUtil.INSTANCE.findDefinitionNode(identifier.getProject(), identifier.getName(), identifier);
        if (definitionNode.size() != 1)
            throw new IllegalStateException();
        definitionNode.get(0).delete();
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

    @NotNull
    public static PsiElement setName(@NotNull DLanguageFuncDeclaration o, @NotNull String newName) {
        o.getIdentifier().setName(newName);
        return o;
    }

    @NotNull
    public static PsiReference getReference(@NotNull DLanguageFuncDeclaration o) {
        return new DReference(o, TextRange.from(0, getName(o).length()));
    }

    @NotNull
    public static ItemPresentation getPresentation(final DLanguageFuncDeclaration o) {
        return new ItemPresentation() {
            @NotNull
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

    @NotNull
    public static List<DLanguageParameter> getParameterList(DLanguageFuncDeclaration o) {
        return Arrays.asList(getChildrenOfType(o.getParameters(), DLanguageParameter.class));
    }

    public static List<DLanguageTemplateParameter> getTemplateArguments(DLanguageFuncDeclaration o) {
        if (o.getTemplateParameters() != null)
            return Arrays.asList(getChildrenOfType(o.getTemplateParameters(), DLanguageTemplateParameter.class));
        return new ArrayList<>();
    }

    @NotNull
    public static List<DLanguageProtectionAttribute> getProtection(DLanguageFuncDeclaration o) {
        return Collections.singletonList(getChildOfType(o, DLanguageProtectionAttribute.class));
    }

    public static boolean isSystem(DLanguageFuncDeclaration o) {
        return false;//todo
    }

    public static boolean isNoGC(DLanguageFuncDeclaration o) {
        return false;//todo
    }

    public static boolean isTrusted(DLanguageFuncDeclaration o) {
        return false;//todo
    }

    public static boolean hasCustomProperty(DLanguageFuncDeclaration o) {
        return false;//todo
    }

    public static boolean isSafe(DLanguageFuncDeclaration o) {
        return false;//todo
    }

    public static boolean isPropertyFunction(DLanguageFuncDeclaration o) {
        return false;//todo
    }

    public static DLanguageUserDefinedAttribute getCustomProperty(DLanguageFuncDeclaration o) {
        return null;//todo
    }
    // ------------- Function Definition ------------------ //

    // ------------- Class Definition ------------------ //
    @NotNull
    public static String getName(@NotNull DLanguageClassDeclaration o) {
        DLanguageClassDeclarationStub stub = o.getStub();
        if (stub != null) return StringUtil.notNullize(stub.getName());

        return o.getIdentifier().getText();
    }

    @Nullable
    public static PsiElement getNameIdentifier(@NotNull DLanguageClassDeclaration o) {
        ASTNode keyNode = o.getNode();
        return keyNode != null ? keyNode.getPsi() : null;
    }

    @NotNull
    public static PsiElement setName(@NotNull DLanguageClassDeclaration o, @NotNull String newName) {
        o.getIdentifier().setName(newName);
        return o;
    }

    @NotNull
    public static PsiReference getReference(@NotNull DLanguageClassDeclaration o) {
        return new DReference(o, TextRange.from(0, getName(o).length()));
    }

    @NotNull
    public static ItemPresentation getPresentation(final DLanguageClassDeclaration o) {
        return new ItemPresentation() {
            @NotNull
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


    public static DLanguageProtectionAttribute getProtection(@NotNull DLanguageClassDeclaration o) {
        return getChildOfType(o, DLanguageProtectionAttribute.class);
    }

    public static List<CanInherit> whatInheritsFrom(@NotNull DLanguageClassDeclaration o) {
        final DLanguageBaseClassList baseClassList = o.getBaseClassList();
        if (baseClassList == null)
            return Collections.emptyList();
        ArrayList<CanInherit> res = new ArrayList<>();
        ArrayList<DLanguageBasicType> basicTypes = new ArrayList<>();
        basicTypes.add(baseClassList.getSuperClass().getBasicType());
        for (DLanguageInterface interface_ : findChildrenOfType(baseClassList.getInterfaces(), DLanguageInterface.class)) {
            basicTypes.add(interface_.getBasicType());
        }
        for (DLanguageBasicType basicType : basicTypes) {
            assert (basicType.getBasicTypeX() == null);
            assert (basicType.getTypeVector() == null);
            assert (basicType.getTypeof() == null);
            final DLanguageIdentifierList identifierList = basicType.getIdentifierList();
            final List<PsiNamedElement> definitionNodesSimple = DResolveUtil.INSTANCE.findDefinitionNodes((DLanguageFile) identifierList.getContainingFile(), getEndOfIdentifierList(identifierList).getName());
            Set<CanInherit> definitionNodes = new HashSet<>();
            for (PsiElement node : definitionNodesSimple) {
                if (definitionNodes instanceof CanInherit)
                    definitionNodes.add((CanInherit) definitionNodes);
            }
            assert (definitionNodes.size() == 1);
            res.add((CanInherit) definitionNodes.toArray()[0]);

        }

        return res;
    }

    public static Map<String, DLanguageIdentifier> getSuperClassNames(@NotNull DLanguageClassDeclaration o) {
        final DLanguageBaseClassList baseClassList = o.getBaseClassList();
        if (baseClassList == null)
            return Collections.emptyMap();
        Map<String, DLanguageIdentifier> res = new HashMap<>();
        ArrayList<DLanguageBasicType> basicTypes = new ArrayList<>();
        basicTypes.add(baseClassList.getSuperClass().getBasicType());
        for (DLanguageInterface interface_ : findChildrenOfType(baseClassList.getInterfaces(), DLanguageInterface.class)) {
            basicTypes.add(interface_.getBasicType());
        }
        for (DLanguageBasicType basicType : basicTypes) {
            assert (basicType.getBasicTypeX() == null);
            assert (basicType.getTypeVector() == null);
            assert (basicType.getTypeof() == null);
            final DLanguageIdentifierList identifierList = basicType.getIdentifierList();
            res.put(getEndOfIdentifierList(identifierList).getName(), getEndOfIdentifierList(identifierList));
        }

        return res;
    }

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
        if (o.getIdentifier() != null)
            o.getIdentifier().setName(newName);
        else
            return null;
        return o;
    }

    @NotNull
    public static PsiReference getReference(@NotNull DLanguageStructDeclaration o) {
        return new DReference(o, TextRange.from(0, getName(o).length()));
    }

    @NotNull
    public static ItemPresentation getPresentation(final DLanguageStructDeclaration o) {
        return new ItemPresentation() {
            @NotNull
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


    // ------------- Struct Definition ------------------ //

    // ------------- Enum Definition ------------------ //
    @NotNull
    public static String getName(@NotNull DLanguageEnumDeclaration o) {
        DLanguageEnumDeclarationStub stub = o.getStub();
        if (stub != null) return StringUtil.notNullize(stub.getName());

        if (o.getIdentifier() != null) {
            return o.getIdentifier().getText();
        } else {
            return "not found";
        }
    }

    @Nullable
    public static PsiElement getNameIdentifier(@NotNull DLanguageEnumDeclaration o) {
        ASTNode keyNode = o.getNode();
        return keyNode != null ? keyNode.getPsi() : null;
    }

    @Nullable
    public static PsiElement setName(@NotNull DLanguageEnumDeclaration o, @NotNull String newName) {
        if (o.getIdentifier() != null) {
            o.getIdentifier().setName(newName);
        } else if (o.getAnonymousEnumDeclaration() != null) {
            return null;
        }
        return o;
    }

    @NotNull
    public static PsiReference getReference(@NotNull DLanguageEnumDeclaration o) {
        return new DReference(o, TextRange.from(0, getName(o).length()));
    }

    @NotNull
    public static ItemPresentation getPresentation(final DLanguageEnumDeclaration o) {
        return new ItemPresentation() {
            @NotNull
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

    public static DLanguageProtectionAttribute getProtection(DLanguageEnumDeclaration o) {
        return getChildOfType(o, DLanguageProtectionAttribute.class);
    }


    // ------------- Enum Definition -------------------- //

    // ------------- Union Definition ------------------ //
    @NotNull
    public static String getName(@NotNull DLanguageUnionDeclaration o) {
        DLanguageUnionDeclarationStub stub = o.getStub();
        if (stub != null) return StringUtil.notNullize(stub.getName());

        if (o.getIdentifier() != null) {
            return o.getIdentifier().getText();
        } else {
            return "not found";
        }
    }

    @Nullable
    public static PsiElement getNameIdentifier(@NotNull DLanguageUnionDeclaration o) {
        ASTNode keyNode = o.getNode();
        return keyNode != null ? keyNode.getPsi() : null;
    }

    @Nullable
    public static PsiElement setName(@NotNull DLanguageUnionDeclaration o, @NotNull String newName) {
        if (o.getIdentifier() != null) {
            o.getIdentifier().setName(newName);
        } else
            return null;
        return o;
    }

    @NotNull
    public static PsiReference getReference(@NotNull DLanguageUnionDeclaration o) {
        return new DReference(o, TextRange.from(0, getName(o).length()));
    }

    @NotNull
    public static ItemPresentation getPresentation(final DLanguageUnionDeclaration o) {
        return new ItemPresentation() {
            @NotNull
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

    public static DLanguageProtectionAttribute getProtection(@NotNull DLanguageUnionDeclaration o) {
        return getChildOfType(o, DLanguageProtectionAttribute.class);
    }

    // ------------- Union Definition -------------------- //

    // ------------- Template Definition ------------------ //

    @NotNull
    public static String getName(@NotNull DLanguageTemplateDeclaration o) {
        DLanguageTemplateDeclarationStub stub = o.getStub();
        if (stub != null) return StringUtil.notNullize(stub.getName());

        return o.getIdentifier().getText();
    }

    @Nullable
    public static PsiElement getNameIdentifier(@NotNull DLanguageTemplateDeclaration o) {
        ASTNode keyNode = o.getNode();
        return keyNode != null ? keyNode.getPsi() : null;
    }

    @NotNull
    public static PsiElement setName(@NotNull DLanguageTemplateDeclaration o, @NotNull String newName) {
        o.getIdentifier().setName(newName);
        return o;
    }

    @NotNull
    public static PsiReference getReference(@NotNull DLanguageTemplateDeclaration o) {
        return new DReference(o, TextRange.from(0, getName(o).length()));
    }

    @NotNull
    public static ItemPresentation getPresentation(final DLanguageTemplateDeclaration o) {
        return new ItemPresentation() {
            @NotNull
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
        if (DUtil.getParentClassOrStruct(o) != null)
            return DUtil.getParentClassOrStruct(o).getName();
        return "";
    }

    @Nullable
    public static PsiElement getNameIdentifier(@NotNull DLanguageConstructor o) {
        ASTNode keyNode = o.getNode();
        return keyNode != null ? keyNode.getPsi() : null;
    }

    @NotNull
    public static PsiElement setName(@NotNull DLanguageConstructor o, @NotNull String newName) {
        DUtil.getParentClassOrStruct(o).setName(newName);
        return o;
    }

    @NotNull
    public static PsiReference getReference(@NotNull DLanguageConstructor o) {
        return new DReference(o, TextRange.from(0, getName(o).length()));
    }

    @NotNull
    public static ItemPresentation getPresentation(final DLanguageConstructor o) {
        return new ItemPresentation() {
            @NotNull
            @Override
            public String getPresentableText() {
                String string = "";
                for (PsiElement psiElement : o.getChildren()) {
                    if (psiElement instanceof DLanguageParametersImpl)
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

    @NotNull
    public static List<DLanguageParameter> getParameterList(@NotNull DLanguageConstructor o) {
            return Arrays.asList(getChildrenOfType(o.getParameters(), DLanguageParameter.class));
    }

    // ------------- Constructor ------------------ //

    // ------------ Module Declaration ----------------- //

    @NotNull
    public static String getName(@NotNull DLanguageModuleDeclaration o) {
        DLanguageModuleDeclarationStub stub = o.getStub();
        if (stub != null) return StringUtil.notNullize(stub.getName());

        if (o.getModuleFullyQualifiedName() == null) {
            Logger.getLogger(DPsiImplUtil.class).debug("this had no name: " + o.getText());
            return "";
        }

        if (o.getModuleFullyQualifiedName().getText() != null) {
            return o.getModuleFullyQualifiedName().getText();
        } else {
            return "not found";
        }
    }

    @Nullable
    public static PsiElement getNameIdentifier(@NotNull DLanguageModuleDeclaration o) {
        ASTNode keyNode = o.getNode();
        return keyNode != null ? keyNode.getPsi() : null;
    }

    @NotNull
    public static PsiElement setName(@NotNull DLanguageModuleDeclaration o, @NotNull String newName) {
        PsiElement e = DElementFactory.createDLanguageModuleFromText(o.getProject(), newName);
        if (e == null) return null;
        o.replace(e);
        return o;
    }

    @NotNull
    public static PsiReference getReference(@NotNull DLanguageModuleDeclaration o) {
        return new DReference(o, TextRange.from(0, getName(o).length()));
    }

    @NotNull
    public static ItemPresentation getPresentation(final DLanguageModuleDeclaration o) {
        return new ItemPresentation() {
            @NotNull
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
    public static DLanguageProtectionAttribute getProtection(DLanguageModuleDeclaration o) {
        return getChildOfType(o, DLanguageProtectionAttribute.class);
    }

    // ------------ Module Declaration ----------------- //

    // ------------- Interface Definition ------------------ //

    @NotNull
    public static String getName(@NotNull DLanguageInterfaceDeclaration o) {
        DLanguageInterfaceDeclarationStub stub = o.getStub();
        if (stub != null) return StringUtil.notNullize(stub.getName());
        if (o.getIdentifier() == null) {
            return "";
        }
        return o.getIdentifier().getText();
    }

    @Nullable
    public static PsiElement getNameIdentifier(@NotNull DLanguageInterfaceDeclaration o) {
        ASTNode keyNode = o.getNode();
        return keyNode != null ? keyNode.getPsi() : null;
    }

    @NotNull
    public static PsiElement setName(@NotNull DLanguageInterfaceDeclaration o, @NotNull String newName) {
        o.getIdentifier().setName(newName);
        return o;
    }

    @NotNull
    public static PsiReference getReference(@NotNull DLanguageInterfaceDeclaration o) {
        return new DReference(o, TextRange.from(0, getName(o).length()));
    }

    @NotNull
    public static ItemPresentation getPresentation(final @NotNull DLanguageInterfaceDeclaration o) {
        return new ItemPresentation() {
            @NotNull
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

    public static List<CanInherit> whatInheritsFrom(@NotNull DLanguageInterfaceDeclaration o) {
        DLanguageBaseInterfaceList baseInterfaceList = o.getBaseInterfaceList();
        if (baseInterfaceList == null) {
            return Collections.emptyList();
        }
        ArrayList<CanInherit> res = new ArrayList<>();
        ArrayList<DLanguageBasicType> basicTypes = new ArrayList<>();
        for (DLanguageInterface interface_ : findChildrenOfType(baseInterfaceList.getInterfaces(), DLanguageInterface.class)) {
            basicTypes.add(interface_.getBasicType());
        }
        for (DLanguageBasicType basicType : basicTypes) {
            assert (basicType.getBasicTypeX() == null);
            assert (basicType.getTypeVector() == null);
            assert (basicType.getTypeof() == null);
            final DLanguageIdentifierList identifierList = basicType.getIdentifierList();
            final List<PsiNamedElement> definitionNodesSimple = DResolveUtil.INSTANCE.findDefinitionNodes((DLanguageFile) identifierList.getContainingFile(), getEndOfIdentifierList(identifierList).getName());
            Set<CanInherit> definitionNodes = new HashSet<>();
            for (PsiElement node : definitionNodesSimple) {
                if (definitionNodes instanceof CanInherit)
                    definitionNodes.add((CanInherit) definitionNodes);
            }
            assert (definitionNodes.size() == 1);
            res.add((CanInherit) definitionNodes.toArray()[0]);

        }

        return res;
    }

    public static Map<String, DLanguageIdentifier> getSuperClassNames(@NotNull DLanguageInterfaceDeclaration o) {
        DLanguageBaseInterfaceList baseInterfaceList = o.getBaseInterfaceList();
        if (baseInterfaceList == null) {
            return Collections.emptyMap();
        }
        Map<String, DLanguageIdentifier> res = new HashMap<>();
        ArrayList<DLanguageBasicType> basicTypes = new ArrayList<>();
        for (DLanguageInterface interface_ : findChildrenOfType(baseInterfaceList.getInterfaces(), DLanguageInterface.class)) {
            basicTypes.add(interface_.getBasicType());
        }
        for (DLanguageBasicType basicType : basicTypes) {
            assert (basicType.getBasicTypeX() == null);
            assert (basicType.getTypeVector() == null);
            assert (basicType.getTypeof() == null);
            final DLanguageIdentifierList identifierList = basicType.getIdentifierList();
            res.put(getEndOfIdentifierList(identifierList).getName(), getEndOfIdentifierList(identifierList));
        }
        return res;
    }

    // ------------- Interface Definition ------------------ //

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

    @NotNull
    public static PsiElement setName(@NotNull DLanguageLabeledStatement o, @NotNull String newName) {
        o.getIdentifier().setName(newName);
        return o;
    }

    @NotNull
    public static PsiReference getReference(@NotNull DLanguageLabeledStatement o) {
        return new DReference(o, TextRange.from(0, getName(o).length()));
    }

    @NotNull
    public static ItemPresentation getPresentation(final @NotNull DLanguageLabeledStatement o) {
        return new ItemPresentation() {
            @NotNull
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

    @NotNull
    public static PsiElement setName(@NotNull DLanguageTemplateMixinDeclaration o, @NotNull String newName) {
        o.getIdentifier().setName(newName);
        return o;
    }

    @NotNull
    public static PsiReference getReference(@NotNull DLanguageTemplateMixinDeclaration o) {
        return new DReference(o, TextRange.from(0, getName(o).length()));
    }

    @NotNull
    public static ItemPresentation getPresentation(final DLanguageTemplateMixinDeclaration o) {
        return new ItemPresentation() {
            @NotNull
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

    // -------------- Mixin Template Resolving ------------------- //

    @Nullable
    public static Mixinable getMixinableDeclaration(@NotNull DLanguageMixinDeclaration t) {
        if (t.getTemplateInstance() != null) {
            if (t.getTemplateInstance().getIdentifier() == null)
                return null;
            final PsiElement resolve = t.getTemplateInstance().getIdentifier().getReference().resolve();
            if (resolve instanceof Mixinable)
                return (Mixinable) resolve;
            return null;
        }
        return null;
    }

    @Nullable
    public static Mixinable getMixinableDeclaration(@NotNull DLanguageTemplateMixin t) {
        final PsiElement definitionNodes = getEndOfIdentifierList(t.getMixinTemplateName().getQualifiedIdentifierList()).getReference().resolve();
        if (definitionNodes instanceof Mixinable) {
            return (Mixinable) definitionNodes;
        }
        return null;
    }

    @Nullable
    public static String getName(@NotNull DLanguageMixinDeclaration t) {
        if (t.getTemplateInstance() != null) {
            if (t.getTemplateInstance().getIdentifier() == null)
                return null;
            return t.getTemplateInstance().getIdentifier().getName();
        }
        return null;
    }

    @NotNull
    public static String getName(@NotNull DLanguageTemplateMixin t) {
        return getEndOfIdentifierList(t.getMixinTemplateName().getQualifiedIdentifierList()).getName();
    }

    @NotNull
    public static String getName(@NotNull DLanguageMixinExpression t) {
        return findChildOfType(t, DLanguageIdentifier.class).getName();
    }

    @NotNull
    public static String getName(@NotNull DLanguageMixinStatement t) {
        return findChildOfType(t, DLanguageIdentifier.class).getName();
    }

    // -------------- Mixin Template Resolving ------------------- //

    // ------------- Var Declaration ------------------ //

    @NotNull
    public static String getName(@NotNull DLanguageDeclaratorInitializer o) {
        DLanguageDeclaratorInitializerStub stub = o.getStub();
        if (stub != null) return StringUtil.notNullize(stub.getName());

        if (getIdentifier(o) != null) {
            return getIdentifier(o).getText();
        } else {
            return "not found";
        }
    }

    @NotNull
    private static DLanguageIdentifier getIdentifier(DLanguageDeclaratorInitializer o) {
        if (o.getAltDeclarator() != null) {
            final DLanguageAltDeclarator altDeclarator = o.getAltDeclarator();
            return getIdentifier(altDeclarator);
        } else /*if (o.getVarDeclarator() != null) */ {
            final DLanguageVarDeclarator varDeclarator = o.getVarDeclarator();
            assert varDeclarator != null;
            return varDeclarator.getIdentifier();
        }
    }

    private static DLanguageIdentifier getIdentifier(DLanguageAltDeclarator altDeclarator) {
        if (altDeclarator.getIdentifier() != null) return altDeclarator.getIdentifier();
        else
            return getIdentifier(altDeclarator.getAltDeclaratorX());
    }

    private static DLanguageIdentifier getIdentifier(DLanguageAltDeclaratorX altDeclaratorX) {
        if (altDeclaratorX.getIdentifier() != null)
            return altDeclaratorX.getIdentifier();
        return getIdentifier(altDeclaratorX.getAltDeclarator());
    }

    @Nullable
    public static PsiElement getNameIdentifier(@NotNull DLanguageDeclaratorInitializer o) {
        ASTNode keyNode = o.getNode();
        return keyNode != null ? keyNode.getPsi() : null;
    }

    @NotNull
    public static PsiElement setName(@NotNull DLanguageDeclaratorInitializer o, @NotNull String newName) {
        if (getIdentifier(o) == null)
            return null;
        getIdentifier(o).setName(newName);
        return o;
    }

    @NotNull
    public static PsiReference getReference(@NotNull DLanguageDeclaratorInitializer o) {
        return new DReference(o, TextRange.from(0, getName(o).length()));
    }

    @NotNull
    public static ItemPresentation getPresentation(final DLanguageDeclaratorInitializer o) {
        return new ItemPresentation() {
            @NotNull
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

    public static boolean actuallyIsDeclaration(DLanguageDeclaratorInitializer o) {
        final DLanguageVarDeclarations parentVarDeclaration = (DLanguageVarDeclarations) o.getParent().getParent();
        if (parentVarDeclaration.getStorageClasses() != null)
            return true;
        if (parentVarDeclaration.getBasicType() != null)
            return true;
        if (o.getAltDeclarator() != null) {
            final DLanguageAltDeclarator altDeclarator = o.getAltDeclarator();
            return false;//todo the majority of the time false is correct however occasionally an alt declarator might actually be a legitimate variable declaration
        }
        if (o.getVarDeclarator() != null) {
            final DLanguageVarDeclarator varDeclarator = o.getVarDeclarator();
            if (varDeclarator.getBasicType2() != null)
                return true;
        }
        return false;//default to false.
    }

    public static Type getVariableDeclarationType(DLanguageDeclaratorInitializer o) {
        return new Type(o);

    }

    // ------------- Var Declaration ------------------ //`

    // ------------- Auto Declaration Y ------------------ //

    @NotNull
    public static String getName(@NotNull DLanguageAutoDeclarationY o) {
        DLanguageAutoDeclarationStub stub = o.getStub();
        if (stub != null) return StringUtil.notNullize(stub.getName());

        if (o.getIdentifier() != null) {
            return o.getIdentifier().getText();
        } else {
            return "not found";
        }
    }

    @Nullable
    public static PsiElement getNameIdentifier(@NotNull DLanguageAutoDeclarationY o) {
        ASTNode keyNode = o.getNode();
        return keyNode != null ? keyNode.getPsi() : null;
    }

    @NotNull
    public static PsiElement setName(@NotNull DLanguageAutoDeclarationY o, @NotNull String newName) {
        o.getIdentifier().setName(newName);
        return o;
    }

    @NotNull
    public static PsiReference getReference(@NotNull DLanguageAutoDeclarationY o) {
        return new DReference(o, TextRange.from(0, getName(o).length()));
    }

    @NotNull
    public static ItemPresentation getPresentation(final DLanguageAutoDeclarationY o) {
        return new ItemPresentation() {
            @NotNull
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

    public static boolean actuallyIsDeclaration(DLanguageAutoDeclarationY o) {
        final DLanguageStorageClasses storageClasses = ((DLanguageAutoDeclaration) o.getParent().getParent()).getStorageClasses();
        if (storageClasses == null)
            return false;
        for (DLanguageStorageClass dLanguageStorageClass : storageClasses.getStorageClassList()) {
            if (dLanguageStorageClass.getKwAuto() != null)
                return true;
        }
        return false;
    }

    public static Type getVariableDeclarationType(DLanguageAutoDeclarationY o) {
        return null;//todo implement

    }

    // ------------- Auto Declaration Y ------------------ //

    // ------------ Import Declaration ----------------- //

    @NotNull
    public static String getName(@NotNull DLanguageImport o) {
        DLanguageImportDeclarationStub stub = o.getStub();
        if (stub != null) return StringUtil.notNullize(stub.getName());

        if (o.getModuleFullyQualifiedName().getText() != null) {
            return o.getModuleFullyQualifiedName().getText();
        } else {
            return "not found";
        }
    }

    @Nullable
    public static PsiElement getNameIdentifier(@NotNull DLanguageImport o) {
        ASTNode keyNode = o.getNode();
        return keyNode != null ? keyNode.getPsi() : null;
    }

    @NotNull
    public static PsiElement setName(@NotNull DLanguageImport o, @NotNull String newName) {
        PsiElement e = DElementFactory.createDLanguageImportFromText(o.getProject(), newName);
        if (e == null) return null;
        o.replace(e);
        return o;
    }

    @NotNull
    public static PsiReference getReference(@NotNull DLanguageImport o) {
        return new DReference(o, TextRange.from(0, getName(o).length()));
    }

    @NotNull
    public static ItemPresentation getPresentation(final DLanguageImport o) {
        return new ItemPresentation() {
            @NotNull
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
    public static DLanguageProtectionAttribute getProtection(DLanguageImport o) {
        return getChildOfType(o, DLanguageProtectionAttribute.class);
    }

    // ------------ Import Declaration ----------------- //

    // ------------ Catch Parameter ----------------- //

    @NotNull
    public static String getName(@NotNull DLanguageCatchParameter o) {
        DLanguageCatchParameterStub stub = o.getStub();
        if (stub != null) return StringUtil.notNullize(stub.getName());

        if (o.getIdentifier() != null) {
            return o.getIdentifier().getName();
        }
        return "anon_catch_parameter";

    }

    @Nullable
    public static PsiElement getNameIdentifier(@NotNull DLanguageCatchParameter o) {
        ASTNode keyNode = o.getNode();
        return keyNode != null ? keyNode.getPsi() : null;
    }

    @NotNull
    public static PsiElement setName(@NotNull DLanguageCatchParameter o, @NotNull String newName) {
        if (o.getIdentifier() != null) {
            o.getIdentifier().setName(newName);
        } else {
            throw new IllegalStateException("renaming a catch parameter withouta name");
        }
        return o;
    }

    @NotNull
    public static PsiReference getReference(@NotNull DLanguageCatchParameter o) {
        return new DReference(o, TextRange.from(0, getName(o).length()));
    }

    @NotNull
    public static ItemPresentation getPresentation(final DLanguageCatchParameter o) {
        return new ItemPresentation() {
            @NotNull
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

    // ------------ Catch Parameter ----------------- //

    // ------------ Alias Declaration Y ----------------- //

    @NotNull
    public static String getName(@NotNull DLanguageAliasDeclarationY o) {
        DLanguageAliasDeclarationYStub stub = o.getStub();
        if (stub != null) return StringUtil.notNullize(stub.getName());
        return o.getIdentifier().getName();
    }

    @Nullable
    public static PsiElement getNameIdentifier(@NotNull DLanguageAliasDeclarationY o) {
        ASTNode keyNode = o.getNode();
        return keyNode != null ? keyNode.getPsi() : null;
    }

    @NotNull
    public static PsiElement setName(@NotNull DLanguageAliasDeclarationY o, @NotNull String newName) {
        o.getIdentifier().setName(newName);
        return o;
    }

    @NotNull
    public static PsiReference getReference(@NotNull DLanguageAliasDeclarationY o) {
        return new DReference(o, TextRange.from(0, getName(o).length()));
    }

    @NotNull
    public static ItemPresentation getPresentation(final DLanguageAliasDeclarationY o) {
        return new ItemPresentation() {
            @NotNull
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

    public static boolean actuallyIsDeclaration(DLanguageAliasDeclarationY o) {
        return true;
    }

    // ------------ Alias Declaration Y ----------------- //

    // ------------ Alias Declaration Single ----------------- //

    @NotNull
    public static String getName(@NotNull DLanguageAliasDeclarationSingle o) {
        DLanguageAliasDeclarationSingleStub stub = o.getStub();
        if (stub != null) return StringUtil.notNullize(stub.getName());
        if (o.getIdentifier() != null) {
            return o.getIdentifier().getName();
        }
        return getIdentifier(o.getDeclarator()).getName();
    }

    @Nullable
    public static PsiElement getNameIdentifier(@NotNull DLanguageAliasDeclarationSingle o) {
        ASTNode keyNode = o.getNode();
        return keyNode != null ? keyNode.getPsi() : null;
    }

    @NotNull
    public static PsiElement setName(@NotNull DLanguageAliasDeclarationSingle o, @NotNull String newName) {
        if (o.getIdentifier() != null) {
            return o.getIdentifier().setName(newName);
        } else {
            getIdentifier(o.getDeclarator()).setName(newName);
        }
        return o;
    }

    @NotNull
    public static PsiReference getReference(@NotNull DLanguageAliasDeclarationSingle o) {
        return new DReference(o, TextRange.from(0, getName(o).length()));
    }

    @NotNull
    public static ItemPresentation getPresentation(final DLanguageAliasDeclarationSingle o) {
        return new ItemPresentation() {
            @NotNull
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

    public static boolean actuallyIsDeclaration(DLanguageAliasDeclarationSingle o) {
        return true;
    }

    // ------------ Alias Declaration Single ----------------- //

    // ------------ Condition Variable Declaration ----------------- //

    public static DLanguageIdentifier getIdentifier(DLanguageConditionVariableDeclaration o) {
        if (o.getIdentifier() != null) {
            return o.getIdentifier();
        }
        if (o.getDeclarator() != null) {
            return getIdentifier(o.getDeclarator());
        }
        throw new IllegalStateException("this should never happen");
    }

    public static DLanguageIdentifier getIdentifier(DLanguageDeclarator declarator) {
        if (declarator.getVarDeclarator() != null) {
            return declarator.getVarDeclarator().getIdentifier();
        }
        if (declarator.getAltDeclarator() != null) {
            return getIdentifier(declarator.getAltDeclarator());
        }
        throw new IllegalStateException("this should never happen");
    }

    @NotNull
    public static String getName(@NotNull DLanguageConditionVariableDeclaration o) {
        DLanguageConditionVariableDeclarationStub stub = o.getStub();
        if (stub != null) return StringUtil.notNullize(stub.getName());
        return getIdentifier(o).getName();
    }

    @Nullable
    public static PsiElement getNameIdentifier(@NotNull DLanguageConditionVariableDeclaration o) {
        ASTNode keyNode = o.getNode();
        return keyNode != null ? keyNode.getPsi() : null;
    }

    @NotNull
    public static PsiElement setName(@NotNull DLanguageConditionVariableDeclaration o, @NotNull String newName) {
        getIdentifier(o).setName(newName);
        return o;
    }

    @NotNull
    public static PsiReference getReference(@NotNull DLanguageConditionVariableDeclaration o) {
        return new DReference(o, TextRange.from(0, getName(o).length()));
    }

    @NotNull
    public static ItemPresentation getPresentation(final DLanguageConditionVariableDeclaration o) {
        return new ItemPresentation() {
            @NotNull
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

    public static boolean actuallyIsDeclaration(DLanguageConditionVariableDeclaration o) {
        return true;
    }

    // ------------ Condition Variable Declaration ----------------- //

    // ------------ Foreach Type Declaration ----------------- //

    @NotNull
    public static String getName(@NotNull DLanguageForeachType o) {
        DLanguageForeachTypeStub stub = o.getStub();
        if (stub != null) return StringUtil.notNullize(stub.getName());
        return o.getIdentifier().getName();
    }

    @Nullable
    public static PsiElement getNameIdentifier(@NotNull DLanguageForeachType o) {
        ASTNode keyNode = o.getNode();
        return keyNode != null ? keyNode.getPsi() : null;
    }

    @NotNull
    public static PsiElement setName(@NotNull DLanguageForeachType o, @NotNull String newName) {
        o.getIdentifier().setName(newName);
        return o;
    }

    @NotNull
    public static PsiReference getReference(@NotNull DLanguageForeachType o) {
        return new DReference(o, TextRange.from(0, getName(o).length()));
    }

    @NotNull
    public static ItemPresentation getPresentation(final DLanguageForeachType o) {
        return new ItemPresentation() {
            @NotNull
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

    public static boolean actuallyIsDeclaration(DLanguageForeachType o) {
        return true;
    }

    // ------------ Foreach Type Declaration ----------------- //

    // ------------ Var Func Declaration ----------------- //

    @NotNull
    public static String getName(@NotNull DLanguageVarFuncDeclaration o) {
        DLanguageVarFuncDeclarationStub stub = o.getStub();
        if (stub != null) return StringUtil.notNullize(stub.getName());
        return o.getIdentifier().getName();
    }

    @Nullable
    public static PsiElement getNameIdentifier(@NotNull DLanguageVarFuncDeclaration o) {
        ASTNode keyNode = o.getNode();
        return keyNode != null ? keyNode.getPsi() : null;
    }

    @NotNull
    public static PsiElement setName(@NotNull DLanguageVarFuncDeclaration o, @NotNull String newName) {
        o.getIdentifier().setName(newName);
        return o;
    }

    @NotNull
    public static PsiReference getReference(@NotNull DLanguageVarFuncDeclaration o) {
        return new DReference(o, TextRange.from(0, getName(o).length()));
    }

    @NotNull
    public static ItemPresentation getPresentation(final DLanguageVarFuncDeclaration o) {
        return new ItemPresentation() {
            @NotNull
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

    // ------------ Var Func Declaration ----------------- //

    // ------------ Parameter ----------------- //

    public static @NotNull
    DLanguageIdentifier getIdentifier(DLanguageParameter o) {
        if (o.getIdentifier() != null) {
            return o.getIdentifier();
        }
        if (o.getDeclarator() != null) {
            return getIdentifier(o.getDeclarator());
        }
        if (o.getType() != null) {
            return DUtil.getEndOfIdentifierList(o.getType().getBasicType().getIdentifierList());
        }
        throw new IllegalStateException("this should never happen");
    }

    @NotNull
    public static String getName(@NotNull DLanguageParameter o) {
        DLanguageParameterStub stub = o.getStub();
        if (stub != null) return StringUtil.notNullize(stub.getName());
        return getIdentifier(o).getName();
    }

    @Nullable
    public static PsiElement getNameIdentifier(@NotNull DLanguageParameter o) {
        ASTNode keyNode = o.getNode();
        return keyNode != null ? keyNode.getPsi() : null;
    }

    @NotNull
    public static PsiElement setName(@NotNull DLanguageParameter o, @NotNull String newName) {
        getIdentifier(o).setName(newName);
        return o;
    }

    @NotNull
    public static PsiReference getReference(@NotNull DLanguageParameter o) {
        return new DReference(o, TextRange.from(0, getName(o).length()));
    }

    @NotNull
    public static ItemPresentation getPresentation(final DLanguageParameter o) {
        return new ItemPresentation() {
            @NotNull
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

    // ------------ Parameter ----------------- //

    // ------------ Template Parameter ----------------- //

    @NotNull
    private static DLanguageIdentifier getIdentifier(DLanguageTemplateTypeParameter templateTypeParameter) {
        if (templateTypeParameter.getIdentifier() != null) {
            return templateTypeParameter.getIdentifier();
        }
        return DUtil.getEndOfIdentifierList(templateTypeParameter.getTypeList().get(0).getBasicType().getIdentifierList());
    }


    public static @NotNull
    DLanguageIdentifier getIdentifier(DLanguageTemplateParameter o) {
        if (o.getTemplateAliasParameter() != null) {
            if (o.getTemplateAliasParameter().getIdentifier() != null) {
                return o.getTemplateAliasParameter().getIdentifier();
            }
            return DUtil.getEndOfIdentifierList(o.getTemplateAliasParameter().getTypeList().get(0).getBasicType().getIdentifierList());
        }
        if (o.getTemplateThisParameter() != null) {
            return getIdentifier(o.getTemplateThisParameter().getTemplateTypeParameter());
        }
        if (o.getTemplateTupleParameter() != null) {
            return o.getTemplateTupleParameter().getIdentifier();
        }
        if (o.getTemplateTypeParameter() != null) {
            return getIdentifier(o.getTemplateTypeParameter());
        }
        if (o.getTemplateValueParameter() != null) {
            return getIdentifier(o.getTemplateValueParameter().getDeclarator());
        }
        throw new IllegalStateException("this should never happen");
    }

    @NotNull
    public static String getName(@NotNull DLanguageTemplateParameter o) {
        DLanguageTemplateParameterStub stub = o.getStub();
        if (stub != null) return StringUtil.notNullize(stub.getName());
        return getIdentifier(o).getName();
    }

    @Nullable
    public static PsiElement getNameIdentifier(@NotNull DLanguageTemplateParameter o) {
        ASTNode keyNode = o.getNode();
        return keyNode != null ? keyNode.getPsi() : null;
    }

    @NotNull
    public static PsiElement setName(@NotNull DLanguageTemplateParameter o, @NotNull String newName) {
        getIdentifier(o).setName(newName);
        return o;
    }

    @NotNull
    public static PsiReference getReference(@NotNull DLanguageTemplateParameter o) {
        return new DReference(o, TextRange.from(0, getName(o).length()));
    }

    @NotNull
    public static ItemPresentation getPresentation(final DLanguageTemplateParameter o) {
        return new ItemPresentation() {
            @NotNull
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

    // ------------ Template Parameter ----------------- //

    // ------------ Enum Member ----------------- //

    @NotNull
    public static String getName(@NotNull DLanguageEnumMember o) {
        DLanguageEnumMemberStub stub = o.getStub();
        if (stub != null) return StringUtil.notNullize(stub.getName());
        return o.getIdentifier().getName();
    }

    @Nullable
    public static PsiElement getNameIdentifier(@NotNull DLanguageEnumMember o) {
        ASTNode keyNode = o.getNode();
        return keyNode != null ? keyNode.getPsi() : null;
    }

    @NotNull
    public static PsiElement setName(@NotNull DLanguageEnumMember o, @NotNull String newName) {
        o.getIdentifier().setName(newName);
        return o;
    }

    @NotNull
    public static PsiReference getReference(@NotNull DLanguageEnumMember o) {
        return new DReference(o, TextRange.from(0, getName(o).length()));
    }

    @NotNull
    public static ItemPresentation getPresentation(final DLanguageEnumMember o) {
        return new ItemPresentation() {
            @NotNull
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

    // ------------ Enum Member ----------------- //

    // -------------------- Visibility --------------------- //

    public static boolean isSomeVisibility(DLanguageAliasDeclaration o, Visibility visibility) {
        final DLanguageAttributeSpecifier attribute = (DLanguageAttributeSpecifier) o.getParent().getParent().getParent();
        if (attribute.getAttribute().getProtectionAttribute() != null) {
            if (attribute.getAttribute().getProtectionAttribute().getText().equals(visibility))//todo iterate
                return true;
        }
        return isSomeVisibility((DNamedElement) o, visibility, AliasContainer.class);

    }

    public static boolean isSomeVisibility(DLanguageEnumDeclaration o, Visibility visibility) {
        return isSomeVisibility(o, visibility, EnumContainer.class);

    }

    public static boolean isSomeVisibility(DLanguageModuleDeclaration o, Visibility visibility) {
        if (o.getAttribute() != null)
            if (o.getAttribute().getProtectionAttribute() != null)
                if (protectionToVisibilty(o.getAttribute().getProtectionAttribute()) == (visibility))
                    return true;
        return visibility == protectionToVisibilty("public");
    }

    public static boolean isSomeVisibility(DNamedElement psiElement, Visibility visibility, Class<? extends Container> containerType) {
        //todo fix
        return false;
    }
//        PsiElement parent = psiElement.getParent();
//        while (true) {
//            //default to public
//            if (containerType.isInstance(parent)) {
//                return visibility == public_;
//            }
//            // check that named element isn't explicitly marked some visibilty, eg. private gh();
//            if (parent instanceof DLanguageDeclDef && ((DLanguageDeclDef) parent).getAttributeSpecifier() != null) {
//                final DLanguageAttributeSpecifier attribute = ((DLanguageDeclDef) parent).getAttributeSpecifier();
//                if (attribute.getAttribute().getProtectionAttribute() != null) {
//                    try {
//                        return protectionToVisibilty(attribute.getAttribute().getProtectionAttribute()) == (visibility);
//                    } catch (IllegalArgumentException e) {
//                        return false;//todo
//                    }
//                }
//            }
//            if (parent instanceof DLanguageDeclDef && ((DLanguageDeclDefs) parent).getDeclDef().getAttributeSpecifier() != null) {
//                final DLanguageAttributeSpecifier attribute = ((DLanguageDeclDefs) parent).getDeclDef().getAttributeSpecifier();
//                if (attribute.getAttribute().getProtectionAttribute() != null && attribute.getOpColon() != null) {
//                    try {
//                        return protectionToVisibilty(attribute.getAttribute().getProtectionAttribute()) == (visibility);
//                    } catch (IllegalArgumentException e) {
//                        return false;
//                    }
//                }
//            }
//            if(parent instanceof DLanguageDeclDef)
//                parent = parent.getPrevSibling();
//            else
//                parent = parent.getParent();
////            //check for public: or private: or protected:
////            if (parent instanceof DLanguageDeclDefs && ((DLanguageDeclDefs) parent).getDeclDef().getAttributeSpecifier() != null) {
////                final DLanguageAttributeSpecifier attribute = ((DLanguageDeclDefs) parent).getDeclDef().getAttributeSpecifier();
////                if (attribute.getAttribute().getProtectionAttribute() != null && attribute.getOpColon() != null) {
////                    try {
////                        return protectionToVisibilty(attribute.getAttribute().getProtectionAttribute()) == (visibility);
////                    } catch (IllegalArgumentException e) {
////                        return false;
////                    }
////                }
////            }
////            parent = parent.getParent();
////        }
//    }

    public static boolean isSomeVisibility(DLanguageTemplateDeclaration o, Visibility visibility) {
        return isSomeVisibility(o, visibility, TemplateContainer.class);
    }

    public static boolean isSomeVisibility(DLanguageInterfaceDeclaration o, Visibility visibility) {
        return isSomeVisibility(o, visibility, InterfaceContainer.class);
    }

    public static boolean isSomeVisibility(DLanguageClassDeclaration o, Visibility visibility) {
        return isSomeVisibility(o, visibility, ClassContainer.class);
    }

    public static boolean isSomeVisibility(DLanguageStructDeclaration o, Visibility visibility) {
        return isSomeVisibility(o, visibility, StructContainer.class);
    }

    public static boolean isSomeVisibility(DLanguageConstructor o, Visibility visibility) {
        return isSomeVisibility(o, visibility, ConstructorContainer.class);
    }

    public static boolean isSomeVisibility(DLanguageFuncDeclaration o, Visibility visibility) {
        return isSomeVisibility(o, visibility, FunctionContainer.class);
    }

    public static boolean isSomeVisibility(VariableDeclaration o, Visibility visibility) {
        return isSomeVisibility(o, visibility, GlobalVariableContainer.class);//todo check that this still works correctly for local vars/ do we care if local vars don't have correct visibility?
    }

    public static boolean isSomeVisibility(DLanguageUnionDeclaration o, Visibility visibility) {
        return isSomeVisibility(o, visibility, UnionContainer.class);//todo check that this still works correctly for local vars/ do we care if local vars don't have correct visibility?
    }

    public static boolean isSomeVisibility(DLanguageTemplateMixinDeclaration o, Visibility visibility) {
        return isSomeVisibility(o, visibility, TemplateMixinContainer.class);
    }

    public static boolean isSomeVisibility(DLanguageImport o, Visibility visibility) {
        return isSomeVisibility(o, visibility, ImportContainer.class);
    }

    // -------------------- Visibility --------------------- //

    // -------------------- Scope processing --------------- //

    /**
     * takes the elements declared in the given psi and passes them to the scope processor via the execute method. The scope processor will return false if it has found what it is "looking for". Note that certain declarations processors will not process child block statement/aggregate bodies since those have their own processor.
     * Some of the process declarations methods may return early while others will search throught the entire scope
     *
     * @param element
     * @param processor
     * @param state
     * @param lastParent todo make use of this do determine if scope statements/decldefs contained inside a element should be processed or not.
     * @param place
     * @return
     */
    public static boolean processDeclarations(DLanguageDeclDefs element,
                                              @NotNull PsiScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              PsiElement lastParent,
                                              @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(element, processor, state, lastParent, place);
    }

    public static boolean processDeclarations(DLanguageFunctionLiteral element,
                                              @NotNull PsiScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              PsiElement lastParent,
                                              @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(element, processor, state, lastParent, place);
    }

    public static boolean processDeclarations(DLanguageLambda element,
                                              @NotNull PsiScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              PsiElement lastParent,
                                              @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(element, processor, state, lastParent, place);
    }

    public static boolean processDeclarations(DLanguageLabeledStatement element,
                                              @NotNull PsiScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              PsiElement lastParent,
                                              @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(element, processor, state, lastParent, place);
    }

    public static boolean processDeclarations(DLanguageForeachStatement element,
                                              @NotNull PsiScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              PsiElement lastParent,
                                              @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(element, processor, state, lastParent, place);
    }

    public static boolean processDeclarations(DLanguageConstructor element,
                                              @NotNull PsiScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              PsiElement lastParent,
                                              @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(element, processor, state, lastParent, place);
    }

    public static boolean processDeclarations(DLanguageFuncDeclaration element,
                                              @NotNull PsiScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              PsiElement lastParent,
                                              @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(element, processor, state, lastParent, place);
    }

    public static boolean processDeclarations(DLanguageWhileStatement element,
                                              @NotNull PsiScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              PsiElement lastParent,
                                              @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(element, processor, state, lastParent, place);
    }

    public static boolean processDeclarations(DLanguageForStatement element,
                                              @NotNull PsiScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              PsiElement lastParent,
                                              @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(element, processor, state, lastParent, place);
    }

    public static boolean processDeclarations(DLanguageDoStatement element,
                                              @NotNull PsiScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              PsiElement lastParent,
                                              @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(element, processor, state, lastParent, place);
    }

    public static boolean processDeclarations(DLanguageIfStatement element,
                                              @NotNull PsiScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              PsiElement lastParent,
                                              @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(element, processor, state, lastParent, place);
    }

    public static boolean processDeclarations(DLanguageStatementList element,
                                              @NotNull PsiScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              PsiElement lastParent,
                                              @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(element, processor, state, lastParent, place);
    }

    public static boolean processDeclarations(DLanguageSwitchStatement element,
                                              @NotNull PsiScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              PsiElement lastParent,
                                              @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(element, processor, state, lastParent, place);
    }

    public static boolean processDeclarations(DLanguageFinalSwitchStatement element,
                                              @NotNull PsiScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              PsiElement lastParent,
                                              @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(element, processor, state, lastParent, place);
    }

    public static boolean processDeclarations(DLanguageWithStatement element,
                                              @NotNull PsiScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              PsiElement lastParent,
                                              @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(element, processor, state, lastParent, place);
    }

    public static boolean processDeclarations(DLanguageSynchronizedStatement element,
                                              @NotNull PsiScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              PsiElement lastParent,
                                              @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(element, processor, state, lastParent, place);
    }

    public static boolean processDeclarations(DLanguageCatch element,
                                              @NotNull PsiScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              PsiElement lastParent,
                                              @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(element, processor, state, lastParent, place);
    }

    public static boolean processDeclarations(DLanguageVarFuncDeclaration element,
                                              @NotNull PsiScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              PsiElement lastParent,
                                              @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(element, processor, state, lastParent, place);
    }

    public static boolean processDeclarations(DLanguageForeachRangeStatement element,
                                              @NotNull PsiScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              PsiElement lastParent,
                                              @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(element, processor, state, lastParent, place);
    }

    public static boolean processDeclarations(DLanguageAliasDeclarationSingle element,
                                              @NotNull PsiScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              PsiElement lastParent,
                                              @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(element, processor, state, lastParent, place);
    }

    public static boolean processDeclarations(DLanguageAliasDeclarationY element,
                                              @NotNull PsiScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              PsiElement lastParent,
                                              @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(element, processor, state, lastParent, place);
    }

    public static boolean processDeclarations(DLanguageAutoDeclarationY element,
                                              @NotNull PsiScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              PsiElement lastParent,
                                              @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(element, processor, state, lastParent, place);
    }

    public static boolean processDeclarations(DLanguageDeclaratorInitializer element,
                                              @NotNull PsiScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              PsiElement lastParent,
                                              @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(element, processor, state, lastParent, place);
    }

    public static boolean processDeclarations(DLanguageParameterList element,
                                              @NotNull PsiScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              PsiElement lastParent,
                                              @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(element, processor, state, lastParent, place);
    }

    public static boolean processDeclarations(DLanguageParameters element,
                                              @NotNull PsiScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              PsiElement lastParent,
                                              @NotNull PsiElement place) {
        if (element.getParameterList() != null) {
            return ScopeProcessorImpl.INSTANCE.processDeclarations(element.getParameterList(), processor, state, lastParent, place);
        }
        return true;
    }

    public static boolean processDeclarations(DLanguageConditionalStatement element,
                                              @NotNull PsiScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              PsiElement lastParent,
                                              @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(element, processor, state, lastParent, place);
    }

    public static boolean processDeclarations(DLanguageScopeStatement element,
                                              @NotNull PsiScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              PsiElement lastParent,
                                              @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(element, processor, state, lastParent, place);
    }

    public static boolean processDeclarations(DLanguageEnumMembers element,
                                              @NotNull PsiScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              PsiElement lastParent,
                                              @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(element, processor, state, lastParent, place);
    }

    public static boolean processDeclarations(DLanguageDeclDef def, @NotNull PsiScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              PsiElement lastParent,
                                              @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(def, processor, state, lastParent, place);
    }


    public static boolean processDeclarations(DLanguageConditionalDeclaration element,
                                              @NotNull PsiScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              PsiElement lastParent,
                                              @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(element, processor, state, lastParent, place);
    }

    public static boolean processDeclarations(DLanguageClassDeclaration element,
                                              @NotNull PsiScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              PsiElement lastParent,
                                              @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(element, processor, state, lastParent, place);
    }

    public static boolean processDeclarations(DLanguageTemplateDeclaration element,
                                              @NotNull PsiScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              PsiElement lastParent,
                                              @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(element, processor, state, lastParent, place);
    }

    public static boolean processDeclarations(DLanguageStatement element,
                                              @NotNull PsiScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              PsiElement lastParent,
                                              @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(element, processor, state, lastParent, place);
    }

    public static boolean processDeclarations(DLanguageStructDeclaration element,
                                              @NotNull PsiScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              PsiElement lastParent,
                                              @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(element, processor, state, lastParent, place);
    }

    public static boolean processDeclarations(DLanguageTemplateMixinDeclaration element,
                                              @NotNull PsiScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              PsiElement lastParent,
                                              @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(element, processor, state, lastParent, place);
    }
    // -------------------- Scope processing --------------- //


    // -------------------- Misc --------------------- //
    public static String getFullName(DNamedElement e) {
        if (e == null || e.getName() == null)
            return "";
        if (e instanceof DLanguageFile && e.getName() != null)
            return getFullName(e.getParentContainer()) + "." + e.getName().replace(".d", "");
        return getFullName(e.getParentContainer()) + "." + e.getName();
    }
    // -------------------- Misc --------------------- //
}

