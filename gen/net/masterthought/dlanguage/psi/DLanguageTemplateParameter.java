
    package net.masterthought.dlanguage.psi;

    import com.intellij.psi.PsiElement;
    import org.jetbrains.annotations.NotNull;
    import static net.masterthought.dlanguage.psi.DLanguageTypes.*;
    import org.jetbrains.annotations.Nullable;
    import com.intellij.psi.util.PsiTreeUtil;
    import java.util.List;
    import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
    import net.masterthought.dlanguage.psi.interfaces.DCompositeElement;




    public interface DLanguageTemplateParameter extends PsiElement , DNamedElement{
            @Nullable
            public DLanguageTemplateAliasParameter getTemplateAliasParameter();
            @Nullable
            public DLanguageTemplateTupleParameter getTemplateTupleParameter();
            @Nullable
            public DLanguageTemplateTypeParameter getTemplateTypeParameter();
            @Nullable
            public DLanguageTemplateThisParameter getTemplateThisParameter();
            @Nullable
            public DLanguageTemplateValueParameter getTemplateValueParameter();
}