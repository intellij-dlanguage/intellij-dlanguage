

package net.masterthought.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.masterthought.dlanguage.psi.*;
import java.util.List;
import static net.masterthought.dlanguage.psi.DLanguageTypes.*;


public class DLanguageMixinDeclarationImpl extends ASTWrapperPsiElement implements DLanguageMixinDeclaration{
       public DLanguageMixinDeclarationImpl (ASTNode node){
               super(node);
       }
       public void accept(@NotNull DLanguageVisitor visitor){
           visitor.visitMixinDeclaration(this);
       }
       public void accept(@NotNull PsiElementVisitor visitor){
           if(visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
           else super.accept(visitor);
       }

            @Nullable
            public DLanguageTemplateMixinExpression getTemplateMixinExpression() {
                return PsiTreeUtil.getChildOfType(this, DLanguageTemplateMixinExpression.class);
            }
            @Nullable
            public DLanguageMixinExpression getMixinExpression() {
                return PsiTreeUtil.getChildOfType(this, DLanguageMixinExpression.class);
            }
            @Nullable
            public PsiElement getOP_SCOLON() {
                return findChildByType(OP_SCOLON);
            }
        
}