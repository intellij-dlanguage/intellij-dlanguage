
    package net.masterthought.dlanguage.psi;

    import com.intellij.psi.PsiElement;
    import org.jetbrains.annotations.NotNull;
    import static net.masterthought.dlanguage.psi.DLanguageTypes.*;
    import org.jetbrains.annotations.Nullable;
    import com.intellij.psi.util.PsiTreeUtil;
    import java.util.List;
    import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
    import net.masterthought.dlanguage.psi.interfaces.DCompositeElement;




    public interface DLanguageTemplateValueParameterDefault extends PsiElement {
            @Nullable
            public PsiElement getOP_EQ();
        
            @Nullable
            public DLanguageAssignExpression getAssignExpression();
            @Nullable
            public PsiElement getKW___FILE__();
        
            @Nullable
            public PsiElement getKW___FUNCTION__();
        
            @Nullable
            public PsiElement getKW___LINE__();
        
            @Nullable
            public PsiElement getKW___MODULE__();
        
            @Nullable
            public PsiElement getKW___PRETTY_FUNCTION__();
        
}