

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


public class DLanguageType_2Impl extends ASTWrapperPsiElement implements DLanguageType_2{
       public DLanguageType_2Impl (ASTNode node){
               super(node);
       }
       public void accept(@NotNull DLanguageVisitor visitor){
           visitor.visitType_2(this);
       }
       public void accept(@NotNull PsiElementVisitor visitor){
           if(visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
           else super.accept(visitor);
       }

            @Nullable
            public DLanguageSymbol getSymbol() {
                return PsiTreeUtil.getChildOfType(this, DLanguageSymbol.class);
            }
            @Nullable
            public DLanguageTypeofExpression getTypeofExpression() {
                return PsiTreeUtil.getChildOfType(this, DLanguageTypeofExpression.class);
            }
            @Nullable
            public DLanguageTypeConstructor getTypeConstructor() {
                return PsiTreeUtil.getChildOfType(this, DLanguageTypeConstructor.class);
            }
            @Nullable
            public DLanguageVector getVector() {
                return PsiTreeUtil.getChildOfType(this, DLanguageVector.class);
            }
            @Nullable
            public DLanguageType getType() {
                return PsiTreeUtil.getChildOfType(this, DLanguageType.class);
            }
            @Nullable
            public DLanguageIdentifierOrTemplateChain getIdentifierOrTemplateChain() {
                return PsiTreeUtil.getChildOfType(this, DLanguageIdentifierOrTemplateChain.class);
            }
            @Nullable
            public PsiElement getOP_DOT() {
                return findChildByType(OP_DOT);
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