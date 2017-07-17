package net.masterthought.dlanguage.psi.impl.named;

import com.intellij.lang.ASTNode;
import com.intellij.navigation.ItemPresentation;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiReference;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.icons.DLanguageIcons;
import net.masterthought.dlanguage.psi.*;
import net.masterthought.dlanguage.psi.impl.DLanguageParametersImpl;
import net.masterthought.dlanguage.psi.impl.DNamedStubbedPsiElementBase;
import net.masterthought.dlanguage.psi.interfaces.HasVisibility;
import net.masterthought.dlanguage.psi.references.DReference;
import net.masterthought.dlanguage.stubs.DLanguageConstructorStub;
import net.masterthought.dlanguage.utils.DUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.List;

import static net.masterthought.dlanguage.psi.DLanguageTypes.*;

public class DLanguageConstructorImpl extends DNamedStubbedPsiElementBase<DLanguageConstructorStub> implements DLanguageConstructor {

    public DLanguageConstructorImpl(DLanguageConstructorStub stub, IStubElementType type) {
        super(stub, type);
    }

    public DLanguageConstructorImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DLanguageVisitor visitor) {
        visitor.visitConstructor(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor) visitor);
        else super.accept(visitor);
    }

    @Override
    @Nullable
    public DLanguageFunctionBody getFunctionBody() {
        return PsiTreeUtil.getChildOfType(this, DLanguageFunctionBody.class);
    }

    @Nullable
    @Override
    public PsiElement getOP_SCOLON() {
        return findChildByType(OP_SCOLON);
    }

    @Nullable
    @Override
    public PsiElement getOP_PAR_RIGHT() {
        return findChildByType(OP_PAR_RIGHT);
    }

    @Nullable
    @Override
    public PsiElement getOP_PAR_LEFT() {
        return findChildByType(OP_PAR_LEFT);
    }

    @Nullable
    @Override
    public PsiElement getKW_THIS() {
        return findChildByType(KW_THIS);
    }

    @Override
    @NotNull
    public List<DLanguageMemberFunctionAttribute> getMemberFunctionAttributes() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageMemberFunctionAttribute.class);
    }

    @Nullable
    @Override
    public DLanguageParameters getParameters() {
        return PsiTreeUtil.getChildOfType(this,DLanguageParameters.class);
    }

    @Nullable
    @Override
    public DLanguageTemplateParameters getTemplateParameters() {
        return PsiTreeUtil.getChildOfType(this,DLanguageTemplateParameters.class);
    }


    @NotNull
    public String getName() {
        if (DUtil.getParentClassOrStructOrTemplateOrInterface(this) == null) {
            throw new IllegalStateException();
        }
        return DUtil.getParentClassOrStructOrTemplateOrInterface(this).getName();
    }

    @Nullable
    public PsiElement getNameIdentifier() {
        ASTNode keyNode = getNode();
        return keyNode != null ? keyNode.getPsi() : null;
    }

    @NotNull
    public PsiReference getReference() {
        return new DReference(this, TextRange.from(0, (this).getName().length()));
    }

    @NotNull
    public PsiElement setName(String newName) {
        DUtil.getParentClassOrStructOrTemplateOrInterface(this).setName(newName);
        return this;
    }

    @NotNull
    public ItemPresentation getPresentation() {
        return new ItemPresentation() {
            @NotNull
            @Override
            public String getPresentableText() {
                String string = "";
                for (PsiElement psiElement : getChildren()) {
                    if (psiElement instanceof DLanguageParametersImpl)
                        string += psiElement.getText();
                }
                return getName() + string;
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

    public boolean isSomeVisibility(HasVisibility.Visibility visibility) {
        //todo fix
        return false;
    }

//    public boolean processDeclarations(PsiScopeProcessor processor, ResolveState state, PsiElement lastParent, PsiElement place) {
//        return DPsiImplUtil.processDeclarations(this, processor, state, lastParent, place);
//    }

}
