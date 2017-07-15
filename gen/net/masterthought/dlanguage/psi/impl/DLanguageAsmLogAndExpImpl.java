

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


public class DLanguageAsmLogAndExpImpl extends ASTWrapperPsiElement implements DLanguageAsmLogAndExp{
       public DLanguageAsmLogAndExpImpl (ASTNode node){
               super(node);
       }
       public void accept(@NotNull DLanguageVisitor visitor){
           visitor.visitAsmLogAndExp(this);
       }
       public void accept(@NotNull PsiElementVisitor visitor){
           if(visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
           else super.accept(visitor);
       }

            @Nullable
            public DLanguageAsmLogAndExp getAsmLogAndExp() {
                return PsiTreeUtil.getChildOfType(this, DLanguageAsmLogAndExp.class);
            }
            @Nullable
            public DLanguageAsmOrExp getAsmOrExp() {
                return PsiTreeUtil.getChildOfType(this, DLanguageAsmOrExp.class);
            }
            @Nullable
            public PsiElement getOP_BOOL_AND() {
                return findChildByType(OP_BOOL_AND);
            }
        
}