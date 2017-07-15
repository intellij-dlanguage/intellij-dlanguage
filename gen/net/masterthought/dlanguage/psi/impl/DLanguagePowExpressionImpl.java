

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


public class DLanguagePowExpressionImpl extends ASTWrapperPsiElement implements DLanguagePowExpression{
       public DLanguagePowExpressionImpl (ASTNode node){
               super(node);
       }
       public void accept(@NotNull DLanguageVisitor visitor){
           visitor.visitPowExpression(this);
       }
       public void accept(@NotNull PsiElementVisitor visitor){
           if(visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
           else super.accept(visitor);
       }

            @Nullable
            public DLanguagePowExpression getPowExpression() {
                return PsiTreeUtil.getChildOfType(this, DLanguagePowExpression.class);
            }
            @Nullable
            public DLanguageUnaryExpression getUnaryExpression() {
                return PsiTreeUtil.getChildOfType(this, DLanguageUnaryExpression.class);
            }
            @Nullable
            public PsiElement getOP_POW() {
                return findChildByType(OP_POW);
            }
        
}