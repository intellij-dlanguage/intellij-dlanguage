
    package net.masterthought.dlanguage.psi;

    import com.intellij.psi.PsiElement;
    import org.jetbrains.annotations.NotNull;
    import static net.masterthought.dlanguage.psi.DLanguageTypes.*;
    import org.jetbrains.annotations.Nullable;
    import com.intellij.psi.util.PsiTreeUtil;
    import java.util.List;
    import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
    import net.masterthought.dlanguage.psi.interfaces.DCompositeElement;




    public interface DLanguageAsmRelExp extends PsiElement {
            @Nullable
            public DLanguageAsmRelExp getAsmRelExp();
            @Nullable
            public DLanguageAsmShiftExp getAsmShiftExp();
            @Nullable
            public PsiElement getOP_GT_EQ();
        
            @Nullable
            public PsiElement getOP_GT();
        
            @Nullable
            public PsiElement getOP_LESS();
        
            @Nullable
            public PsiElement getOP_LESS_EQ();
        
}