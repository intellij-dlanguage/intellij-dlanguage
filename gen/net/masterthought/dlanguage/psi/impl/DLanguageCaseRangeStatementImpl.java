

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


public class DLanguageCaseRangeStatementImpl extends ASTWrapperPsiElement implements DLanguageCaseRangeStatement{
       public DLanguageCaseRangeStatementImpl (ASTNode node){
               super(node);
       }
       public void accept(@NotNull DLanguageVisitor visitor){
           visitor.visitCaseRangeStatement(this);
       }
       public void accept(@NotNull PsiElementVisitor visitor){
           if(visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
           else super.accept(visitor);
       }

                @NotNull
                public List<PsiElement> getKW_CASEs() {
                    return findChildrenByType(KW_CASE);
                }
            
            @Nullable
            public PsiElement getOP_TRIPLEDOT() {
                return findChildByType(OP_TRIPLEDOT);
            }
        
                @NotNull
                public List<PsiElement> getOP_COLONs() {
                    return findChildrenByType(OP_COLON);
                }
            
            @Nullable
            public DLanguageAssignExpression getAssignExpression() {
                return PsiTreeUtil.getChildOfType(this, DLanguageAssignExpression.class);
            }
            @Nullable
            public DLanguageDeclarationsAndStatements getDeclarationsAndStatements() {
                return PsiTreeUtil.getChildOfType(this, DLanguageDeclarationsAndStatements.class);
            }
}