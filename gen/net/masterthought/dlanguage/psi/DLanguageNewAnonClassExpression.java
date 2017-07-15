
    package net.masterthought.dlanguage.psi;

    import com.intellij.psi.PsiElement;
    import org.jetbrains.annotations.NotNull;
    import static net.masterthought.dlanguage.psi.DLanguageTypes.*;
    import org.jetbrains.annotations.Nullable;
    import com.intellij.psi.util.PsiTreeUtil;
    import java.util.List;
    import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
    import net.masterthought.dlanguage.psi.interfaces.DCompositeElement;




    public interface DLanguageNewAnonClassExpression extends PsiElement {
            @Nullable
            public PsiElement getKW_NEW();
        
            @Nullable
            public PsiElement getKW_CLASS();
        
                @NotNull
                public List<DLanguageArguments> getArgumentss();
            @Nullable
            public DLanguageBaseClassList getBaseClassList();
            @Nullable
            public DLanguageStructBody getStructBody();
}