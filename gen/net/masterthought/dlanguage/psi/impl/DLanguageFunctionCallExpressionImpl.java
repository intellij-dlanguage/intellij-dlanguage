

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


public class DLanguageFunctionCallExpressionImpl extends ASTWrapperPsiElement implements DLanguageFunctionCallExpression{
       public DLanguageFunctionCallExpressionImpl (ASTNode node){
               super(node);
       }
       public void accept(@NotNull DLanguageVisitor visitor){
           visitor.visitFunctionCallExpression(this);
       }
       public void accept(@NotNull PsiElementVisitor visitor){
           if(visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
           else super.accept(visitor);
       }

            @Nullable
            public DLanguageType getType() {
                return PsiTreeUtil.getChildOfType(this, DLanguageType.class);
            }
            @Nullable
            public DLanguageArguments getArguments() {
                return PsiTreeUtil.getChildOfType(this, DLanguageArguments.class);
            }
            @Nullable
            public DLanguageUnaryExpression getUnaryExpression() {
                return PsiTreeUtil.getChildOfType(this, DLanguageUnaryExpression.class);
            }
            @Nullable
            public DLanguageTemplateArguments getTemplateArguments() {
                return PsiTreeUtil.getChildOfType(this, DLanguageTemplateArguments.class);
            }
}