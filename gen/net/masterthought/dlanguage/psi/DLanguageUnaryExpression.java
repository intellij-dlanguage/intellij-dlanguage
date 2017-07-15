
    package net.masterthought.dlanguage.psi;

    import com.intellij.psi.PsiElement;
    import org.jetbrains.annotations.NotNull;
    import static net.masterthought.dlanguage.psi.DLanguageTypes.*;
    import org.jetbrains.annotations.Nullable;
    import com.intellij.psi.util.PsiTreeUtil;
    import java.util.List;
    import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
    import net.masterthought.dlanguage.psi.interfaces.DCompositeElement;




    public interface DLanguageUnaryExpression extends PsiElement {
            @Nullable
            public DLanguagePrimaryExpression getPrimaryExpression();
            @Nullable
            public DLanguageFunctionCallExpression getFunctionCallExpression();
            @Nullable
            public DLanguageUnaryExpression getUnaryExpression();
            @Nullable
            public DLanguageNewExpression getNewExpression();
            @Nullable
            public DLanguageDeleteExpression getDeleteExpression();
            @Nullable
            public DLanguageCastExpression getCastExpression();
            @Nullable
            public DLanguageAssertExpression getAssertExpression();
            @Nullable
            public DLanguageIdentifierOrTemplateInstance getIdentifierOrTemplateInstance();
            @Nullable
            public PsiElement getOP_PAR_RIGHT();
        
            @Nullable
            public PsiElement getOP_PAR_LEFT();
        
            @Nullable
            public DLanguageSliceExpression getSliceExpression();
            @Nullable
            public DLanguageIndexExpression getIndexExpression();
            @Nullable
            public DLanguageType getType();
            @Nullable
            public PsiElement getOP_DOT();
        
            @Nullable
            public PsiElement getOP_AND();
        
            @Nullable
            public PsiElement getOP_ASTERISK();
        
            @Nullable
            public PsiElement getOP_MINUS();
        
            @Nullable
            public PsiElement getOP_MINUS_MINUS();
        
            @Nullable
            public PsiElement getOP_NOT();
        
            @Nullable
            public PsiElement getOP_PLUS();
        
            @Nullable
            public PsiElement getOP_PLUS_PLUS();
        
            @Nullable
            public PsiElement getOP_TILDA();
        
}