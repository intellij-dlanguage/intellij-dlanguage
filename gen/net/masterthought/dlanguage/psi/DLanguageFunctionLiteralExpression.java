
    package net.masterthought.dlanguage.psi;

    import com.intellij.psi.PsiElement;
    import org.jetbrains.annotations.NotNull;
    import static net.masterthought.dlanguage.psi.DLanguageTypes.*;
    import org.jetbrains.annotations.Nullable;
    import com.intellij.psi.util.PsiTreeUtil;
    import java.util.List;
    import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
    import net.masterthought.dlanguage.psi.interfaces.DCompositeElement;




    public interface DLanguageFunctionLiteralExpression extends PsiElement {
            @Nullable
            public DLanguageType getType();
            @Nullable
            public PsiElement getKW_FUNCTION();
        
            @Nullable
            public PsiElement getKW_DELEGATE();
        
            @Nullable
            public DLanguageParameters getParameters();
                @NotNull
                public List<DLanguageFunctionAttribute> getFunctionAttributes();
            @Nullable
            public DLanguageFunctionBody getFunctionBody();
}