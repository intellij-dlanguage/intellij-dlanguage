

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


public class DLanguageFinalSwitchStatementImpl extends ASTWrapperPsiElement implements DLanguageFinalSwitchStatement{
       public DLanguageFinalSwitchStatementImpl (ASTNode node){
               super(node);
       }
       public void accept(@NotNull DLanguageVisitor visitor){
           visitor.visitFinalSwitchStatement(this);
       }
       public void accept(@NotNull PsiElementVisitor visitor){
           if(visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
           else super.accept(visitor);
       }

            @Nullable
            public PsiElement getKW_FINAL() {
                return findChildByType(KW_FINAL);
            }
        
            @Nullable
            public DLanguageSwitchStatement getSwitchStatement() {
                return PsiTreeUtil.getChildOfType(this, DLanguageSwitchStatement.class);
            }
}