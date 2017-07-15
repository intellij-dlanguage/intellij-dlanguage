

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


public class DLanguageStaticIfConditionImpl extends ASTWrapperPsiElement implements DLanguageStaticIfCondition{
       public DLanguageStaticIfConditionImpl (ASTNode node){
               super(node);
       }
       public void accept(@NotNull DLanguageVisitor visitor){
           visitor.visitStaticIfCondition(this);
       }
       public void accept(@NotNull PsiElementVisitor visitor){
           if(visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
           else super.accept(visitor);
       }

            @Nullable
            public DLanguageStaticIfCondition getStaticIfCondition() {
                return PsiTreeUtil.getChildOfType(this, DLanguageStaticIfCondition.class);
            }
            @Nullable
            public DLanguageAssignExpression getAssignExpression() {
                return PsiTreeUtil.getChildOfType(this, DLanguageAssignExpression.class);
            }
            @Nullable
            public PsiElement getOP_PAR_RIGHT() {
                return findChildByType(OP_PAR_RIGHT);
            }
        
            @Nullable
            public PsiElement getOP_PAR_LEFT() {
                return findChildByType(OP_PAR_LEFT);
            }
        
}