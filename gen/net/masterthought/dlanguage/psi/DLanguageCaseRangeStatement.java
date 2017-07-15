
    package net.masterthought.dlanguage.psi;

    import com.intellij.psi.PsiElement;
    import org.jetbrains.annotations.NotNull;
    import static net.masterthought.dlanguage.psi.DLanguageTypes.*;
    import org.jetbrains.annotations.Nullable;
    import com.intellij.psi.util.PsiTreeUtil;
    import java.util.List;
    import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
    import net.masterthought.dlanguage.psi.interfaces.DCompositeElement;




    public interface DLanguageCaseRangeStatement extends PsiElement {
                @NotNull
                public List<PsiElement> getKW_CASEs();
            
            @Nullable
            public PsiElement getOP_TRIPLEDOT();
        
                @NotNull
                public List<PsiElement> getOP_COLONs();
            
            @Nullable
            public DLanguageAssignExpression getAssignExpression();
            @Nullable
            public DLanguageDeclarationsAndStatements getDeclarationsAndStatements();
}