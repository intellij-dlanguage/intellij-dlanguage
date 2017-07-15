

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


public class DLanguageConditionalStatementImpl extends ASTWrapperPsiElement implements DLanguageConditionalStatement{
       public DLanguageConditionalStatementImpl (ASTNode node){
               super(node);
       }
       public void accept(@NotNull DLanguageVisitor visitor){
           visitor.visitConditionalStatement(this);
       }
       public void accept(@NotNull PsiElementVisitor visitor){
           if(visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
           else super.accept(visitor);
       }

            @Nullable
            public DLanguageCompileCondition getCompileCondition() {
                return PsiTreeUtil.getChildOfType(this, DLanguageCompileCondition.class);
            }
                @NotNull
                public List<DLanguageDeclarationOrStatement> getDeclarationOrStatements() {
                    return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageDeclarationOrStatement.class);
                }
            @Nullable
            public PsiElement getKW_ELSE() {
                return findChildByType(KW_ELSE);
            }
        
}