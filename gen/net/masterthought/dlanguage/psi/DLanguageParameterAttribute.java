
    package net.masterthought.dlanguage.psi;

    import com.intellij.psi.PsiElement;
    import org.jetbrains.annotations.NotNull;
    import static net.masterthought.dlanguage.psi.DLanguageTypes.*;
    import org.jetbrains.annotations.Nullable;
    import com.intellij.psi.util.PsiTreeUtil;
    import java.util.List;
    import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
    import net.masterthought.dlanguage.psi.interfaces.DCompositeElement;




    public interface DLanguageParameterAttribute extends PsiElement {
            @Nullable
            public PsiElement getKW_FINAL();
        
            @Nullable
            public PsiElement getKW_IN();
        
            @Nullable
            public PsiElement getKW_LAZY();
        
            @Nullable
            public PsiElement getKW_OUT();
        
            @Nullable
            public PsiElement getKW_REF();
        
            @Nullable
            public PsiElement getKW_SCOPE();
        
            @Nullable
            public PsiElement getKW_AUTO();
        
            @Nullable
            public DLanguageTypeConstructor getTypeConstructor();
}