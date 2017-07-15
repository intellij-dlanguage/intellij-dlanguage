
    package net.masterthought.dlanguage.psi;

    import com.intellij.psi.PsiElement;
    import org.jetbrains.annotations.NotNull;
    import static net.masterthought.dlanguage.psi.DLanguageTypes.*;
    import org.jetbrains.annotations.Nullable;
    import com.intellij.psi.util.PsiTreeUtil;
    import java.util.List;
    import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
    import net.masterthought.dlanguage.psi.interfaces.DCompositeElement;




    public interface DLanguageAsmExp extends PsiElement {
            @Nullable
            public DLanguageAsmLogOrExp getAsmLogOrExp();
            @Nullable
            public DLanguageAsmExp getAsmExp();
            @Nullable
            public PsiElement getOP_QUEST();
        
            @Nullable
            public PsiElement getOP_COLON();
        
}