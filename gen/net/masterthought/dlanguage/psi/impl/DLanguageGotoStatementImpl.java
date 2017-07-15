

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


public class DLanguageGotoStatementImpl extends ASTWrapperPsiElement implements DLanguageGotoStatement{
       public DLanguageGotoStatementImpl (ASTNode node){
               super(node);
       }
       public void accept(@NotNull DLanguageVisitor visitor){
           visitor.visitGotoStatement(this);
       }
       public void accept(@NotNull PsiElementVisitor visitor){
           if(visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
           else super.accept(visitor);
       }

            @Nullable
            public DLanguageIdentifier getIdentifier() {
                return PsiTreeUtil.getChildOfType(this, DLanguageIdentifier.class);
            }
            @Nullable
            public DLanguageExpression getExpression() {
                return PsiTreeUtil.getChildOfType(this, DLanguageExpression.class);
            }
            @Nullable
            public PsiElement getKW_DEFAULT() {
                return findChildByType(KW_DEFAULT);
            }
        
            @Nullable
            public PsiElement getKW_CASE() {
                return findChildByType(KW_CASE);
            }
        
            @Nullable
            public PsiElement getKW_GOTO() {
                return findChildByType(KW_GOTO);
            }
        
            @Nullable
            public PsiElement getOP_SCOLON() {
                return findChildByType(OP_SCOLON);
            }
        
}