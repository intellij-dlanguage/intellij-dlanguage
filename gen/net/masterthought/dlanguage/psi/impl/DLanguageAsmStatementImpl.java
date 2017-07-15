

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


public class DLanguageAsmStatementImpl extends ASTWrapperPsiElement implements DLanguageAsmStatement{
       public DLanguageAsmStatementImpl (ASTNode node){
               super(node);
       }
       public void accept(@NotNull DLanguageVisitor visitor){
           visitor.visitAsmStatement(this);
       }
       public void accept(@NotNull PsiElementVisitor visitor){
           if(visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
           else super.accept(visitor);
       }

            @Nullable
            public PsiElement getKW_ASM() {
                return findChildByType(KW_ASM);
            }
        
            @Nullable
            public PsiElement getOP_BRACES_LEFT() {
                return findChildByType(OP_BRACES_LEFT);
            }
        
            @Nullable
            public PsiElement getOP_BRACES_RIGHT() {
                return findChildByType(OP_BRACES_RIGHT);
            }
        
                @NotNull
                public List<DLanguageAsmInstruction> getAsmInstructions() {
                    return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageAsmInstruction.class);
                }
}