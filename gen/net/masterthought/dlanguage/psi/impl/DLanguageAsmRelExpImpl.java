

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


public class DLanguageAsmRelExpImpl extends ASTWrapperPsiElement implements DLanguageAsmRelExp{
       public DLanguageAsmRelExpImpl (ASTNode node){
               super(node);
       }
       public void accept(@NotNull DLanguageVisitor visitor){
           visitor.visitAsmRelExp(this);
       }
       public void accept(@NotNull PsiElementVisitor visitor){
           if(visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
           else super.accept(visitor);
       }

            @Nullable
            public DLanguageAsmRelExp getAsmRelExp() {
                return PsiTreeUtil.getChildOfType(this, DLanguageAsmRelExp.class);
            }
            @Nullable
            public DLanguageAsmShiftExp getAsmShiftExp() {
                return PsiTreeUtil.getChildOfType(this, DLanguageAsmShiftExp.class);
            }
            @Nullable
            public PsiElement getOP_GT_EQ() {
                return findChildByType(OP_GT_EQ);
            }
        
            @Nullable
            public PsiElement getOP_GT() {
                return findChildByType(OP_GT);
            }
        
            @Nullable
            public PsiElement getOP_LESS() {
                return findChildByType(OP_LESS);
            }
        
            @Nullable
            public PsiElement getOP_LESS_EQ() {
                return findChildByType(OP_LESS_EQ);
            }
        
}