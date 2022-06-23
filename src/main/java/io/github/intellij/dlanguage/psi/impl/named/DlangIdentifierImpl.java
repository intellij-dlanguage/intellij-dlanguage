package io.github.intellij.dlanguage.psi.impl.named;

import com.intellij.lang.ASTNode;
import com.intellij.navigation.ItemPresentation;
import com.intellij.openapi.util.Condition;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.*;
import io.github.intellij.dlanguage.psi.named.DlangAliasInitializer;
import io.github.intellij.dlanguage.psi.named.DlangAutoDeclarationPart;
import io.github.intellij.dlanguage.psi.named.DlangCatch;
import io.github.intellij.dlanguage.psi.named.DlangConstructor;
import io.github.intellij.dlanguage.psi.named.DlangDeclarator;
import io.github.intellij.dlanguage.psi.named.DlangEnumDeclaration;
import io.github.intellij.dlanguage.psi.named.DlangEnumMember;
import io.github.intellij.dlanguage.psi.named.DlangForeachType;
import io.github.intellij.dlanguage.psi.named.DlangFunctionDeclaration;
import io.github.intellij.dlanguage.psi.named.DlangIdentifier;
import io.github.intellij.dlanguage.psi.named.DlangInterfaceOrClass;
import io.github.intellij.dlanguage.psi.named.DlangParameter;
import io.github.intellij.dlanguage.psi.named.DlangStructDeclaration;
import io.github.intellij.dlanguage.psi.named.DlangTemplateDeclaration;
import io.github.intellij.dlanguage.psi.named.DlangUnionDeclaration;
import io.github.intellij.dlanguage.psi.impl.DElementFactory;
import io.github.intellij.dlanguage.psi.impl.DNamedStubbedPsiElementBase;
import io.github.intellij.dlanguage.psi.references.DReference;
import io.github.intellij.dlanguage.resolve.DResolveUtil;
import io.github.intellij.dlanguage.stubs.DlangIdentifierStub;
import io.github.intellij.dlanguage.utils.DUtil;
import java.util.Set;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DlangIdentifierImpl extends DNamedStubbedPsiElementBase<DlangIdentifierStub> implements DlangIdentifier {

    public DlangIdentifierImpl(final DlangIdentifierStub stub, final IStubElementType type) {
        super(stub, type);
    }

    public DlangIdentifierImpl(final ASTNode node) {
        super(node);
    }

    public void accept(@NotNull final DlangVisitor visitor) {
        visitor.visitDNamedElement(this);visitor.visitIdentifier(this);
    }

    public void accept(@NotNull final PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) accept((DlangVisitor) visitor);
        else super.accept(visitor);
    }

    @NotNull
    public String getName() {
        final DlangIdentifierStub stub = this.getGreenStub();
        if (stub != null) return StringUtil.notNullize(stub.getName());
        return getText();
    }

    @Nullable
    public DlangIdentifier getNameIdentifier() {
        return this;
    }

    @NotNull
    public PsiReference getReference() {
        return new DReference(this, TextRange.from(0, getName().length()));
    }

    @NotNull
    public PsiElement setName(@NotNull final String newName) {
        final PsiElement e = DElementFactory.createDLanguageIdentifierFromText(getProject(), newName);
        if (e == null) {
            throw new IllegalStateException(
                "Something went horribly wrong while renaming. Target nane:" + newName + " Project"
                    + getProject().toString() + " e:" + e);
        }
        return replace(e);
    }

    @NotNull
    public ItemPresentation getPresentation() {
        return new DlangItemPresentation(getContainingFile()) {
            @NotNull
            @Override
            public String getPresentableText() {
                //todo keep this up to date
                final PsiNamedElement firstMatch = (PsiNamedElement) PsiTreeUtil.findFirstParent(DlangIdentifierImpl.this, new Condition<PsiElement>() {
                    @Override
                    public boolean value(final PsiElement element) {
                        return element instanceof DlangFunctionDeclaration
                            || element instanceof DlangInterfaceOrClass
                            || element instanceof DlangTemplateDeclaration
                            || element instanceof DlangUnionDeclaration
                            || element instanceof DlangStructDeclaration
                            || element instanceof DlangParameter
                            || element instanceof DLanguageTemplateParameter
                            || element instanceof DlangEnumDeclaration
                            || element instanceof DlangEnumMember || element instanceof DlangCatch
                            || element instanceof DlangForeachType
                            || element instanceof DLanguageIfCondition;
                    }
                });
                final PsiNamedElement funcDecl = (PsiNamedElement) DUtil
                    .findParentOfType(DlangIdentifierImpl.this, DlangFunctionDeclaration.class);
                final PsiNamedElement classDecl;
                if (DUtil.findParentOfType(DlangIdentifierImpl.this, DLanguageClassDeclaration.class) == null) {
                    classDecl = null;
                } else
                    classDecl = ((DLanguageClassDeclaration) DUtil.findParentOfType(DlangIdentifierImpl.this, DLanguageClassDeclaration.class)).getInterfaceOrClass();
                final PsiNamedElement templateDecl = (PsiNamedElement) DUtil.findParentOfType(DlangIdentifierImpl.this, DlangTemplateDeclaration.class);
                final PsiNamedElement unionDecl = (PsiNamedElement) DUtil.findParentOfType(DlangIdentifierImpl.this, DlangUnionDeclaration.class);
                final PsiNamedElement structDecl = (PsiNamedElement) DUtil.findParentOfType(DlangIdentifierImpl.this, DlangStructDeclaration.class);
                final PsiNamedElement interfaceDecl;
                if (DUtil.findParentOfType(DlangIdentifierImpl.this, DLanguageInterfaceDeclaration.class) == null) {
                    interfaceDecl = null;
                } else
                    interfaceDecl = ((DLanguageInterfaceDeclaration) DUtil.findParentOfType(DlangIdentifierImpl.this, DLanguageInterfaceDeclaration.class)).getInterfaceOrClass();
                final PsiNamedElement parameterDecl = (PsiNamedElement) DUtil
                    .findParentOfType(DlangIdentifierImpl.this, DlangParameter.class);
                final PsiNamedElement templateParameterDecl = (PsiNamedElement) DUtil.findParentOfType(DlangIdentifierImpl.this, DLanguageTemplateParameter.class);
                final PsiNamedElement enumDeclarationDecl = (PsiNamedElement) DUtil.findParentOfType(DlangIdentifierImpl.this, DlangEnumDeclaration.class);
                final PsiNamedElement enumMemberDecl = (PsiNamedElement) DUtil
                    .findParentOfType(DlangIdentifierImpl.this, DlangEnumMember.class);
                final PsiNamedElement catchDecl = (PsiNamedElement) DUtil
                    .findParentOfType(DlangIdentifierImpl.this, DlangCatch.class);

                final PsiNamedElement autoDeclarationDecl = (PsiNamedElement) DUtil
                    .findParentOfType(DlangIdentifierImpl.this, DlangAutoDeclarationPart.class);
                final PsiNamedElement ifConditionDecl = (PsiNamedElement) DUtil.findParentOfType(DlangIdentifierImpl.this, DLanguageIfCondition.class);
                final PsiNamedElement foreachTypeDecl = (PsiNamedElement) DUtil
                    .findParentOfType(DlangIdentifierImpl.this, DlangForeachType.class);
                final PsiNamedElement declaratorDecl = (PsiNamedElement) DUtil
                    .findParentOfType(DlangIdentifierImpl.this, DlangDeclarator.class);
                final PsiNamedElement aliasInitializerDecl = (PsiNamedElement) DUtil
                    .findParentOfType(DlangIdentifierImpl.this, DlangAliasInitializer.class);
                final PsiNamedElement labeledStatementDecl = (PsiNamedElement) DUtil.findParentOfType(DlangIdentifierImpl.this, DLanguageLabeledStatement.class);
                final PsiNamedElement constructorDecl = (PsiNamedElement) DUtil
                    .findParentOfType(DlangIdentifierImpl.this, DlangConstructor.class);
                final PsiNamedElement eponymousTemplateDeclarationDecl = (PsiNamedElement) DUtil.findParentOfType(DlangIdentifierImpl.this, DLanguageEponymousTemplateDeclaration.class);


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
        };
    }

    public void delete() {
        final Set<PsiNamedElement> definitionNode = DResolveUtil.Companion.getInstance(getProject()).findDefinitionNode(this, false);
        if (definitionNode.size() != 1)
            throw new IllegalStateException();
        ((PsiElement) definitionNode.toArray()[0]).delete();
    }

    @Nullable
    @Override
    public PsiElement getID() {
        return findChildByType(DlangTypes.ID);
    }
}
