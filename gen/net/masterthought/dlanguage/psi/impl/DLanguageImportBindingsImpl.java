

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


public class DLanguageImportBindingsImpl extends ASTWrapperPsiElement implements DLanguageImportBindings{
       public DLanguageImportBindingsImpl (ASTNode node){
               super(node);
       }
       public void accept(@NotNull DLanguageVisitor visitor){
           visitor.visitImportBindings(this);
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
                public List<DLanguageImportBind> getImportBinds() {
                    return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageImportBind.class);
                }
            @Nullable
            public DLanguageSingleImport getSingleImport() {
                return PsiTreeUtil.getChildOfType(this, DLanguageSingleImport.class);
            }
            @Nullable
            public PsiElement getOP_COLON() {
                return findChildByType(OP_COLON);
            }
        
}