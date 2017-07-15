
    package net.masterthought.dlanguage.psi;

    import com.intellij.psi.PsiElement;
    import org.jetbrains.annotations.NotNull;
    import static net.masterthought.dlanguage.psi.DLanguageTypes.*;
    import org.jetbrains.annotations.Nullable;
    import com.intellij.psi.util.PsiTreeUtil;
    import java.util.List;
    import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
    import net.masterthought.dlanguage.psi.interfaces.DCompositeElement;




    public interface DLanguageTemplateAliasParameter extends PsiElement {
            @Nullable
            public PsiElement getKW_ALIAS();
        
            @Nullable
            public DLanguageIdentifier getIdentifier();
                @NotNull
                public List<DLanguageType> getTypes();
                @NotNull
                public List<DLanguageAssignExpression> getAssignExpressions();
            @Nullable
            public PsiElement getOP_COLON();
        
            @Nullable
            public PsiElement getOP_EQ();
        
}