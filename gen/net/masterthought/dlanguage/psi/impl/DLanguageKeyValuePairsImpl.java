

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


public class DLanguageKeyValuePairsImpl extends ASTWrapperPsiElement implements DLanguageKeyValuePairs{
       public DLanguageKeyValuePairsImpl (ASTNode node){
               super(node);
       }
       public void accept(@NotNull DLanguageVisitor visitor){
           visitor.visitKeyValuePairs(this);
       }
       public void accept(@NotNull PsiElementVisitor visitor){
           if(visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
           else super.accept(visitor);
       }

                @NotNull
                public List<PsiElement> getOP_COMMAs() {
                    return findChildrenByType(OP_COMMA);
                }
            
                @NotNull
                public List<DLanguageKeyValuePair> getKeyValuePairs() {
                    return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageKeyValuePair.class);
                }
}