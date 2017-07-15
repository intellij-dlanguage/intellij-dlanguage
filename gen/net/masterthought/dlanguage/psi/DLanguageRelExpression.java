
    package net.masterthought.dlanguage.psi;

    import com.intellij.psi.PsiElement;
    import org.jetbrains.annotations.NotNull;
    import static net.masterthought.dlanguage.psi.DLanguageTypes.*;
    import org.jetbrains.annotations.Nullable;
    import com.intellij.psi.util.PsiTreeUtil;
    import java.util.List;
    import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
    import net.masterthought.dlanguage.psi.interfaces.DCompositeElement;




    public interface DLanguageRelExpression extends PsiElement {
            @Nullable
            public DLanguageRelExpression getRelExpression();
            @Nullable
            public DLanguageShiftExpression getShiftExpression();
            @Nullable
            public PsiElement getOP_GT();
        
            @Nullable
            public PsiElement getOP_GT_EQ();
        
            @Nullable
            public PsiElement getOP_LESS();
        
            @Nullable
            public PsiElement getOP_LESS_EQ();
        
            @Nullable
            public PsiElement getOP_LESS_GR();
        
            @Nullable
            public PsiElement getOP_LESS_GR_EQ();
        
            @Nullable
            public PsiElement getOP_NOT_GR();
        
            @Nullable
            public PsiElement getOP_NOT_GR_EQ();
        
            @Nullable
            public PsiElement getOP_NOT_LESS();
        
            @Nullable
            public PsiElement getOP_NOT_LESS_EQ();
        
            @Nullable
            public PsiElement getOP_UNORD();
        
            @Nullable
            public PsiElement getOP_UNORD_EQ();
        
}