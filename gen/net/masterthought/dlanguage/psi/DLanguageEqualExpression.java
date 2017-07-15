
    package net.masterthought.dlanguage.psi;

    import com.intellij.psi.PsiElement;
    import org.jetbrains.annotations.NotNull;
    import static net.masterthought.dlanguage.psi.DLanguageTypes.*;
    import org.jetbrains.annotations.Nullable;
    import com.intellij.psi.util.PsiTreeUtil;
    import java.util.List;
    import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
    import net.masterthought.dlanguage.psi.interfaces.DCompositeElement;




    public interface DLanguageEqualExpression extends PsiElement {
                @NotNull
                public List<DLanguageShiftExpression> getShiftExpressions();
            @Nullable
            public PsiElement getOP_EQ_EQ();
        
            @Nullable
            public PsiElement getOP_NOT_EQ();
        
}