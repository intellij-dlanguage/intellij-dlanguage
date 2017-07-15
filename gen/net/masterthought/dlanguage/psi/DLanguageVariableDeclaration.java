
    package net.masterthought.dlanguage.psi;

    import com.intellij.psi.PsiElement;
    import org.jetbrains.annotations.NotNull;
    import static net.masterthought.dlanguage.psi.DLanguageTypes.*;
    import org.jetbrains.annotations.Nullable;
    import com.intellij.psi.util.PsiTreeUtil;
    import java.util.List;
    import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
    import net.masterthought.dlanguage.psi.interfaces.DCompositeElement;




    public interface DLanguageVariableDeclaration extends PsiElement {
            @Nullable
            public DLanguageAutoDeclaration getAutoDeclaration();
            @Nullable
            public PsiElement getOP_SCOLON();
        
            @Nullable
            public DLanguageType getType();
                @NotNull
                public List<DLanguageDeclarator> getDeclarators();
                @NotNull
                public List<PsiElement> getOP_COMMAs();
            
            @Nullable
            public PsiElement getOP_EQ();
        
            @Nullable
            public DLanguageFunctionBody getFunctionBody();
}