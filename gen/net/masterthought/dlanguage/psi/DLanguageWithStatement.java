
    package net.masterthought.dlanguage.psi;

    import com.intellij.psi.PsiElement;
    import org.jetbrains.annotations.NotNull;
    import static net.masterthought.dlanguage.psi.DLanguageTypes.*;
    import org.jetbrains.annotations.Nullable;
    import com.intellij.psi.util.PsiTreeUtil;
    import java.util.List;
    import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
    import net.masterthought.dlanguage.psi.interfaces.DCompositeElement;




    public interface DLanguageWithStatement extends PsiElement {
            @Nullable
            public PsiElement getKW_WITH();
        
            @Nullable
            public DLanguageExpression getExpression();
            @Nullable
            public PsiElement getOP_PAR_RIGHT();
        
            @Nullable
            public PsiElement getOP_PAR_LEFT();
        
            @Nullable
            public DLanguageStatementNoCaseNoDefault getStatementNoCaseNoDefault();
}