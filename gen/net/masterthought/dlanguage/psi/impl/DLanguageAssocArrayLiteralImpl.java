

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


public class DLanguageAssocArrayLiteralImpl extends ASTWrapperPsiElement implements DLanguageAssocArrayLiteral{
       public DLanguageAssocArrayLiteralImpl (ASTNode node){
               super(node);
       }
       public void accept(@NotNull DLanguageVisitor visitor){
           visitor.visitAssocArrayLiteral(this);
       }
       public void accept(@NotNull PsiElementVisitor visitor){
           if(visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
           else super.accept(visitor);
       }

            @Nullable
            public DLanguageKeyValuePairs getKeyValuePairs() {
                return PsiTreeUtil.getChildOfType(this, DLanguageKeyValuePairs.class);
            }
            @Nullable
            public PsiElement getOP_BRACKET_RIGHT() {
                return findChildByType(OP_BRACKET_RIGHT);
            }
        
            @Nullable
            public PsiElement getOP_BRACKET_LEFT() {
                return findChildByType(OP_BRACKET_LEFT);
            }
        
}