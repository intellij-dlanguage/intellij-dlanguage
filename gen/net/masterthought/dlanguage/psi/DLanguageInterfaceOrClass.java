
    package net.masterthought.dlanguage.psi;

    import com.intellij.psi.PsiElement;
    import org.jetbrains.annotations.NotNull;
    import static net.masterthought.dlanguage.psi.DLanguageTypes.*;
    import org.jetbrains.annotations.Nullable;
    import com.intellij.psi.util.PsiTreeUtil;
    import java.util.List;
    import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
    import net.masterthought.dlanguage.psi.interfaces.DCompositeElement;




    public interface DLanguageInterfaceOrClass extends PsiElement , DNamedElement{
            @Nullable
            public DLanguageIdentifier getIdentifier();
            @Nullable
            public DLanguageTemplateParameters getTemplateParameters();
            @Nullable
            public PsiElement getOP_COLON();
        
            @Nullable
            public DLanguageStructBody getStructBody();
            @Nullable
            public DLanguageConstraint getConstraint();
            @Nullable
            public DLanguageBaseClassList getBaseClassList();
}