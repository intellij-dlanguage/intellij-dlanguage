

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


public class DLanguageTypeConstructorImpl extends ASTWrapperPsiElement implements DLanguageTypeConstructor{
       public DLanguageTypeConstructorImpl (ASTNode node){
               super(node);
       }
       public void accept(@NotNull DLanguageVisitor visitor){
           visitor.visitTypeConstructor(this);
       }
       public void accept(@NotNull PsiElementVisitor visitor){
           if(visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
           else super.accept(visitor);
       }

            @Nullable
            public PsiElement getKW_CONST() {
                return findChildByType(KW_CONST);
            }
        
            @Nullable
            public PsiElement getKW_IMMUTABLE() {
                return findChildByType(KW_IMMUTABLE);
            }
        
            @Nullable
            public PsiElement getKW_INOUT() {
                return findChildByType(KW_INOUT);
            }
        
            @Nullable
            public PsiElement getKW_SHARED() {
                return findChildByType(KW_SHARED);
            }
        
            @Nullable
            public PsiElement getKW_SCOPE() {
                return findChildByType(KW_SCOPE);
            }
        
}