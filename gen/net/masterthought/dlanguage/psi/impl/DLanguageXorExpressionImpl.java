

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


public class DLanguageXorExpressionImpl extends ASTWrapperPsiElement implements DLanguageXorExpression{
       public DLanguageXorExpressionImpl (ASTNode node){
               super(node);
       }
       public void accept(@NotNull DLanguageVisitor visitor){
           visitor.visitXorExpression(this);
       }
       public void accept(@NotNull PsiElementVisitor visitor){
           if(visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
           else super.accept(visitor);
       }

            @Nullable
            public DLanguageXorExpression getXorExpression() {
                return PsiTreeUtil.getChildOfType(this, DLanguageXorExpression.class);
            }
            @Nullable
            public DLanguageAndExpression getAndExpression() {
                return PsiTreeUtil.getChildOfType(this, DLanguageAndExpression.class);
            }
            @Nullable
            public PsiElement getOP_XOR() {
                return findChildByType(OP_XOR);
            }
        
}