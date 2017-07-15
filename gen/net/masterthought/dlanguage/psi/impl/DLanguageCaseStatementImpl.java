

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


public class DLanguageCaseStatementImpl extends ASTWrapperPsiElement implements DLanguageCaseStatement{
       public DLanguageCaseStatementImpl (ASTNode node){
               super(node);
       }
       public void accept(@NotNull DLanguageVisitor visitor){
           visitor.visitCaseStatement(this);
       }
       public void accept(@NotNull PsiElementVisitor visitor){
           if(visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
           else super.accept(visitor);
       }

            @Nullable
            public PsiElement getKW_CASE() {
                return findChildByType(KW_CASE);
            }
        
            @Nullable
            public PsiElement getOP_COLON() {
                return findChildByType(OP_COLON);
            }
        
            @Nullable
            public DLanguageArgumentList getArgumentList() {
                return PsiTreeUtil.getChildOfType(this, DLanguageArgumentList.class);
            }
            @Nullable
            public DLanguageDeclarationAndStatement getDeclarationAndStatement() {
                return PsiTreeUtil.getChildOfType(this, DLanguageDeclarationAndStatement.class);
            }
}