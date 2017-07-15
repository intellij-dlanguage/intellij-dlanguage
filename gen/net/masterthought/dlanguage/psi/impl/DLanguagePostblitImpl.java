

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


public class DLanguagePostblitImpl extends ASTWrapperPsiElement implements DLanguagePostblit{
       public DLanguagePostblitImpl (ASTNode node){
               super(node);
       }
       public void accept(@NotNull DLanguageVisitor visitor){
           visitor.visitPostblit(this);
       }
       public void accept(@NotNull PsiElementVisitor visitor){
           if(visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
           else super.accept(visitor);
       }

            @Nullable
            public DLanguageFunctionBody getFunctionBody() {
                return PsiTreeUtil.getChildOfType(this, DLanguageFunctionBody.class);
            }
            @Nullable
            public PsiElement getOP_SCOLON() {
                return findChildByType(OP_SCOLON);
            }
        
                @NotNull
                public List<PsiElement> getKW_THISs() {
                    return findChildrenByType(KW_THIS);
                }
            
            @Nullable
            public PsiElement getOP_PAR_LEFT() {
                return findChildByType(OP_PAR_LEFT);
            }
        
            @Nullable
            public PsiElement getOP_PAR_RIGHT() {
                return findChildByType(OP_PAR_RIGHT);
            }
        
            @Nullable
            public DLanguageMemberFunctionAttribute getMemberFunctionAttribute() {
                return PsiTreeUtil.getChildOfType(this, DLanguageMemberFunctionAttribute.class);
            }
}