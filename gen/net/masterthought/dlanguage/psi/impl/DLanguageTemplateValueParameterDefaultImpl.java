

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


public class DLanguageTemplateValueParameterDefaultImpl extends ASTWrapperPsiElement implements DLanguageTemplateValueParameterDefault{
       public DLanguageTemplateValueParameterDefaultImpl (ASTNode node){
               super(node);
       }
       public void accept(@NotNull DLanguageVisitor visitor){
           visitor.visitTemplateValueParameterDefault(this);
       }
       public void accept(@NotNull PsiElementVisitor visitor){
           if(visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
           else super.accept(visitor);
       }

            @Nullable
            public PsiElement getOP_EQ() {
                return findChildByType(OP_EQ);
            }
        
            @Nullable
            public DLanguageAssignExpression getAssignExpression() {
                return PsiTreeUtil.getChildOfType(this, DLanguageAssignExpression.class);
            }
            @Nullable
            public PsiElement getKW___FILE__() {
                return findChildByType(KW___FILE__);
            }
        
            @Nullable
            public PsiElement getKW___FUNCTION__() {
                return findChildByType(KW___FUNCTION__);
            }
        
            @Nullable
            public PsiElement getKW___LINE__() {
                return findChildByType(KW___LINE__);
            }
        
            @Nullable
            public PsiElement getKW___MODULE__() {
                return findChildByType(KW___MODULE__);
            }
        
            @Nullable
            public PsiElement getKW___PRETTY_FUNCTION__() {
                return findChildByType(KW___PRETTY_FUNCTION__);
            }
        
}