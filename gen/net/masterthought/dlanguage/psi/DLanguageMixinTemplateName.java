
    package net.masterthought.dlanguage.psi;

    import com.intellij.psi.PsiElement;
    import org.jetbrains.annotations.NotNull;
    import static net.masterthought.dlanguage.psi.DLanguageTypes.*;
    import org.jetbrains.annotations.Nullable;
    import com.intellij.psi.util.PsiTreeUtil;
    import java.util.List;
    import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
    import net.masterthought.dlanguage.psi.interfaces.DCompositeElement;




    public interface DLanguageMixinTemplateName extends PsiElement {
            @Nullable
            public DLanguageTypeofExpression getTypeofExpression();
            @Nullable
            public DLanguageIdentifierOrTemplateChain getIdentifierOrTemplateChain();
            @Nullable
            public DLanguageSymbol getSymbol();
            @Nullable
            public PsiElement getOP_DOT();
        
}