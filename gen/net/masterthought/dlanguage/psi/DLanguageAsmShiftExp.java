
    package net.masterthought.dlanguage.psi;

    import com.intellij.psi.PsiElement;
    import org.jetbrains.annotations.NotNull;
    import static net.masterthought.dlanguage.psi.DLanguageTypes.*;
    import org.jetbrains.annotations.Nullable;
    import com.intellij.psi.util.PsiTreeUtil;
    import java.util.List;
    import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
    import net.masterthought.dlanguage.psi.interfaces.DCompositeElement;




    public interface DLanguageAsmShiftExp extends PsiElement {
            @Nullable
            public DLanguageAsmShiftExp getAsmShiftExp();
            @Nullable
            public DLanguageAsmAddExp getAsmAddExp();
            @Nullable
            public PsiElement getOP_SH_LEFT();
        
            @Nullable
            public PsiElement getOP_SH_RIGHT();
        
            @Nullable
            public PsiElement getOP_USH_RIGHT();
        
}