
    package net.masterthought.dlanguage.psi;

    import com.intellij.psi.PsiElement;
    import org.jetbrains.annotations.NotNull;
    import static net.masterthought.dlanguage.psi.DLanguageTypes.*;
    import org.jetbrains.annotations.Nullable;
    import com.intellij.psi.util.PsiTreeUtil;
    import java.util.List;
    import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
    import net.masterthought.dlanguage.psi.interfaces.DCompositeElement;




    public interface DLanguageTypeSpecialization extends PsiElement {
            @Nullable
            public DLanguageType getType();
            @Nullable
            public PsiElement getKW___PARAMETERS();
        
            @Nullable
            public PsiElement getKW_STRUCT();
        
            @Nullable
            public PsiElement getKW_UNION();
        
            @Nullable
            public PsiElement getKW_CLASS();
        
            @Nullable
            public PsiElement getKW_INTERFACE();
        
            @Nullable
            public PsiElement getKW_ENUM();
        
            @Nullable
            public PsiElement getKW_FUNCTION();
        
            @Nullable
            public PsiElement getKW_DELEGATE();
        
            @Nullable
            public PsiElement getKW_SUPER();
        
            @Nullable
            public PsiElement getKW_CONST();
        
            @Nullable
            public PsiElement getKW_IMMUTABLE();
        
            @Nullable
            public PsiElement getKW_INOUT();
        
            @Nullable
            public PsiElement getKW_SHARED();
        
            @Nullable
            public PsiElement getKW_RETURN();
        
}