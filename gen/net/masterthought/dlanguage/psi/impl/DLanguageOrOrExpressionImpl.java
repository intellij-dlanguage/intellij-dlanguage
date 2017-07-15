

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


public class DLanguageOrOrExpressionImpl extends ASTWrapperPsiElement implements DLanguageOrOrExpression{
       public DLanguageOrOrExpressionImpl (ASTNode node){
               super(node);
       }
       public void accept(@NotNull DLanguageVisitor visitor){
           visitor.visitOrOrExpression(this);
       }
       public void accept(@NotNull PsiElementVisitor visitor){
           if(visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
           else super.accept(visitor);
       }

            @Nullable
            public DLanguageAndAndExpression getAndAndExpression() {
                return PsiTreeUtil.getChildOfType(this, DLanguageAndAndExpression.class);
            }
            @Nullable
            public DLanguageOrOrExpression getOrOrExpression() {
                return PsiTreeUtil.getChildOfType(this, DLanguageOrOrExpression.class);
            }
            @Nullable
            public PsiElement getOP_BOOL_OR() {
                return findChildByType(OP_BOOL_OR);
            }
        
}