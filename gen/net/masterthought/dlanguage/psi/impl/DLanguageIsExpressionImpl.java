

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


public class DLanguageIsExpressionImpl extends ASTWrapperPsiElement implements DLanguageIsExpression{
       public DLanguageIsExpressionImpl (ASTNode node){
               super(node);
       }
       public void accept(@NotNull DLanguageVisitor visitor){
           visitor.visitIsExpression(this);
       }
       public void accept(@NotNull PsiElementVisitor visitor){
           if(visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
           else super.accept(visitor);
       }

            @Nullable
            public PsiElement getOP_PAR_RIGHT() {
                return findChildByType(OP_PAR_RIGHT);
            }
        
            @Nullable
            public PsiElement getOP_PAR_LEFT() {
                return findChildByType(OP_PAR_LEFT);
            }
        
            @Nullable
            public DLanguageType getType() {
                return PsiTreeUtil.getChildOfType(this, DLanguageType.class);
            }
            @Nullable
            public DLanguageIdentifier getIdentifier() {
                return PsiTreeUtil.getChildOfType(this, DLanguageIdentifier.class);
            }
            @Nullable
            public DLanguageTypeSpecialization getTypeSpecialization() {
                return PsiTreeUtil.getChildOfType(this, DLanguageTypeSpecialization.class);
            }
            @Nullable
            public DLanguageTemplateParameterList getTemplateParameterList() {
                return PsiTreeUtil.getChildOfType(this, DLanguageTemplateParameterList.class);
            }
            @Nullable
            public PsiElement getOP_COMMA() {
                return findChildByType(OP_COMMA);
            }
        
            @Nullable
            public PsiElement getOP_COLON() {
                return findChildByType(OP_COLON);
            }
        
            @Nullable
            public PsiElement getOP_EQ() {
                return findChildByType(OP_EQ);
            }
        
            @Nullable
            public PsiElement getKW_IS() {
                return findChildByType(KW_IS);
            }
        
}