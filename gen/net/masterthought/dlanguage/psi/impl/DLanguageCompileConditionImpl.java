

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


public class DLanguageCompileConditionImpl extends ASTWrapperPsiElement implements DLanguageCompileCondition{
       public DLanguageCompileConditionImpl (ASTNode node){
               super(node);
       }
       public void accept(@NotNull DLanguageVisitor visitor){
           visitor.visitCompileCondition(this);
       }
       public void accept(@NotNull PsiElementVisitor visitor){
           if(visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
           else super.accept(visitor);
       }

            @Nullable
            public DLanguageVersionCondition getVersionCondition() {
                return PsiTreeUtil.getChildOfType(this, DLanguageVersionCondition.class);
            }
            @Nullable
            public DLanguageDebugCondition getDebugCondition() {
                return PsiTreeUtil.getChildOfType(this, DLanguageDebugCondition.class);
            }
            @Nullable
            public DLanguageStaticIfCondition getStaticIfCondition() {
                return PsiTreeUtil.getChildOfType(this, DLanguageStaticIfCondition.class);
            }
}