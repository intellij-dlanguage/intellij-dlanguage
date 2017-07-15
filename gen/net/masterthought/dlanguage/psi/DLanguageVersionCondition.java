
    package net.masterthought.dlanguage.psi;

    import com.intellij.psi.PsiElement;
    import org.jetbrains.annotations.NotNull;
    import static net.masterthought.dlanguage.psi.DLanguageTypes.*;
    import org.jetbrains.annotations.Nullable;
    import com.intellij.psi.util.PsiTreeUtil;
    import java.util.List;
    import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
    import net.masterthought.dlanguage.psi.interfaces.DCompositeElement;




    public interface DLanguageVersionCondition extends PsiElement {
            @Nullable
            public PsiElement getKW_VERSION();
        
            @Nullable
            public PsiElement getKW_UNITTEST();
        
            @Nullable
            public PsiElement getKW_ASSERT();
        
            @Nullable
            public PsiElement getINTEGER_LITERAL();
        
            @Nullable
            public DLanguageIdentifier getIdentifier();
            @Nullable
            public PsiElement getOP_PAR_RIGHT();
        
            @Nullable
            public PsiElement getOP_PAR_LEFT();
        
}