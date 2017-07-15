
    package net.masterthought.dlanguage.psi;

    import com.intellij.psi.PsiElement;
    import org.jetbrains.annotations.NotNull;
    import static net.masterthought.dlanguage.psi.DLanguageTypes.*;
    import org.jetbrains.annotations.Nullable;
    import com.intellij.psi.util.PsiTreeUtil;
    import java.util.List;
    import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
    import net.masterthought.dlanguage.psi.interfaces.DCompositeElement;




    public interface DLanguageAsmXorExp extends PsiElement {
            @Nullable
            public DLanguageAsmXorExp getAsmXorExp();
            @Nullable
            public DLanguageAsmAndExp getAsmAndExp();
            @Nullable
            public PsiElement getOP_XOR();
        
}