

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


public class DLanguageOperandsImpl extends ASTWrapperPsiElement implements DLanguageOperands{
       public DLanguageOperandsImpl (ASTNode node){
               super(node);
       }
       public void accept(@NotNull DLanguageVisitor visitor){
           visitor.visitOperands(this);
       }
       public void accept(@NotNull PsiElementVisitor visitor){
           if(visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
           else super.accept(visitor);
       }

            @Nullable
            public PsiElement getOP_COMMA() {
                return findChildByType(OP_COMMA);
            }
        
            @Nullable
            public DLanguageOperands getOperands() {
                return PsiTreeUtil.getChildOfType(this, DLanguageOperands.class);
            }
            @Nullable
            public DLanguageAsmExp getAsmExp() {
                return PsiTreeUtil.getChildOfType(this, DLanguageAsmExp.class);
            }
}