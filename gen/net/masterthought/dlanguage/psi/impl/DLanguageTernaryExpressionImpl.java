

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


public class DLanguageTernaryExpressionImpl extends ASTWrapperPsiElement implements DLanguageTernaryExpression{
       public DLanguageTernaryExpressionImpl (ASTNode node){
               super(node);
       }
       public void accept(@NotNull DLanguageVisitor visitor){
           visitor.visitTernaryExpression(this);
       }
       public void accept(@NotNull PsiElementVisitor visitor){
           if(visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
           else super.accept(visitor);
       }

            @Nullable
            public PsiElement getOP_QUEST() {
                return findChildByType(OP_QUEST);
            }
        
            @Nullable
            public PsiElement getOP_COLON() {
                return findChildByType(OP_COLON);
            }
        
            @Nullable
            public DLanguageOrOrExpression getOrOrExpression() {
                return PsiTreeUtil.getChildOfType(this, DLanguageOrOrExpression.class);
            }
            @Nullable
            public DLanguageExpression getExpression() {
                return PsiTreeUtil.getChildOfType(this, DLanguageExpression.class);
            }
            @Nullable
            public DLanguageTernaryExpression getTernaryExpression() {
                return PsiTreeUtil.getChildOfType(this, DLanguageTernaryExpression.class);
            }
}