
    package net.masterthought.dlanguage.psi;

    import com.intellij.psi.PsiElement;
    import org.jetbrains.annotations.NotNull;
    import static net.masterthought.dlanguage.psi.DLanguageTypes.*;
    import org.jetbrains.annotations.Nullable;
    import com.intellij.psi.util.PsiTreeUtil;
    import java.util.List;
    import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
    import net.masterthought.dlanguage.psi.interfaces.DCompositeElement;




    public interface DLanguageAttribute extends PsiElement {
            @Nullable
            public DLanguagePragmaExpression getPragmaExpression();
            @Nullable
            public DLanguageStorageClass getStorageClass();
            @Nullable
            public PsiElement getKW_EXPORT();
        
            @Nullable
            public PsiElement getKW_PACKAGE();
        
            @Nullable
            public PsiElement getKW_PRIVATE();
        
            @Nullable
            public PsiElement getKW_PROTECTED();
        
            @Nullable
            public PsiElement getKW_PUBLIC();
        
}