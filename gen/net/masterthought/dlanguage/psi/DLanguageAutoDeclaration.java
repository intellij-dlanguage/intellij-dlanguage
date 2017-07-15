
    package net.masterthought.dlanguage.psi;

    import com.intellij.psi.PsiElement;
    import org.jetbrains.annotations.NotNull;
    import static net.masterthought.dlanguage.psi.DLanguageTypes.*;
    import org.jetbrains.annotations.Nullable;
    import com.intellij.psi.util.PsiTreeUtil;
    import java.util.List;
    import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
    import net.masterthought.dlanguage.psi.interfaces.DCompositeElement;




    public interface DLanguageAutoDeclaration extends PsiElement {
            @Nullable
            public DLanguageStorageClass getStorageClass();
                @NotNull
                public List<PsiElement> getOP_COMMAs();
            
            @Nullable
            public PsiElement getOP_SCOLON();
        
                @NotNull
                public List<DLanguageAutoDeclarationPart> getAutoDeclarationParts();
}