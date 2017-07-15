
    package net.masterthought.dlanguage.psi;

    import com.intellij.psi.PsiElement;
    import org.jetbrains.annotations.NotNull;
    import static net.masterthought.dlanguage.psi.DLanguageTypes.*;
    import org.jetbrains.annotations.Nullable;
    import com.intellij.psi.util.PsiTreeUtil;
    import java.util.List;
    import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
    import net.masterthought.dlanguage.psi.interfaces.DCompositeElement;




    public interface DLanguageGotoStatement extends PsiElement {
            @Nullable
            public DLanguageIdentifier getIdentifier();
            @Nullable
            public DLanguageExpression getExpression();
            @Nullable
            public PsiElement getKW_DEFAULT();
        
            @Nullable
            public PsiElement getKW_CASE();
        
            @Nullable
            public PsiElement getKW_GOTO();
        
            @Nullable
            public PsiElement getOP_SCOLON();
        
}