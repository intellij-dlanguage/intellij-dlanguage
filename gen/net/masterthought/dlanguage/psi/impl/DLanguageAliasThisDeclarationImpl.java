

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


public class DLanguageAliasThisDeclarationImpl extends ASTWrapperPsiElement implements DLanguageAliasThisDeclaration{
       public DLanguageAliasThisDeclarationImpl (ASTNode node){
               super(node);
       }
       public void accept(@NotNull DLanguageVisitor visitor){
           visitor.visitAliasThisDeclaration(this);
       }
       public void accept(@NotNull PsiElementVisitor visitor){
           if(visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
           else super.accept(visitor);
       }

            @Nullable
            public PsiElement getKW_ALIAS() {
                return findChildByType(KW_ALIAS);
            }
        
            @Nullable
            public DLanguageIdentifier getIdentifier() {
                return PsiTreeUtil.getChildOfType(this, DLanguageIdentifier.class);
            }
            @Nullable
            public PsiElement getKW_THIS() {
                return findChildByType(KW_THIS);
            }
        
            @Nullable
            public PsiElement getOP_SCOLON() {
                return findChildByType(OP_SCOLON);
            }
        
}