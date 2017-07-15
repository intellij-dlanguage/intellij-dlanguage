
    package net.masterthought.dlanguage.psi;

    import com.intellij.psi.PsiElement;
    import org.jetbrains.annotations.NotNull;
    import static net.masterthought.dlanguage.psi.DLanguageTypes.*;
    import org.jetbrains.annotations.Nullable;
    import com.intellij.psi.util.PsiTreeUtil;
    import java.util.List;
    import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
    import net.masterthought.dlanguage.psi.interfaces.DCompositeElement;




    public interface DLanguageCastExpression extends PsiElement {
            @Nullable
            public DLanguageCastQualifier getCastQualifier();
            @Nullable
            public DLanguageType getType();
            @Nullable
            public DLanguageUnaryExpression getUnaryExpression();
            @Nullable
            public PsiElement getKW_CAST();
        
            @Nullable
            public PsiElement getOP_PAR_LEFT();
        
            @Nullable
            public PsiElement getOP_PAR_RIGHT();
        
}