
    package net.masterthought.dlanguage.psi;

    import com.intellij.psi.PsiElement;
    import org.jetbrains.annotations.NotNull;
    import static net.masterthought.dlanguage.psi.DLanguageTypes.*;
    import org.jetbrains.annotations.Nullable;
    import com.intellij.psi.util.PsiTreeUtil;
    import java.util.List;
    import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
    import net.masterthought.dlanguage.psi.interfaces.DCompositeElement;




    public interface DLanguageForStatement extends PsiElement {
                @NotNull
                public List<DLanguageDeclarationOrStatement> getDeclarationOrStatements();
                @NotNull
                public List<DLanguageExpression> getExpressions();
            @Nullable
            public PsiElement getOP_BRACES_RIGHT();
        
            @Nullable
            public PsiElement getOP_BRACES_LEFT();
        
            @Nullable
            public PsiElement getKW_FOR();
        
            @Nullable
            public PsiElement getOP_SCOLON();
        
}