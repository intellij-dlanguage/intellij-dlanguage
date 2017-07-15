

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


public class DLanguageForeachStatementImpl extends ASTWrapperPsiElement implements DLanguageForeachStatement{
       public DLanguageForeachStatementImpl (ASTNode node){
               super(node);
       }
       public void accept(@NotNull DLanguageVisitor visitor){
           visitor.visitForeachStatement(this);
       }
       public void accept(@NotNull PsiElementVisitor visitor){
           if(visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
           else super.accept(visitor);
       }

            @Nullable
            public PsiElement getKW_FOREACH() {
                return findChildByType(KW_FOREACH);
            }
        
            @Nullable
            public PsiElement getKW_FOREACH_REVERSE() {
                return findChildByType(KW_FOREACH_REVERSE);
            }
        
            @Nullable
            public DLanguageDeclarationOrStatement getDeclarationOrStatement() {
                return PsiTreeUtil.getChildOfType(this, DLanguageDeclarationOrStatement.class);
            }
                @NotNull
                public List<DLanguageExpression> getExpressions() {
                    return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageExpression.class);
                }
            @Nullable
            public PsiElement getOP_BRACES_RIGHT() {
                return findChildByType(OP_BRACES_RIGHT);
            }
        
            @Nullable
            public PsiElement getOP_BRACES_LEFT() {
                return findChildByType(OP_BRACES_LEFT);
            }
        
            @Nullable
            public PsiElement getOP_DDOT() {
                return findChildByType(OP_DDOT);
            }
        
            @Nullable
            public DLanguageForeachType getForeachType() {
                return PsiTreeUtil.getChildOfType(this, DLanguageForeachType.class);
            }
            @Nullable
            public DLanguageForeachTypeList getForeachTypeList() {
                return PsiTreeUtil.getChildOfType(this, DLanguageForeachTypeList.class);
            }
            @Nullable
            public PsiElement getOP_SCOLON() {
                return findChildByType(OP_SCOLON);
            }
        
}