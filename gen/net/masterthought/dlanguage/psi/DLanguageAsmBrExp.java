
    package net.masterthought.dlanguage.psi;

    import com.intellij.psi.PsiElement;
    import org.jetbrains.annotations.NotNull;
    import static net.masterthought.dlanguage.psi.DLanguageTypes.*;
    import org.jetbrains.annotations.Nullable;
    import com.intellij.psi.util.PsiTreeUtil;
    import java.util.List;
    import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
    import net.masterthought.dlanguage.psi.interfaces.DCompositeElement;




    public interface DLanguageAsmBrExp extends PsiElement {
            @Nullable
            public DLanguageAsmExp getAsmExp();
            @Nullable
            public DLanguageAsmUnaExp getAsmUnaExp();
            @Nullable
            public DLanguageAsmBrExp getAsmBrExp();
            @Nullable
            public PsiElement getOP_BRACKET_RIGHT();
        
            @Nullable
            public PsiElement getOP_BRACKET_LEFT();
        
}