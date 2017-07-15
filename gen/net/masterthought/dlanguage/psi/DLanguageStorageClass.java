
    package net.masterthought.dlanguage.psi;

    import com.intellij.psi.PsiElement;
    import org.jetbrains.annotations.NotNull;
    import static net.masterthought.dlanguage.psi.DLanguageTypes.*;
    import org.jetbrains.annotations.Nullable;
    import com.intellij.psi.util.PsiTreeUtil;
    import java.util.List;
    import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
    import net.masterthought.dlanguage.psi.interfaces.DCompositeElement;




    public interface DLanguageStorageClass extends PsiElement {
            @Nullable
            public DLanguageAtAttribute getAtAttribute();
            @Nullable
            public DLanguageDeprecated getDeprecated();
            @Nullable
            public DLanguageAlignAttribute getAlignAttribute();
            @Nullable
            public DLanguageLinkageAttribute getLinkageAttribute();
            @Nullable
            public PsiElement getKW_SYNCHRONIZED();
        
            @Nullable
            public DLanguageTypeConstructor getTypeConstructor();
            @Nullable
            public PsiElement getKW_ABSTRACT();
        
            @Nullable
            public PsiElement getKW_AUTO();
        
            @Nullable
            public PsiElement getKW_ENUM();
        
            @Nullable
            public PsiElement getKW_EXTERN();
        
            @Nullable
            public PsiElement getKW_FINAL();
        
            @Nullable
            public PsiElement getKW_VIRTUAL();
        
            @Nullable
            public PsiElement getKW_NOTHROW();
        
            @Nullable
            public PsiElement getKW_OVERRIDE();
        
            @Nullable
            public PsiElement getKW_PURE();
        
            @Nullable
            public PsiElement getKW_REF();
        
            @Nullable
            public PsiElement getKW___GSHARED();
        
            @Nullable
            public PsiElement getKW_SCOPE();
        
            @Nullable
            public PsiElement getKW_STATIC();
        
}