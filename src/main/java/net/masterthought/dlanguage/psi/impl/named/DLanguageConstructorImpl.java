package net.masterthought.dlanguage.psi.impl.named;

import com.intellij.lang.ASTNode;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiFile;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.icons.DlangIcons;
import net.masterthought.dlanguage.psi.*;
import net.masterthought.dlanguage.psi.impl.DLanguageParametersImpl;
import net.masterthought.dlanguage.psi.impl.DNamedStubbedPsiElementBase;
import net.masterthought.dlanguage.resolve.ScopeProcessorImpl;
import net.masterthought.dlanguage.stubs.DlangConstructorStub;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.List;

import static net.masterthought.dlanguage.psi.DlangTypes.*;
import static net.masterthought.dlanguage.utils.DUtil.getParentClassOrStructOrTemplateOrInterfaceOrUnion;

public class DLanguageConstructorImpl extends DNamedStubbedPsiElementBase<DlangConstructorStub> implements DLanguageConstructor {

    public DLanguageConstructorImpl(final DlangConstructorStub stub, final IStubElementType type) {
        super(stub, type);
    }

    public DLanguageConstructorImpl(final ASTNode node) {
        super(node);
    }

    public void accept(@NotNull final DlangVisitor visitor) {
        visitor.visitConstructor(this);
    }

    public void accept(@NotNull final PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) accept((DlangVisitor) visitor);
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

    @Nullable
    public DlangIdentifier getNameIdentifier() {
        if (getParentClassOrStructOrTemplateOrInterfaceOrUnion(this) == null)
            return null;
        return getParentClassOrStructOrTemplateOrInterfaceOrUnion(this).getNameIdentifier();
    }

    @NotNull
    public ItemPresentation getPresentation() {
        return new ItemPresentation() {
            @NotNull
            @Override
            public String getPresentableText() {
                String string = "";
                for (final PsiElement psiElement : getChildren()) {
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
                return psiFile instanceof DlangFile ? ((DlangFile) psiFile).getModuleOrFileName() : null;
            }

            @Nullable
            @Override
            public Icon getIcon(final boolean unused) {
                return DlangIcons.FILE;
            }
        };
    }

    public boolean processDeclarations(@NotNull final PsiScopeProcessor processor, @NotNull final ResolveState state, final PsiElement lastParent, @NotNull final PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(this, processor, state, lastParent, place);
    }

}
