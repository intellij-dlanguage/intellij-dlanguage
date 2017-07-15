

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


public class DLanguageFunctionAttributeImpl extends ASTWrapperPsiElement implements DLanguageFunctionAttribute{
       public DLanguageFunctionAttributeImpl (ASTNode node){
               super(node);
       }
       public void accept(@NotNull DLanguageVisitor visitor){
           visitor.visitFunctionAttribute(this);
       }
       public void accept(@NotNull PsiElementVisitor visitor){
           if(visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
           else super.accept(visitor);
       }

            @Nullable
            public DLanguageAtAttribute getAtAttribute() {
                return PsiTreeUtil.getChildOfType(this, DLanguageAtAttribute.class);
            }
            @Nullable
            public PsiElement getKW_PURE() {
                return findChildByType(KW_PURE);
            }
        
            @Nullable
            public PsiElement getKW_NOTHROW() {
                return findChildByType(KW_NOTHROW);
            }
        
}