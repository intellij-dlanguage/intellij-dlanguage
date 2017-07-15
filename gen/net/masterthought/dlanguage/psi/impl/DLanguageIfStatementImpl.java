

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


public class DLanguageIfStatementImpl extends ASTWrapperPsiElement implements DLanguageIfStatement{
       public DLanguageIfStatementImpl (ASTNode node){
               super(node);
       }
       public void accept(@NotNull DLanguageVisitor visitor){
           visitor.visitIfStatement(this);
       }
       public void accept(@NotNull PsiElementVisitor visitor){
           if(visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
           else super.accept(visitor);
       }

                @NotNull
                public List<DLanguageDeclarationOrStatement> getDeclarationOrStatements() {
                    return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageDeclarationOrStatement.class);
                }
            @Nullable
            public PsiElement getKW_ELSE() {
                return findChildByType(KW_ELSE);
            }
        
            @Nullable
            public PsiElement getKW_IF() {
                return findChildByType(KW_IF);
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
            public DLanguageIfCondition getIfCondition() {
                return PsiTreeUtil.getChildOfType(this, DLanguageIfCondition.class);
            }
}