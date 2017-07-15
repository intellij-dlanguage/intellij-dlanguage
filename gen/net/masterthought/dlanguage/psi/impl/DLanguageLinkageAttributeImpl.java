

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


public class DLanguageLinkageAttributeImpl extends ASTWrapperPsiElement implements DLanguageLinkageAttribute{
       public DLanguageLinkageAttributeImpl (ASTNode node){
               super(node);
       }
       public void accept(@NotNull DLanguageVisitor visitor){
           visitor.visitLinkageAttribute(this);
       }
       public void accept(@NotNull PsiElementVisitor visitor){
           if(visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
           else super.accept(visitor);
       }

            @Nullable
            public DLanguageIdentifierChain getIdentifierChain() {
                return PsiTreeUtil.getChildOfType(this, DLanguageIdentifierChain.class);
            }
            @Nullable
            public DLanguageIdentifier getIdentifier() {
                return PsiTreeUtil.getChildOfType(this, DLanguageIdentifier.class);
            }
            @Nullable
            public PsiElement getOP_PAR_RIGHT() {
                return findChildByType(OP_PAR_RIGHT);
            }
        
            @Nullable
            public PsiElement getOP_PAR_LEFT() {
                return findChildByType(OP_PAR_LEFT);
            }
        
            @Nullable
            public PsiElement getOP_PLUS_PLUS() {
                return findChildByType(OP_PLUS_PLUS);
            }
        
            @Nullable
            public PsiElement getKW_EXTERN() {
                return findChildByType(KW_EXTERN);
            }
        
            @Nullable
            public PsiElement getOP_COMMA() {
                return findChildByType(OP_COMMA);
            }
        
}