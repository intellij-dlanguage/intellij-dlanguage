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
import net.masterthought.dlanguage.psi.interfaces.CanInherit;
import net.masterthought.dlanguage.psi.interfaces.VariableDeclaration;
import net.masterthought.dlanguage.psi.interfaces.containers.*;
import net.masterthought.dlanguage.psi.references.DReference;
import net.masterthought.dlanguage.stubs.*;
import net.masterthought.dlanguage.utils.DUtil;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.*;

import static com.intellij.psi.util.PsiTreeUtil.*;
import static net.masterthought.dlanguage.psi.interfaces.HasVisibility.Visibility;
import static net.masterthought.dlanguage.psi.interfaces.HasVisibility.Visibility.public_;
import static net.masterthought.dlanguage.utils.DResolveUtil.findDefinitionNodes;
import static net.masterthought.dlanguage.utils.DUtil.getEndOfIdentifierList;


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

    @Nullable
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

    @NotNull
    public static List<DLanguageParameter> getArguments(DLanguageFuncDeclaration o) {
        return Arrays.asList(getChildrenOfType(o.getFuncDeclaratorSuffix().getParameters(), DLanguageParameter.class));
    }

    public static List<DLanguageTemplateParameter> getTemplateArguments(DLanguageFuncDeclaration o) {
        if (o.getFuncDeclaratorSuffix().getTemplateParameters() != null)
            return Arrays.asList(getChildrenOfType(o.getFuncDeclaratorSuffix().getTemplateParameters(), DLanguageTemplateParameter.class));
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
            final Set<PsiNamedElement> definitionNodes = findDefinitionNodes(getEndOfIdentifierList(identifierList));
            assert (definitionNodes.size() == 1);
            res.add((CanInherit) definitionNodes.toArray()[0]);

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
        PsiElement e = DElementFactory.createDLanguageEnumDeclarationFromText(o.getProject(), newName);
        if (e == null) return null;
        o.replace(e);
        return o;
    }

    @NotNull
    public static PsiReference getReference(@NotNull DLanguageEnumDeclaration o) {
        return new DReference(o, TextRange.from(0, getName(o).length()));
    }

    @NotNull
    public static ItemPresentation getPresentation(final DLanguageEnumDeclaration o) {
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
        PsiElement e = DElementFactory.createDLanguageUnionDeclarationFromText(o.getProject(), newName);
        if (e == null) return null;
        o.replace(e);
        return o;
    }

    @NotNull
    public static PsiReference getReference(@NotNull DLanguageUnionDeclaration o) {
        return new DReference(o, TextRange.from(0, getName(o).length()));
    }

    @NotNull
    public static ItemPresentation getPresentation(final DLanguageUnionDeclaration o) {
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

    public static DLanguageProtectionAttribute getProtection(@NotNull DLanguageUnionDeclaration o) {
        return getChildOfType(o, DLanguageProtectionAttribute.class);
    }


    // ------------- Union Definition -------------------- //


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

    @NotNull
    public static List<DLanguageParameter> getArguments(@NotNull DLanguageConstructor o) {
        if (o.getConstructorTemplate() == null) {
            return Arrays.asList(getChildrenOfType(o.getParameters(), DLanguageParameter.class));
        } else {
            return Arrays.asList(getChildrenOfType(o.getConstructorTemplate().getParameters(), DLanguageParameter.class));
        }
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

    public static boolean actuallyIsDeclaration(DLanguageAliasDeclaration o) {
        return true;
    }

    public static DLanguageType getDeclarationType(DLanguageAliasDeclaration o) {
        return null;
    }

    // ------------- Alias Definition ------------------ //

    // ------------ Module Declaration ----------------- //

    @NotNull
    public static String getName(@NotNull DLanguageModuleDeclaration o) {
        DLanguageModuleDeclarationStub stub = o.getStub();
        if (stub != null) return StringUtil.notNullize(stub.getName());

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

    @Nullable
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
    public static DLanguageProtectionAttribute getProtection(DLanguageModuleDeclaration o) {
        return getChildOfType(o, DLanguageProtectionAttribute.class);
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
    public static ItemPresentation getPresentation(final @NotNull DLanguageInterfaceDeclaration o) {
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

    public static List<CanInherit> whatInheritsFrom(@NotNull DLanguageInterfaceDeclaration o) {
        DLanguageBaseInterfaceList baseInterfaceList = o.getBaseInterfaceList();
        if (o.getInterfaceTemplateDeclaration() != null)
            baseInterfaceList = o.getInterfaceTemplateDeclaration().getBaseInterfaceList();
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
            final Set<PsiNamedElement> definitionNodes = findDefinitionNodes(getEndOfIdentifierList(identifierList));
            assert (definitionNodes.size() == 1);
            res.add((CanInherit) definitionNodes.toArray()[0]);

        }

        return res;
    }

    public static List<DLanguageTemplateParameter> getTemplateArguments(@NotNull DLanguageInterfaceDeclaration o) {
        if (o.getInterfaceTemplateDeclaration() == null)
            return Collections.emptyList();
        return new ArrayList<>(findChildrenOfType(o.getInterfaceTemplateDeclaration().getTemplateParameters(), DLanguageTemplateParameter.class));
    }

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
    public static ItemPresentation getPresentation(final @NotNull DLanguageLabeledStatement o) {
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

    // -------------- Mixin Template Resolving ------------------- //
    @Nullable
    public static DLanguageTemplateMixinDeclaration getTemplateMixinDeclaration(@NotNull DLanguageMixinDeclaration t) {
        if (t.getTemplateInstance() != null) {
            if (t.getTemplateInstance().getIdentifier() == null)
                return null;
            final Set<PsiNamedElement> definitionNodes = findDefinitionNodes(t.getTemplateInstance().getIdentifier());
            if (definitionNodes.size() != 1)//this could be a source of bugs if resolve isn't working properly
                return null;
            if (definitionNodes.toArray()[0] instanceof DLanguageTemplateMixinDeclaration) {
                return (DLanguageTemplateMixinDeclaration) definitionNodes.toArray()[0];
            }

        }
        return null;
    }

    @Nullable
    public static DLanguageTemplateDeclaration getTemplateDeclaration(@NotNull DLanguageMixinDeclaration t) {
        if (t.getTemplateInstance() != null) {
            if (t.getTemplateInstance().getIdentifier() == null)
                return null;
            final Set<PsiNamedElement> definitionNodes = findDefinitionNodes(t.getTemplateInstance().getIdentifier());
            if (definitionNodes.size() != 1)//this could be a source of bugs if resolve isn't working properly
                return null;
            if (definitionNodes.toArray()[0] instanceof DLanguageTemplateDeclaration) {
                return (DLanguageTemplateDeclaration) definitionNodes.toArray()[0];
            }

        }
        return null;
    }

    @Nullable
    public static DLanguageTemplateMixinDeclaration getTemplateMixinDeclaration(@NotNull DLanguageTemplateMixin t) {
        final Set<PsiNamedElement> definitionNodes = findDefinitionNodes(getEndOfIdentifierList(t.getMixinTemplateName().getQualifiedIdentifierList()));
        if (definitionNodes.size() != 1)//this could be a source of bugs if resolve isn't working properly
            return null;
        if (definitionNodes.toArray()[0] instanceof DLanguageTemplateMixinDeclaration) {
            return (DLanguageTemplateMixinDeclaration) definitionNodes.toArray()[0];
        }
        return null;
    }

    @Nullable
    public static DLanguageTemplateDeclaration getTemplateDeclaration(@NotNull DLanguageTemplateMixin t) {
        final Set<PsiNamedElement> definitionNodes = findDefinitionNodes(getEndOfIdentifierList(t.getMixinTemplateName().getQualifiedIdentifierList()));
        if (definitionNodes.size() != 1)//this could be a source of bugs if resolve isn't working properly
            return null;
        if (definitionNodes.toArray()[0] instanceof DLanguageTemplateDeclaration) {
            return (DLanguageTemplateDeclaration) definitionNodes.toArray()[0];
        }
        return null;
    }

    @Nullable
    public static DLanguageTemplateMixinDeclaration getTemplateMixinDeclaration(DLanguageMixinExpression t) {
        if (t.getTemplateInstance() != null) {
            if (t.getTemplateInstance().getIdentifier() == null)
                return null;
            final Set<PsiNamedElement> definitionNodes = findDefinitionNodes(t.getTemplateInstance().getIdentifier());
            if (definitionNodes.size() != 1)//this could be a source of bugs if resolve isn't working properly
                return null;
            if (definitionNodes.toArray()[0] instanceof DLanguageTemplateMixinDeclaration) {
                return (DLanguageTemplateMixinDeclaration) definitionNodes.toArray()[0];
            }

        }
        return null;
    }

    @Nullable
    public static DLanguageTemplateDeclaration getTemplateDeclaration(DLanguageMixinExpression t) {
        if (t.getTemplateInstance() != null) {
            if (t.getTemplateInstance().getIdentifier() == null)
                return null;
            final Set<PsiNamedElement> definitionNodes = findDefinitionNodes(t.getTemplateInstance().getIdentifier());
            if (definitionNodes.size() != 1)//this could be a source of bugs if resolve isn't working properly
                return null;
            if (definitionNodes.toArray()[0] instanceof DLanguageTemplateDeclaration) {
                return (DLanguageTemplateDeclaration) definitionNodes.toArray()[0];
            }

        }
        return null;
    }

    @Nullable
    public static DLanguageTemplateMixinDeclaration getTemplateMixinDeclaration(DLanguageMixinStatement t) {
        if (t.getTemplateInstance() != null) {
            if (t.getTemplateInstance().getIdentifier() == null)
                return null;
            final Set<PsiNamedElement> definitionNodes = findDefinitionNodes(t.getTemplateInstance().getIdentifier());
            if (definitionNodes.size() != 1)//this could be a source of bugs if resolve isn't working properly
                return null;
            if (definitionNodes.toArray()[0] instanceof DLanguageTemplateMixinDeclaration) {
                return (DLanguageTemplateMixinDeclaration) definitionNodes.toArray()[0];
            }

        }
        return null;
    }

    @Nullable
    public static DLanguageTemplateDeclaration getTemplateDeclaration(DLanguageMixinStatement t) {
        if (t.getTemplateInstance() != null) {
            if (t.getTemplateInstance().getIdentifier() == null)
                return null;
            final Set<PsiNamedElement> definitionNodes = findDefinitionNodes(t.getTemplateInstance().getIdentifier());
            if (definitionNodes.size() != 1)//this could be a source of bugs if resolve isn't working properly
                return null;
            if (definitionNodes.toArray()[0] instanceof DLanguageTemplateDeclaration) {
                return (DLanguageTemplateDeclaration) definitionNodes.toArray()[0];
            }

        }
        return null;
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

    @Nullable
    private static DLanguageIdentifier getIdentifier(DLanguageDeclaratorInitializer o) {
        if (o.getAltDeclarator() != null) {
            final DLanguageAltDeclarator altDeclarator = o.getAltDeclarator();
            if (altDeclarator.getIdentifier() != null) return altDeclarator.getIdentifier();
            else return altDeclarator.getAltDeclaratorX().getIdentifier();
        }
        if (o.getVarDeclarator() != null) {
            final DLanguageVarDeclarator varDeclarator = o.getVarDeclarator();
            if (varDeclarator.getBasicType2() != null)
                return varDeclarator.getIdentifier();
        }
        return null;
    }

    @Nullable
    public static PsiElement getNameIdentifier(@NotNull DLanguageDeclaratorInitializer o) {
        ASTNode keyNode = o.getNode();
        return keyNode != null ? keyNode.getPsi() : null;
    }

    @Nullable
    public static PsiElement setName(@NotNull DLanguageDeclaratorInitializer o, @NotNull String newName) {
        PsiElement e = DElementFactory.createDLanguageDeclaratorInitializerFromText(o.getProject(), newName);
        if (e == null) return null;
        o.replace(e);
        return o;
    }

    @NotNull
    public static PsiReference getReference(@NotNull DLanguageDeclaratorInitializer o) {
        return new DReference(o, TextRange.from(0, getName(o).length()));
    }

    @NotNull
    public static ItemPresentation getPresentation(final DLanguageDeclaratorInitializer o) {
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

    public static DLanguageType getVariableDeclarationType(DLanguageDeclaratorInitializer o) {
        return null;//todo implement

    }

    // ------------- Var Declaration ------------------ //

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

    @Nullable
    public static PsiElement setName(@NotNull DLanguageAutoDeclarationY o, @NotNull String newName) {
        PsiElement e = DElementFactory.createDLanguageAutoDeclarationYFromText(o.getProject(), newName);
        if (e == null) return null;
        o.replace(e);
        return o;
    }

    @NotNull
    public static PsiReference getReference(@NotNull DLanguageAutoDeclarationY o) {
        return new DReference(o, TextRange.from(0, getName(o).length()));
    }

    @NotNull
    public static ItemPresentation getPresentation(final DLanguageAutoDeclarationY o) {
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

    public static boolean actuallyIsDeclaration(DLanguageAutoDeclarationY o) {
        final DLanguageStorageClasses storageClasses = ((DLanguageAutoDeclaration) o.getParent().getParent()).getStorageClasses();
        if (storageClasses == null)
            return false;
        return storageClasses.getStorageClass().getKwAuto() != null;
    }

    public static DLanguageType getVariableDeclarationType(DLanguageAutoDeclarationY o) {
        return null;//todo implement

    }
    // ------------- Auto Declaration Y ------------------ //


    // ------------- Static Constructor ------------------//
    @NotNull
    public static String getName(@NotNull DLanguageStaticConstructor o) {
        return "this";
//        DLanguageStaticConstructorStub stub = o.getStub();
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
    public static PsiElement getNameIdentifier(@NotNull DLanguageStaticConstructor o) {
        ASTNode keyNode = o.getNode();
        return keyNode != null ? keyNode.getPsi() : null;
    }

    @Nullable
    public static PsiElement setName(@NotNull DLanguageStaticConstructor o, @NotNull String newName) {
        PsiElement e = DElementFactory.createDLanguageStaticConstructorFromText(o.getProject(), newName);
        if (e == null) return null;
        o.replace(e);
        return o;
    }

    @NotNull
    public static PsiReference getReference(@NotNull DLanguageStaticConstructor o) {
        return new DReference(o, TextRange.from(0, getName(o).length()));
    }

    @NotNull
    public static ItemPresentation getPresentation(final DLanguageStaticConstructor o) {
        return new ItemPresentation() {
            @Nullable
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

    // ------------- Static Constructor ------------------//


    // ---------- Shared Static Constructor ---------------//

    @NotNull
    public static String getName(@NotNull DLanguageSharedStaticConstructor o) {
        return "this";//not sure about wether or not this should be "this" or not. Copy paste the bellow back in at a later date, but the classeclaration part should also include structs/templates/modules. todo
//        DLanguageSharedStaticConstructorStub stub = o.getStub();
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
    public static PsiElement getNameIdentifier(@NotNull DLanguageSharedStaticConstructor o) {
        ASTNode keyNode = o.getNode();
        return keyNode != null ? keyNode.getPsi() : null;
    }

    @Nullable
    public static PsiElement setName(@NotNull DLanguageSharedStaticConstructor o, @NotNull String newName) {
        PsiElement e = DElementFactory.createDLanguageSharedStaticConstructorFromText(o.getProject(), newName);
        if (e == null) return null;
        o.replace(e);
        return o;
    }

    @NotNull
    public static PsiReference getReference(@NotNull DLanguageSharedStaticConstructor o) {
        return new DReference(o, TextRange.from(0, getName(o).length()));
    }

    @NotNull
    public static ItemPresentation getPresentation(final DLanguageSharedStaticConstructor o) {
        return new ItemPresentation() {
            @Nullable
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

    // ---------- Shared Static Constructor ---------------//

    // ------------- Static Destructor ------------------ //
    @NotNull
    public static String getName(@NotNull DLanguageStaticDestructor o) {
        return "~this";
//        DLanguageStaticDestructorStub stub = o.getStub();
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
    public static PsiElement getNameIdentifier(@NotNull DLanguageStaticDestructor o) {
        ASTNode keyNode = o.getNode();
        return keyNode != null ? keyNode.getPsi() : null;
    }

    @Nullable
    public static PsiElement setName(@NotNull DLanguageStaticDestructor o, @NotNull String newName) {
        PsiElement e = DElementFactory.createDLanguageStaticDestructorFromText(o.getProject(), newName);
        if (e == null) return null;
        o.replace(e);
        return o;
    }

    @NotNull
    public static PsiReference getReference(@NotNull DLanguageStaticDestructor o) {
        return new DReference(o, TextRange.from(0, getName(o).length()));
    }

    @NotNull
    public static ItemPresentation getPresentation(final DLanguageStaticDestructor o) {
        return new ItemPresentation() {
            @Nullable
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
    public static String getName(@NotNull DLanguageSharedStaticDestructor o) {
        return "~this";
//        DLanguageSharedStaticDestructorStub stub = o.getStub();
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


    // ------------- Shared Static Destructor ------------- //

    @Nullable
    public static PsiElement getNameIdentifier(@NotNull DLanguageSharedStaticDestructor o) {
        ASTNode keyNode = o.getNode();
        return keyNode != null ? keyNode.getPsi() : null;
    }

    @Nullable
    public static PsiElement setName(@NotNull DLanguageSharedStaticDestructor o, @NotNull String newName) {
        PsiElement e = DElementFactory.createDLanguageSharedStaticDestructorFromText(o.getProject(), newName);
        if (e == null) return null;
        o.replace(e);
        return o;
    }

    @NotNull
    public static PsiReference getReference(@NotNull DLanguageSharedStaticDestructor o) {
        return new DReference(o, TextRange.from(0, getName(o).length()));
    }

    @NotNull
    public static ItemPresentation getPresentation(final DLanguageSharedStaticDestructor o) {
        return new ItemPresentation() {
            @Nullable
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

    // ------------- Shared Static Destructor ------------- //

    // -------------------- Visibility --------------------- //

    public static boolean isSomeVisibility(DLanguageAliasDeclaration o, Visibility visibility) {
        final DLanguageAttributeSpecifier attribute = (DLanguageAttributeSpecifier) o.getParent().getParent().getParent();
        if (attribute.getAttribute().getProtectionAttribute() != null) {
            if (attribute.getAttribute().getProtectionAttribute().getText().equals(visibility))
                return true;
        }
        return isSomeVisibility(o, visibility, AliasContainer.class);

    }

    public static boolean isSomeVisibility(DLanguageEnumDeclaration o, Visibility visibility) {
        return isSomeVisibility(o, visibility, EnumContainer.class);

    }

    public static boolean isSomeVisibility(DLanguageModuleDeclaration o, Visibility visibility) {
        if (o.getAttribute() != null)
            if (o.getAttribute().getProtectionAttribute() != null)
                if (DUtil.protectionToVisibilty(o.getAttribute().getProtectionAttribute()) == (visibility))
                    return true;
        return visibility == DUtil.protectionToVisibilty("public");
    }

    private static boolean isSomeVisibility(PsiElement psiElement, Visibility visibility, Class<? extends Container> containerType) {
        PsiElement parent = psiElement.getParent();
        while (true) {
            if (containerType.isInstance(parent)) {
                return visibility == public_;
            }
            if (parent instanceof DLanguageDeclDef && ((DLanguageDeclDef) parent).getAttributeSpecifier() != null) {
                final DLanguageAttributeSpecifier attribute = ((DLanguageDeclDef) parent).getAttributeSpecifier();
                if (attribute.getAttribute().getProtectionAttribute() != null) {
                    if (DUtil.protectionToVisibilty(attribute.getAttribute().getProtectionAttribute()) == (visibility))
                        return true;
                }
            }
            parent = parent.getParent();
        }
    }

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

    public static boolean isSomeVisibility(DLanguageStaticConstructor o, Visibility visibility) {
        return isSomeVisibility(o, visibility, ConstructorContainer.class);//todo convert to static constructor container
    }

    public static boolean isSomeVisibility(DLanguageSharedStaticConstructor o, Visibility visibility) {
        return isSomeVisibility(o, visibility, ConstructorContainer.class);//todo convert to static constructor container
    }

    public static boolean isSomeVisibility(DLanguageDestructor o, Visibility visibility) {
        return isSomeVisibility(o, visibility, DestructorContainer.class);
    }

    public static boolean isSomeVisibility(DLanguageStaticDestructor o, Visibility visibility) {
        return isSomeVisibility(o, visibility, DestructorContainer.class);//todo convert to static destructor container
    }

    public static boolean isSomeVisibility(DLanguageSharedStaticDestructor o, Visibility visibility) {
        return isSomeVisibility(o, visibility, DestructorContainer.class);//todo convert to static destructor container
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

    // -------------------- Visibility --------------------- //
}

