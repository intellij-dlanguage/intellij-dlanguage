

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


public class DLanguageLambdaExpressionImpl extends ASTWrapperPsiElement implements DLanguageLambdaExpression{
       public DLanguageLambdaExpressionImpl (ASTNode node){
               super(node);
       }
       public void accept(@NotNull DLanguageVisitor visitor){
           visitor.visitLambdaExpression(this);
       }
       public void accept(@NotNull PsiElementVisitor visitor){
           if(visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
           else super.accept(visitor);
       }

            @Nullable
            public DLanguageIdentifier getIdentifier() {
                return PsiTreeUtil.getChildOfType(this, DLanguageIdentifier.class);
            }
            @Nullable
            public PsiElement getKW_FUNCTION() {
                return findChildByType(KW_FUNCTION);
            }
        
            @Nullable
            public PsiElement getKW_DELEGATE() {
                return findChildByType(KW_DELEGATE);
            }
        
            @Nullable
            public PsiElement getOP_LAMBDA_ARROW() {
                return findChildByType(OP_LAMBDA_ARROW);
            }
        
            @Nullable
            public DLanguageAssignExpression getAssignExpression() {
                return PsiTreeUtil.getChildOfType(this, DLanguageAssignExpression.class);
            }
            @Nullable
            public DLanguageParameters getParameters() {
                return PsiTreeUtil.getChildOfType(this, DLanguageParameters.class);
            }
                @NotNull
                public List<DLanguageFunctionAttribute> getFunctionAttributes() {
                    return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageFunctionAttribute.class);
                }
}