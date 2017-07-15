

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


public class DLanguageTypeSpecializationImpl extends ASTWrapperPsiElement implements DLanguageTypeSpecialization{
       public DLanguageTypeSpecializationImpl (ASTNode node){
               super(node);
       }
       public void accept(@NotNull DLanguageVisitor visitor){
           visitor.visitTypeSpecialization(this);
       }
       public void accept(@NotNull PsiElementVisitor visitor){
           if(visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
           else super.accept(visitor);
       }

            @Nullable
            public DLanguageType getType() {
                return PsiTreeUtil.getChildOfType(this, DLanguageType.class);
            }
            @Nullable
            public PsiElement getKW___PARAMETERS() {
                return findChildByType(KW___PARAMETERS);
            }
        
            @Nullable
            public PsiElement getKW_STRUCT() {
                return findChildByType(KW_STRUCT);
            }
        
            @Nullable
            public PsiElement getKW_UNION() {
                return findChildByType(KW_UNION);
            }
        
            @Nullable
            public PsiElement getKW_CLASS() {
                return findChildByType(KW_CLASS);
            }
        
            @Nullable
            public PsiElement getKW_INTERFACE() {
                return findChildByType(KW_INTERFACE);
            }
        
            @Nullable
            public PsiElement getKW_ENUM() {
                return findChildByType(KW_ENUM);
            }
        
            @Nullable
            public PsiElement getKW_FUNCTION() {
                return findChildByType(KW_FUNCTION);
            }
        
            @Nullable
            public PsiElement getKW_DELEGATE() {
                return findChildByType(KW_DELEGATE);
            }
        
            @Nullable
            public PsiElement getKW_SUPER() {
                return findChildByType(KW_SUPER);
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
            public PsiElement getKW_RETURN() {
                return findChildByType(KW_RETURN);
            }
        
}