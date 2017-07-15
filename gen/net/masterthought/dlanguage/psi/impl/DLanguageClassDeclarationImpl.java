

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


public class DLanguageClassDeclarationImpl extends ASTWrapperPsiElement implements DLanguageClassDeclaration{
       public DLanguageClassDeclarationImpl (ASTNode node){
               super(node);
       }
       public void accept(@NotNull DLanguageVisitor visitor){
           visitor.visitClassDeclaration(this);
       }
       public void accept(@NotNull PsiElementVisitor visitor){
           if(visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
           else super.accept(visitor);
       }

            @Nullable
            public PsiElement getKW_CLASS() {
                return findChildByType(KW_CLASS);
            }
        
            @Nullable
            public DLanguageIdentifier getIdentifier() {
                return PsiTreeUtil.getChildOfType(this, DLanguageIdentifier.class);
            }
            @Nullable
            public PsiElement getOP_SCOLON() {
                return findChildByType(OP_SCOLON);
            }
        
            @Nullable
            public PsiElement getOP_COLON() {
                return findChildByType(OP_COLON);
            }
        
            @Nullable
            public DLanguageStructBody getStructBody() {
                return PsiTreeUtil.getChildOfType(this, DLanguageStructBody.class);
            }
            @Nullable
            public DLanguageTemplateParameters getTemplateParameters() {
                return PsiTreeUtil.getChildOfType(this, DLanguageTemplateParameters.class);
            }
            @Nullable
            public DLanguageConstraint getConstraint() {
                return PsiTreeUtil.getChildOfType(this, DLanguageConstraint.class);
            }
            @Nullable
            public DLanguageBaseClassList getBaseClassList() {
                return PsiTreeUtil.getChildOfType(this, DLanguageBaseClassList.class);
            }
}