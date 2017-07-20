package net.masterthought.dlanguage.psi.impl.named;

import com.intellij.lang.ASTNode;
import com.intellij.navigation.ItemPresentation;
import com.intellij.openapi.util.Condition;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.*;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.icons.DLanguageIcons;
import net.masterthought.dlanguage.psi.*;
import net.masterthought.dlanguage.psi.impl.DElementFactory;
import net.masterthought.dlanguage.psi.impl.DNamedStubbedPsiElementBase;
import net.masterthought.dlanguage.psi.impl.DPsiImplUtil;
import net.masterthought.dlanguage.psi.references.DReference;
import net.masterthought.dlanguage.resolve.DResolveUtil;
import net.masterthought.dlanguage.stubs.DLanguageIdentifierStub;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.List;

import static net.masterthought.dlanguage.psi.DLanguageTypes.ID;

public class DLanguageIdentifierImpl extends DNamedStubbedPsiElementBase<DLanguageIdentifierStub> implements DLanguageIdentifier {

    public DLanguageIdentifierImpl(DLanguageIdentifierStub stub, IStubElementType type) {
        super(stub, type);
    }

    public DLanguageIdentifierImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DLanguageVisitor visitor) {
        visitor.visitIdentifier(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor) visitor);
        else super.accept(visitor);
    }

    @NotNull
    public String getName() {
        DLanguageIdentifierStub stub = this.getStub();
        if (stub != null) return StringUtil.notNullize(stub.getName());
        return getText();
    }

    @Nullable
    public PsiElement getNameIdentifier() {
        ASTNode keyNode = getNode();
        return keyNode != null ? keyNode.getPsi() : null;
    }

    @NotNull
    public PsiReference getReference() {
        return new DReference(this, TextRange.from(0, DPsiImplUtil.getName(this).length()));
    }

    @NotNull
    public PsiElement setName(@NotNull String newName) {
        PsiElement e = DElementFactory.createDLanguageIdentifierFromText(getProject(), newName);
        if (e == null) return null;
        replace(e);
        return this;
    }

    @NotNull
    public ItemPresentation getPresentation() {
        return new ItemPresentation() {
            @NotNull
            @Override
            public String getPresentableText() {
                //todo keep this up to date
                PsiNamedElement firstMatch = (PsiNamedElement) PsiTreeUtil.findFirstParent(DLanguageIdentifierImpl.this, new Condition<PsiElement>() {
                    @Override
                    public boolean value(PsiElement element) {
                        return element instanceof DLanguageFunctionDeclaration || element instanceof DLanguageInterfaceOrClass || element instanceof DLanguageTemplateDeclaration || element instanceof DLanguageUnionDeclaration || element instanceof DLanguageStructDeclaration || element instanceof DLanguageParameter || element instanceof DLanguageTemplateParameter || element instanceof DLanguageEnumDeclaration || element instanceof DLanguageEnumMember || element instanceof DLanguageCatch || element instanceof DLanguageForeachType || element instanceof DLanguageIfCondition;
                    }
                });
                PsiNamedElement funcDecl = (PsiNamedElement) DPsiImplUtil.findParentOfType(DLanguageIdentifierImpl.this, DLanguageFunctionDeclaration.class);
                PsiNamedElement classDecl;
                if (DPsiImplUtil.findParentOfType(DLanguageIdentifierImpl.this, DLanguageClassDeclaration.class) == null) {
                    classDecl = null;
                } else
                    classDecl = ((DLanguageClassDeclaration) DPsiImplUtil.findParentOfType(DLanguageIdentifierImpl.this, DLanguageClassDeclaration.class)).getInterfaceOrClass();
                PsiNamedElement templateDecl = (PsiNamedElement) DPsiImplUtil.findParentOfType(DLanguageIdentifierImpl.this, DLanguageTemplateDeclaration.class);
                PsiNamedElement unionDecl = (PsiNamedElement) DPsiImplUtil.findParentOfType(DLanguageIdentifierImpl.this, DLanguageUnionDeclaration.class);
                PsiNamedElement structDecl = (PsiNamedElement) DPsiImplUtil.findParentOfType(DLanguageIdentifierImpl.this, DLanguageStructDeclaration.class);
                PsiNamedElement interfaceDecl;
                if (DPsiImplUtil.findParentOfType(DLanguageIdentifierImpl.this, DLanguageInterfaceDeclaration.class) == null) {
                    interfaceDecl = null;
                } else
                    interfaceDecl = ((DLanguageInterfaceDeclaration) DPsiImplUtil.findParentOfType(DLanguageIdentifierImpl.this, DLanguageInterfaceDeclaration.class)).getInterfaceOrClass();
                PsiNamedElement parameterDecl = (PsiNamedElement) DPsiImplUtil.findParentOfType(DLanguageIdentifierImpl.this, DLanguageParameter.class);
                PsiNamedElement templateParameterDecl = (PsiNamedElement) DPsiImplUtil.findParentOfType(DLanguageIdentifierImpl.this, DLanguageTemplateParameter.class);
                PsiNamedElement enumDeclarationDecl = (PsiNamedElement) DPsiImplUtil.findParentOfType(DLanguageIdentifierImpl.this, DLanguageEnumDeclaration.class);
                PsiNamedElement enumMemberDecl = (PsiNamedElement) DPsiImplUtil.findParentOfType(DLanguageIdentifierImpl.this, DLanguageEnumMember.class);
                PsiNamedElement catchDecl = (PsiNamedElement) DPsiImplUtil.findParentOfType(DLanguageIdentifierImpl.this, DLanguageCatch.class);

                PsiNamedElement autoDeclarationDecl = (PsiNamedElement) DPsiImplUtil.findParentOfType(DLanguageIdentifierImpl.this, DLanguageAutoDeclarationPart.class);
                PsiNamedElement ifConditionDecl = (PsiNamedElement) DPsiImplUtil.findParentOfType(DLanguageIdentifierImpl.this, DLanguageIfCondition.class);
                PsiNamedElement foreachTypeDecl = (PsiNamedElement) DPsiImplUtil.findParentOfType(DLanguageIdentifierImpl.this, DLanguageForeachType.class);
                PsiNamedElement declaratorDecl = (PsiNamedElement) DPsiImplUtil.findParentOfType(DLanguageIdentifierImpl.this, DLanguageDeclarator.class);
                PsiNamedElement aliasInitializerDecl = (PsiNamedElement) DPsiImplUtil.findParentOfType(DLanguageIdentifierImpl.this, DLanguageAliasInitializer.class);
                PsiNamedElement labeledStatementDecl = (PsiNamedElement) DPsiImplUtil.findParentOfType(DLanguageIdentifierImpl.this, DLanguageLabeledStatement.class);
                PsiNamedElement constructorDecl = (PsiNamedElement) DPsiImplUtil.findParentOfType(DLanguageIdentifierImpl.this, DLanguageConstructor.class);
                PsiNamedElement eponymousTemplateDeclarationDecl = (PsiNamedElement) DPsiImplUtil.findParentOfType(DLanguageIdentifierImpl.this, DLanguageEponymousTemplateDeclaration.class);


                String description = "";
                if (funcDecl != null && funcDecl == firstMatch) {
                    description = " [Function] (" + funcDecl.getName() + ")";
                }
                if (classDecl != null && classDecl == firstMatch) {
                    description = " [Class] (" + classDecl.getName() + ")";
                }
                if (templateDecl != null && templateDecl == firstMatch) {
                    description = " [Template] (" + templateDecl.getName() + ")";
                }
                if (unionDecl != null && unionDecl == firstMatch) {
                    description = " [Union] (" + unionDecl.getName() + ")";
                }
                if (structDecl != null && structDecl == firstMatch) {
                    description = " [Struct] (" + structDecl.getName() + ")";
                }
                if (interfaceDecl != null && interfaceDecl == firstMatch) {
                    description = " [Interface] (" + interfaceDecl.getName() + ")";
                }
                if (parameterDecl != null && parameterDecl == firstMatch) {
                    description = " [Parameter] (" + parameterDecl.getName() + ")";
                }
                if (templateParameterDecl != null && templateParameterDecl == firstMatch) {
                    description = " [Template Parameter] (" + templateParameterDecl.getName() + ")";
                }
                if (enumDeclarationDecl != null && enumDeclarationDecl == firstMatch) {
                    description = " [Enum Declaration] (" + enumDeclarationDecl.getName() + ")";
                }
                if (enumMemberDecl != null && enumMemberDecl == firstMatch) {
                    description = " [Enum Member] (" + enumMemberDecl.getName() + ")";
                }
                if (catchDecl != null && catchDecl == firstMatch) {
                    description = " [Catch] (" + catchDecl.getName() + ")";
                }
                if (autoDeclarationDecl != null && autoDeclarationDecl == firstMatch) {
                    description = " [Auto Variable] (" + autoDeclarationDecl.getName() + ")";
                }
                if (ifConditionDecl != null && ifConditionDecl == firstMatch) {
                    description = " [If Condition Variable] (" + ifConditionDecl.getName() + ")";
                }
                if (foreachTypeDecl != null && foreachTypeDecl == firstMatch) {
                    description = " [Foreach Variable] (" + foreachTypeDecl.getName() + ")";
                }
                if (declaratorDecl != null && declaratorDecl == firstMatch) {
                    description = " [Variable] (" + declaratorDecl.getName() + ")";
                }
                if (aliasInitializerDecl != null && aliasInitializerDecl == firstMatch) {
                    description = " [Alias Declaration] (" + aliasInitializerDecl.getName() + ")";
                }
                if (labeledStatementDecl != null && labeledStatementDecl == firstMatch) {
                    description = " [Label] (" + labeledStatementDecl.getName() + ")";
                }
                if (constructorDecl != null && constructorDecl == firstMatch) {
                    description = " [Constructor] (" + constructorDecl.getName() + ")";
                }
                if (eponymousTemplateDeclarationDecl != null && eponymousTemplateDeclarationDecl == firstMatch) {
                    description = " [Eponymous Template Declaration] (" + eponymousTemplateDeclarationDecl.getName() + ")";
                }


                return getName() + description;
            }

            /**
             * This is needed to decipher between files when resolving multiple references.
             */
            @Nullable
            @Override
            public String getLocationString() {
                final PsiFile psiFile = getContainingFile();
                return psiFile instanceof DLanguageFile ? ((DLanguageFile) psiFile).getModuleOrFileName() : null;
            }

            @Nullable
            @Override
            public Icon getIcon(boolean unused) {
                return DLanguageIcons.FILE;
            }
        };
    }

    public void delete() {
        final List<PsiNamedElement> definitionNode = DResolveUtil.INSTANCE.findDefinitionNode(getProject(), this);
        if (definitionNode.size() != 1)
            throw new IllegalStateException();
        definitionNode.get(0).delete();
    }

    @Nullable
    @Override
    public PsiElement getID() {
        return findChildByType(ID);
    }
}
