

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


public class DLanguageIdentityExpressionImpl extends ASTWrapperPsiElement implements DLanguageIdentityExpression{
       public DLanguageIdentityExpressionImpl (ASTNode node){
               super(node);
       }
       public void accept(@NotNull DLanguageVisitor visitor){
           visitor.visitIdentityExpression(this);
       }
       public void accept(@NotNull PsiElementVisitor visitor){
           if(visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
           else super.accept(visitor);
       }

                @NotNull
                public List<DLanguageShiftExpression> getShiftExpressions() {
                    return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageShiftExpression.class);
                }
            @Nullable
            public PsiElement getKW_IS() {
                return findChildByType(KW_IS);
            }
        
            @Nullable
            public PsiElement getOP_NOT() {
                return findChildByType(OP_NOT);
            }
        
}